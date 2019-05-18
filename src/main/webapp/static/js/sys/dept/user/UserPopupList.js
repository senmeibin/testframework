//定义数据一览画面JS类
SysApp.Sys.UserPopupList = Class.create();

SysApp.Sys.UserPopupList.prototype = Object.extend(new SysApp.Sys.CommonList(), {
    //初期化回调函数
    initCallback: function () {
        this.autoSearch = false;
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "10%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("用户名", "userCd", "15%", SORT_YES));
        this.colList.push(new ColInfo("工号", "userNumber", "10%", SORT_YES));
        this.colList.push(new ColInfo("姓名", "userName", "15%", SORT_YES));
        this.colList.push(new ColInfo("邮箱地址", "userMail", "17%", SORT_YES));
        this.colList.push(new ColInfo("手机号码", "userPhone", "10%", SORT_YES));
        this.colList.push(new ColInfo("入职日期", "entryDate", "10%", SORT_YES));
        this.colList.push(new ColInfo("是否启用", "recordStatus", "10%", SORT_YES, ALIGN_CENTER));

        this.datagrid.cssClass = "app-table-list";
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            strHtml = "";
            if (rowData.recordStatus == 1) {
                strHtml += this.createLockIcon(rowNo);
            }
            else {
                strHtml += this.createUnlockIcon(rowNo);
            }

            //创建列表编辑图标
            strHtml += this.createEditIcon(rowNo);
            if (rowData.regSystem == 0) {
                //创建列表删除图标
                strHtml += this.createDeleteIcon(rowNo);
            }
        }
        //POPUP详细表示
        else if (colName == "userCd") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        else if (colName == "recordStatus") {
            strHtml = rowData[colName] == "1" ? "启用" : "<font color='red'>停用</font>";
        }
        //日期字段
        else if (colName == "entryDate" || colName == "birthday") {
            strHtml = rowData[colName].formatYMD();
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    setDeptUid: function (deptUid) {
        this.setValue("deptUid", deptUid);
    }
});