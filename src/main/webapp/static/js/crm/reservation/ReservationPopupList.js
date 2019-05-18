//定义数据一览画面JS类
SysApp.Crm.ReservationPopupList = Class.create();

SysApp.Crm.ReservationPopupList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //Override父类方法
    onClick_Add: function () {
        this.inputInstance.setStudent(this.student);
        this.inputInstance.show();
    },

    //设置学员信息
    setStudent: function (student) {
        this.$("studentInfo").html(student.name + "-" + student.parentName + "-" + student.mobile + "-" + student.baseLevelName);
        this.$("ctlTitle").html("【" + student.name + "】的预约记录一览");
        this.setValue("studentUid", student.uid);
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("预约日期", "reservationDate", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约校区", "reservationCampusName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约班级", "reservationCampusClassName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约目的", "purposeName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约内容", "contents", "40%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("是否到访", "isVisited", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("是否报名", "isRegistration", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

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

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "contents") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //预约日期
        else if (colName == "reservationDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHM();
        }
        else if (colName == "isVisited") {
            strHtml = rowData[colName] == 0 ? "-" : "已到访";
        }
        else if (colName == "isRegistration") {
            strHtml = rowData[colName] == 0 ? "-" : "已报名";
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
    }
});