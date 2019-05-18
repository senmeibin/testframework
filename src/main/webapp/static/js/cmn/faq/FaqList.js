//定义数据一览画面JS类
SysApp.Cmn.FaqList = Class.create();

SysApp.Cmn.FaqList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        //只读时，隐藏新增
        if (this.isReadonly) {
            this.displayDom("appCodeSearchDiv", false);
        }
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        if (!this.isReadonly) {
            this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
            this.colList.push(new ColInfo("应用名称/模块名称", "appName", "10%", SORT_YES));
            this.colList.push(new ColInfo("问题描述", "question", "29%", SORT_YES));
            this.colList.push(new ColInfo("问题解答", "answer", "29%", SORT_YES));
            this.colList.push(new ColInfo("适用角色", "applicableRole", "10%", SORT_YES));
            this.colList.push(new ColInfo("责任人", "responsibleRole", "10%", SORT_YES));
            this.colList.push(new ColInfo("备注", "remark", "4%", SORT_YES, ALIGN_CENTER));
        }
        else {
            this.colList.push(new ColInfo("问题描述", "question", "38%", SORT_YES));
            this.colList.push(new ColInfo("问题解答", "answer", "36%", SORT_YES));
            this.colList.push(new ColInfo("适用角色", "applicableRole", "10%", SORT_YES));
            this.colList.push(new ColInfo("责任人", "responsibleRole", "10%", SORT_YES));
            this.colList.push(new ColInfo("备注", "remark", "4%", SORT_YES, ALIGN_CENTER));
        }

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);
        }
        else if (colName == "appName") {
            strHtml = rowData[colName] + "<br/>" + rowData.moduleName;
        }
        else if (colName == "question") {
            strHtml = $M.enterToBr(rowData[colName]);
        }
        else if (colName == "answer") {
            strHtml = $M.enterToBr(rowData[colName]);
        }
        else if (colName == "remark") {
            strHtml = this.createRemarkColumn(rowData);
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

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});