//定义数据一览画面JS类
SysApp.Sys.UserList = Class.create();

SysApp.Sys.UserList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("用户名", "userCd", "12%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "10%", SORT_YES));
        this.colList.push(new ColInfo("部门名称", "deptFullName", "19%", SORT_YES));
        this.colList.push(new ColInfo("职位名称", "positionName", "8%", SORT_YES));
        this.colList.push(new ColInfo("邮箱地址", "userMail", "20%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "userPhone", "8%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("入职日期", "entryDate", "7%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("是否启用", "recordStatus", "6%", SORT_YES, ALIGN_CENTER));

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "80px", SORT_NO, ALIGN_CENTER),
            new ColInfo("用户名", "userCd", "150px", SORT_YES),
            new ColInfo("工号", "userNumber", "150px", SORT_YES),
            new ColInfo("考勤号", "attendanceNo", "100px", SORT_YES),
            new ColInfo("姓名", "userName", "100px", SORT_YES),
            new ColInfo("部门名称", "deptFullName", "200px", SORT_YES),
            new ColInfo("职位名称", "positionName", "100px", SORT_YES),
            new ColInfo("已分配权限", "userRoleNames", "250px", SORT_YES),
            new ColInfo("性别", "sexName", "50px", SORT_YES, ALIGN_CENTER),
            new ColInfo("出生日期", "birthday", "75px", SORT_YES, ALIGN_CENTER),
            new ColInfo("英文名", "userEnName", "75px", SORT_YES, ALIGN_CENTER),
            new ColInfo("邮箱地址", "userMail", "150px", SORT_YES),
            new ColInfo("手机号码", "userPhone", "100px", SORT_YES, ALIGN_CENTER),
            new ColInfo("传真号码", "userFaxphone", "100px", SORT_YES),
            new ColInfo("分机号码", "userExtensionNumber", "100px", SORT_YES),
            new ColInfo("家庭地址", "userAddress", "100px", SORT_YES),
            new ColInfo("邮政编码", "userZipcode", "100px", SORT_YES),
            new ColInfo("入职日期", "entryDate", "75px", SORT_YES, ALIGN_CENTER),
            new ColInfo("退职日期", "retireDate", "75px", SORT_YES, ALIGN_CENTER),
            new ColInfo("个人主题", "themeName", "75px", SORT_YES),
            new ColInfo("用户坐席", "userAgent", "75px", SORT_YES),
            new ColInfo("划入时间", "shiftDate", "75px", SORT_YES, ALIGN_CENTER),
            new ColInfo("备注", "remark", "100px", SORT_YES),
            new ColInfo("是否启用", "recordStatus", "80px", SORT_YES, ALIGN_CENTER)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            if (SysApp.Cmn.isUdcServer) {
                //创建列表编辑图标
                strHtml = this.createEditButton(rowNo);
                if (rowData.recordStatus == 1) {
                    strHtml += this.createLockButton(rowNo);
                }
                else {
                    strHtml += this.createUnlockButton(rowNo);
                }
                if (rowData.regSystem == 0) {
                    //创建列表删除图标
                    strHtml += this.createDeleteButton(rowNo, false, "delete");
                }
            }
            else {
                //创建授权按钮
                strHtml = this.createRoleButton(rowNo);
            }

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "userCd") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<span style='color: red;'>停用</span>";
        }
        //日期字段
        else if (colName == "entryDate" || colName == "birthday" || colName == "retireDate" || colName == "shiftDate") {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    createRoleButton: function (rowNo) {
        return "<button type='button' class='btn btn-primary' onclick='" + this.selfInstance + ".goInput(\"" + rowNo + "\");return false;'><i class='fa fa-pencil'></i>&nbsp;权限维护</button>";
    },

    /** ********************以下方法为可选方法********************* */
    //初期化回调函数
    initCallback: function () {
        this.initCommonCallback();
    },

    removeOperationColumn: function () {
        //重写内容，不删除操作栏
        //重写同步提醒消息
        this.setSyncNoticeMessage("用户基础数据自动从UDC服务器同步，您只能进行用户权限维护。");
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
    },

    //创建详细POPUP表示的超链接
    createDetailLink: function (rowData, colName, rowNo) {
        var userCd = this.isNotEmpty(rowData[colName]) ? rowData[colName].split("^")[0] : "";
        //详细控件实例不存在的场合
        if (this.isNull(this.detailInstance)) {
            return userCd.escapeHTML();
        }
        else {
            return "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showDetail(" + rowNo + ");return false;'>" + userCd.escapeHTML() + "</a>";
        }
    }
});