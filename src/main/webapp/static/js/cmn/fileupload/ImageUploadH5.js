//定义数据一览画面JS类
SysApp.Cmn.ImageUploadH5 = Class.create();

SysApp.Cmn.ImageUploadH5.prototype = Object.extend(new SysCmn.CoreInput(), {
    initCallback: function () {
        //初期化Uploadify控件
        setTimeout(this.initUploadify.bind(this), this.loadDelayTime);
        //默认非空场合
        if (this.isEmpty(this.base64ImageFlag)) {
            this.base64ImageFlag = false;
        }
    },

    //初期化处理
    initUploadify: function () {
        //初期化上传控件
        this.initUploadImage();
    },

    //初期化上传控件
    initUploadImage: function () {
        var thisObj = this;
        if (this.isEmpty(this.bigImageWidth) || this.isEmpty(this.bigImageHeight)) {
            this.bigImageWidth = -1;
            this.bigImageHeight = -1;
        }

        if (this.isEmpty(this.smallImageWidth) || this.isEmpty(this.smallImageHeight)) {
            this.smallImageWidth = -1;
            this.smallImageHeight = -1;
        }

        if (this.isEmpty(this.compressWidth)) {
            this.compressWidth = 800;
        }

        this.$("imageUpload").change(function () {
            thisObj.parentInstance = eval(thisObj.parentInstance);
            //参数校验
            if ($M.isNull(thisObj.parentInstance)) {
                this.showErrorMessage("参数设置错误：parentInstance未正确设置。");
                return;
            }

            var that = this;
            lrz(that.files[0], {
                width: thisObj.compressWidth
            }).then(function (rst) {
                //获取图片的base64函数校验
                if (thisObj.isFunction(thisObj.parentInstance.getBase64Image)) {
                    //上传图片回调函数
                    thisObj.parentInstance.getBase64Image(rst.base64);
                }
                //上传文件
                if (!thisObj.base64ImageFlag) {
                    var xhr = new XMLHttpRequest();
                    xhr.open('POST', '/cmn/fileupload/uploadImages');
                    xhr.onload = function () {
                        if (xhr.status === 200) {
                            // 上传成功
                            var uploadResult = JSON.parse(xhr.response);
                            if (uploadResult.result == -1) {
                                thisObj.showErrorMessage(uploadResult.message);
                            }
                            else {
                                var pictureUrl = uploadResult.pictureUrl;
                                var bigPictureUrl = uploadResult.bigPictureUrl;
                                var smallPictureUrl = uploadResult.smallPictureUrl;
                                //获取返回路径
                                thisObj.$("pictureUrl").val(pictureUrl);
                                thisObj.$("bigPictureUrl").val(bigPictureUrl);
                                thisObj.$("smallPictureUrl").val(smallPictureUrl);
                                //预览图片
                                if (thisObj.previewFlag != "false") {
                                    thisObj.$("imagePath").attr("src", window.contextPath + "/" + smallPictureUrl);
                                    thisObj.displayDom("imagePath", true);
                                }
                            }

                            //回调函数校验
                            if (thisObj.isFunction(thisObj.parentInstance.uploadCallback)) {
                                uploadResult.moduleName = thisObj.moduleName;
                                //上传图片回调函数
                                thisObj.parentInstance.uploadCallback(uploadResult);
                            }
                        } else {
                            // 处理其他情况
                        }
                    };
                    xhr.onerror = function () {
                        // 处理错误
                    };
                    // 添加参数和触发上传
                    rst.formData.append('appCode', thisObj.appCode);
                    rst.formData.append('moduleName', thisObj.moduleName);
                    rst.formData.append('relationUid', thisObj.relationUid);
                    rst.formData.append('bigImageWidth', thisObj.bigImageWidth);
                    rst.formData.append('bigImageHeight', thisObj.bigImageHeight);
                    rst.formData.append('smallImageWidth', thisObj.smallImageWidth);
                    rst.formData.append('smallImageHeight', thisObj.smallImageHeight);
                    xhr.send(rst.formData);
                }
                return rst;
            });
        });
    },

    onQueueComplete: function () {
    },

    //设置为只读模式
    setReadonlyMode: function (isReadOnly) {
        if (isReadOnly) {
            $(this.getMainContentClassName()).parent().remove();
        }
    }
});