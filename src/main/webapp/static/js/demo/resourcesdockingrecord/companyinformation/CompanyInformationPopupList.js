//定义数据一览画面JS类
SysApp.Demo.CompanyInformationPopupList = Class.create();

SysApp.Demo.CompanyInformationPopupList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    first: true,
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.popupPosition = "top";
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        //初期化所有备选列
        this.colList.push(new ColInfo("入孵编号", "companyNo", "13%", SORT_YES));
        this.colList.push(new ColInfo("企业名称", "companyName", "18%", SORT_YES));
        this.colList.push(new ColInfo("入驻时间", "settlingDate", "9%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("组织机构代码", "organizationNo", "13%", SORT_YES, ALIGN_LEFT));
        this.colList.push(new ColInfo("所属基地", "baseName", "10%", SORT_YES));
        this.colList.push(new ColInfo("实际负责人", "companyPrincipal", "9%", SORT_YES));
        this.colList.push(new ColInfo("联系人", "contactPerson", "9%", SORT_YES));
        this.colList.push(new ColInfo("孵化状态", "incubationStateName", "10%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("操作", "operation", "9%", SORT_NO, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建成本图标
            strHtml = this.createOperationIcon(rowNo);
        }
        //日期格式化
        else if ((colName == "contractBeginDate" || colName == "contractDeadlineDate") && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //创建操作图标
    createOperationIcon: function (rowNo) {
        return "<button type='button' class='btn btn-primary' onclick='" + this.selfInstance + ".goResourcesDockingRecord(" + rowNo + ");return false;'><i class='fa fa-check'></i>选择</button>";
    },

    goResourcesDockingRecord: function (rowNo) {
        var rowData = this.getRowData(rowNo);
        window.location = this.resourcesDockingRecordInputUrl + "?companyUid=" + rowData.uid;
    },

    showCallback: function () {
        if (this.first) {
            this.refreshList();
            this.first = false;
        }
    }
});