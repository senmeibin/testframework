//定义数据一览画面JS类
SysApp.Crm.CourseScheduleInnerList = Class.create();

SysApp.Crm.CourseScheduleInnerList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        //iScroll初期化
        this.initIScroll();

        this.initCommonCallback();
        this.autoSearch = false;
        this.bindEvent("btnCustomSearch", "click", this.onClick_CustomSearch.bind(this));

        //设置检索按钮的DOM ID【只有this.dom.btnSearch设置有值回车检索才有效】
        this.dom.btnSearch = "btnCustomSearch";
    },

    //自定义查询
    onClick_CustomSearch: function () {
        this.onClick_Search();
    }
})