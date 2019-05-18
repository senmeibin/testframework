// 定义数据输入画面JS类
SysApp.Sys.DatabaseManagerTab = Class.create();

SysApp.Sys.DatabaseManagerTab.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        //Tab对象实例化
        this.initTab("DatabaseManager", 2, this.tabOnChange, this.tabNum);
    },

    //TAB切换
    tabOnChange: function (tabObj, tabIndex) {
        //进程信息
        if (tabIndex == 0) {
            SysApp.Sys.DatabaseProcessInfoListIns.getList(true);
        }
        //操作日志
        else if (tabIndex == 1) {
            SysApp.Sys.DatabaseProcessOperationLogListIns.getList(true);
        }
    }
});