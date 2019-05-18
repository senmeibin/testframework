//定义数据一览画面JS类
SysApp.Sys.DeptList = Class.create();

SysApp.Sys.DeptList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();
        this.colList.push(new ColInfo("操作", "operation", "16%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("部门名称", "deptName", "21%", SORT_YES));
        this.colList.push(new ColInfo("部门层级", "deptClassName", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("部门分类", "deptCategoryName", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("部门别名", "deptAliasName", "10%", SORT_YES));
        this.colList.push(new ColInfo("部门全称", "deptFullName", "30%", SORT_YES));
        this.colList.push(new ColInfo("备注", "remark", "4%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.useRowNo = false;

        this.datagrid.cssClass = "app-table-list";
    },

    createUserListIcon: function (rowData) {
        var html = "<span class='popovers 'data-trigger='hover' data-placement='top' data-content='查看部门下属员工'>";
        html += "<i class='fa fa-user fa-lg fa-blue' onclick='" + this.selfInstance + ".showUserList(\"" + rowData.uid + "\");return false;'></i></a>";
        html += "</span>";
        return html;
    },

    //添加下级部门
    addSubDept: function (index) {
        var json = this.datagrid.data[index];
        this.inputInstance.parentDept = json;
        this.goInput();
    },

    //创建列表添加图标
    createAddIcon: function (rowNo) {
        var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='添加下级部门'>";
        html += "<i class='fa fa-plus fa-lg fa-width-fixed fa-blue' onclick='" + this.selfInstance + ".addSubDept(" + rowNo + ");return false;'></i></a>";
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
                if (this.datagrid.data[i].parentDeptUid == rowData.parentDeptUid) {
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
                if (this.datagrid.data[i].parentDeptUid == rowData.parentDeptUid) {
                    result = true;
                }
            }
        }
        return result;
    },

    //统计子部门数量
    countSubGroup: function (uid) {
        var count = 0;

        $.each(this.datagrid.data, function (index, row) {
            if (row.parentDeptUid == uid) {
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
            //创建查看部门员工图标
            strHtml += this.createUserListIcon(rowData);

            //创建列表添加下级图标
            strHtml += this.createAddIcon(rowNo);

            //创建列表编辑图标
            strHtml += this.createEditIcon(rowNo);
            //停用或者启用
            if (rowData.recordStatus == 1) {
                strHtml += this.createLockIcon(rowNo);
            }
            else {
                strHtml += this.createUnlockIcon(rowNo);
            }

            //创建列表删除图标
            strHtml += this.createDeleteIcon(rowNo);

            //创建列表上移图标
            strHtml += this.createUpIcon(rowNo, this.canUp(rowData, rowNo) ? 'blue' : 'gray');

            //创建列表下移图标
            strHtml += this.createDownIcon(rowNo, this.canDown(rowData, rowNo) ? 'blue' : 'gray');

        }
        //POPUP详细表示
        else if (colName == "deptName") {
            //展开收缩图标
            var icon = "<i id='" + rowData.uid + "' class=\"parent-" + rowData.parentDeptUid + "\"></i>";

            //有子部门的场合，才显示部门名称前的Icon图标
            if (this.countSubGroup(rowData.uid) > 0) {
                icon = "<i style='width:12px'' onclick='" + this.selfInstance + ".expandDept(\"" + rowData.uid + "\"); ' id='" + rowData.uid + "' class=\"fa expand-dept fa-lg fa-caret-down parent-" + rowData.parentDeptUid + "\"></i>";
            }

            var deptName = strHtml.substring(strHtml.lastIndexOf("#") + 1);

            //替换#字符为空格
            var spacer = strHtml.substring(0, strHtml.lastIndexOf("#") + 1).replaceAll("#", "&nbsp;&nbsp;&nbsp;");

            rowData.deptName = rowData.deptName.replaceAll("#", "");
            var strHtml = spacer + icon + this.createDetailLink(rowData, colName, rowNo);

            //集团的场合
            if (rowData['deptClass'] == 0) {
                strHtml = "<b>" + strHtml + "</b>"
            }
        }
        //备注字段
        else if (colName == "remark") {
            strHtml = this.createRemarkColumn(rowData);
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //部门展开/收缩
    expandDept: function (uid, hidden) {
        var thisObj = this;

        //展开/收缩表示用的 图标切换
        if (thisObj.isNull(hidden)) {
            thisObj.$(uid).toggleClass("fa-caret-right fa-caret-down");
        }

        $(".parent-" + uid).each(function () {
            //递归找到下属部门的行
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
            $(".expand-dept", obj).each(function () {
                thisObj.expandDept(this.id, hidden);
            });
        });
    },

    /** ********************以下方法为可选方法********************* */
    initCallback: function () {
        this.initCommonCallback();

        //无分页查询
        this.searchMethod = "searchNoPage";

        //组织架构树形展示
        this.bindEvent("btnDeptList", 'click', this.onClick_DeptList.bind(this));
    },

    showUserList: function (uid) {
        this.UserPopupListIns = SysApp.Sys.UserPopupListIns;
        this.UserPopupListIns.setDeptUid(uid);
        this.UserPopupListIns.show();
        this.UserPopupListIns.getList(true);
    },

    //组织架构树形展示
    onClick_DeptList: function () {
        window.location = this.deptListUrl;
    },

    //上移
    upLink: function (rowNo) {
        // 从下往上找 找到上面面第一个有相同parent的行并交换排序值
        for (var i = rowNo - 1; i >= 0; i--) {
            if (this.datagrid.data[i].parentDeptUid == this.datagrid.data[rowNo].parentDeptUid) {
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
            if (this.datagrid.data[i].parentDeptUid == this.datagrid.data[rowNo].parentDeptUid) {
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