//定义数据一览画面JS类
SysApp.Sys.EnterpriseList = Class.create();

SysApp.Sys.EnterpriseList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("企业名称", "enterpriseName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("企业简称", "abbreviation", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("企业标签", "enterpriseLabel", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("法人代表", "legalPersonName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("所属行业", "industryName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("企业性质", "propertyName", "10%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("企业规模", "scaleName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("联系人", "contactName", "8%", SORT_YES, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("联系人电话", "contactTelephone", "8%", SORT_YES, ALIGN_CENTER,false, CAMEL_NO));
        this.colList.push(new ColInfo("是否启用", "recordStatus", "6%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("企业名称", "enterpriseName", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("企业简称", "abbreviation", "100px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("企业标签", "enterpriseLabel", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("法人代表", "legalPersonName", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("所属行业", "industryName", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("企业性质", "propertyName", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("企业规模", "scaleName", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("企业地址", "address", "150px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("邮编", "zipcode", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("电话", "telephone", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("传真", "fax", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("邮箱", "email", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("网址", "url", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("注册资金", "registeredCapital", "80px", SORT_YES, ALIGN_RIGHT, false, CAMEL_NO),
            new ColInfo("开票抬头", "invoiceTitle", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("开票注册地址", "invoiceRegisteredAddress", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("开票电话", "invoiceTelephone", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("开票开户行", "invoiceBank", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("开票帐号", "invoiceAccountNo", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("开票税号", "invoiceTaxNo", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("联系人", "contactName", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("联系人电话", "contactTelephone", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("邮箱", "contactEmail", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("业务范围", "businessScope", "80px", SORT_YES, ALIGN_LEFT, false, CAMEL_NO),
            new ColInfo("是否启用", "recordStatus", "80px", SORT_YES, ALIGN_CENTER)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            strHtml = "";
            if (rowData.recordStatus == 1) {
                //创建列表编辑图标
                strHtml = this.createEditButton(rowNo);
                //创建停用图标
                strHtml += this.createLockButton(rowNo);
                //创建设置图标
                strHtml += this.createSettingButton(rowNo);
            }
            else {
                //创建启用图标
                strHtml += this.createUnlockButton(rowNo);
            }

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "enterpriseName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<span style='color: red;'>停用</span>";
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //创建设置图标
    createSettingButton: function (rowNo) {
        return "<button type='button' class='btn btn-primary split-line' onclick='" + this.selfInstance + ".onClick_Setting(" + rowNo + ");return false;'><i class='fa fa-gear fa-width-fixed'></i>设置</button>";
    },

    onClick_Setting: function (rowNo) {
        var json = this.datagrid.data[rowNo];
        SysApp.Sys.EnterpriseSettingInputIns.setEnterpriseName(json.enterpriseName);
        SysApp.Sys.EnterpriseSettingInputIns.show(null, 1, json.uid);
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
    }
});