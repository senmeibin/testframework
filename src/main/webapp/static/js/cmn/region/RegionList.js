//定义数据一览画面JS类
SysApp.Cmn.RegionList = Class.create();

SysApp.Cmn.RegionList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.autoSearch = false;

        //绑定停用区域按钮
        this.bindEvent("btnLock", 'click', this.onClick_UpdateRecordStatus.bind(this, 8));
        //绑定启用区域按钮
        this.bindEvent("btnUnLock", 'click', this.onClick_UpdateRecordStatus.bind(this, 1));
        //绑定区域批量设置按钮
        this.bindEvent("btnRegionSetting", 'click', this.onClick_ShowRegionSetting.bind(this, 1));
        //UDC同步时，禁用操作按钮
        if (!SysApp.Cmn.isUdcServer && this.isSyncDataPage == "true") {
            this.displayDom("btnLock", false);
            this.displayDom("btnUnLock", false);
            this.displayDom("btnRegionSetting", false);
        }
    },

    //修改区域记录状态
    onClick_UpdateRecordStatus: function (recordStatus) {
        if (this.isEmpty(this.getValue("uid"))) {
            this.showWarningMessage("请先选择区域名称。");
            return false;
        }
        this.showConfirm("确定" + (recordStatus == 1 ? "启用" : "停用") + "【" + this.getText("uid") + "】的全部区域吗？", this.updateRecordStatusAjax.bind(this, recordStatus));
    },

    //修改区域记录状态ajax
    updateRecordStatusAjax: function (recordStatus) {
        var params = "uid=" + this.getValue("uid") + "&recordStatus=" + recordStatus;
        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "updateRegionRecordStatus", this.updateRecordStatusCallback.bind(this), params);
    },

    //提交处理[Callback后处理]
    updateRecordStatusCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.showInfoMessage("区域记录状态修改成功。", this.refreshList.bind(this));
        }
        else {
            this.showErrorMessage("区域记录状态修改失败。");
        }
    },

    //弹出区域批量设置Popup
    onClick_ShowRegionSetting: function () {
        this.RegionSettingIns = SysApp.Cmn.RegionSettingIns;
        this.RegionSettingIns.clearForm();
        this.RegionSettingIns.show();
        this.RegionSettingIns.getList(true);
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "15%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("区域名称", "regionName", "55%", SORT_YES));
        this.colList.push(new ColInfo("区域层级", "regionGrade", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("表示顺序", "dispSeq", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("是否启用", "recordStatus", "8%", SORT_YES, ALIGN_CENTER));

        this.datagrid.useRowNo = false;
        this.datagrid.cssClass = "app-table-list";
    },

    //添加下级地区
    addSubRegion: function (index) {
        var json = this.datagrid.data[index];
        this.inputInstance.parentRegion = json;
        this.goInput();
    },

    //创建列表添加图标
    createAddIcon: function (rowNo) {
        var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='添加下级地区'>";
        html += "<i class='fa fa-plus fa-lg fa-blue fa-width-fixed' onclick='" + this.selfInstance + ".addSubRegion(" + rowNo + ");return false;'></i></a>";
        html += "</span>";
        return html;
    },
    createUpIcon: function (rowNo, color) {
        var html = "<span class='popovers 'data-trigger='hover' data-placement='top' data-content='上移'>";
        html += "<i class='fa fa-arrow-up fa-lg fa-width-fixed fa-" + color + "' onclick='" + this.selfInstance + ".upLink(" + rowNo + ");return false;'></i></a>";
        html += "</span>";
        return html;
    },
    createDownIcon: function (rowNo, color) {
        var html = "<span  class='popovers' data-trigger='hover' data-placement='top' data-content='下移'>";
        html += "<i disabled='disabled'  class='fa fa-arrow-down fa-lg fa-width-fixed fa-" + color + "' onclick='" + this.selfInstance + ".downLink(" + rowNo + ");return false;'></i></a>";
        html += "</span>";
        return html;
    },

    //是否可以上移
    canUp: function (rowData, rowNo) {
        var result = false;
        if (rowNo != 0) {
            for (var i = rowNo - 1; i >= 0; i--) {
                if (this.datagrid.data[i].parentUid == rowData.parentUid) {
                    result = true;
                }
            }
        }
        return result;
    },

    //是否可以下移
    canDown: function (rowData, rowNo) {
        var result = false;
        var length = this.datagrid.data.length;
        if (rowNo + 1 != length) {
            for (var i = rowNo + 1; i < length; i++) {
                if (this.datagrid.data[i].parentUid == rowData.parentUid) {
                    result = true;
                }
            }
        }
        return result;
    },

    //统计子地区数量
    countSubGroup: function (uid) {
        var count = 0;

        $.each(this.datagrid.data, function (index, row) {
            if (row.parentUid == uid) {
                count++;
            }
        });

        return count;
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            strHtml = "";
            //创建列表添加下级图标
            strHtml += this.createAddIcon(rowNo);

            //创建列表编辑图标
            strHtml += this.createEditIcon(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteIcon(rowNo);

            //创建列表上移图标
            strHtml += this.createUpIcon(rowNo, this.canUp(rowData, rowNo) ? 'blue' : 'gray');

            //创建列表下移图标
            strHtml += this.createDownIcon(rowNo, this.canDown(rowData, rowNo) ? 'blue' : 'gray');
        }
        //POPUP详细表示
        else if (colName == "regionName") {
            //展开收缩图标
            var icon = "<i id='" + rowData.uid + "' class=\"parent-" + rowData.parentUid + "\"></i>";

            //有子的场合，才显示地区名称前的Icon图标
            if (this.countSubGroup(rowData.uid) > 0) {
                icon = "<i style='width:12px'' onclick='" + this.selfInstance + ".expandRegion(\"" + rowData.uid + "\"); ' id='" + rowData.uid + "' class=\"fa expand-region fa-lg fa-caret-down parent-" + rowData.parentUid + "\"></i>";
            }

            var regionName = strHtml.substring(strHtml.lastIndexOf("#") + 1);

            //替换#字符为空格
            var spacer = strHtml.substring(0, strHtml.lastIndexOf("#") + 1).replaceAll("#", "&nbsp;&nbsp;&nbsp;");

            rowData.regionName = rowData.regionName.replaceAll("#", "");
            var strHtml = spacer + icon + this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<font color='red'>停用</font>";
        }

        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //地区展开/收缩
    expandRegion: function (uid, hidden) {
        var thisObj = this;

        //展开/收缩表示用的 图标切换
        if (thisObj.isNull(hidden)) {
            thisObj.$(uid).toggleClass("fa-caret-right fa-caret-down");
        }

        $(".parent-" + uid).each(function () {
            //递归找到下属地区的行
            var obj = $(this).parentsUntil("tr").parent();

            //Click事件处理的场合
            if (thisObj.isNull(hidden)) {
                if (obj.is(":hidden")) {
                    $(obj).show();
                    hidden = true;
                } else {
                    $(obj).hide();
                    hidden = false;
                }
            }
            //递归处理的场合
            else {
                if (hidden) {
                    $(obj).show();
                } else {
                    $(obj).hide();
                }
            }
            $(".expand-region", obj).each(function () {
                thisObj.expandRegion(this.id, hidden);
            });
        });
    },

    /** ********************以下方法为可选方法********************* */
    //画面Dom值自定义设置[检索条件Entity取得之前]
    customDomValue: function () {

    },

    //检索条件Entity自定义设置[检索条件Entity取得之后]
    customSearchEntity: function () {

    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    },

    //上移
    upLink: function (rowNo) {
        // 从下往上找 找到上面面第一个有相同parent的行并交换排序值
        for (var i = rowNo - 1; i >= 0; i--) {
            if (this.datagrid.data[i].parentUid == this.datagrid.data[rowNo].parentUid) {
                var beforeRowJson = this.datagrid.data[i];
                var thisRowJson = this.datagrid.data[rowNo];

                this.moveData(beforeRowJson.uid, thisRowJson.uid);
                break;
            }
        }
    },

    //下移
    downLink: function (rowNo) {
        var length = this.datagrid.data.length;
        //从上往下找 找到下面第一个有相同parent的行并交换排序值
        for (var i = rowNo + 1; i < length; i++) {
            if (this.datagrid.data[i].parentUid == this.datagrid.data[rowNo].parentUid) {
                var afterRowJson = this.datagrid.data[i];
                var thisRowJson = this.datagrid.data[rowNo];

                this.moveData(afterRowJson.uid, thisRowJson.uid);
                break;
            }
        }
    },

    //数据移动 交换数据位置
    moveData: function (sourceUid, targetUid) {
        var params = "sourceUid=" + sourceUid + "&targetUid=" + targetUid;

        //处理中提示消息
        this.showProcessing(false);

        //Ajax処理（POST方式）
        AjaxIns.sendPOST(this.controller + "moveData", this.moveDataCallback.bind(this), params);
    },

    //数据移动
    moveDataCallback: function (response) {
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        if (ajaxResult.result > 0) {
            this.refreshList();
        }
        else {
            this.hideMessage();
        }
    }
});