//定义数据一览画面JS类
SysApp.Crm.StudentList = Class.create();

SysApp.Crm.StudentList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //导入订单附属信息点击事件
        this.bindEvent("btnImport", "click", this.onClick_Import.bind(this));
    },

    //快捷查询-日期赋值
    fastSearch: function (status, dom) {
        $(".fast-search a").removeClass("selected");
        $(dom).addClass("selected");

        if (status == 1) {
            this.setValue("recentConsultTime$from_search", new Date().formatYMD());
            this.setValue("recentConsultTime$to_search", new Date().formatYMD());
        }
        else if (status == 2) {
            this.setValue("recentConsultTime$from_search", new Date().addDate("DAY", -3).formatYMD());
            this.setValue("recentConsultTime$to_search", new Date().formatYMD());
        }
        else if (status == 3) {
            this.setValue("recentConsultTime$from_search", new Date().addDate("DAY", -7).formatYMD());
            this.setValue("recentConsultTime$to_search", new Date().formatYMD());
        }
        this.getList(true);
    },

    //导入点击事件
    onClick_Import: function () {
        SysApp.Crm.StudentPopupImportIns.show();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("咨询时间", "recentConsultTime", "12%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("咨询方式", "consultMethodName", "7%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("信息来源", "sourceTypeName", "7%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("家长姓名", "parentName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("学员姓名", "name", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("手机号码", "mobile", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("学员年龄", "studentAge", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("围棋基础", "baseLevelName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("所属校区", "studentCampusName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("课程顾问", "studentBelongConsultantUserName", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("学员状态", "studentStatusName", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("跟进次数", "followupCount", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("首次咨询时间", "firstConsultTime", "120px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("最近咨询时间", "recentConsultTime", "120px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("咨询方式", "consultMethodName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("信息来源", "sourceTypeName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("家长姓名", "parentName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("学员姓名", "name", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("性别", "genderName", "40px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("证件类型", "cardTypeName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("证件号码", "cardNumber", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("学员年龄", "studentAge", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("出生年月", "birthday", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("围棋基础", "baseLevelName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("所属校区", "studentCampusName", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("课程顾问", "studentBelongConsultantUserName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("手机号码", "mobile", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("电子邮件", "email", "180px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("所在省", "provinceName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("所在市", "cityName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("所在区", "regionName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("家庭住址", "homeAddress", "200px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("之前学校", "beforeSchool", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("学员状态", "studentStatusName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("跟进次数", "followupCount", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("备注", "remark", "200px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            strHtml += this.createFollowupButton(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //跟进次数
        else if (colName == "followupCount") {
            strHtml = "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showFollowupList(" + rowNo + ");return false;'>" + rowData[colName] + "次</a>";
        }
        //家长姓名
        else if (colName == "parentName" && this.isNotEmpty(rowData.relationshipTypeName)) {
            strHtml = rowData[colName] + "[" + rowData.relationshipTypeName + "]";
        }
        //POPUP详细表示
        else if (colName == "name") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //首次咨询时间
        else if (colName == "firstConsultTime" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHM();
        }
        //最近咨询时间
        else if (colName == "recentConsultTime" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHM();
        }
        //出生年月
        else if (colName == "birthday" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYM();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    showFollowupInput: function (rowNo) {
        SysApp.Crm.FollowupInputIns.setStudent(this.getRowData(rowNo));
        SysApp.Crm.FollowupInputIns.show();
    },

    showFollowupDetail: function () {
        SysApp.Crm.FollowupDetailIns.show();
    },

    showFollowupList: function (rowNo) {
        SysApp.Crm.FollowupPopupListIns.setStudent(this.getRowData(rowNo));
        SysApp.Crm.FollowupPopupListIns.show();
        SysApp.Crm.FollowupPopupListIns.getList(true);
    },

    //创建跟进按钮
    createFollowupButton: function (rowNo, disabled, className) {
        return "<button type='button' class='btn btn-primary" + this.getButtonClass(className) + "' onclick='" + this.selfInstance + ".showFollowupInput(\"" + rowNo + "\");return false;'><i class='fa fa-plus fa-width-fixed'></i>跟进</button>";
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