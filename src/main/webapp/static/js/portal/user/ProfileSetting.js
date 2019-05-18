// 定义数据输入画面JS类
SysApp.Portal.ProfileSetting = Class.create();

SysApp.Portal.ProfileSetting.prototype = Object.extend(new SysApp.Portal.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.saveMethod = "saveProfile";

        this.initCommonCallback();
    }
});