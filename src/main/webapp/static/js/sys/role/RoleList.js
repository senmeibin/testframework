//定义数据一览画面JS类
SysApp.Sys.RoleList = Class.create();

SysApp.Sys.RoleList.prototype = Object.extend(new SysApp.Sys.CommonList(), {

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("应用名称", "appName", "12%", SORT_YES));
        this.colList.push(new ColInfo("角色编号", "roleCode", "18%", SORT_YES));
        this.colList.push(new ColInfo("角色名称", "roleName", "20%", SORT_YES));
        this.colList.push(new ColInfo("角色描述", "description", "28%", SORT_YES));
        this.colList.push(new ColInfo("表示顺序", "dispSeq", "6%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("备注", "remark", "4%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            //手动登录的场合
            if (rowData.regSystem == 0) {
                //创建列表删除图标
                strHtml += this.createDeleteButton(rowNo, false, "delete");
            }

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        else if (colName == "appName") {
            strHtml = rowData[colName] + "[" + rowData.appCode + "]";
        }
        //POPUP详细表示
        else if (colName == "roleCode") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "remark") {
            strHtml = this.createRemarkColumn(rowData);
        }
        else if (colName == "dispSeq") {
            strHtml = "<input type='text'  id='dispSeq_" + rowNo + "' data-uid='" + rowData.uid + "' class='form-control' maxlength='4' style='text-align: right; width: 100%;' value='" + rowData.dispSeq + "'>";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    /** ********************以下方法为可选方法********************* */
    initCallback: function () {
        this.initCommonCallback();
        //关闭回车检索
        this.useEnterSearch = false;
        //绑定批量保存按钮
        this.bindEvent("btnBatchSaveDispSeq", "click", this.onClick_BatchSaveDispSeq.bind(this));
    },

    onClick_BatchSaveDispSeq: function () {
        this.showConfirm("确定要批量设置并保存表示顺序吗？", this.batchSaveDispSeq.bind(this));
    },

    //批量保存表示顺序
    batchSaveDispSeq: function () {
        var thisObj = this;
        var itemList = [];
        var errorFlag = false;
        $("input[id^=dispSeq]").each(function (index, item) {
            var dispSeq = parseInt($(item).val(), 10);
            if (isNaN(dispSeq)) {
                errorFlag = true;
                return false;
            }

            itemList.push({
                uid: $(item).data("uid"),
                dispSeq: dispSeq
            });
        });

        if (errorFlag) {
            thisObj.showErrorMessage("请在表示顺序中输入正确的数字。");
            return;
        }

        var params = "itemJson=" + JsonUtility.ToJSON(itemList);
        //Ajax处理前显示提示画面
        this.showProcessing();

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "batchSaveDispSeq", this.batchSaveDispSeqCallback.bind(this), params);
    },

    //批量保存表示顺序[Callback后处理]
    batchSaveDispSeqCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.showInfoMessage("表示顺序批量保存成功。", this.refreshList.bind(this));
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    }
});