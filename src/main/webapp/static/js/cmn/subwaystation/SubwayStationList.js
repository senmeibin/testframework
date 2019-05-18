//定义数据一览画面JS类
SysApp.Cmn.SubwayStationList = Class.create();

SysApp.Cmn.SubwayStationList.prototype = Object.extend(new SysApp.Cmn.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.popupPosition = "firstTop";
        //自定义新增地铁站信息按钮事件
        this.bindEvent("btnInsertStation", 'click', this.onClick_InsertStation.bind(this));
    },

    // 初期化一览数据列信息[注意：必须实现的方法]
    initTableColList: function () {
        //Table共通属性初期化
        this.initTableCommonProperty();

        this.colList.push(new ColInfo("操作", "operation", "8%", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("省份", "provinceName", "15%", SORT_YES));
        this.colList.push(new ColInfo("城市", "cityName", "15%", SORT_YES));
        this.colList.push(new ColInfo("线路号", "subwayNo", "15%", SORT_YES));
        this.colList.push(new ColInfo("地铁站名", "stationName", "15%", SORT_YES));
        this.colList.push(new ColInfo("商圈区域", "circleArea", "20%", SORT_YES));
        this.colList.push(new ColInfo("表示顺序", "dispSeq", "10%", SORT_YES, ALIGN_RIGHT));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;

        this.datagrid.cssClass = "app-table-list app-table-list-input";
    },

    //地铁站信息维护
    onClick_InsertStation: function () {
        this.SubwayStationInputIns = SysApp.Cmn.SubwayStationInputIns;
        this.SubwayStationInputIns.clearForm();
        this.SubwayStationInputIns.setTitle("地铁站信息新增");
        this.SubwayStationInputIns.show(this.refreshList.bind(this), 0);
        this.SubwayStationInputIns.setSubwayInfo(this.getValue("subwayUid"), this.getValue("subwayNo"));
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        //操作列按钮
        if (colName == "operation") {
            //创建列表编辑图标
            strHtml = this.createEditButton(rowNo);

            //创建列表删除图标
            strHtml += this.createDeleteButton(rowNo);

            //创建操作按钮组
            strHtml = this.createGroupButton(rowNo, strHtml, "btn-75px");
        }
        //POPUP详细表示
        else if (colName == "subwayUid") {
            strHtml = this.createDetailLink(rowData, colName, rowNo);
        }

        // 字符串的场合
        else if (typeof rowData[colName] == "string") {
            // HTML特殊字符转换处理
            strHtml = rowData[colName].escapeHTML();
        }

        return strHtml;
    },

    //地铁站信息设置
    setSubwayInfo: function (subwayUid, subwayNo, provinceName, cityName) {
        this.setValue("subwayNo", subwayNo);
        this.setValue("subwayUid", subwayUid);
        this.$("ctlTitle").html(provinceName + "-" + cityName + "-" + subwayNo);
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