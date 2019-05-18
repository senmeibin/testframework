SysApp.Pms.Default = Class.create();


SysApp.Pms.Default.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        //共通Property初期化
        this.initCommonProperty();
    },

    initDefault: function () {
        $("div", ".dashboard-info-block").mouseover(function () {
            $(this).css("opacity", "1");
        })

        $("div", ".dashboard-info-block").mouseout(function () {
            $(this).css("opacity", "0.85");
        })
        //显示问候语
        this.showGreetings();
    },

    showGreetings: function () {
        var now = new Date(), hour = now.getHours(), greetings = "";
        if (hour < 6) {
            greetings = "凌晨好，";
        }
        else if (hour < 9) {
            greetings = "早上好，";
        }
        else if (hour < 12) {
            greetings = "上午好，";
        }
        else if (hour < 14) {
            greetings = "中午好，";
        }
        else if (hour < 17) {
            greetings = "下午好，";
        }
        else if (hour < 19) {
            greetings = "傍晚好，";
        }
        else if (hour < 22) {
            greetings = "晚上好，";
        }
        else {
            greetings = "夜里好，";
        }
        this.setValue("greetingsSpan", greetings);
    }
});
