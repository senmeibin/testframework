//定义数据一览画面JS类
SysApp.Crm.StudentPaymentInnerList = Class.create();

SysApp.Crm.StudentPaymentInnerList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return 50;
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.autoSearch = false;
    },

    //创建缴费记录
    createPaymentList: function (student) {
        this.setValue("studentUid", student.uid);
        this.createList(student.paymentList);
        this.createListAfter();
    },

    //刷新一览画面[Override父类方法强制刷新列表]
    refreshList: function () {
        this.getList(true);
    },

    //表格创建后处理
    createListAfter: function () {
        if (window.iScrollStudentRelationList) {
            window.iScrollStudentRelationList.refresh();
        }
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "5%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("课程顾问", "paymentBelongConsultantUserName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("缴费日期", "paymentDate", "11%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("收据序号", "receiptNumber", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("付款人", "payer", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("付款方式", "paymentMethodName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("费用科目", "itemTypeName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("应付金额", "payableAmount", "8%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));
        this.colList.push(new ColInfo("实收金额", "paymentAmount", "8%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));
        this.colList.push(new ColInfo("退款金额", "refundAmount", "8%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));
        this.colList.push(new ColInfo("备注", "remark", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("添加日期", "insertDate", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.useClientSort = true;
        this.datagrid.headerTextAlignMode = "autoWithColInfo";
        this.datagrid.showTopPager = false;
        this.datagrid.pager.showRecordInfo = false;
        this.datagrid.cssClass = "app-table-list app-table-list-input";

        this.datagrid.totalColList.push("payableAmount");
        this.datagrid.totalColList.push("paymentAmount");
        this.datagrid.totalColList.push("refundAmount");
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditIcon(rowNo);
        }
        //缴费日期
        else if (colName == "paymentDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showDetail(" + rowNo + ");return false;'>" + rowData[colName].formatMDHM() + "</a>";
        }
        else if (colName == "payableAmount" || colName == "paymentAmount" || colName == "refundAmount") {
            strHtml = this.formatCny(rowData[colName]);
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