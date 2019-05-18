//定义数据一览画面JS类
SysApp.Sys.ParameterList = Class.create();

SysApp.Sys.ParameterList.prototype = Object.extend(new SysApp.Sys.CommonList(), {

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();
        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("系统编号", "systemCode", "8%", SORT_YES));
        this.colList.push(new ColInfo("应用名称", "appName", "12%", SORT_YES));
        this.colList.push(new ColInfo("参数名称", "name", "20%", SORT_YES));
        this.colList.push(new ColInfo("参数值", "value", "20%", SORT_YES));
        this.colList.push(new ColInfo("参数描述", "description", "20%", SORT_YES));
        this.colList.push(new ColInfo("参数状态", "recordStatus", "6%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("备注", "remark", "4%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //数据导入
    onClick_Import: function () {
        window.location = this.importUrl;
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);
            //停用/启用按钮
            if (rowData.recordStatus == 1) {
                strHtml += this.createLockButton(rowNo);
            }
            else {
                strHtml += this.createUnlockButton(rowNo);
            }

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo, false, "delete");

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        else if (colName == "appName") {
            strHtml = rowData.appName + "[" + rowData.appCode + "]";
        }
        else if (colName == "remark") {
            strHtml = this.createRemarkColumn(rowData);
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<span style='color: red;'>停用</span>";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    /** ********************以下方法为可选方法********************* */
    initCallback: function () {
        this.initCommonCallback();
    },

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