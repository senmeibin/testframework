//定义数据一览画面JS类
SysApp.Demo.EmployeeList = Class.create();

SysApp.Demo.EmployeeList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "5%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("用户名", "userCd", "8%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "7%", SORT_YES));
        this.colList.push(new ColInfo("性别", "sexName", "7%", SORT_YES));
        this.colList.push(new ColInfo("工号", "userNumber", "10%", SORT_YES));
        this.colList.push(new ColInfo("电子邮件", "email", "12%", SORT_YES));
        this.colList.push(new ColInfo("出生日期", "birthday", "9%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("部门", "deptFullName", "15%", SORT_YES));
        this.colList.push(new ColInfo("岗位", "positionName", "11%", SORT_YES));
        this.colList.push(new ColInfo("入职日期", "entryDate", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("人员状态", "statusName", "6%", SORT_YES));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER),
            new ColInfo("登录账号", "userCd", "100px", SORT_YES),
            new ColInfo("姓名", "userName", "100px", SORT_YES),
            new ColInfo("性别", "sexName", "100px", SORT_YES),
            new ColInfo("出生日期", "birthday", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("年龄", "age", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("身份证号", "idCardNo", "100px", SORT_YES),
            new ColInfo("籍贯", "ancestralNativePlace", "100px", SORT_YES),
            new ColInfo("民族", "ethnicityName", "100px", SORT_YES),
            new ColInfo("工号", "userNumber", "100px", SORT_YES),
            new ColInfo("户口所在地", "registeredResidence", "100px", SORT_YES),
            new ColInfo("政治面貌", "policyName", "100px", SORT_YES),
            new ColInfo("学历", "educationName", "100px", SORT_YES),
            new ColInfo("学位", "educationalDegreeName", "100px", SORT_YES),
            new ColInfo("身高", "stature", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("体重", "weight", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("血型", "bloodTypeName", "100px", SORT_YES),
            new ColInfo("星座", "constellation", "100px", SORT_YES),
            new ColInfo("婚姻状况", "maritalStatusName", "100px", SORT_YES),
            new ColInfo("国籍", "nationality", "100px", SORT_YES),
            new ColInfo("电子邮件", "email", "100px", SORT_YES),
            new ColInfo("固定电话", "telephone", "100px", SORT_YES),
            new ColInfo("手机", "mobile", "100px", SORT_YES),
            new ColInfo("传值", "fax", "100px", SORT_YES),
            new ColInfo("家庭住址", "homeAddress", "100px", SORT_YES),
            new ColInfo("毕业院校", "graduateInstitutions", "100px", SORT_YES),
            new ColInfo("所学专业", "major", "100px", SORT_YES),
            new ColInfo("户口类型", "residenceType", "100px", SORT_YES),
            new ColInfo("出生地", "birthPlace", "100px", SORT_YES),
            new ColInfo("所在省", "contactProvinceName", "100px", SORT_YES),
            new ColInfo("所在市", "contactCityName", "100px", SORT_YES),
            new ColInfo("所在区县", "contactCountyName", "100px", SORT_YES),
            new ColInfo("联系地址", "contactAddress", "100px", SORT_YES),
            new ColInfo("健康状况", "healthInfo", "100px", SORT_YES),
            new ColInfo("所属基地", "baseName", "100px", SORT_YES),
            new ColInfo("所属部门", "deptFullName", "100px", SORT_YES),
            new ColInfo("岗位", "positionName", "100px", SORT_YES),
            new ColInfo("职等", "positionsGrade", "100px", SORT_YES),
            new ColInfo("参加工作日期", "firstJobDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("入职日期", "entryDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("司龄", "seniority", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("试用期结束日期", "probationEndDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("转正日期", "positiveDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("离职日期", "retireDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("用工方式", "employmentType", "100px", SORT_YES),
            new ColInfo("合同开始日期", "contractStartDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("合同结束日期", "contractEndDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("公积金帐号", "accumulationFundAccount", "100px", SORT_YES),
            new ColInfo("开户银行", "bankName", "100px", SORT_YES),
            new ColInfo("工资卡号", "bankAccountNumber", "100px", SORT_YES),
            new ColInfo("办公地点", "locationFullName", "100px", SORT_YES),
            new ColInfo("人员状态", "statusName", "100px", SORT_YES),
            new ColInfo("备注", "remark", "100px", SORT_YES)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);
        }
        //POPUP详细表示
        else if (colName == "userName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //出生日期
        else if (colName == "birthday" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //参加工作日期
        else if (colName == "firstJobDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //入职日期
        else if (colName == "entryDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //试用期结束日期
        else if (colName == "probationEndDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //转正日期
        else if (colName == "positiveDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //离职日期
        else if (colName == "retireDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //合同开始日期
        else if (colName == "contractStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //合同结束日期
        else if (colName == "contractEndDate" && this.isNotEmpty(rowData[colName])) {
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

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});