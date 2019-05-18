// 定义数据输入画面JS类
SysApp.Sys.ContactInnerDetail = Class.create();

SysApp.Sys.ContactInnerDetail.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //Tab对象实例化
        this.initTab("MultiTabView", 3, this.tabOnChange);
    },

    //TAB切换
    tabOnChange: function (tabObj, tabIndex) {
    },

    initContactInfo: function (ContactEntity) {
        this.initInputForm(ContactEntity);
    },

    getDetail: function (uid) {
        if (this.isEmpty(uid)) return;

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "getDetail", this.getDetailCallback.bind(this), "uid=" + uid);
    },

    getDetailCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }
        this.inputEntity = JsonUtility.Parse(response.responseText);

        this.initInputForm(this.inputEntity);
    },

    hide: function () {
        this.displayDom("MultiTabView", false);
    }
});