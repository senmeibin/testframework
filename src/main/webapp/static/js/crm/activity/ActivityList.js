//定义数据一览画面JS类
SysApp.Crm.ActivityList = Class.create();

SysApp.Crm.ActivityList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("活动分类", "categoryName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("活动主题", "title", "20%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("活动目的", "purpose", "32%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("活动开始日期", "startDate", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("活动结束日期", "endDate", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("申请日期", "applyDate", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("审批状态", "auditStatusName", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("活动分类", "categoryName", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("活动种类", "typeName", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("活动主题", "title", "300px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("活动目的", "purpose", "300px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("活动开始日期", "startDate", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("活动结束日期", "endDate", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("活动地址", "address", "200px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("活动对象", "target", "200px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("负责人", "responsibleUserName", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("申请校区", "applyCampusName", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("申请日期", "applyDate", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("申请人", "applyUserName", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("审批时间", "auditDate", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("审批人", "auditUserName", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("审批状态", "auditStatusName", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("审批意见", "auditComment", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("表示顺序", "dispSeq", "80px", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO),
            new ColInfo("备注", "remark", "60px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "title") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //活动开始日期
        else if (colName == "startDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //活动结束日期
        else if (colName == "endDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //申请日期
        else if (colName == "applyDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //审批时间
        else if (colName == "auditDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
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

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //验证后处理
    validateFormAfter: function () {
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});