// 定义数据输入画面JS类
SysApp.Cmn.FilePreview = Class.create();

SysApp.Cmn.FilePreview.prototype = Object.extend(new SysCmn.CoreInput(), {
    getPrimaryKey: function () {
        return "uid";
    },

    //初期化回调处理
    initCallback: function () {
        this.popupPosition = "firstTop";
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        this.$("ctlTitle").html("附件预览");

        $(this.getFrame()).css("width", $M.getClientWidth() - 100);
        $(this.getFrame()).css("left", "50px");

        this.$("viewerPlaceHolder").css("height", $M.getClientHeight() - 145);
    },

    /**
     * 用OfficeOnlineServer服务器预览
     * @param attachmentUid
     * @param isImportFile (1:是数据导入文件，  不传或其他值为普通附件）
     */
    officeOnlinePreview: function (attachmentUid, isImportFile, refresh) {
        if (this.isEmpty(isImportFile)) {
            isImportFile = "0";
        }
        AjaxIns.sendPOST(this.controller + "officeOnlinePreview", this.officeOnlinePreviewCallBack.bind(this, refresh), "attachmentUid=" + attachmentUid + "&isImportFile=" + isImportFile);
    },

    officeOnlinePreviewCallBack: function (refresh, response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }
        var ajaxResult = JsonUtility.Parse(response.responseText);

        if (this.isNotNull(SysApp.Cmn.FileUploadListIns) && (this.isNull(refresh) || refresh == true)) {
            //刷新预览次数列数据
            SysApp.Cmn.FileUploadListIns.getList(false, false);
        }

        //处理成功 (如果配置到主文档目录以外的时候，上传目录的context必须是upload ，否则不能预览）
        if (ajaxResult.result > 0) {
            //此文件名是带upload 相对目录的文件名（如upload/20171127161002743000000000000584.xlsx 或者 upload/201711/20171127161002743000000000000584.xlsx）
            var fileName = ajaxResult.content;
            //文档路径
            var docURI = encodeURIComponent(location.protocol + "//" + location.hostname + "/" + fileName);
            //创建iframe
            var previewFrame = $("<iframe width='100%' height='100%' frameborder='0'></iframe>");
            //设置链接地址
            previewFrame.attr("src", this.officeOnlineServerDomain + "/op/view.aspx?src=" + docURI);
            //插入页面中
            this.$("viewerPlaceHolder").html("").append(previewFrame);
        }
        else {
            this.$("viewerPlaceHolder").html(ajaxResult.message)
        }
    },

    /**
     * 校验IP地址是否合法
     * @param ip
     * @returns {boolean}
     */
    isValidIP: function (ip) {
        var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
        return reg.test(ip);
    },

    /**
     * 预览主接口
     * @param attachmentUid 附件UID
     * @param isImportFile (1:是数据导入文件，不传或其他值为普通附件）
     */
    preview: function (attachmentUid, isImportFile, refresh) {
        this.show();
        this.$("viewerPlaceHolder").html("页面正在加载中，请稍后...");
        //office在线服务器域名为空
        if (this.isEmpty(this.officeOnlineServerDomain)) {
            this.showErrorMessage("Office在线预览服务域名未配置，禁止文档预览。");
            return;
        }

        //当前为IP访问
        if (this.isValidIP(location.hostname)) {
            this.showErrorMessage("IP地址访问，禁止文档预览（请使用域名访问）。");
            return;
        }

        //校验OfficeWebApps服务器是否可用
        var thisObj = this;
        var objIMG = new Image();
        objIMG.src = this.officeOnlineServerDomain + "/op/icon.png?t=" + new Date().getTime();
        objIMG.onload = function () {
            thisObj.officeOnlinePreview(attachmentUid, isImportFile, refresh);
        };
        objIMG.onerror = function () {
            thisObj.showErrorMessage("Office在线预览服务访问不到，禁止文档预览。");
        };
    },

    showErrorMessage: function (msg) {
        this.$("viewerPlaceHolder").html("<span style=\"color:red\">预览失败：" + msg + "</span>");
    }
});