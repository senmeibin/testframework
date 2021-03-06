//定义数据一览画面JS类
SysApp.Pms.ResearchInfoList = Class.create();

SysApp.Pms.ResearchInfoList.prototype = Object.extend(new SysApp.Pms.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("类别", "researchInfoName", "11%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("标题", "title", "11%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("有效开始日期", "effectStartDate", "11%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("有效终了日期", "effectEndDate", "11%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("流程节点", "workflowNode", "11%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("项目", "projectName", "11%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("备注", "remark", "11%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("评论次数", "commentCount", "11%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));

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
            strHtml = this.createEditButton(rowNo);

            //创建列表评论图标
            strHtml += this.createCommentButton(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //评论次数
        else if (colName == "commentCount") {
            strHtml = "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showCommentList(" + rowNo + ");return false;'>" + rowData[colName] + "次</a>";
        }
        //POPUP详细表示
        else if (colName == "title") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //有效开始日期
        else if (colName == "effectStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //有效终了日期
        else if (colName == "effectEndDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    showCommentInput: function (rowNo) {
        SysApp.Pms.ResearchInfoCommentInputIns.inputEntity = {};
        SysApp.Pms.ResearchInfoCommentInputIns.setResearchInfo(this.getRowData(rowNo));
        SysApp.Pms.ResearchInfoCommentInputIns.show();
    },

    showCommentList: function (rowNo) {
        SysApp.Pms.ResearchInfoCommentPopupListIns.setResearchInfo(this.getRowData(rowNo));
        SysApp.Pms.ResearchInfoCommentPopupListIns.show();
        SysApp.Pms.ResearchInfoCommentPopupListIns.getList(true);
    },
    //创建评论按钮
    createCommentButton: function (rowNo, disabled, className) {
        return "<button type='button' class='btn btn-primary" + this.getButtonClass(className) + "' onclick='" + this.selfInstance + ".showCommentInput(\"" + rowNo + "\");return false;'><i class='fa fa-plus fa-width-fixed'></i>评论</button>";
    },
    /** ********************以下方法为可选方法********************* */
    //画面Dom值自定义设置[检索条件Entity取得之前]
    customDomValue: function () {

    },

    //检索条件Entity自定义设置[检索条件Entity取得之后]
    customSearchEntity: function () {

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