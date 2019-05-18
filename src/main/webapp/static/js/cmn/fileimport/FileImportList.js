//定义数据一览画面JS类
SysApp.Cmn.FileImportList = Class.create();

SysApp.Cmn.FileImportList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    initCallback: function () {
        this.initCommonCallback();
        this.$("ctlTitle").html("【" + this.moduleName + "】数据导入日志");
        //防止导入详细结果过大， 不使用标准search方法
        this.searchMethod = "searchWithShortDetailMessage";
        //如果显示查询条件， 设置查询日期的起始导入日期为三个月前， 默认查自己导入的
        if (this.showSearchCondition) {
            //默认登录人
            this.setValue("userName", window.loginUserName);
            //导入日期开始值
            this.setValue("insertDate$from_search", new Date().addDate("MONTH", -3).formatYMD());
        }
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();
        this.colList = new Array();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("文件名称", "fileName", "27%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入批次号", "batchNo", "6%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入结果", "importResult", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入者", "insertUserName", "4%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入日期", "insertDate", "11%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入属性1", "importAttributeName01", "13%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入属性2", "importAttributeName02", "11%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("导入属性3", "importAttributeName03", "11%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";

        this.bindEvent("btnShowFileImportList", "click", this.showFileImportList.bind(this));

        this.autoSearch = false;
        this.popupPosition = "firstTop";
        this.datagrid.showTopPager = false;
    },

    showFileImportList: function () {
        this.show();
        this.getList(true);
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
        else if (colName == "importAttributeName01" || colName == "importAttributeName02" || colName == "importAttributeName03") {
            if (this.isNotEmpty(rowData[colName])) {
                strHtml = rowData[colName] + "：" + rowData[colName.replaceAll("Name", "Value")];
            }
        }
        //POPUP详细表示
        else if (colName == "fileName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo) + "（" + rowData.fileSize + "）";
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

    //检索条件Entity自定义设置[检索条件Entity取得之后]
    customSearchEntity: function () {
        this.searchEntity["main.appCode"] = this.appCode;
        this.searchEntity["main.moduleName"] = this.moduleName;
    },

    download: function (uid) {
        window.open(this.controller + "download?uid=" + uid);
    }
});