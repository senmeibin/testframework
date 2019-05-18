//定义数据一览画面JS类
SysApp.Sys.SyncEntityList = Class.create();

SysApp.Sys.SyncEntityList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("类名称", "entityClassName", "25%", SORT_YES));
        this.colList.push(new ColInfo("类路径", "entityClassPath", "37%", SORT_YES));
        this.colList.push(new ColInfo("备注", "remark", "28%", SORT_YES));

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

            //创建操作日志图标
            strHtml += this.createOperationLogButton(rowData);

            //同步对象实体类 不能进行删除
            if (rowData.entityClassPath != "com.bpms.sys.entity.ext.SyncEntityExt") {
                //创建列表删除图标
                strHtml += this.createDeleteButton(rowNo, false, "delete");
            }

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    createOperationLogButton: function (rowData) {
        return "<button type='button' class='btn btn-default' onclick='" + this.selfInstance + ".showOperationLogDetail(\"" + rowData.entityClassPath + "\", 1);return false;'><i class='fa fa-list'></i>&nbsp;同步日志</button>";
    },

    showOperationLogDetail: function (entityClassName) {
        var operationLogListIns = SysApp.Sys.OperationLogListIns;
        operationLogListIns.setSearchCondition(entityClassName);
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