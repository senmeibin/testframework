//定义数据一览画面JS类
SysApp.Pms.ResearchInfoCommentPopupList = Class.create();

SysApp.Pms.ResearchInfoCommentPopupList.prototype = Object.extend(new SysApp.Pms.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.autoSearch = false;
    },

    //Override父类方法
    onClick_Add: function () {
        this.inputInstance.inputEntity = {};
        this.inputInstance.setResearchInfo(this.researchInfo);
        this.inputInstance.show();
    },

    //市场调研信息
    setResearchInfo: function (researchInfo) {
        this.researchInfo = researchInfo;

        this.setValue("researchInfoName", researchInfo.researchInfoName);
        this.setValue("researchInfoTitle", researchInfo.title);
        this.setValue("researchInfoUid", researchInfo.uid);
        this.$("ctlTitle").html("【" + researchInfo.title + "】的评论一览");
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("评论内容", "content", "30%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("评论者", "updateUserName", "7%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("评论时间", "updateDate", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("备注", "remark", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createCommentButton(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "content") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //评论时间
        else if (colName == "updateDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMDHM();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },
    //创建编辑图标
    createCommentButton: function (rowNo, disabled, className) {
        return "<button type='button' class='btn btn-primary" + this.getButtonClass(className) + "' onclick='" + this.selfInstance + ".showCommentInput(\"" + rowNo + "\");return false;'><i class='fa fa-pencil fa-width-fixed'></i>编辑</button>";
        //return "<button type='button' class='btn btn-primary" + this.getButtonClass(className) + "' onclick='" + this.selfInstance + ".showCommentInput(\"" + rowNo + "\");return false;'><i class='fa fa-plus fa-width-fixed'></i>评论</button>";
    },
    showCommentInput: function (rowNo) {
        this.goInput(rowNo);
        this.inputInstance.setResearchInfo(this.researchInfo);
        return false;

    },
    /** ********************以下方法为可选方法********************* */
    //画面Dom值自定义设置[检索条件Entity取得之前]
    customDomValue: function () {

    },

    //检索条件Entity自定义设置[检索条件Entity取得之后]
    customSearchEntity: function () {

    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});