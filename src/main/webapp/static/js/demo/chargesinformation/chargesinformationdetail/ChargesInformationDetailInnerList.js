﻿//定义数据一览画面JS类
SysApp.Demo.ChargesInformationDetailInnerList = Class.create();

SysApp.Demo.ChargesInformationDetailInnerList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        if (SysApp.Demo.ChargesInformationInputIns.isEditMode()) {
            this.colList.push(new ColInfo("应付日期", "dueChargesDate", "10%", SORT_NO));
        }
        this.colList.push(new ColInfo("收费时间", "chargesDate", "20%", SORT_NO));
        this.colList.push(new ColInfo("收费人", "chargersName", "15%", SORT_NO));
        this.colList.push(new ColInfo("收款方式", "chargesTypeCd", "15%", SORT_NO));
        this.colList.push(new ColInfo("备注", "remark", "28%", SORT_NO));
        this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO));

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
                "          <button type='button' class='btn btn-white' onclick='SysApp.Demo.ChargesInformationDetailInnerListIns.addRow(this);' title='添加'>" +
                "              <i class='fa fa-plus-square'></i>" +
                "          </button>" +
                "          <button type='button' class='btn btn-white' onclick='SysApp.Demo.ChargesInformationDetailInnerListIns.deleteRow(this);' title='删除'>" +
                "              <i class='fa fa-trash-o'></i>" +
                "          </button>" +
                "      </div>";
            //企业收费信息UID隐藏域
            strHtml += "<input type='hidden' data-allow-clear='false' value='" + this.getValue("chargesInformationUid") + "' id='detail-chargesInformationUid|" + rowNo + "'/>";
        }
        //收费时间
        else if (colName == "chargesDate") {
            strHtml = "<div class='input-group datetime-picker'>" +
                "          <input id='detail-chargesDate|" + rowNo + "' type='text' class='form-control required chargesDate-input' rowNo='" + rowNo + "' data-title='收费时间' maxlength='10' placeholder='请输入收费时间' style='width: 161px;' value='" + rowData[colName] + "'>" +
                "          <span class='input-group-addon' style='width: 39px;'>" +
                "               <span class='glyphicon glyphicon-calendar'></span>" +
                "          </span>" +
                "      </div>";
        }
        //收费人
        else if (colName == "chargersName") {
            strHtml = "<input id='detail-chargersName|" + rowNo + "' type='text' rowNo='" + rowNo + "' class='form-control required chargersName-input' maxlength='32' data-rangelength='[0,32]' placeholder='请输入收费人' value='" + rowData[colName] + "'>";
        }
        //收款方式
        else if (colName == "chargesTypeCd") {
            strHtml = "<select class='form-control required-list chargesTypeCd-input' rowNo='" + rowNo + "' id='detail-chargesTypeCd|" + rowNo + "' value='" + rowData[colName] + "'>";
            var chargesTypeCdDom = $(this.getDom("chargesTypeCd")).clone();
            chargesTypeCdDom.find("option[value='" + rowData[colName] + "']").attr("selected", true);
            strHtml += chargesTypeCdDom.html();
            strHtml += "</select>";
        }
        //备注
        else if (colName == "remark") {
            strHtml = "<input type='text' maxlength='256' rowNo='" + rowNo + "' id='detail-remark|" + rowNo + "' value='" + rowData[colName] + "' class='form-control remark-input' style='width:98%;' placeholder='请输入备注'/>";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    createListAfter: function () {
        //日历控件初期化
        this.initCalendar();
    },

    //行添加
    addRow: function (obj) {
        if (SysApp.Demo.ChargesInformationInputIns.isEditMode()) {
            var dueChargesDom = $(obj).parents("tr").find("td").eq(1).find("nobr");

            var oldValue = dueChargesDom.html();

            dueChargesDom.html(SysApp.Demo.ChargesInformationInputIns.getValue("nextReceiptDate"));

            this._copyRow(obj, false);

            this.addRowCallback();

            dueChargesDom.html(oldValue);
        }
        else {
            this._copyRow(obj, false);

            this.addRowCallback();
        }
    },

    //添加行回调方法
    addRowCallback: function () {
        //日历控件初期化
        this.initCalendar();
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