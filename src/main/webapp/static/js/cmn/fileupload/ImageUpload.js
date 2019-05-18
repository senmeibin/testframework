//定义数据一览画面JS类
SysApp.Cmn.ImageUpload = Class.create();

SysApp.Cmn.ImageUpload.prototype = Object.extend(new SysCmn.CoreInput(), {
    initCallback: function () {
        //初期化Uploadify控件
        setTimeout(this.initUploadify.bind(this), this.loadDelayTime);
    },

    //初期化Uploadify控件
    initUploadify: function () {
        //文件类型为空的场合
        if (this.isEmpty(this.fileTypeExts)) {
            this.fileTypeExts = "*.bmp;*.png;*.gif;*.jpg;";
            this.fileTypeDesc = "图像文件";
        }

        if (this.isEmpty(this.fileTypeDesc)) {
            this.fileTypeDesc = "图像文件";
        }

        //默认限制文件大小10MB
        if (this.isEmpty(this.fileSizeLimit)) {
            this.fileSizeLimit = "10MB";
        }

        if (this.isEmpty(this.bigImageWidth) || this.isEmpty(this.bigImageHeight)) {
            this.bigImageWidth = -1;
            this.bigImageHeight = -1;
        }

        if (this.isEmpty(this.smallImageWidth) || this.isEmpty(this.smallImageHeight)) {
            this.smallImageWidth = -1;
            this.smallImageHeight = -1;
        }

        var thisObj = this;
        this.$("file_upload").uploadify({
            'formData': {
                'appCode': thisObj.appCode,
                'moduleName': thisObj.moduleName,
                'relationUid': thisObj.relationUid,
                'bigImageWidth': thisObj.bigImageWidth,
                'bigImageHeight': thisObj.bigImageHeight,
                'smallImageWidth': thisObj.smallImageWidth,
                'smallImageHeight': thisObj.smallImageHeight
            },
            'buttonText': '<i class="' + this.uploadButtonIcon + '"></i>&nbsp;' + this.uploadButtonText,
            'fileSizeLimit': thisObj.fileSizeLimit,
            'fileTypeDesc': thisObj.fileTypeDesc,
            'fileTypeExts': thisObj.fileTypeExts,
            'preventCaching': false,
            'fileObjName': 'file',
            'multi': this.multiUpload,
            'swf': window.UploadifySwfPath,
            'uploader': window.UploadifyUploader,
            'buttonClass': 'upload-image-button',
            'height': '30',
            'width': this.uploadButtonWidth,
            'queueID': thisObj.getId("fileQueue"),
            'onUploadSuccess': function (file, data, response) {
                thisObj.onUploadSuccess(file, data, response);
            },
            'onQueueComplete': function () {
                thisObj.onQueueComplete();
                thisObj.getDom("fileQueue").innerHTML = "";
            }
        });
    },

    onUploadSuccess: function (file, data, response) {
        var uploadResult = JSON.parse(data);
        if (uploadResult.result == -1) {
            this.showErrorMessage(uploadResult.message);
        }
        else {
            var pictureUrl = uploadResult.pictureUrl;
            var bigPictureUrl = uploadResult.bigPictureUrl;
            var smallPictureUrl = uploadResult.smallPictureUrl;
            //获取返回路径
            this.$("pictureUrl").val(pictureUrl);
            this.$("bigPictureUrl").val(bigPictureUrl);
            this.$("smallPictureUrl").val(smallPictureUrl);
            //预览图片
            if (this.previewFlag != "false") {
                this.$("imagePath").attr("src", window.contextPath + "/" + smallPictureUrl);
                this.displayDom("imagePath", true);
            }
        }
        this.parentInstance = eval(this.parentInstance);
        //回调函数校验
        if (this.isFunction(this.parentInstance.uploadCallback)) {
            //上传图片回调函数
            this.parentInstance.uploadCallback(uploadResult);
        }
    },

    onQueueComplete: function () {
    }
});