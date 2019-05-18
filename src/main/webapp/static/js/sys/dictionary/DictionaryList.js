//定义数据一览画面JS类
SysApp.Sys.DictionaryList = Class.create();

SysApp.Sys.DictionaryList.prototype = Object.extend(new SysApp.Sys.CommonList(), {

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("大区分CD", "mainCd", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("大区分名称", "mainName", "15%", SORT_NO));
        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("小区分CD", "subCd", "7%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("小区分名称", "subName", "20%", SORT_NO));
        this.colList.push(new ColInfo("字典状态", "recordStatus", "6%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("表示顺序", "dispSeq", "6%", SORT_NO, ALIGN_RIGHT));
        this.colList.push(new ColInfo("备注", "remark", "28%", SORT_NO, ALIGN_LEFT));

        this.exportFileName = "数据字典";

        this.datagrid.useRowClickEvent = false;
        this.datagrid.rowSpanCallback = this.rowSpanCallback;
        this.datagrid.isSubsequentRowCallback = this.isSubsequentRowCallback;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //行合并Callback
    rowSpanCallback: function (rowData, colName, rowNo) {
        if (colName == "mainCd") {
            var span = 0;
            for (var i = 0; i < this.data.length; i++) {
                if (this.data[i].mainCd == rowData.mainCd) {
                    span++;
                }
            }
            return span;
        }
        if (colName == "mainName") {
            var span = 0;
            for (var i = 0; i < this.data.length; i++) {
                if (this.data[i].mainCd == rowData.mainCd) {
                    span++;
                }
            }
            return span;
        }
    },

    //是否是合并行的后续行Callback
    isSubsequentRowCallback: function (rowData, colName, rowNo) {
        if (rowNo == 0) return false;

        //与前行的大类相同的场合
        if (colName == "mainName" && rowData.mainCd == this.data[rowNo - 1].mainCd) {
            return true;
        }

        //与前行的大类相同的场合
        if (colName == "mainCd" && rowData.mainCd == this.data[rowNo - 1].mainCd) {
            return true;
        }
    },

    //数据导入
    onClick_Import: function () {
        window.location = this.importUrl;
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);
            strHtml += this.createCopyButton(rowNo);

            if (rowData.recordStatus == 1) {
                strHtml += this.createLockButton(rowNo, false, "delete");
            }
            else {
                strHtml += this.createUnlockButton(rowNo, false, "delete");
            }

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo, false, "delete");

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
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

    //创建列表复制按钮
    createCopyButton: function (rowNo, disabled) {
        if (disabled == true) {
            return "<button type='button' class='btn btn-primary' disabled='true'><i class='fa fa-copy fa-width-fixed'></i>复制</button>";
        }
        return "<button type='button' class='btn btn-primary' onclick='" + this.selfInstance + ".onClick_CopyRow(" + rowNo + ");return false;'><i class='fa fa-copy fa-width-fixed'></i>复制</button>";
    },

    onClick_CopyRow: function (index) {
        var json = this.getRowData(index);
        //行数据设定
        this.inputInstance.inputEntity = $.extend(true, {}, json);
        //清空UID主键
        this.inputInstance.inputEntity.uid = "";
        //列表画面JS对象
        this.inputInstance.listInstance = this;
        //当前编辑数据索引位置
        this.inputInstance.listDataIndex = -1;

        this.inputInstance.show(this.refreshList.bind(this), 0);
    },

    /** ********************以下方法为可选方法********************* */
    initCallback: function () {
        this.initCommonCallback();

        this.autoHideFlashMessage = true;
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