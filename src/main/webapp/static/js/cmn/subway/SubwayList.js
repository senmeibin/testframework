//定义数据一览画面JS类
SysApp.Cmn.SubwayList = Class.create();

SysApp.Cmn.SubwayList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();

        //地铁线路信息绑定
        this.bindEvent("btnSubwayStationSync", "click", this.onClick_btnSubwayStationSync.bind(this));
    },

    //取得地铁线路接口数据同步
    onClick_btnSubwayStationSync: function () {

        this.showConfirm("地铁信息数据量较大需要一定的时间，确认要同步更新吗？", this.btnSubwayStationSyncAjax.bind(this));

    },

    //手动取得地铁线路接口同步数据
    btnSubwayStationSyncAjax: function () {

        //处理中提示消息
        this.showProcessing(false);

        //Ajax処理（POST方式）
        AjaxIns.sendPOST(this.controller + "initSubwayStationInfo", this.subwayStationCallback.bind(this));
    },

    //手动取得地铁线路接口同步数据
    subwayStationCallback: function (response) {
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }
        var ajaxResult = JsonUtility.Parse(response.responseText);
        if (ajaxResult.result > 0) {
            this.showInfoMessage(ajaxResult.message);
        }
        else {
            this.showErrorMessage(ajaxResult.message);
        }
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("省份", "provinceName", "25%", SORT_YES));
        this.colList.push(new ColInfo("城市", "cityName", "25%", SORT_YES));
        this.colList.push(new ColInfo("线路号", "subwayNo", "30%", SORT_YES));
        this.colList.push(new ColInfo("表示顺序", "dispSeq", "10%", SORT_YES, ALIGN_RIGHT));

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
            strHtml = this.createEditButton(rowNo);
            //地铁站新维护
            strHtml += this.createSubwayStationButton(rowData, "split-line");

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "cityUid") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }

        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //地铁站信息维护
    createSubwayStationButton: function (rowData, className) {
        if (this.isEmpty(className)) {
            className = "";
        }
        else {
            className = " " + className;
        }
        return "<button type='button' class='btn btn-primary" + className + "' onclick='" + this.selfInstance + ".createSubwayStation(\"" + rowData.uid + "\",\"" + rowData.subwayNo + "\",\"" + rowData.provinceName + "\",\"" + rowData.cityName + "\");return false;'><i class='fa fa-deaf fa-width-fixed'></i>维护地铁站</button>";
    },

    //维护地铁站信息
    createSubwayStation: function (subwayUid, subwayNo, provinceName, cityName) {
        this.hideMessage();
        this.SubwayStationPopupListIns = SysApp.Cmn.SubwayStationPopupListIns
        this.SubwayStationPopupListIns.show();
        this.SubwayStationPopupListIns.setSubwayInfo(subwayUid, subwayNo, provinceName, cityName);
        this.SubwayStationPopupListIns.getList(true);
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