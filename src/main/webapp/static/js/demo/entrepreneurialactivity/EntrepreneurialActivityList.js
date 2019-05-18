//定义数据一览画面JS类
SysApp.Demo.EntrepreneurialActivityList = Class.create();

SysApp.Demo.EntrepreneurialActivityList.prototype = Object.extend(new SysApp.Demo.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "6%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("活动编号", "activityNo", "10%", SORT_YES));
        this.colList.push(new ColInfo("活动主题", "activityTopic", "14%", SORT_YES));
        this.colList.push(new ColInfo("会务角色", "conferenceRoleName", "8%", SORT_YES));
        this.colList.push(new ColInfo("服务类型", "serviceTypeName", "8%", SORT_YES));
        this.colList.push(new ColInfo("活动地点", "activityLocation", "14%", SORT_YES));
        this.colList.push(new ColInfo("活动类型", "activityTypeName", "8%", SORT_YES));
        this.colList.push(new ColInfo("参与导师", "tutorName", "8%", SORT_YES));
        this.colList.push(new ColInfo("活动时间", "startTime", "14%", SORT_YES, ALIGN_CENTER));
        this.colList.push(new ColInfo("活动金额", "activityAmount", "8%", SORT_YES, ALIGN_RIGHT));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //初期化所有备选列
    initAllColList: function () {
        this.allColList = [
            new ColInfo("操作", "operation", "100px", SORT_NO, ALIGN_CENTER),
            new ColInfo("活动编号", "activityNo", "100px", SORT_YES),
            new ColInfo("活动主题", "activityTopic", "180px", SORT_YES),
            new ColInfo("会务角色", "conferenceRoleName", "100px", SORT_YES),
            new ColInfo("服务类型", "serviceTypeName", "100px", SORT_YES),
            new ColInfo("活动地点", "activityLocation", "200px", SORT_YES),
            new ColInfo("服务内容", "serviceContentName", "200px", SORT_YES),
            new ColInfo("活动类型", "activityTypeName", "100px", SORT_YES),
            new ColInfo("活动人数", "activityNumber", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("参与导师", "tutorName", "150px", SORT_YES),
            new ColInfo("活动纪要", "activitySummary", "100px", SORT_YES),
            new ColInfo("活动时间", "startTime", "180px", SORT_YES, ALIGN_CENTER),
            new ColInfo("活动金额", "activityAmount", "100px", SORT_YES, ALIGN_RIGHT),
            new ColInfo("备注", "remark", "100px", SORT_YES)
        ];
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);
        }
        //POPUP详细表示
        else if (colName == "activityNo") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }
        //POPUP详细表示
        else if (colName == "tutorName" && this.isNotEmpty(rowData.tutorUid)) {
            strHtml = "";
            var arr = rowData.tutorUid.split(",");
            var arrName = rowData.tutorName.split(",");
            for (var i = 0, len = arr.length; i < len; i++) {
                strHtml += "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showTutorDetail(\"" + arr[i] + "\");return false;'>" + arrName[i] + "</a>&nbsp;";
            }
        }
        //活动时间
        else if (colName == "startTime" && this.isNotEmpty(rowData[colName])) {
            strHtml = rowData[colName].formatYMD() + " 至 " + rowData['endTime'].formatYMD();
        }
        //活动金额
        else if (colName == "activityAmount") {
            strHtml = this.formatCny(rowData[colName]);
        }
        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    showTutorDetail: function (tutorUid) {
        SysApp.Demo.TutorDetailIns.getDetail(tutorUid);
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