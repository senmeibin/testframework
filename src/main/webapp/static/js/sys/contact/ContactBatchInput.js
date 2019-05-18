// 定义数据输入画面JS类
SysApp.Sys.ContactBatchInput = Class.create();

SysApp.Sys.ContactBatchInput.prototype = Object.extend(new SysApp.Sys.CommonInput(), {
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
        this.createDetailList()
    },

    //创建明细列表
    createDetailList: function () {
        //明细一览数据
        this.detailList = JsonUtility.Parse(this.getValue("jsonContactList"));
        var html = "";

        // 明细数据不存在的场合，默认表示3行空数据
        if (this.detailList.length == 0) {
            var model = JsonUtility.Parse(this.getValue("jsonInputEntity"));

            this.detailList.push(model);
            this.detailList.push(model);
            this.detailList.push(model);
        }

        for (var i = 0; i < this.detailList.length; i++) {
            var model = this.detailList[i];

            html += "<tr>" +
                "   <td class='row-no' style='line-height:25px;text-align:center;width: 40px;'>" + (i + 1) + "</td>" +
                "   <td>" +
                "       <input type='hidden' id='uid|" + i + "' value='" + model.uid + "' />" +
                "       <input type='text' class='form-control required-list' maxlength='6' id='name|" + i + "' value='" + model.name + "' />" +
                "   </td>" +
                "   <td>" +
                "       <select class='form-control' id='department|" + i + "' data-value='" + model.department + "'>" +
                this.getDom("dept_list").innerHTML +
                "       </select>" +
                "   </td>" +
                "   <td>" +
                "       <select class='form-control' id='post|" + i + "' data-value='" + model.post + "'>" +
                this.getDom("post_list").innerHTML +
                "       </select>" +
                "   </td>" +
                "   <td>" +
                "       <input type='text' class='form-control' maxlength='32' id='responsibility|" + i + "' value='" + model.responsibility + "' />" +
                "   </td>" +
                "   <td>" +
                "       <input type='text' class='form-control required-list' maxlength='32' id='mobile|" + i + "' value='" + model.mobile + "' />" +
                "   </td>" +
                "   <td>" +
                "       <input type='text' class='form-control' maxlength='32' id='birthday|" + i + "' value='" + (this.isNull(model.birthday) ? "" : model.birthday) + "' />" +
                "   </td>" +
                "   <td>" +
                "       <input type='text' class='form-control' maxlength='16' id='telephone|" + i + "' value='" + model.telephone + "' />" +
                "   </td>" +
                "   <td>" +
                "       <input type='text' class='form-control' maxlength='32' id='remark|" + i + "' value='" + model.remark + "' />" +
                "   <td style='text-align: center;width:110px'>" +
                "       <div class='btn-group  btn-group-sm' style='margin-top:2px;'>" +
                "           <button type='button' class='btn btn-white' onclick='SysApp.Sys.ContactBatchInputIns.addRow(this);' title='添加'>" +
                "               <i class='fa fa-plus-square fa-width-fixed'></i>" +
                "           </button>" +
                "           <button type='button' class='btn btn-white' onclick='SysApp.Sys.ContactBatchInputIns.copyRow(this);' title='复制'>" +
                "               <i class='fa fa-copy fa-width-fixed'></i>" +
                "           </button>" +
                "           <button type='button' class='btn btn-white' onclick='SysApp.Sys.ContactBatchInputIns.deleteRow(this);' title='删除'>" +
                "               <i class='fa fa-trash-o fa-width-fixed'></i>" +
                "           </button>" +
                "       </div>" +
                "    </td>" +
                "</tr>";
        }

        $(".detail-list").html(html);

        //重置下拉选择框的默认选择值
        $("select", ".detail-list").each(function () {
            $(this).val($(this).data("value"));
        });

        //详细画面的场合，全部无效化处理
        if (this.isDetailMode()) {
            this.disableInput(".detail-list", true);
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
    },

    //取得明细一览
    getDetailList: function () {
        var thisObj = this;
        this.detailList = new Array();

        var index = 1;
        $("tr", $(".detail-list")).each(function () {
            var row = {};
            $("input[type='text'], input[type='hidden'], select, input[type='checkbox'], input[type='radio']", this).each(function () {
                //根据数据库字段名称设置数组值
                row[this.id.split("|")[0]] = $(this).val();
            });

            //必须输入字段为空的场合
            if (thisObj.isEmpty(row.name) && thisObj.isEmpty(row.mobile)) {
                return;
            }

            //记录状态有效
            row.recordStatus = 1;

            thisObj.detailList.push(row);
        });
        return this.detailList;
    }
});