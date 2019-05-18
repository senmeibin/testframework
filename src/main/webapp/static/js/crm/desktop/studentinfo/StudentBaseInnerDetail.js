//定义数据一览画面JS类
SysApp.Crm.StudentBaseInnerDetail = Class.create();

SysApp.Crm.StudentBaseInnerDetail.prototype = Object.extend(new SysApp.Crm.StudentInfoCommon(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //创建学员信息
    createStudentDetail: function (student) {
        //创建学员信息
        this.createStudentInfo(student);

        //事件加载列表
        this.initClickLoadList();

        //Title提示框
        this.titlePromptBox();

        //操作台-左侧-滚动条-刷新
        window.iScrollStudentInfo.refresh();
    },

    //显示学员基础信息编辑
    showStudentPopupInput: function () {
        //编辑模式
        SysApp.Crm.StudentPopupInputIns.editMode = 1;
        //清空画面历史数据
        SysApp.Crm.StudentPopupInputIns.clearForm();
        SysApp.Crm.StudentPopupInputIns.inputEntity = SysApp.Crm.DesktopIns.studentInfoMap[SysApp.Crm.DesktopIns.currentStudentUid];
        //显示编辑学员基础页面popup
        SysApp.Crm.StudentPopupInputIns.show(null, 1);
    },

    //学员划出后操作
    assignOutAfter: function () {
        //关闭学员tab
        SysApp.Crm.DesktopIns.closeStudentInfoTab(this, SysApp.Crm.DesktopIns.currentStudentUid);
        //刷新人才库
        SysApp.Crm.ResumeBelongInnerListIns.getList(false);
    },

    /**********************以下方法为可选方法**********************/
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