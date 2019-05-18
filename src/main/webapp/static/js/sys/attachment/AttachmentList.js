//定义数据一览画面JS类
SysApp.Sys.AttachmentList = Class.create();

SysApp.Sys.AttachmentList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("应用名称", "appName", "10%", SORT_YES));
        this.colList.push(new ColInfo("模块名称", "moduleName", "8%", SORT_YES));
        this.colList.push(new ColInfo("文件名称", "fileName", "37%", SORT_YES));
        this.colList.push(new ColInfo("上传日期", "insertDate", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("上传者", "insertUserName", "7%", SORT_YES));
        this.colList.push(new ColInfo("文件大小", "fileSize", "6%", SORT_YES, ALIGN_RIGHT));
        this.colList.push(new ColInfo("预览次数", "previewCount", "6%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));
        this.colList.push(new ColInfo("下载次数", "downloadCount", "6%", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    onUploadSuccess: function (file, data, response) {
        this.getList(true);
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            strHtml += "<button type='button' class='btn btn-default' onclick='" + this.selfInstance + ".download(\"" + rowData.uid + "\");return false;'><i class='fa fa-download fa-width-fixed'></i>下载</button>";

            var mimeType = $M.getExtension(rowData.fileName);

            //如果是图片格式 支持在线预览 （临时）
            if (mimeType == 'png' || mimeType == 'gif' || mimeType == 'jpg' || mimeType == 'jpeg' || mimeType == 'bmp') {
                strHtml += "<button type='button' class='btn btn-default' onclick='" + this.selfInstance + ".preview(\"" + rowData.uid + "\");return false;'><i class='fa fa-eye fa-width-fixed'></i>预览</button>";
            }
            else if (this.filePreviewInstance.isEnable && (mimeType == 'doc' || mimeType == 'docx' || mimeType == 'xls' || mimeType == 'xlsx' || mimeType == 'ppt' || mimeType == 'pptx' || mimeType == 'txt' || mimeType == 'pdf')) {
                //创建预览按钮
                strHtml += "<button type='button' class='btn btn-default' onclick='SysApp.Cmn.FilePreviewIns.preview(\"" + rowData.uid + "\");return false;'><i class='fa fa-eye fa-width-fixed'></i>预览</button>";
            }

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "fileName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "previewCount" || colName == "downloadCount") {
            if (rowData[colName] == 0 || this.isEmpty(rowData[colName])) {
                return 0;
            }
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

    preview: function (uid) {
        window.open("/sys/attachment/preview?uid=" + uid);
    },

    download: function (uid) {
        window.open("/sys/attachment/download?uid=" + uid);
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