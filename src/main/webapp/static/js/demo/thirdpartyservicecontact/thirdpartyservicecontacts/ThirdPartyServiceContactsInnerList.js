//定义数据一览画面JS类
SysApp.Demo.ThirdPartyServiceContactsInnerList = Class.create();

SysApp.Demo.ThirdPartyServiceContactsInnerList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("联系人", "contactName", "15%", SORT_NO));
        this.colList.push(new ColInfo("联系电话", "contactNumber", "15%", SORT_NO));
        this.colList.push(new ColInfo("邮箱", "contactMail", "15%", SORT_NO));
        this.colList.push(new ColInfo("部门职务", "positionDept", "18%", SORT_NO));
        this.colList.push(new ColInfo("备注", "remark", "20%", SORT_NO));
        this.colList.push(new ColInfo("操作", "operation", "15%", SORT_NO, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = "<div class='btn-group  btn-group-sm' style='margin-top:2px;'>" +
                "          <button type='button' class='btn btn-white' onclick='SysApp.Demo.ThirdPartyServiceContactsInnerListIns.addRow(this);' title='添加'>" +
                "              <i class='fa fa-plus-square'></i>" +
                "          </button>" +
                "          <button type='button' class='btn btn-white' onclick='SysApp.Demo.ThirdPartyServiceContactsInnerListIns.copyRow(this);' title='复制'>" +
                "              <i class='fa fa-copy'></i>" +
                "          </button>" +
                "          <button type='button' class='btn btn-white' onclick='SysApp.Demo.ThirdPartyServiceContactsInnerListIns.deleteRow(this);' title='删除'>" +
                "              <i class='fa fa-trash-o'></i>" +
                "          </button>" +
                "      </div>";
            //企业收费信息UID隐藏域
            strHtml += "<input type='hidden' data-allow-clear='false' value='" + this.getValue("thirdPartyServiceUid") + "' id='detail-thirdPartyServiceUid|" + rowNo + "'/>";
        }
        //联系人
        else if (colName == "contactName") {
            strHtml = "<input type='text' maxlength='32' rowNo='" + rowNo + "' id='detail-contactName|" + rowNo + "' value='" + rowData[colName] + "' class='form-control contactName-input required' style='width:98%;' placeholder='请输入联系人'/>";
        }
        //联系电话
        else if (colName == "contactNumber") {
            strHtml = "<input type='text' maxlength='32' rowNo='" + rowNo + "' id='detail-contactNumber|" + rowNo + "' value='" + rowData[colName] + "' class='form-control contactNumber-input' style='width:98%;' placeholder='请输入联系电话'/>";
        }
        //邮箱
        else if (colName == "contactMail") {
            strHtml = "<input type='text' maxlength='32' rowNo='" + rowNo + "' id='detail-contactMail|" + rowNo + "' value='" + rowData[colName] + "' class='form-control contactMail-input' style='width:98%;' placeholder='请输入邮箱'/>";
        }
        //部门职务
        else if (colName == "positionDept") {
            strHtml = "<input type='text' maxlength='128' rowNo='" + rowNo + "' id='detail-positionDept|" + rowNo + "' value='" + rowData[colName] + "' class='form-control positionDept-input' style='width:98%;' placeholder='请输入部门职务'/>";
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
    }
});