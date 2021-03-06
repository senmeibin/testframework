// 定义数据输入画面JS类
SysApp.Crm.PaymentInput = Class.create();

SysApp.Crm.PaymentInput.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //设置学员信息
    setStudent: function (student) {
        this.setValue("studentUid", student.uid);
        this.setValue("studentName", student.name);
    },

    //数据删除后回调
    deleteDataCallback: function (response) {
        this.saveDataCallback(response);
    },

    //数据保存后回调
    saveDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功
        if (ajaxResult.result > 0) {
            this.hideMessage();
            this.hide();

            if (this.isNotNull(SysApp.Crm.StudentPaymentInnerListIns)) {
                SysApp.Crm.StudentPaymentInnerListIns.getList(true);
            }
            if (this.isNotNull(SysApp.Crm.PaymentPopupListIns)) {
                SysApp.Crm.PaymentPopupListIns.getList(true);
            }
            if (this.isNotNull(SysApp.Crm.PaymentListIns)) {
                SysApp.Crm.PaymentListIns.getList(true);
            }
            if (this.isNotNull(SysApp.Crm.StudentListIns)) {
                SysApp.Crm.StudentListIns.getList(true);
            }
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "缴费记录编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "缴费记录添加";

            //缴费日期 默认为系统时间
            this.inputEntity.paymentDate = new Date().formatYMDHMS();

            //课程顾问UID = 登录用户
            this.setValue("paymentBelongConsultantUserUid", window.loginUserUid);
        }
        else {
            //是否可删除
            var canDelete = new Date(this.inputEntity.paymentDate).getTime() > (new Date().getTime() - 24 * 3600 * 1000);
            if (canDelete) {
                this.displayDom("btnDelete", true);
            }
            else {
                this.displayDom("btnDelete", false);
            }
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);

        //缴费时间
        if (this.isNotEmpty(this.inputEntity.paymentDate)) {
            this.setValue("paymentDate", this.inputEntity.paymentDate.formatYMD());
            SysCmn.TimeSelect.reservationTime.setSelectedValue(this.inputEntity.paymentDate);
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        this.inputEntity.paymentDate = this.inputEntity.paymentDate + " " + SysCmn.TimeSelect.reservationTime.getSelectedValue();
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