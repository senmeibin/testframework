//定义数据一览画面JS类
SysApp.Sys.AttachmentImportList = Class.create();

SysApp.Sys.AttachmentImportList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("应用名称", "appName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("模块名称", "moduleName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("文件名称", "fileName", "26%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入批次号", "batchNo", "8%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入者", "insertUserName", "6%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入日期", "insertDate", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入属性名1", "importAttributeName01", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入属性值1", "importAttributeValue01", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入结果", "importResult", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("应用编号", "appCode", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("模块名称", "moduleName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("文件名称", "fileName", "200px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("文件大小", "fileSize", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("MIME类型", "mimeType", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入批次号", "batchNo", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入者", "insertUserName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入日期", "insertDate", "120px", SORT_YES, ALIGN_CENTER, false, CAMEL_NO),
            new ColInfo("导入属性名1", "importAttributeName01", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性名2", "importAttributeName02", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性名3", "importAttributeName03", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性名4", "importAttributeName04", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性名5", "importAttributeName05", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性值1", "importAttributeValue01", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性值2", "importAttributeValue02", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性值3", "importAttributeValue03", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性值4", "importAttributeValue04", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入属性值5", "importAttributeValue05", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入结果", "importResult", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("导入详细结果", "importResultDetailMessage", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("备注", "remark", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            strHtml = "<button type='button' class='btn btn-default' onclick='" + this.selfInstance + ".download(\"" + rowData.uid + "\");return false;'><i class='fa fa-download fa-width-fixed'></i>下载</button>";

            var mimeType = $M.getExtension(rowData.fileName);
            if (this.filePreviewInstance.isEnable && (mimeType == 'doc' || mimeType == 'docx' || mimeType == 'xls' || mimeType == 'xlsx' || mimeType == 'ppt' || mimeType == 'pptx' || mimeType == 'txt' || mimeType == 'pdf')) {
                //创建预览按钮
                strHtml += "<button type='button' class='btn btn-default' onclick='SysApp.Cmn.FilePreviewIns.preview(\"" + rowData.uid + "\", \"1\");return false;'><i class='fa fa-eye fa-width-fixed'></i>预览</button>";
            }

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "fileName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "appName") {
            strHtml = rowData.appName + "[" + rowData.appCode + "]";
        }
        else if (colName == "insertDate") {
            strHtml = rowData[colName].formatYMDHM();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    download: function (uid) {
        window.open("/sys/attachmentimport/download?uid=" + uid);
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