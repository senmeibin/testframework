//定义学员详情画面共通JS类
SysApp.Crm.StudentInfoCommon = Class.create();

SysApp.Crm.StudentInfoCommon.prototype = Object.extend(new SysApp.Crm.CommonInput(), {
    //创建学员信息
    createStudentInfo: function (student) {
        this.$("studentBaseInfo").empty();

        //家庭住址
        var address = joinStudentAddress(student);

        //性别图标
        var genderIcon = "/static/images/crm/user-men.png";
        if (this.isNotEmpty(student.genderName) && student.genderName == '女') {
            genderIcon = "/static/images/crm/user-women.png";
        }
        var relationshipTypeName = "";
        if (this.isNotEmpty(student.relationshipTypeName)) {
            relationshipTypeName = "[" + student.relationshipTypeName + "]";
        }
        var html = "<h4>" +
            "   <img src='" + genderIcon + "'>" +
            "   <strong>" + student.name + "[" + student.genderName + "]</strong>" +
            "</h4>" +
            "<div class='active'>" +
            "   <ul>" +
            "       <li>" +
            "           <label>家长姓名：</label><strong>" + student.parentName + relationshipTypeName + "</strong>" +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <i class='fa fa-phone'></i>&nbsp;" +
            "           <strong>" + student.mobile + "</strong>" +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <i class='fa fa-envelope-o'></i>&nbsp;" +
            "           <strong>" + student.email + "</strong>" +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>学员状态：</label>" + student.studentStatusName +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>围棋基础：</label>" + student.baseLevelName +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>学员年龄：</label>" + student.studentAge + "岁[" + student.birthday.formatYM() + "]" +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>所属校区：</label>" + student.studentBelongCampusName +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>课程顾问：</label>" + student.studentBelongConsultantUserName +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>咨询方式：</label>" + student.consultMethodName +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>信息来源：</label>" + student.sourceTypeName +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>家庭住址：</label>" + address +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>之前学校：</label>" + student.beforeSchool +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>证件类型：</label>" + student.cardTypeName +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>证件号码：</label>" + student.cardNumber +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>跟进次数：</label>" + student.followupCount + "次" +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>首次咨询：</label>" + student.firstConsultTime +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>最近咨询：</label>" + student.recentConsultTime +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>创建时间：</label>" + student.insertDate +
            "       </li>" +
            "   </ul>" +
            "   <ul>" +
            "       <li>" +
            "           <label>备注信息：</label>" + student.remark +
            "       </li>" +
            "   </ul>" +
            "</div>" +
            "<div class='student-detail-edit'>" +
            "   <button class='btn btn-link' type='button' onclick='" + this.selfInstance + ".showStudentPopupInput();return false;' style='margin-top: 22px;'>" +
            "       <i class='fa fa-edit title-prompt-box' data-placement='top' data-title='编辑学员信息'></i>" +
            "   </button>" +
            "</div>";

        this.$("studentBaseInfo").prepend(html);
    },

    //获取划转记录
    getStudentAssignLogListAjax: function (thisObj) {
        var studentUid = thisObj.currentStudentUid;
        //划转记录不为空场合
        if (this.isNotEmpty(thisObj.studentInfoMap) && this.isNotEmpty(thisObj.studentInfoMap[studentUid].studentAssignLogList)) {
            this.createStudentAssignLogList(thisObj.studentInfoMap[studentUid].studentAssignLogList);
            return false;
        }

        var params = "studentUid=" + studentUid;
        //Ajax处理前显示提示画面
        this.showProcessing();

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.studentAssignLogController + "getStudentAssignLogList", this.getStudentAssignLogListCallback.bind(this, studentUid, thisObj), params);
    },

    //获取划转记录回调
    getStudentAssignLogListCallback: function (studentUid, thisObj, response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合
        if (ajaxResult.result > 0) {
            this.hideMessage();
            var studentAssignLogList = ajaxResult.content;

            if (this.isNotEmpty(thisObj.studentInfoMap)) {
                //更新前端划转记录信息
                thisObj.studentInfoMap[studentUid].studentAssignLogList = studentAssignLogList;
            }

            this.createStudentAssignLogList(studentAssignLogList);
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    },

    //设置划转记录
    createStudentAssignLogList: function (studentAssignLogList) {
        this.$("studentAssignLogList").empty();
        if (studentAssignLogList.length < 1) {
            this.$("studentAssignLogList").prepend('<li><div class="text-center" style="color:#ccc">暂无划转记录</div></li>');
            return false;
        }
        var studentAssignLogHtml = "";
        for (var i = 0; i < studentAssignLogList.length; i++) {
            var beforeUserName = this.isEmpty(studentAssignLogList[i].beforeUserName) ? "划入" : studentAssignLogList[i].beforeUserName;
            var afterUserName = this.isEmpty(studentAssignLogList[i].afterUserName) ? "划出" : studentAssignLogList[i].afterUserName;

            studentAssignLogHtml +=
                '<li>' +
                '   <div class="recommend-record-right" style="width: 44%;">' +
                '       <span>' + studentAssignLogList[i].insertDate.formatMDHM() + '</span>' +
                '       <em class="title-prompt-box" data-placement="top" data-title="划转类型">' + studentAssignLogList[i].assignTypeName + '</em>' +
                '   </div>' +
                '   <div class="recommend-record-left">' +
                '           <i class="title-prompt-box" data-placement="top" data-title="划转前课程顾问">' + beforeUserName + '</i>' +
                '           <i class="fa fa-long-arrow-right" style="padding: 0 10px;color: #00c309;"></i>' +
                '           <a href="#" class="title-prompt-box" data-placement="top" data-title="划转后课程顾问">' + afterUserName + '</a>' +
                '   </div>' +
                '</li>';
        }

        this.$("studentAssignLogList").prepend(studentAssignLogHtml);

        //操作台-左侧-滚动条-刷新
        window.iScrollStudentInfo.refresh();

        if (this.isNotNull(window.iScrollStudentInfoRight)) {
            //人才库学员详情右侧滚动
            window.iScrollStudentInfoRight.refresh();
        }

        this.titlePromptBox();
    },

    //事件加载列表
    initClickLoadList: function () {
        //清空划转记录
        this.$("studentAssignLogList").empty();
        //隐藏划转记录
        $(".student-assign-log-evt").next().removeClass("active");
        $(".student-assign-log-evt").children("em").removeClass("fa-angle-down").addClass("fa-angle-right");
    },
});