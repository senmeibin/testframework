// 定义数据输入画面JS类
SysApp.Sys.LoginLogPopupList = Class.create();

SysApp.Sys.LoginLogPopupList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        //默认不查询
        this.autoSearch = false;

        this.popupPosition = "firstTop";
    },

    //检索条件Entity自定义设置[检索条件Entity取得之后]
    customSearchEntity: function () {
        this.searchEntity["main.insertDate$from_search"] = this.parentInstance.getValue("insertDate$from_search");
        this.searchEntity["main.insertDate$to_search"] = this.parentInstance.getValue("insertDate$to_search");
    },

    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("登录日期", "insertDate", "17%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("用户名", "userCd", "20%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "20%", SORT_YES));
        this.colList.push(new ColInfo("登录IP", "remoteIp", "20%", SORT_YES));
        this.colList.push(new ColInfo("登录类型", "loginTypeName", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("登录结果", "loginResultName", "10%", SORT_YES, ALIGN_CENTER));

        this.datagrid.cssClass = "app-table-list";
    }
});