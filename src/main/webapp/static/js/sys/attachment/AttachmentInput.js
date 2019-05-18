// 定义数据输入画面JS类
SysApp.Sys.AttachmentInput = Class.create();

SysApp.Sys.AttachmentInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "附件文件编辑";
        this.$("ctlTitle").html(title);

        //以下字段禁止编辑
        //应用名称
        this.disabledDom("appName", true);

        //模块名称
        this.disabledDom("moduleName", true);

        //关联
        this.disabledDom("relationUid", true);

        //文件地址
        this.disabledDom("filePath", true);

        //文件大小
        this.disabledDom("fileSize", true);

        //mime类型
        this.disabledDom("mimeType", true);

        //禁用删除按钮
        this.displayDom("btnDelete", true);

        //编辑时显示无后缀名文件名
        var fileName = this.getValue("fileName");

        if (fileName.lastIndexOf('.') > 0) {
            //获取无后缀名名称
            fileName = fileName.substr(0, fileName.lastIndexOf('.'));

            this.setValue("fileName", fileName);
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //拼接后缀名
        this.inputEntity.fileName = this.inputEntity.fileName + "." + $M.getExtension(this.inputEntity.filePath);
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