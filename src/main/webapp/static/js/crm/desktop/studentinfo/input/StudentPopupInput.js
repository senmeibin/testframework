//定义数据一览画面JS类
SysApp.Crm.StudentPopupInput = Class.create();

SysApp.Crm.StudentPopupInput.prototype = Object.extend(new SysApp.Crm.StudentInput(), {
    //数据保存后回调
    saveDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功
        if (ajaxResult.result > 0) {
            this.hideMessage();

            this.hide();

            //编辑模式的场合，重新设置学员信息
            if (this.editMode == 1) {
                SysApp.Crm.DesktopIns.setStudentInfo(this.inputEntity);
            }
            else {
                //添加模式的场合，刷新学员列表
                SysApp.Crm.StudentInnerListIns.getList(true);
            }
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        this.saveConfirmMessage = "学员信息保存后将会重新加载学员详情，确定要保存吗？";

        //滚动条
        if (window.iScrollStudentPopupInput) {
            $(".iscroll-wrapper", this.getMainContentClassName()).height($(window).height() - 108);
            window.iScrollStudentPopupInput.refresh();
        }

        if (this.isNotEmpty(this.inputEntity.birthday)) {
            this.setValue("birthday", this.inputEntity.birthday.formatYM());
            this.initCalendar();
        }
    }
});