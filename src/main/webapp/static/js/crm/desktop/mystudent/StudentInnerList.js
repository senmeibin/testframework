//定义数据一览画面JS类
SysApp.Crm.StudentInnerList = Class.create();

SysApp.Crm.StudentInnerList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    getPageSize: function () {
        return 50;
    },

    //初期化回调处理
    initCallback: function () {
        //iScroll初期化
        this.initIScroll();

        this.initCommonCallback();
        this.autoSearch = true;
        this.bindEvent("btnCustomSearch", "click", this.onClick_CustomSearch.bind(this));

        //设置检索按钮的DOM ID【只有this.dom.btnSearch设置有值回车检索才有效】
        this.dom.btnSearch = "btnCustomSearch";

        this.bindEvent("btnFollowupAdd", "click", this.showFollowupPopupInput.bind(this));
        this.bindEvent("btnReservationAdd", "click", this.showReservationPopupInput.bind(this));
        this.bindEvent("btnRegistrationAdd", "click", this.showRegistrationPopupInput.bind(this));
        this.bindEvent("btnPaymentAdd", "click", this.showPaymentPopupInput.bind(this));

        this.bindEvent("btnStudentAdd", "click", this.showStudentPopupInput.bind(this));
    },

    bindScheduleFollowup: function () {
        var thisObj = this;
        //绑定 待跟进学员
        $(".auto-match-position i.fa").click(function () {
            if ($(this).hasClass("fa-toggle-off")) {
                $(this).removeClass("fa-toggle-off").addClass("fa-toggle-on");
                //开启过滤
                if ($(this).hasClass("have-next-followup")) {
                    thisObj.setValue("nextFollowupDate", "IS NOT NULL");
                }
            } else {
                $(this).removeClass("fa-toggle-on").addClass("fa-toggle-off");
                //关闭过滤
                if ($(this).hasClass("have-next-followup")) {
                    thisObj.setValue("nextFollowupDate", "");
                }
            }
            thisObj.getList(true);
        });
    },

    //新增学员信息
    showStudentPopupInput: function () {
        //新增模式
        SysApp.Crm.StudentPopupInputIns.editMode = 0;
        SysApp.Crm.StudentPopupInputIns.inputEntity = {};
        //学员信息
        SysApp.Crm.StudentPopupInputIns.inputEntity.studentBelongCampusUid = window.loginUserUid;
        SysApp.Crm.StudentPopupInputIns.inputEntity.studentBelongConsultantUserUid = window.loginUserUid;

        //新增模式
        SysApp.Crm.StudentPopupInputIns.inputEntity.editMode = 0;
        SysApp.Crm.StudentPopupInputIns.show();
    },

    //新增跟进记录
    showFollowupPopupInput: function () {
        //取得当前学员Tab的学员信息
        var student = SysApp.Crm.DesktopIns.getCurrentStudent();

        SysApp.Crm.FollowupInputIns.inputEntity = {};
        //学员信息
        SysApp.Crm.FollowupInputIns.inputEntity.studentUid = student.uid;
        SysApp.Crm.FollowupInputIns.inputEntity.studentName = student.name;

        //新增模式
        SysApp.Crm.FollowupInputIns.inputEntity.editMode = 0;

        SysApp.Crm.FollowupInputIns.show();
    },

    //新增预约记录
    showReservationPopupInput: function () {
        //取得当前学员Tab的学员信息
        var student = SysApp.Crm.DesktopIns.getCurrentStudent();

        SysApp.Crm.ReservationInputIns.inputEntity = {};
        //学员信息
        SysApp.Crm.ReservationInputIns.inputEntity.studentUid = student.uid;
        SysApp.Crm.ReservationInputIns.inputEntity.studentName = student.name;

        //新增模式
        SysApp.Crm.ReservationInputIns.inputEntity.editMode = 0;

        SysApp.Crm.ReservationInputIns.show();
    },

    //新增报名记录
    showRegistrationPopupInput: function () {
        //取得当前学员Tab的学员信息
        var student = SysApp.Crm.DesktopIns.getCurrentStudent();

        SysApp.Crm.RegistrationInputIns.inputEntity = {};
        //学员信息
        SysApp.Crm.RegistrationInputIns.inputEntity.studentUid = student.uid;
        SysApp.Crm.RegistrationInputIns.inputEntity.studentName = student.name;

        //新增模式
        SysApp.Crm.RegistrationInputIns.inputEntity.editMode = 0;

        SysApp.Crm.RegistrationInputIns.show();
    },

    //新增缴费记录
    showPaymentPopupInput: function () {
        //取得当前学员Tab的学员信息
        var student = SysApp.Crm.DesktopIns.getCurrentStudent();

        SysApp.Crm.PaymentInputIns.inputEntity = {};
        //学员信息
        SysApp.Crm.PaymentInputIns.inputEntity.studentUid = student.uid;
        SysApp.Crm.PaymentInputIns.inputEntity.studentName = student.name;

        //新增模式
        SysApp.Crm.PaymentInputIns.inputEntity.editMode = 0;

        SysApp.Crm.PaymentInputIns.show();
    },

    //自定义查询
    onClick_CustomSearch: function () {
        this.onClick_Search();
    },

    //自定义显示
    customCreateList: function (json) {
        var html = "";
        var thisObj = this;
        this.studentList = json.content;
        //无数据场合
        if (this.studentList.length == 0) {
            this.$("divList").html("<div class='text-center data-not-exist'>暂无符合条件的学员信息</div>");
            this.$("divBottomPager").html("");
            return;
        }

        //循环处理学员信息
        this.studentList.each(function (student, index) {
            //基本信息
            var sexAgeInfo = "男";
            if (thisObj.isNotEmpty(student.genderName)) {
                sexAgeInfo = student.genderName;
            }
            if (thisObj.isNotEmpty(student.studentAge)) {
                sexAgeInfo = sexAgeInfo + "│" + student.studentAge + "岁";
            }

            //性别图标
            var genderIcon = "/static/images/crm/user-men.png";
            if (thisObj.isNotEmpty(student.genderName) && student.genderName == '女') {
                genderIcon = "/static/images/crm/user-women.png";
            }
            //居住地，工作，学历信息
            var studentBaseInfo = "";

            //家庭住址
            var address = joinStudentAddress(student);
            if (thisObj.isNotEmpty(address)) {
                studentBaseInfo = "<i data-title='家庭住址' class='title-prompt-box' data-placement='top'>" + address + " │ " + "</i>";
            }

            if (thisObj.isNotEmpty(student.baseLevelName)) {
                studentBaseInfo += student.baseLevelName;
            } else {
                studentBaseInfo += "未知";
            }

            if (thisObj.isNotEmpty(student.studentBelongCampusName)) {
                studentBaseInfo += " │ " + student.studentBelongCampusName;
            }
            if (thisObj.isNotEmpty(student.studentBelongConsultantUserName)) {
                studentBaseInfo += " │ " + student.studentBelongConsultantUserName;
            }
            if (thisObj.isNotEmpty(student.email)) {
                studentBaseInfo += " │ " + student.email;
            }

            //跟进次数
            studentBaseInfo += " │ 共跟进 <b>" + student.followupCount + "</b>次 ";

            //最近跟进记录存在的场合
            if (thisObj.isNotEmpty(student.contents)) {
                var saleProcessName = student.saleProcessName;
                if (thisObj.isNotEmpty(saleProcessName)) {
                    saleProcessName = "-" + saleProcessName;
                }
                var acceptShiftName = student.acceptShiftName;
                if (thisObj.isNotEmpty(acceptShiftName)) {
                    acceptShiftName = "-" + acceptShiftName;
                }

                studentBaseInfo += "<br/><b>最近跟进</b>[" + student.followupDate.formatMDHM() + "-" + student.followupMethodName + "]" + student.contents + saleProcessName + acceptShiftName;
                if (thisObj.isNotEmpty(student.nextFollowupDate)) {
                    //是否过期
                    var expired = new Date(student.nextFollowupDate).getTime() < new Date().getTime();
                    var color = "#0099CC";
                    if (expired) {
                        color = "#E4403F";
                    }

                    studentBaseInfo += " | <span style='color: " + color + "'><i class='fa fa-hand-o-right'></i>下次跟进时间：" + student.nextFollowupDate.formatMDHM() + "</span>";
                }
            }

            var mobile = student.mobile;

            //添加时间，更新时间，首次咨询时间，最近咨询时间
            var insertDate = student.insertDate.formatMDHM();
            var updateDate = student.updateDate.formatMDHM();
            var firstConsultTime = student.firstConsultTime.formatMDHM();
            var recentConsultTime = student.recentConsultTime.formatMDHM();

            var relationshipTypeName = "";
            if (thisObj.isNotEmpty(student.relationshipTypeName)) {
                relationshipTypeName = "[" + student.relationshipTypeName + "]";
            }

            html += "<li id='" + student.uid + "'>" +
                "   <h3>家长姓名：" + student.parentName + relationshipTypeName + "</h3>" +
                "   <label class='user-photo'>" +
                "       <img src='" + genderIcon + "'>" +
                "   </label>" +
                '   <div>' +
                "       <h4>" +
                "           <a href='#' class='hover-underline' onClick='" + thisObj.desktopInstance + ".getStudentInfo(\"" + student.uid + "\",this)'>" +
                "               <span class='title-prompt-box' data-placement='top' data-title='查看学员详情'>" + student.name + "</span>" +
                "           </a>" +
                "           <span>" + sexAgeInfo + "</span>" +
                "           <div>" +
                "               <i class='fa fa-plus-circle title-prompt-box' data-placement='top' data-title='添加时间'></i>" + insertDate +
                "               <i class='fa fa-refresh title-prompt-box' data-placement='top' data-title='更新日期'></i>" + updateDate;

            if (thisObj.isNotEmpty(firstConsultTime)) {
                html += "               <i class='fa fa-commenting title-prompt-box' data-placement='top' data-title='首次咨询时间'></i>" + firstConsultTime;
            }
            if (thisObj.isNotEmpty(recentConsultTime)) {
                html += "               <i class='fa fa-comments title-prompt-box' data-placement='top' data-title='最近咨询时间'></i>" + recentConsultTime;
            }

            var sourceTypeName = "";
            if (thisObj.isNotEmpty(student.sourceTypeName)) {
                sourceTypeName = " │ " + student.sourceTypeName;
            }

            var studentStatusName = "";
            if (thisObj.isNotEmpty(student.studentStatusName)) {
                studentStatusName = " │ " + student.studentStatusName;
            }

            html += "           </div>" +
                "       </h4>" +
                "       <p>" +
                "           <button class='call-phone'>" +
                "               <i class='fa fa-phone'></i>" +
                "           </button>" + mobile + " │ " + studentBaseInfo + "&nbsp;" +
                "       </p>" +
                "   </div>" +
                "   <div class='desktop-innner-list-lower-right-corner' id='" + student.uid + "'>" +
                "   </div>" +
                "   <div class='my-student-right-info'>" + student.consultMethodName + sourceTypeName + studentStatusName +
                "   </div>" +
                "</li>";
        });
        this.$("divList").html(html);
        this.datagrid.createPager(this.getDom("divBottomPager"));

        //滚动条
        if (window.iScrollStudentInnerList) {
            window.iScrollStudentInnerList.refresh();
        }

        //滚动条
        if (window.iScrollTaskInnerList) {
            window.iScrollTaskInnerList.refresh();
        }

        //Title提示框
        this.titlePromptBox();
    }
})