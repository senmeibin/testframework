// 定义数据输入画面JS类
SysApp.Cmn.MainCustomerImport = Class.create();

SysApp.Cmn.MainCustomerImport.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
    //数据导入验证
    checkFile: function () {
        var file = this.getValue("file");
        if (this.isEmpty(file)) {
            this.showMessage("请选择上传的文件。", MSG_TYPE_ERROR);
            return false;
        }
        else {
            if (!file.endsWith(".xls") && !file.endsWith(".xlsx")) {
                this.showMessage("请选择后缀名为.xls或者.xlsx上传。", MSG_TYPE_ERROR);
                return false;
            }
        }
        return true;
    },

    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        this.autoHideFlashMessage = true;
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {

    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {

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