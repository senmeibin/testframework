//定义数据一览画面JS类
SysApp.Crm.PaymentList = Class.create();

SysApp.Crm.PaymentList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //快捷查询-日期赋值
    fastSearch: function (status, dom) {
        $(".fast-search a").removeClass("selected");
        $(dom).addClass("selected");

        if (status == 1) {
            this.setValue("paymentDate$from_search", new Date().formatYMD());
            this.setValue("paymentDate$to_search", new Date().formatYMD());
        }
        else if (status == 2) {
            this.setValue("paymentDate$from_search", new Date().addDate("DAY", -3).formatYMD());
            this.setValue("paymentDate$to_search", new Date().formatYMD());
        }
        else if (status == 3) {
            this.setValue("paymentDate$from_search", new Date().addDate("DAY", -7).formatYMD());
            this.setValue("paymentDate$to_search", new Date().formatYMD());
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

        this.colList.push(new ColInfo("缴费日期", "paymentDate", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("收据序号", "receiptNumber", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("付款人", "payer", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("付款方式", "paymentMethodName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("费用科目", "itemTypeName", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("应付金额", "payableAmount", "8%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));
        this.colList.push(new ColInfo("实收金额", "paymentAmount", "8%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));
        this.colList.push(new ColInfo("退款金额", "refundAmount", "6%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.totalColList.push("payableAmount");
        this.datagrid.totalColList.push("paymentAmount");
        this.datagrid.totalColList.push("refundAmount");

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

            new ColInfo("所属校区", "paymentBelongCampusName", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("课程顾问", "paymentBelongConsultantUserName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("缴费日期", "paymentDate", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("收据序号", "receiptNumber", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("付款人", "payer", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("付款方式", "paymentMethodName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("费用科目", "itemTypeName", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("应付金额", "payableAmount", "100px", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO),
            new ColInfo("实收金额", "paymentAmount", "100px", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO),
            new ColInfo("收费情况", "paymentDetails", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("收款人", "payeeUserName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("退款金额", "refundAmount", "100px", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO),
            new ColInfo("退款原因", "refundReason", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("退款人", "refundUserName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
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
        else if (colName == "payableAmount" || colName == "paymentAmount" || colName == "refundAmount") {
            strHtml = this.formatCny(rowData[colName]);
        }
        //缴费日期
        else if (colName == "paymentDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showDetail(" + rowNo + ");return false;'>" + rowData[colName].formatMDHM() + "</a>";
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