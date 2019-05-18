//定义数据一览画面JS类
SysApp.Sys.DeptUserInnerList = Class.create();

SysApp.Sys.DeptUserInnerList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return -1;
    },

    //初期化回调函数
    initCallback: function () {
        this.initCommonCallback();

        this.autoSearch = false;
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("用户名", "userCd", "12%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "12%", SORT_YES));
        this.colList.push(new ColInfo("职位名称", "positionName", "8%", SORT_YES));
        this.colList.push(new ColInfo("邮箱地址", "userMail", "20%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "userPhone", "12%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("入职日期", "entryDate", "12%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("是否启用", "recordStatus", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("备注", "remark", "4%", SORT_YES, ALIGN_CENTER));

        this.initTableCommonProperty();

        this.datagrid.cssClass = "app-table-list app-table-list-input";
        this.datagrid.pager.showRecordInfo = false;
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            strHtml = "";
            if (rowData.regSystem == 0) {
                if (rowData.recordStatus == 1) {
                    strHtml += this.createLockButton(rowNo);
                }
                else {
                    strHtml += this.createUnlockButton(rowNo);
                }
            }
            //创建列表编辑图标
            strHtml += this.createEditButton(rowNo);
            if (rowData.regSystem == 0) {
                //创建列表删除图标
                strHtml += this.createDeleteButton(rowNo, false, "delete");
            }

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "userCd") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<font color='red'>停用</font>";
        }
        //日期字段
        else if (colName == "entryDate" || colName == "birthday") {
            strHtml = rowData[colName].formatYMD();
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

    setDeptUid: function (deptUid) {
        this.inputUrl = this.userInputUrl + "&deptUid=" + deptUid;
        this.setValue("deptUid", deptUid);
    },

    //创建详细POPUP表示的超链接
    createDetailLink: function (rowData, colName, rowNo) {
        var userCd = this.isNotEmpty(rowData[colName]) ? rowData[colName].split("^")[0] : "";
        //详细控件实例不存在的场合
        if (this.isNull(this.detailInstance)) {
            return userCd.escapeHTML();
        }
        else {
            return "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showDetail(" + rowNo + ");return false;'>" + userCd.escapeHTML() + "</a>";
        }
    }
});