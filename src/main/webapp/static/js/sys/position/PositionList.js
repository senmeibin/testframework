//定义数据一览画面JS类
SysApp.Sys.PositionList = Class.create();

SysApp.Sys.PositionList.prototype = Object.extend(new SysApp.Sys.CommonList(), {

    //初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("职位名称", "positionName", "30%", SORT_YES));
        this.colList.push(new ColInfo("职位级别", "dispSeq", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("是否启用", "recordStatus", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("备注", "remark", "44%", SORT_YES, ALIGN_LEFT));

        //Table共通属性初期化
        this.initTableCommonProperty();

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];
        //操作列
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);
            if (rowData.recordStatus == 1) {
                strHtml += this.createLockButton(rowNo);
            }
            else {
                strHtml += this.createUnlockButton(rowNo);
            }

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo, false, "delete");

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        else if (colName == "positionName") {
            strHtml = "<a href='#' class='edit-link' onclick='" + this.selfInstance + ".goInput(" + rowNo + ");return false;'>" + rowData[colName] + "</a>";
        }
        else if (colName == "dispSeq") {
            var disp_seq = ["", "一级", "二级", "三级", "四级", "五级", "六级", "七级", "八级", "九级", "十级"];
            strHtml = disp_seq[rowData[colName]];
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<font color='red'>停用</font>";
        }
        else if (colName == "updateDate") {
            strHtml = rowData[colName].formatYMD();
        }
        //字符串的场合
        else if (typeof rowData[colName] == "string") {
            //HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }
        return strHtml;
    },

    /**********************以下方法为可选方法**********************/
    //初期化回调函数
    initCallback: function () {
        this.initCommonCallback();
    },

    //画面Dom值自定义设置[检索条件Entity取得之前]
    customDomValue: function () {

    },

    //检索条件Entity自定义设置[检索条件Entity取得之后]
    customSearchEntity: function () {

    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});