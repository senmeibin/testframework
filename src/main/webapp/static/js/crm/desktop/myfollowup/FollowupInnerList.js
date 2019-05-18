//定义数据一览画面JS类
SysApp.Crm.FollowupInnerList = Class.create();

SysApp.Crm.FollowupInnerList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //分页显示的记录数[注意：必须实现的方法]
    getPageSize: function () {
        return 50;
    },

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
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "3%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("家长姓名", "studentParentName", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("学员姓名", "studentName", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("手机号码", "studentMobile", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("围棋基础", "studentBaseLevelName", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        this.colList.push(new ColInfo("跟进时间", "followupDate", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("跟进方式", "followupMethodName", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("跟进内容", "contents", "31%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("销售进程", "saleProcessName", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("是否预约", "acceptShiftName", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("下次跟进时间", "nextFollowupDate", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

        this.datagrid.divBottomPager = this.getDom("divBottomPager");
        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.headerTextAlignMode = "autoWithColInfo";
        this.datagrid.showTopPager = false;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditIcon(rowNo);
        }
        //学员姓名
        else if (colName == "studentName") {
            strHtml = "<a href='#' class='detail-link' onClick='" + this.desktopInstance + ".getStudentInfo(\"" + rowData.studentUid + "\",this);return false;'>" +
                "<span class='title-prompt-box' data-placement='top' data-title='查看学员详情'>" + rowData[colName] + "</span>" +
                "</a>";
        }
        //POPUP详细表示
        else if (colName == "contents") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //跟进时间
        else if (colName == "followupDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHM();
        }
        //下次跟进时间
        else if (colName == "nextFollowupDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHM();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //表格创建后处理
    createListAfter: function () {
        if (window.iScrollFollowupInnerList) {
            window.iScrollFollowupInnerList.refresh();
        }

        //Title提示框
        this.titlePromptBox();
    }
})