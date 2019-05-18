//定义数据一览画面JS类
SysApp.Demo.PotentialCustomerList = Class.create();

SysApp.Demo.PotentialCustomerList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("所属基地", "baseName", "8%", SORT_YES));
        this.colList.push(new ColInfo("企业名称", "companyName", "18%", SORT_YES));
        this.colList.push(new ColInfo("组织机构代码", "organizationNo", "14%", SORT_YES));
        this.colList.push(new ColInfo("实际负责人", "companyPrincipal", "8%", SORT_YES));
        this.colList.push(new ColInfo("联系人", "contactPerson", "8%", SORT_YES));
        this.colList.push(new ColInfo("登记注册类型", "registrationTypeName", "8%", SORT_YES));
        this.colList.push(new ColInfo("负责人", "principal", "8%", SORT_YES));
        this.colList.push(new ColInfo("企业类型", "companyTypeName", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("入驻概率", "entryProbabilityName", "8%", SORT_YES, ALIGN_CENTER));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "150px", SORT_NO, ALIGN_CENTER),
            new ColInfo("所属基地", "baseName", "100px", SORT_YES),
            new ColInfo("企业名称", "companyName", "200px", SORT_YES),
            new ColInfo("组织机构代码", "organizationNo", "150px", SORT_YES),
            new ColInfo("注册地区（省）", "registeredProvinceName", "100px", SORT_YES),
            new ColInfo("注册地区（市）", "registeredCityName", "100px", SORT_YES),
            new ColInfo("注册地区（县）", "registeredAreaName", "100px", SORT_YES),
            new ColInfo("注册地址", "registeredAddress", "100px", SORT_YES),
            new ColInfo("成立时间", "establishmentDate", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("注册资金（万元）", "registeredCapital", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("实际负责人", "companyPrincipal", "100px", SORT_YES),
            new ColInfo("负责人电话", "principalPhone", "100px", SORT_YES),
            new ColInfo("负责人邮箱", "principalMail", "100px", SORT_YES),
            new ColInfo("联系人", "contactPerson", "100px", SORT_YES),
            new ColInfo("联系人电话", "contactPhone", "100px", SORT_YES),
            new ColInfo("联系人邮箱", "contactMail", "100px", SORT_YES),
            new ColInfo("办公地址", "officeAddress", "100px", SORT_YES),
            new ColInfo("办公地邮政编码", "officePostalCode", "100px", SORT_YES),
            new ColInfo("登记注册类型", "registrationTypeName", "100px", SORT_YES),
            new ColInfo("高新技术分类", "highTechType", "100px", SORT_YES),
            new ColInfo("行业分类", "industryCategory", "100px", SORT_YES),
            new ColInfo("专业技术方向", "professionalTechnicalName", "100px", SORT_YES),
            new ColInfo("网站/APP", "website", "200px", SORT_YES),
            new ColInfo("企业简介", "companyProfile", "100px", SORT_YES),
            new ColInfo("联络内容", "contactContent", "100px", SORT_YES),
            new ColInfo("负责人", "principal", "100px", SORT_YES),
            new ColInfo("后续对接方案", "followUpScheme", "100px", SORT_YES),
            new ColInfo("企业类型", "companyTypeName", "100px", SORT_YES),
            new ColInfo("入驻概率", "entryProbabilityName", "100px", SORT_YES),
            new ColInfo("招商渠道", "investmentChannelsName", "100px", SORT_YES),
            new ColInfo("备注", "remark", "180px", SORT_YES)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            //创建列表转入图标
            strHtml += this.createTurnButton(rowData.uid);
        }
        //POPUP详细表示
        else if (colName == "companyName") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //成立时间
        else if (colName == "establishmentDate" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    createTurnButton: function (uid) {
        return "<button type='button' class='btn btn-warning' onclick='" + this.selfInstance + ".onClick_turnCompany(\"" + uid + "\");return false;'><i class='fa fa-share fa-width-fixed'></i>转入</button>";
    },

    onClick_turnCompany: function (uid) {
        this.showConfirm("您确定要将当前企业数据转入孵化企业信息中吗？", this.turnCompany.bind(this, uid));
    },

    turnCompany: function (uid) {
        this.showProcessing();
        AjaxIns.sendPOST(this.controller + "turnCompany", this.turnCompanyCallback.bind(this), "uid=" + uid);
    },

    turnCompanyCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);
        if (ajaxResult.result > 0) {
            window.location = this.companyInformationInputUrl + "?uid=" + ajaxResult.content + "&detail=0";
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
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