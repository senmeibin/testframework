// 定义数据输入画面JS类
SysApp.Cmn.RegionInput = Class.create();

SysApp.Cmn.RegionInput.prototype = Object.extend(new SysApp.Cmn.CommonInput(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var title = "地区信息编辑";
        //添加模式的场合
        if (this.isAddMode()) {
            title = "地区信息添加";
            if (this.isNull(this.parentRegion)) {
                //添加模式的场合，默认为层级为1
                this.setValue("regionGrade", 1);
            }
            else {
                //父地区下级地区添加
                var regionGrade = this.parentRegion.regionGrade + 1;
                this.setValue("regionGrade", regionGrade);
                this.setValue("parentUid", this.parentRegion.uid)
            }
            this.disabledDom("regionGrade", true);
            this.disabledDom("parentUid", true);
        } else {
            var jsonOptionList = JsonUtility.Parse(this.getValue("jsonOptionList"));
            if (this.inputEntity.regionGrade == 1) {
                this.createOption("parentUid", null, "subCd", "subName", true, "请选择");
            }
            else if (this.inputEntity.regionGrade == 2) {
                this.createOption("parentUid", jsonOptionList.grade1, "subCd", "subName", true, "请选择");
            }
            else if (this.inputEntity.regionGrade == 3) {
                this.createOption("parentUid", jsonOptionList.grade2, "subCd", "subName", true, "请选择");
            }
            this.setValue("parentUid", this.inputEntity.parentUid);
        }

        //画面模式的场合重写画面标题
        if (!this.isPopupMode()) {
            this.setTitle(title);
        }

        this.$("ctlTitle").html(title);
        $('select[id*=parentUid]', this.getMainContentClassName()).trigger("change");

        if (this.isNotNull(this.inputEntity) && this.isNotNull(this.inputEntity.regionName)) {
            //过滤名称前特殊符号
            this.setValue("regionName", this.inputEntity.regionName.replaceAll("#", ""));
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //上级地区未设定的场合，上级地区设定为0
        if (this.isEmpty(this.inputEntity.parentUid)) {
            this.inputEntity.parentUid = 0;
        }
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