//定义数据一览画面JS类
SysApp.Cmn.FileUploadList = Class.create();

SysApp.Cmn.FileUploadList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();
        this.colList = new Array();

        var fileName = "文件名称";
        if (this.isThumbnail) {
            fileName = "缩略图";
        }

        if (this.showSimpleList) {
            if (this.showOperationColumn) {
                this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO, ALIGN_CENTER));
                this.colList.push(new ColInfo(fileName, "fileName", "54%", SORT_YES));
            }
            else {
                this.colList.push(new ColInfo(fileName, "fileName", "64%", SORT_YES));
            }
            this.colList.push(new ColInfo("上传人", "insertUserName", "8%", SORT_YES, ALIGN_CENTER));
            this.colList.push(new ColInfo("上传日期", "insertDate", "16%", SORT_YES, ALIGN_CENTER));
            this.colList.push(new ColInfo("预览/下载数", "previewCount", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
        }
        else {
            if (this.showRemarkColumn) {
                if (this.showOperationColumn) {
                    this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
                    this.colList.push(new ColInfo(fileName, "fileName", "24%", SORT_YES));
                }
                else {
                    this.colList.push(new ColInfo(fileName, "fileName", "32%", SORT_YES));
                }
                this.colList.push(new ColInfo("文件分类", "moduleName", "6%", SORT_YES, ALIGN_CENTER));
                this.colList.push(new ColInfo("上传人", "insertUserName", "6%", SORT_YES, ALIGN_CENTER));
                this.colList.push(new ColInfo("上传日期", "insertDate", "12%", SORT_YES, ALIGN_CENTER));
                this.colList.push(new ColInfo("文件大小", "fileSize", "6%", SORT_YES, ALIGN_RIGHT));
                this.colList.push(new ColInfo("文件描述", "remark", "26%", SORT_YES));
                this.colList.push(new ColInfo("预览/下载数", "previewCount", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
            }
            else {
                if (this.showOperationColumn) {
                    this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
                    this.colList.push(new ColInfo(fileName, "fileName", "35%", SORT_YES));
                }
                else {
                    this.colList.push(new ColInfo(fileName, "fileName", "43%", SORT_YES));
                }
                this.colList.push(new ColInfo("文件分类", "moduleName", "7%", SORT_YES, ALIGN_CENTER));
                this.colList.push(new ColInfo("上传人", "insertUserName", "7%", SORT_YES, ALIGN_CENTER));
                this.colList.push(new ColInfo("上传日期", "insertDate", "17%", SORT_YES, ALIGN_CENTER));
                this.colList.push(new ColInfo("文件大小", "fileSize", "10%", SORT_YES, ALIGN_RIGHT));
                this.colList.push(new ColInfo("预览/下载数", "previewCount", "10%", SORT_YES, ALIGN_CENTER, false, CAMEL_NO));
            }
        }


        this.datagrid.showTopPager = false;
        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //参照UID集合设定
    setReferenceUids: function (referenceUids) {
        if (this.isNotEmpty(referenceUids)) {
            this.setValue("relationUid", this.relationUid + "," + referenceUids);
        }
        else {
            this.setValue("relationUid", this.relationUid);
        }
        //刷新附件列表
        this.getList(true);
    },

    //参照UID设定
    setReferenceUid: function (referenceUid, isShowProcessing) {
        this.setValue("relationUid", referenceUid);
        this.relationUid = referenceUid;
        this.initUploadify();
        //刷新附件列表
        this.getList(true, isShowProcessing);
    },

    hidePanelHeader: function () {
        $(".panel-heading", this.getMainContentClassName()).hide();
    },

    createListBefore: function () {
        $(".panel-body", this.getMainContentClassName()).show();
    },

    hidePanelBody: function () {
        if (this.isNull(this.datagrid.data.length) || this.datagrid.data.length == 0) {
            $(".panel-body", this.getMainContentClassName()).hide();
        }
        else {
            $(".panel-body", this.getMainContentClassName()).show();
        }
    },

    //初期化Uploadify控件
    initUploadify: function () {
        if (this.isEmpty(this.multiUpload) || this.multiUpload == "true") {
            this.multiUpload = true;
        }
        else if (this.multiUpload == "false") {
            this.multiUpload = false;
        }
        //文件类型为空的场合
        if (this.isEmpty(this.fileTypeExts)) {
            this.fileTypeExts = "*.*";
            this.fileTypeDesc = "所有文件";
        }

        if (this.isEmpty(this.fileTypeDesc)) {
            this.fileTypeDesc = "附件文件";
        }

        if (this.isEmpty(this.uploadButtonText)) {
            this.uploadButtonText = "上传附件";
        }

        if (this.isEmpty(this.uploadButtonWidth)) {
            this.uploadButtonWidth = "100";
        }

        //默认限制文件大小10MB
        if (this.isEmpty(this.fileSizeLimit)) {
            this.fileSizeLimit = "20MB";
        }

        try {
            var thisObj = this;
            this.$("file_upload").uploadify({
                'formData': {
                    'appCode': thisObj.appCode,
                    'moduleName': thisObj.moduleName,
                    'categoryCd': thisObj.categoryCd,
                    'relationUid': thisObj.relationUid,
                    'bigImageWidth': thisObj.bigImageWidth,
                    'bigImageHeight': thisObj.bigImageHeight,
                    'smallImageWidth': thisObj.smallImageWidth,
                    'smallImageHeight': thisObj.smallImageHeight
                },
                'buttonText': '<i class="fa fa-cloud-upload"></i>' + thisObj.uploadButtonText,
                'fileSizeLimit': thisObj.fileSizeLimit,
                'fileTypeDesc': thisObj.fileTypeDesc,
                'fileTypeExts': thisObj.fileTypeExts,
                'preventCaching': false,
                'multi': thisObj.multiUpload,
                'swf': window.UploadifySwfPath,
                'uploader': window.UploadifyUploader,
                'buttonClass': 'upload-file-button',
                'height': '30',
                'width': thisObj.uploadButtonWidth,
                'queueID': thisObj.getId("fileQueue"),
                'onUploadSuccess': function (file, data, response) {
                    thisObj.onUploadSuccess(file, data, response);
                },
                'onQueueComplete': function () {
                    thisObj.onQueueComplete();
                    thisObj.getDom("fileQueue").innerHTML = "";
                }
            });
        }
        catch (e) {
            console.error("Uploadify控件初期化失败：" + e);
            console.error("如果您使用的是360浏览器，请在浏览器地址栏右侧切换到极速模式。");
        }
    },

    onUploadSuccess: function (file, data, response) {
    },

    onQueueComplete: function () {
        this.getList(true);
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //文件类型
        var mimeType = $M.getExtension(rowData.fileName);

        var loginUserUid = window.loginUserUid;
        //登录人情报不存在时
        if (this.isEmpty(loginUserUid)) {
            loginUserUid = "unknownUser";
        }
        //现在日期与附件创建日期的天数差
        var intervalDays = this.getIntervalDaysWithSysdate(rowData["insertDate"].formatYMD());
        //操作列按钮
        if (colName == "operation") {
            strHtml = "";
            //非只读 && 非关联附件文件的场合才可编辑与删除
            if (this.isReadonly == false && this.relationUid == rowData.relationUid) {
                if (this.showEditButton) {
                    //允许其他用户编辑 或者 不允许其他用户编辑但是是本人上传附件时， 允许编辑
                    if (this.allowOtherUserEdit || loginUserUid == rowData["insertUser"]) {
                        //如果允许删除文件天数有设定， 则必须是指定天数内的才允许编辑
                        if (this.allowEditFileDays == 0 || (this.allowEditFileDays > 0 && intervalDays <= this.allowEditFileDays)) {
                            if (this.showOperationIcon) {
                                //创建列表编辑图标
                                strHtml += this.createEditIcon(rowNo);
                            }
                            else {
                                //创建列表编辑图标
                                strHtml += this.createEditButton(rowNo);
                            }
                        }
                    }
                }

                if (this.showDeleteButton) {
                    //允许其他用户编辑 或者 不允许其他用户编辑但是是本人上传附件时， 允许删除
                    if (this.allowOtherUserEdit || loginUserUid == rowData["insertUser"]) {
                        //如果允许删除文件天数有设定， 则必须是指定天数内的才允许删除
                        if (this.allowEditFileDays == 0 || (this.allowEditFileDays > 0 && intervalDays <= this.allowEditFileDays)) {
                            if (this.showOperationIcon) {
                                //创建列表删除图标
                                strHtml += this.createDeleteIcon(rowNo);
                            }
                            else {
                                //创建列表删除图标
                                strHtml += this.createDeleteButton(rowNo);
                            }
                        }
                    }
                }
            }

            //如果是图片格式 支持附件预览 （临时）
            if (this.isImage(mimeType)) {
                if (this.showOperationIcon) {
                    strHtml += "<span class='popovers' data-trigger='hover' data-placement='top' data-content='预览'>";
                    strHtml += "<i onclick='" + this.selfInstance + ".preview(\"" + rowData.uid + "\");return false;' class='fa fa-eye fa-lg fa-blue fa-width-fixed'></i>";
                    strHtml += "</span>";
                }
                else {
                    //创建预览按钮
                    strHtml += "<button type='button' class='btn btn-default' onclick='" + this.selfInstance + ".preview(\"" + rowData.uid + "\");return false;'><i class='fa fa-eye fa-width-fixed'></i>预览</button>";
                }
            }
            else if (this.filePreviewIns.isEnable && (mimeType == 'doc' || mimeType == 'docx' || mimeType == 'xls' || mimeType == 'xlsx' || mimeType == 'ppt' || mimeType == 'pptx' || mimeType == 'pdf')) {
                if (this.showOperationIcon) {
                    strHtml += "<span class='popovers' data-trigger='hover' data-placement='top' data-content='预览'>";
                    strHtml += "<i onclick='SysApp.Cmn.FilePreviewIns.preview(\"" + rowData.uid + "\");return false;' class='fa fa-eye fa-lg fa-blue fa-width-fixed'></i>";
                    strHtml += "</span>";
                }
                else {
                    //创建预览按钮
                    strHtml += "<button type='button' class='btn btn-default' onclick='SysApp.Cmn.FilePreviewIns.preview(\"" + rowData.uid + "\");return false;'><i class='fa fa-eye fa-width-fixed'></i>预览</button>";
                }
            }
            if (!this.showOperationIcon) {
                //创建操作按钮组
                strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
            }
        }
        //POPUP详细表示
        else if (colName == "fileName") {
            //显示缩略图[用于图片上传的场合]
            if (this.isThumbnail && this.isImage(mimeType)) {
                strHtml = "<img height='100px' src='/" + rowData["filePath"] + "'>";
            } else {
                strHtml = this.createDetailLink(rowData, colName, rowNo);
            }
        }
        else if (colName == "insertDate") {
            strHtml = rowData[colName].formatYMDHM();
        }
        else if (colName == "previewCount" || colName == "downloadCount") {
            strHtml = this.createAccessLogListLink(rowData, colName, rowNo) + "&nbsp;/&nbsp;" + this.createAccessLogListLink(rowData, "downloadCount", rowNo);
        }
        else if (colName == "remark") {
            strHtml = $M.enterToBr(rowData[colName]);
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    createAccessLogListLink: function (rowData, colName, rowNo) {
        if (rowData[colName] == 0 || this.isEmpty(rowData[colName])) {
            return 0;
        }
        if (colName == "previewCount") {
            return "<a href='#' onclick='SysApp.Sys.AttachmentAccessLogPopupListIns.showAccessLogList(\"" + rowData.uid + "\", 0);'><b>" + rowData[colName] + "</b></a>";
        }
        else if (colName == "downloadCount") {
            return "<a href='#' onclick='SysApp.Sys.AttachmentAccessLogPopupListIns.showAccessLogList(\"" + rowData.uid + "\", 1);'><b>" + rowData[colName] + "</b></a>";
        }
    },

    isImage: function (mimeType) {
        mimeType = mimeType.toLowerCase();
        return (mimeType == 'png' || mimeType == 'gif' || mimeType == 'jpg' || mimeType == 'jpeg' || mimeType == 'bmp');
    },

    preview: function (uid, refresh) {
        window.open(this.controller + "preview?uid=" + uid);
        if (this.isNull(refresh) || refresh == true) {
            setTimeout(this.getList.bind(this, false, false), 1500);
        }
    },

    download: function (uid, refresh) {
        window.open(this.controller + "download?uid=" + uid);
        if (this.isNull(refresh) || refresh == true) {
            setTimeout(this.getList.bind(this, false, false), 1500);
        }
    },

    //创建详细POPUP表示的超链接
    createDetailLink: function (rowData, colName, rowNo) {
        //允许下载的场合
        if (this.allowDownload) {
            //附件文件名称支持下载功能
            return "<a  title='下载附件文件' href='#' onclick='" + this.selfInstance + ".download(\"" + rowData.uid + "\");return false;' class='detail-link'><i class='fa fa-download fa-lg fa-blue'></i>" + rowData[colName].escapeHTML() + "</a>";
        }
        else {
            return rowData[colName].escapeHTML();
        }
    },

    //表格创建后处理
    createListAfter: function () {
        if (this.isNull(this.datagrid.data.length) || this.datagrid.data.length == 0 || this.datagrid.data.length == 1 || this.allowDownload == false) {
            this.displayDom("divPackDownload", false);
        }
        else {
            this.displayDom("divPackDownload", true);
        }
        //不显示批量打包下载按钮的场合
        if (this.showPackDownload == false) {
            this.displayDom("divPackDownload", false);
        }

        //判断是否有调用实例，如果有则调用uploadCallback
        if (this.isNotEmpty(this.callInstance)) {
            var objCallInstance = eval(this.callInstance);
            //如果调用元不为空
            if (!$M.isNull(objCallInstance)) {
                if (this.isFunction(objCallInstance.uploadCallback)) {
                    //回调上传后的函数
                    objCallInstance.uploadCallback();
                }
            }
        }

        //是否设置最大上传数量
        if (this.isEmpty(this.maxFileCount)) {
            return;
        }

        //上传数量不得超过限定数量
        if (this.datagrid.data.length >= this.maxFileCount) {
            //隐藏上传按钮 显示提示信息
            this.displayUploadContainer(false);
            this.displayDom("divFileMaxMessage", true);
        }
        else {
            //显示上传按钮 隐藏提示信息
            this.displayUploadContainer(true);
            this.displayDom("divFileMaxMessage", false);
        }
    },

    //取得上传文件数
    getFileCount: function () {
        var count = this.datagrid.data.length;
        if (this.isEmpty(count)) count = 0;
        return count;
    },

    initCallback: function () {
        if (this.isEmpty(this.allowDownload) || this.allowDownload == "true" || this.allowDownload == true) {
            this.allowDownload = true;
        }
        else {
            this.allowDownload = false;
        }

        if (this.isEmpty(this.isReadonly)) {
            this.isReadonly = false;
        }
        else {
            this.isReadonly = this.isReadonly == "true" ? true : false;
        }

        //是否允许他人编辑，原来没这个属性时可以编辑， 所以不传入默认为true
        if (this.isEmpty(this.allowOtherUserEdit)) {
            this.allowOtherUserEdit = true;
        }
        else {
            this.allowOtherUserEdit = this.allowOtherUserEdit == "true" ? true : false;
        }

        //是否允许删除指定日以前的附件，原来没这个属性时可以编辑， 所以不传入默认为0
        if (this.isEmpty(this.allowEditFileDays)) {
            this.allowEditFileDays = 0;
        }
        else {
            this.allowEditFileDays = parseInt(this.allowEditFileDays);
        }

        //编辑按钮默认显示
        if (this.isEmpty(this.showEditButton)) {
            this.showEditButton = true;
        }
        else {
            this.showEditButton = this.showEditButton == "true" ? true : false;
        }

        //删除按钮默认显示
        if (this.isEmpty(this.showDeleteButton)) {
            this.showDeleteButton = true;
        }
        else {
            this.showDeleteButton = this.showDeleteButton == "true" ? true : false;
        }

        this.initCommonCallback();

        if (this.isInitUploadify) {
            //初期化Uploadify控件
            setTimeout(this.initUploadify.bind(this), this.loadDelayTime);
        }

        this.displayUploadContainer(!this.isReadonly);

        //允许下载的场合
        if (this.allowDownload) {
            this.bindEvent("btnPackDownload", "click", this.packDownload.bind(this));
        }
        else {
            this.displayDom("btnPackDownload", false);
        }
    },

    //打包下载
    packDownload: function () {
        if (this.isNull(this.datagrid.data.length) || this.datagrid.data.length == 0) {
            this.showErrorMessage("没有需要打包下载的附件文件。");
            return;
        }
        window.open(this.controller + "packDownload?relationUid=" + this.getValue("relationUid"));
    },

    //设置附件文件控件为只读状态
    setReadonlyMode: function (readonly) {
        //如果不传入， 设定为true
        if (this.isEmpty(readonly)) {
            readonly = true;
        }
        this.isReadonly = readonly;

        this.displayUploadContainer(!readonly);
        //重新创建检索一览数据
        this.reCreateList();
    },

    //控制上传区域是否显示
    displayUploadContainer: function (display) {
        this.displayDomByClass("." + this.clientID + this.categoryCd + "-MainContent .upload-button-container", display);
    },

    //动态设置附件提示title 和提示信息
    //提示信息 需以半角分号(;)
    setHelpInfo: function (helpInfoTitle, helpInfoMessages) {
        //如果提示消息的dom不存在， 返回
        if (this.isEmpty(this.getDom("promptFold"))) {
            return;
        }
        var helpHtml = "";
        //如果提示信息为空， 隐藏提示区域
        if (this.isEmpty(helpInfoMessages)) {
            this.displayDom("promptFold", false);
        }

        //消息分号分隔
        var helpInfoMessageList = helpInfoMessages.split(";") || [];
        if (this.isEmpty(helpInfoTitle)) {
            helpInfoTitle = "所需附件";
        }
        helpHtml += "<label><i class=\"fa fa-hand-pointer-o\"></i>" + helpInfoTitle + "</label>";
        helpHtml += "<ol>";
        for (var j = 0; j < helpInfoMessageList.length; j++) {
            helpHtml += "<li class=\"weight\" style=\"color: red;\">" + helpInfoMessageList[j] + "</li>";
        }
        helpHtml += "</ol>";
        $(".help-info").html(helpHtml);
    },

    /**
     * 获取指定日期与系统日期之间的天数差
     *
     * @param strDate    指定日期（ yyyy/MM/dd格式）
     * @returns 天数差
     */
    getIntervalDaysWithSysdate: function (strDate) {
        if (this.isEmpty(strDate)) {
            return 0;
        }
        //指定日期
        var specifyDate = new Date(strDate);
        //系统日期
        var strNowDate = new Date().formatYMD();
        //如要时分秒
        var nowDate = new Date(strNowDate);

        var days = parseInt((nowDate.getTime() - specifyDate.getTime()) / parseInt(1000 * 3600 * 24));
        return days;
    }
});