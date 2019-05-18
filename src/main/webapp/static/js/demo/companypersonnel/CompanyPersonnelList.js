//定义数据一览画面JS类
SysApp.Demo.CompanyPersonnelList = Class.create();

SysApp.Demo.CompanyPersonnelList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("企业名称", "companyName", "20%", SORT_YES));
        this.colList.push(new ColInfo("上年年末从业人员总数", "lastYearTotalEmployees", "10%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("博士毕业人数", "numberOfDoctor", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("留学归国人员数", "numberOfReturnedStudents", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("千人计划数", "numberOfThousandsPlans", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("应届毕业生", "numberOfGraduates", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("大专以上毕业人数", "numberOfCollegeGraduates", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("硕士毕业人数", "numberOfMaster", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("本科毕业人数", "numberOfUndergraduate", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("中专毕业人数", "numberOfSecondaryVocational ", "7%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("高中以下人数", "numberOfHighSchool", "7%", SORT_YES, ALIGN_RIGHT));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER),
            new ColInfo("企业名称", "companyName", "100px", SORT_YES),
            new ColInfo("上年年末从业人员总数", "lastYearTotalEmployees", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("博士毕业人数", "numberOfDoctor", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("留学归国人员数", "numberOfReturnedStudents", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("千人计划数", "numberOfThousandsPlans", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("应届毕业生", "numberOfGraduates", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("大专以上毕业人数", "numberOfCollegeGraduates", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("上海户籍员工数量", "numberOfShanghai", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("外籍员工数量", "numberOfForeign", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("行政管理人员", "numberOfAdministrativeStaff", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("市场营销人员", "numberOfMarketingStaff", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("研发设计人员", "numberOfResearch", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("领导管理人员", "numberOfLead", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("硕士毕业人数", "numberOfMaster", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("本科毕业人数", "numberOfUndergraduate", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("中专毕业人数", "numberOfSecondaryVocational ", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("高中以下人数", "numberOfHighSchool", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("高级职称人数", "numberOfSeniorTitle", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("中级职称人数", "numberOfIntermediateTitle", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("初级职称人数", "numberOfJuniorTitle", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("无职称人数", "numberOfNoneTitle", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("备注", "remark", "100px", SORT_YES)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createDetailButton(rowNo);
        }
        //POPUP详细表示
        else if (colName == "companyName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
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