//定义数据一览画面JS类
SysApp.Pms.ProjectList = Class.create();

SysApp.Pms.ProjectList.prototype = Object.extend(new SysApp.Pms.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("名称", "name", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("地址", "address", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("报名开始时间", "entryStartDate", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("报名终了时间", "entryEndDate", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("踏勘开始时间", "explorationStartDate", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("开标时间", "bidDate", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("进场时间", "projectStartDate", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("流程节点", "workflowNode", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("子流程节点", "subWorkflowNode", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("企业", "enterpriseName", "100px", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO),
            new ColInfo("名称", "name", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("地址", "address", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("报名开始时间", "entryStartDate", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("报名终了时间", "entryEndDate", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("踏勘开始时间", "explorationStartDate", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("开标时间", "bidDate", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("进场时间", "projectStartDate", "100px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("实施能力", "exeCapability", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("预算价格", "budgetaryPrice", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("价格评估", "priceEvaluation", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("流程节点", "workflowNode", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("子流程节点", "subWorkflowNode", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("前项目", "preProjectName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("备注", "remark", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "name") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //报名开始时间
        else if (colName == "entryStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //报名终了时间
        else if (colName == "entryEndDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //踏勘开始时间
        else if (colName == "explorationStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //开标时间
        else if (colName == "bidDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        //进场时间
        else if (colName == "projectStartDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
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