//定义数据一览画面JS类
SysApp.Demo.WeeklyReportDetailInnerList = Class.create();

SysApp.Demo.WeeklyReportDetailInnerList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("周次", "weekName", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("已完成工作", "completedWork", "25%", SORT_NO));
        this.colList.push(new ColInfo("未完成工作", "unfinishedWork", "25%", SORT_NO));
        this.colList.push(new ColInfo("开心的事", "happyThing", "25%", SORT_NO));
        this.colList.push(new ColInfo("备注", "remark", "15%", SORT_NO));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];
        //已完成工作
        if (colName == "completedWork") {
            strHtml = "<input type='hidden' id='detail-weekCd|" + rowNo + "' data-allow-clear='false' value='" + rowData.weekCd + "'/>";
            strHtml += "<textarea id='detail-completedWork|" + rowNo + "' rows='3' maxlength='256' cols='30' class='form-control completedWork-input' style='width: 98%;' placeholder='请输入已完成工作'>" + rowData[colName] + "</textarea>";
        }
        //未完成工作
        else if (colName == "unfinishedWork") {
            strHtml = "<textarea id='detail-unfinishedWork|" + rowNo + "' rows='3' maxlength='256' cols='30' class='form-control unfinishedWork-input' style='width: 98%;' placeholder='请输入未完成工作'>" + rowData[colName] + "</textarea>";
        }
        //开心的事
        else if (colName == "happyThing") {
            strHtml = "<textarea id='detail-happyThing|" + rowNo + "' rows='3' maxlength='256' cols='30' class='form-control happyThing-input' style='width: 98%;' placeholder='请输入开心的事'>" + rowData[colName] + "</textarea>";
        }
        //备注
        else if (colName == "remark") {
            strHtml = "<textarea id='detail-remark|" + rowNo + "' rows='3' maxlength='256' cols='30' class='form-control remark-input' style='width: 98%;' placeholder='请输入备注'>" + rowData[colName] + "</textarea>";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //取得成本明细数据【供成本主程序调用】
    getDetailList: function () {
        //原始数据
        var detailList = new Array();
        //新增的数据
        var thisObj = this;
        $(this.getDom("divList")).find("tr:gt(0)").each(function () {
            var row = {};
            $("input[type='text'], input[type='hidden'], select, input[type='checkbox'], input[type='radio'], textarea", this).each(function () {
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