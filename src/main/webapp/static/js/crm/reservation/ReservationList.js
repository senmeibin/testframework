//定义数据一览画面JS类
SysApp.Crm.ReservationList = Class.create();

SysApp.Crm.ReservationList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //快捷查询-日期赋值
    fastSearch: function (status, dom) {
        $(".fast-search a").removeClass("selected");
        $(dom).addClass("selected");

        if (status == 1) {
            this.setValue("reservationDate$from_search", new Date().formatYMD());
            this.setValue("reservationDate$to_search", new Date().formatYMD());
        }
        else if (status == 2) {
            this.setValue("reservationDate$from_search", new Date().addDate("DAY", -3).formatYMD());
            this.setValue("reservationDate$to_search", new Date().formatYMD());
        }
        else if (status == 3) {
            this.setValue("reservationDate$from_search", new Date().addDate("DAY", -7).formatYMD());
            this.setValue("reservationDate$to_search", new Date().formatYMD());
        }
        this.getList(true);
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("家长姓名", "studentParentName", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("学员姓名", "studentName", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("手机号码", "studentMobile", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("围棋基础", "studentBaseLevelName", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        this.colList.push(new ColInfo("预约日期", "reservationDate", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约校区", "reservationCampusName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约班级", "reservationCampusClassName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约目的", "purposeName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("预约内容", "contents", "14%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("是否到访", "isVisited", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("是否报名", "isRegistration", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("咨询方式", "studentConsultMethodName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("信息来源", "studentSourceTypeName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("家长姓名", "studentParentName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("学员姓名", "studentName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("性别", "studentGenderName", "40px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("手机号码", "studentMobile", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("学员年龄", "studentAge", "60px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("围棋基础", "studentBaseLevelName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("所属校区", "studentBelongCampusName", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("电子邮件", "studentEmail", "180px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("所在省", "studentProvinceName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("所在市", "studentCityName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("所在区", "studentRegionName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("家庭住址", "studentHomeAddress", "200px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("之前学校", "studentBeforeSchool", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("学员状态", "studentStatusName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("跟进次数", "studentFollowupCount", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("首次咨询时间", "studentFirstConsultTime", "120px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("最近咨询时间", "studentRecentConsultTime", "120px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),

            new ColInfo("预约日期", "reservationDate", "120px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("预约校区", "reservationCampusName", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("预约班级", "reservationCampusClassName", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("预约目的", "purposeName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("预约内容", "contents", "300px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("是否到访", "isVisited", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("是否报名", "isRegistration", "80px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("课程顾问", "consultantUserName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("授课讲师", "teacherUserName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("备注", "remark", "300px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("添加日期", "insertDate", "150px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO)
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
        else if (colName == "isVisited") {
            strHtml = rowData[colName] == 0 ? "-" : "已到访";
        }
        else if (colName == "isRegistration") {
            strHtml = rowData[colName] == 0 ? "-" : "已报名";
        }
        //POPUP详细表示
        else if (colName == "contents") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //预约日期
        else if (colName == "reservationDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHM();
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