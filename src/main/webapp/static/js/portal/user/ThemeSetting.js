// 定义数据输入画面JS类
SysApp.Portal.ThemeSetting = Class.create();

SysApp.Portal.ThemeSetting.prototype = Object.extend(new SysApp.Portal.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.saveMethod = "saveTheme";

        this.initCommonCallback();

        var thisObj = this;

        $("a", ".theme-setting").each(function () {
            if ($(this).data("theme") == thisObj.themeName) {
                $(this).addClass("theme-setting-selected");
            }
        });

        $("a", ".theme-setting").click(function () {
            $("a", ".theme-setting").each(function () {
                $(this).removeClass("theme-setting-selected");
            });

            thisObj.themeName = $(this).data("theme");
            $(this).addClass("theme-setting-selected");
        });

        this.showSaveDataSuccessMessage = false;

        this.saveConfirmMessage = "确定要设置个人主题吗？";
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "themeName=" + this.themeName;
    }
});