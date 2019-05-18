// 定义数据输入画面JS类
SysApp.Sys.RedisCacheInput = Class.create();

SysApp.Sys.RedisCacheInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
    /**********************以下方法为可选方法**********************/
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        //清除参数缓存
        this.bindEvent("btnDeleteParameter", "click", this.deleteRedis.bind(this, 1));
        //清除字典缓存
        this.bindEvent("btnDeleteDictionary", "click", this.deleteRedis.bind(this, 2));
        //清除DSQL缓存
        this.bindEvent("btnDeleteDSQL", "click", this.deleteRedis.bind(this, 3));
        //清除指定缓存
        this.bindEvent("btnDeleteSpecial", "click", this.deleteRedis.bind(this, 4));
        //清除全部
        this.bindEvent("btnDeleteAll", "click", this.deleteRedis.bind(this, 5));
        //读取指定缓存
        this.bindEvent("btnReadRedis", "click", this.readRedis.bind(this));
    },

    //删除redis
    deleteRedis: function (deleteType) {
        //清除参数缓存
        if (deleteType == 1) {
            this.deleteConfirmMessage = "确定清除系统参数缓存吗？";
        }
        //清除字典缓存
        else if (deleteType == 2) {
            this.deleteConfirmMessage = "确定清除数据字典缓存吗？";
        }
        //清除DSQL缓存
        else if (deleteType == 3) {
            this.deleteConfirmMessage = "确定清除DSQL缓存吗？";
        }
        //清除指定缓存
        else if (deleteType == 4) {
            if (this.isEmpty(this.getValue("redisKey"))) {
                this.showErrorMessage("请指定需要清除的缓存Key。");
                return;
            }
            this.deleteConfirmMessage = "确定清除指定RedisKey缓存吗？";
        }
        //清除全部
        else if (deleteType == 5) {
            this.deleteConfirmMessage = "确定清除系统中所有缓存吗？";
        }
        this.showConfirm(this.deleteConfirmMessage, this.deleteCacheData.bind(this, deleteType));
    },

    //删除redis key 相应的数据
    deleteCacheData: function (deleteType) {
        var params = "deleteType=" + deleteType;
        if (deleteType == 4) {
            params = params + "&redisKey=" + this.getValue("redisKey");
            //不需要模糊匹配 (传0）
            if (this.isEmpty(this.getValue("isLike"))) {
                params = params + "&isLike=0";
            }
            else {
                params = params + "&isLike=" + this.getValue("isLike")
            }

        }
        //Ajax处理前显示提示画面
        this.showProcessing(false);
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "redisCacheDelete", this.deleteRedisCallback.bind(this), params);
    },

    //清除处理后[Callback后处理]
    deleteRedisCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后，继续显示本页面
        this.showMessage(ajaxResult.content, MSG_TYPE_INFO);
    },

    readRedis: function () {
        if (this.isEmpty(this.getValue("readRedisKey"))) {
            this.showErrorMessage("请指定需要读取的缓存Key。");
            return;
        }
        var params = "redisKey=" + this.getValue("readRedisKey");
        //Ajax处理前显示提示画面
        this.showProcessing(false);
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "redisCacheRead", this.readRedisCallback.bind(this), params);
    },

    readRedisCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        //处理成功的场合、显示处理结果消息
        if (ajaxResult.result > 0) {
            this.setValue("readList", ajaxResult.content);
            this.displayDom("divReadRedisList", true);
            this.hideMessage();
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {

    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //验证后处理
    validateFormAfter: function () {
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});