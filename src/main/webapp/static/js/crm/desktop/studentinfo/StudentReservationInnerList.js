//定义数据一览画面JS类
SysApp.Crm.StudentReservationInnerList = Class.create();

SysApp.Crm.StudentReservationInnerList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return 50;
    },

    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.autoSearch = false;
    },

    //创建预约记录
    createReservationList: function (student) {
        this.setValue("studentUid", student.uid);
        this.createList(student.reservationList);
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
        this.colList.push(new ColInfo("课程顾问", "reservationBelongConsultantUserName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约日期", "reservationDate", "11%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约目的", "purposeName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约内容", "contents", "40%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约班级", "reservationCampusClassName", "13%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("是否到访", "isVisited", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("是否报名", "isRegistration", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.useClientSort = true;
        this.datagrid.headerTextAlignMode = "autoWithColInfo";
        this.datagrid.showTopPager = false;
        this.datagrid.pager.showRecordInfo = false;
        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditIcon(rowNo);
        }
        else if (colName == "isVisited") {
            strHtml = rowData[colName] == 0 ? "-" : "已到访";
        }
        else if (colName == "isRegistration") {
            strHtml = rowData[colName] == 0 ? "-" : "已报名";
        }
        //POPUP详细表示
        else if (colName == "contents") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
            if (this.isNotEmpty(rowData.remark)) {
                strHtml += "<br/>" + rowData.remark;
            }
        }
        //预约日期
        else if (colName == "reservationDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatMDHM();
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