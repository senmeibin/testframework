// 定义数据输入画面JS类
SysApp.Demo.CompanyExtendedTabs = Class.create();

SysApp.Demo.CompanyExtendedTabs.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //Tab对象实例化
        this.initTab("MultiTabView", 10, this.extendedTabOnChange);

        //附加信息TAB设置企业UID
        SysApp.Demo.CompanyAdditionalInnerListIns.setCompanyUid(this.companyUid);

        //股权信息TAB设置企业UID
        SysApp.Demo.CompanyStockInnerListIns.setCompanyUid(this.companyUid);

        //人员结构TAB设置企业UID
        SysApp.Demo.CompanyPersonnelInnerListIns.setCompanyUid(this.companyUid);

        //核心团队人员TAB设置企业UID
        SysApp.Demo.CompanyTeamInnerListIns.setCompanyUid(this.companyUid);

        //财务信息TAB设置企业UID
        SysApp.Demo.CompanyFinancialInnerListIns.setCompanyUid(this.companyUid);

        //专利版权TAB设置企业UID
        SysApp.Demo.CompanyCopyrightInnerListIns.setCompanyUid(this.companyUid);

        //投融资TAB设置企业UID
        SysApp.Demo.CompanyInvestmentInnerListIns.setCompanyUid(this.companyUid);

        //知识产权TAB设置企业UID
        SysApp.Demo.CompanyIntellectualInnerListIns.setCompanyUid(this.companyUid);

        //企业资质TAB设置企业UID
        SysApp.Demo.CompanyQualificationInnerListIns.setCompanyUid(this.companyUid);

        //机构信息TAB设置企业UID
        SysApp.Demo.CompanyInstitutionalInnerListIns.setCompanyUid(this.companyUid);
    },

    //TAB切换
    extendedTabOnChange: function (tabObj, tabIndex) {
        //附加信息TAB切换
        if (tabIndex == 0 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyAdditionalInnerListIns.getList(true);
        }
        //股权信息TAB切换
        else if (tabIndex == 1 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyStockInnerListIns.getList(true);
        }
        //人员结构TAB切换
        else if (tabIndex == 2 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyPersonnelInnerListIns.getList(true);
        }
        //核心团队人员TAB切换
        else if (tabIndex == 3 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyTeamInnerListIns.getList(true);
        }
        //财务信息TAB切换
        else if (tabIndex == 4 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyFinancialInnerListIns.getList(true);
        }
        //专利版权TAB切换
        else if (tabIndex == 5 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyCopyrightInnerListIns.getList(true);
        }
        //投融资TAB切换
        else if (tabIndex == 6 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyInvestmentInnerListIns.getList(true);
        }
        //投融资TAB切换
        else if (tabIndex == 7 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyIntellectualInnerListIns.getList(true);
        }
        //企业资质
        else if (tabIndex == 8 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyQualificationInnerListIns.getList(true);
        }
        //机构信息
        else if (tabIndex == 9 && this.isNotEmpty(this.companyUid)) {
            SysApp.Demo.CompanyInstitutionalInnerListIns.getList(true);
        }
    }
});