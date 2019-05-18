//定义数据一览画面JS类
SysApp.Demo.CompanyInstitutionalDetailInnerList = Class.create();

SysApp.Demo.CompanyInstitutionalDetailInnerList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("名称", "name", "30%", SORT_NO));
        this.colList.push(new ColInfo("地址", "address", "38%", SORT_NO));
        this.colList.push(new ColInfo("备注", "remark", "20%", SORT_NO));
        this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //一览取得用[Callback后处理]
    createListBefore: function () {
        //当获取页面数据为空时，自动新增一条空记录
        if (this.data.content.length == 0) {
            this.data.content = [{
                "uid": "",
                "insertUser": "",
                "insertUserName": "",
                "insertDate": "",
                "updateUser": "",
                "updateUserName": "",
                "updateDate": "",
                "remark": "",
                "recordStatus": 1,
                "saveType": "",
                "editMode": 0,
                "entityClass": "",
                "institutionalUid": "\"" + this.getValue("institutionalUid") + "\"",
                "name": "",
                "address": ""
            }];
        }
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = "<div class='btn-group  btn-group-sm' style='margin-top:2px;'>" +
                "          <button type='button' class='btn btn-white' onclick='SysApp.Demo.CompanyInstitutionalDetailInnerListIns.addRow(this);' title='添加'>" +
                "              <i class='fa fa-plus-square'></i>" +
                "          </button>" +
                "          <button type='button' class='btn btn-white' onclick='SysApp.Demo.CompanyInstitutionalDetailInnerListIns.copyRow(this);' title='复制'>" +
                "              <i class='fa fa-copy'></i>" +
                "          </button>" +
                "          <button type='button' class='btn btn-white' onclick='SysApp.Demo.CompanyInstitutionalDetailInnerListIns.deleteRow(this);' title='删除'>" +
                "              <i class='fa fa-trash-o'></i>" +
                "          </button>" +
                "      </div>";
            //机构UID隐藏域
            strHtml += "<input type='hidden' data-allow-clear='false' value='" + this.getValue("institutionalUid") + "' id='detail-institutionalUid|" + rowNo + "'/>";
        }
        //名称
        else if (colName == "name") {
            strHtml = "<input type='text' maxlength='32' rowNo='" + rowNo + "' id='detail-name|" + rowNo + "' value='" + rowData[colName] + "' class='form-control name-input required' style='width:98%;' placeholder='请输入名称'/>";
        }
        //地址
        else if (colName == "address") {
            strHtml = "<input type='text' maxlength='64' rowNo='" + rowNo + "' id='detail-address|" + rowNo + "' value='" + rowData[colName] + "' class='form-control address-input' style='width:98%;' placeholder='请输入地址'/>";
        }
        //备注
        else if (colName == "remark") {
            strHtml = "<input type='text' maxlength='256' rowNo='" + rowNo + "' id='detail-remark|" + rowNo + "' value='" + rowData[colName] + "' class='form-control remark-input' style='width:98%;' placeholder='请输入备注'/>";
        }

        return strHtml;
    },

    //取得明细数据【供主程序调用】
    getDetailList: function () {
        //原始数据
        var detailList = new Array();
        //新增的数据
        var thisObj = this;
        $(this.getDom("divList")).find("tr:gt(0)").each(function () {
            var row = {};
            $("input[type='text'], input[type='hidden'], select, input[type='checkbox'], input[type='radio']", this).each(function () {
                var detailValue = $(this).val();
                var property = this.id.split("-")[1].split("|")[0];
                //根据数据库字段名称设置数组值
                row[property] = detailValue;
            });
            //记录状态有效
            row.recordStatus = 1;
            detailList.push(row);
        });
        return detailList;
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
    },

    //行号重置[重写父类方法]
    resetRowNo: function (table) {
        //去除标题列没从1开始
        var index = 1;
        $("tr:gt(0)", table).each(function () {
            $(".first-td-border", this).html(index);
            index++;
        });
    },

    //行删除
    deleteRow: function (obj) {
        var table = $(obj).parents("table");

        //最后一行 清除行内值
        if ($("tr", table).length == 2) {
            $("input[type='text']", "#" + this.dom.divList).each(function () {
                this.value = "";
            });
        }
        else {
            //删除当前行
            var row = $(obj).parents("tr");
            $(row).remove();
        }

        //重置列表的序号列
        this.resetRowNo(table);
    }
});