//定义数据一览画面JS类
SysApp.Demo.CompanyExtendedCommonList = Class.create();

SysApp.Demo.CompanyExtendedCommonList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //设置简历UID
    setCompanyUid: function (companyUid) {
        this.setValue("companyUid", companyUid);
        this.getList(true);
    },

    goInput: function (index, detail, tableId) {
        if (this.validateInputObject() == false) {
            return;
        }
        if (this.isNull(detail)) {
            detail = 0;
        }
        var json = this.getRowData(index, tableId);
        //显示
        if (this.isNotEmpty(this.inputInstance)) {
            //行数据设定
            this.inputInstance.inputEntity = $.extend(true, {}, json);
            //公司UID
            this.inputInstance.inputEntity.companyUid = this.getValue("companyUid");
            //列表画面JS对象
            this.inputInstance.listInstance = this;
            //当前编辑数据索引位置
            this.inputInstance.listDataIndex = index;

            //详细表示标志位
            this.inputInstance.isDetail = (detail == 1);

            this.inputInstance.show();
        }
    }
});

// 定义数据输入画面JS类
SysApp.Demo.CompanyExtendedCommonInput = Class.create();

SysApp.Demo.CompanyExtendedCommonInput.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
    //加载企业信息
    showCompanyInformation: function () {
        if (this.isNotEmpty(SysApp.Demo.CompanyInformationInnerDetailIns)) {
            SysApp.Demo.CompanyInformationInnerDetailIns.getDetail(this.inputEntity.companyUid);
        }
    }
});

// 定义数据输入画面JS类
SysApp.Demo.CompanyExtendedCommonInnerInput = Class.create();

SysApp.Demo.CompanyExtendedCommonInnerInput.prototype = Object.extend(new SysApp.Demo.CommonInput(), {
    //关闭
    onClick_Close: function () {
        //清空form表单
        this.clearForm();
        //隐藏编辑form
        this.displayDom(this.getClientIdDivId(), false);
    },

    //显示编辑form
    show: function () {
        //显示编辑form
        this.displayDom(this.getClientIdDivId(), true);
        //显示删除
        this.displayDom(this.dom.btnDelete, this.isNotEmpty(this.inputEntity.uid));
        //清空form表单
        this.clearForm();
        this.initInputForm(this.inputEntity);
    },

    customDeleteDataCallback: function (ajaxResult) {
        //隐藏编辑form
        this.displayDom(this.getClientIdDivId(), false);
        if (this.getInnerListInstance) {
            this.getInnerListInstance().getList(true);
        }
    },

    //保存后回调函数
    customSaveDataCallback: function () {
        this.displayDom(this.getClientIdDivId(), false);
        if (this.getInnerListInstance) {
            this.getInnerListInstance().getList(true);
        }
    },

    //验证后处理
    validateFormAfter: function () {
        if (SysApp.Demo.CompanyInformationInputIns.isAddMode()) {
            this.showErrorMessage("请先保存企业信息，再添加教育经历。");
            return false;
        }
        return true;
    },

    getClientIdDivId: function () {
        return this.clientID.substring(0, 1).toLocaleLowerCase() + this.clientID.substring(1) + "Div";
    }
});