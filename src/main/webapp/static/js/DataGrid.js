//DataGridUtility.js类文件定义开始-------------------------------------------------------//
//取得CSSClass名称
function getDataGirdClassUID(datagrid, cssGroupName, option) {
    var clsName = "tb_" + datagrid.tableId + "_" + cssGroupName;
    if ($M.isEmpty(option)) {
        return clsName
    }

    return clsName + "_" + option;
}

//单元格Click事件处理
function dataGridCellClick(tableId, rowNo, colName) {
    var datagrid = SysCmn.DataGridList[tableId];
    if (typeof datagrid.cellOnClickCallback == "function") {
        datagrid.cellOnClickCallback(datagrid.getRowData(rowNo), rowNo, colName);
    }
}

//CheckBox选择处理[单选]
function dataGridCheckOne(tableId) {
    var datagrid = SysCmn.DataGridList[tableId];

    //清空上次的内容
    datagrid.selectedIndexs = [];

    $(".dataGridCheckAll_" + tableId).each(function () {
        var rowNo = $(this).attr("rowNo");
        //选择済的场合
        if (this.checked) {
            datagrid.selectedIndexs.push(rowNo);
        }

        //选择状態设定
        datagrid.getRowData(rowNo).sysChecked = this.checked;
    });

    if (typeof datagrid.onCheckedCallback == "function") {
        datagrid.onCheckedCallback(datagrid.selectedIndexs);
    }
}

//CheckBox选择处理[全选]
function dataGridCheckAll(obj, tableId) {
    var datagrid = SysCmn.DataGridList[tableId];

    //清空上次的内容
    datagrid.selectedIndexs = [];

    $(".dataGridCheckAll_" + tableId).each(function () {
        //CheckOne有效的场合、选择可能
        if (this.disabled == false) {
            this.checked = obj.checked;
        }
    });

    dataGridCheckOne(tableId);
}

var DATATABLE_PADDING_WIDTH = 4 * 2;

//重新计算表格各列的列宽(％→px转换)
function reCalcColumnPixelWidth(datagrid) {
    //使用百分比宽度的场合
    if (datagrid.usePercentWidth) return;

    for (var i = 0; i < datagrid.colList.size(); i++) {
        var colInfo = datagrid.colList[i];

        //Pixel的场合
        if (colInfo.originalWidth.indexOf("%") == -1) {
            continue;
        }

        //%的场合
        var divWidth = parseInt($("#" + datagrid.divId).width(), 10);
        if (isNaN(divWidth)) {
            continue;
        }

        //选择功能有效的场合
        if (datagrid.hasData() && (datagrid.useCheckOne || datagrid.useCheckAll)) divWidth = divWidth - 20 - DATATABLE_PADDING_WIDTH;

        //Scroll功能有效的场合
        if (datagrid.hasData() && datagrid.useScroll) divWidth = divWidth - 20;

        //RowNo功能有效的场合
        if (datagrid.hasData() && datagrid.useRowNo) divWidth = divWidth - 35 - DATATABLE_PADDING_WIDTH;

        var w = (divWidth - DATATABLE_PADDING_WIDTH * datagrid.colList.size() - datagrid.colList.size() - 1) * parseInt(colInfo.originalWidth, 10) / 100;
        if (isNaN(w)) {
            continue;
        }

        colInfo.width = (w + 4) + "px";
    }
}

var targetMousedown = false;
var targetResizeable = false;
var targetCell;
var targetCellWidth = 0;
var targetHeaderWidth = 0;
var screenXStart = 0;

function initDataGridColumnResizeEvent(tableId) {
    var targetGrid = $E(tableId);
    var headerCells = targetGrid.rows[0].cells;
    for (var i = 0; i < headerCells.length; i++) {
        Event.observe(headerCells[i], "mousedown", datagridOnMouseDown.bind(window, tableId));
        Event.observe(headerCells[i], "mousemove", datagridOnMouseMove.bind(window, tableId));
    }
}

function datagridOnMouseDown(tableId, event) {
    var targetGrid = $E(tableId);
    if (targetResizeable == true) {
        var evt = event || window.event;
        targetMousedown = true;
        screenXStart = evt.screenX;
        targetCellWidth = targetCell.offsetWidth;
        targetHeaderWidth = targetGrid.offsetWidth;
    }
}

function datagridOnMouseMove(tableId, event) {
    var targetGrid = $E(tableId);
    var evt = event || window.event;

    //当前操作表头列
    var currentCell = evt.target || evt.srcElement;

    //注：解决Firefox无offsetX属性的问题
    var offsetX = evt.offsetX || (evt.clientX - currentCell.getBoundingClientRect().left);
    if (targetMousedown == true) {
        //计算后的新的宽度
        var width = (targetCellWidth + (evt.screenX - screenXStart)) + "px";
        targetCell.style.width = width;
        targetGrid.style.width = (targetHeaderWidth + (evt.screenX - screenXStart)) + "px";
    }
    else {
        var trObj = targetGrid.rows[0];
        //实际改变本单元格列宽
        if (currentCell.offsetWidth - offsetX <= 4) {
            targetCell = currentCell;
            targetResizeable = true;
            //修改光标样式
            currentCell.style.cursor = 'col-resize';
        }
        //实际改变前一单元格列宽，但是表格左边框线不可拖动
        else if (offsetX <= 4 && currentCell.cellIndex > 0) {
            targetCell = trObj.cells[currentCell.cellIndex - 1];
            targetResizeable = true;
            currentCell.style.cursor = 'col-resize';
        }
        else {
            targetResizeable = false;
            currentCell.style.cursor = 'default';
        }
    }
}

document.onmouseup = function (event) {
    targetResizeable = false;
    targetMousedown = false;
}
//DataGridUtility.js类文件定义结束-------------------------------------------------------//


//DataGrid.js类文件定义开始--------------------------------------------------------------//
SysCmn.DataGridList = [];

//居左
var ALIGN_LEFT = "left";
//居中
var ALIGN_CENTER = "center";
//居右
var ALIGN_RIGHT = "right";

//驼峰命名字段
var CAMEL_YES = true;
//下滑分割命名字段
var CAMEL_NO = false;

//数据库字段驼峰标识符
var COL_CAMEL_FLAG = "^C";

//排序：是
var SORT_YES = true;
//排序：否
var SORT_NO = false;
//仅供数据导出列
var ONLY_FOR_EXPORT = true;

//共通表格类定义
SysCmn.DataGrid = function () {
    //分页对象
    this.pager = new SysCmn.DataGridPager();

    this.dom = {};

    //一览数据[包含分页信息]
    this.data = [];
    this.clientData = {};

    //表格列信息
    this.colList = {};

    //表格HTML字符集
    this.tableHtml = [];

    //行鼠标事件有效标志位
    this.useRowMouseEvent = true;

    //行Click事件有效标志位
    this.useRowClickEvent = true;

    //表格表示用DIV
    this.divId = "";

    //表格ID（DIVID + "_grid"）
    this.tableId = "";

    //表格头对象
    this.headerIns = new SysCmn.DataGridHeader();

    //单元格Callback方法
    this.cellCallback = null;

    //单元格ToolTips标题Callback方法
    this.cellToolTipsCallback = null;

    //分页Callback方法
    this.changePageCallback = null;

    //排序CallBack方法
    this.sortDataCallback = null;

    //行选择功能无效/有效标志位
    this.useCheckOne = false;

    //CheckBox显示Callback方法
    this.showCheckOneCallback = null;

    //CheckOne无效状态Callback方法
    this.createCheckboxDisabledCallback = null;

    //全选择功能标志位
    this.useCheckAll = true;

    //选中的行号[单选]
    this.selectedIndex = -1;

    //选中的行号[多选]
    this.selectedIndexs = [];

    //所有列省略表示
    this.ellipsisAllCol = false;

    //ellipsis列一览
    this.ellipsisColList = [];

    //Tips表示列一览
    this.tipsColList = [];

    //合计列一览
    this.totalColList = [];

    //数据输入列一览
    this.inputColList = [];

    //内容改行表示功能标志位
    this.contentNoBr = true;

    //一次性排序功能标志位
    this.sortOne = false;

    //表格内容滚动功能标志位[行头锁定]
    this.useScroll = false;

    //表格CssClass
    this.cssClass = "datatable";

    //行CssClass设定Callback方法
    this.rowClassCallback = null;

    //单元格CssClass设定Callback方法
    this.cellClassCallback = null;

    //行高设定Callback方法
    this.rowHeightCallback = null;

    //行Click Callback方法
    this.rowOnClickCallback = null;

    //单元格Click Callback方法
    this.cellOnClickCallback = null;

    //表格行数
    this.rowCount = 0;

    //表格Header表示用DIV
    this.divHeader = null;

    //表格Body表示用DIV
    this.divTable = null;

    //分页用DIV
    this.divTopPager = null;
    this.divBottomPager = null;
    //显示列表上部分页信息
    this.showTopPager = true;

    //表格头部高度
    this.headerHeight = "20px";

    //Client侧排序功能有效标志位（JSON対象排序）
    this.useClientSort = false;
    this.clientSortCallback = null;

    //默认排序列名称（JSON対象排序）
    this.defaultSortColName = "";
    this.defaultSortMode = "asc";

    //默认全选择功能有效标志位
    this.useDefaultSelectedAll = false;

    //单元格合并Callback方法
    this.colSpanCallback = null;
    //行合并Callback方法
    this.rowSpanCallback = null;

    //是否是合并单元格的后续单元格Callback方法
    this.isSubsequentCellCallback = null;
    //是否是合并行的后续行Callback方法
    this.isSubsequentRowCallback = null;

    //Client侧分页功能有效标志位
    this.useClientChangePage = false;

    //是否启用RowNo功能
    this.useRowNo = false;

    //没有数据时，是否显示没有数据的提示消息？
    this.showNoDataTable = true;

    //是否调整最后列的宽度标志位
    this.resizeLastColumnWidth = true;

    //是否固定宽度表示
    this.fixedWidth = false;

    //表格宽度为100%
    this.tableWidth100Percent = false;

    //过滤HTML/SCRIPT标签
    this.htmlFilterDiv = null;

    this.rowNo = 1;

    //表格头显示标志位
    this.showHeader = true;

    //使用百分比宽度
    this.usePercentWidth = false;

    //是否使用奇偶行功能
    this.useOddStyle = true;

    //标题文本对齐模式(center：居中/left：居左/right：居右/autoWithColInfo：与ColInfo定义一致)
    this.headerTextAlignMode = "center";

    //允许拖放列宽(默认：不允许)
    this.allowResizeColumnWidth = false;

    //是否使用多表头显示
    this.useMultiHeader = false;

    //多表头行间隔
    this.multiHeaderLineStep = 20;
};

SysCmn.DataGrid.prototype = {
    //初期化处理
    init: function (divId, json, colList, cellCallback) {
        for (var i = 0; i < colList.length; i++) {
            var item = colList[i];
            //过滤仅供数据导出列（数据导出列对外不显示）
            if (item.onlyForExport) {
                colList.splice(i, 1);
                i--;
            }
        }

        this.divId = divId;
        this.jDiv = $($E(this.divId));

        var datagrid = this;

        //删除resize事件
        $("#" + this.divId).unbind("resize");

        $("#" + this.divId).resize(function () {
            datagrid.resizeTable();
        });

        //保存表格数据列表
        if (json.content != null && json.content.length > 0) {
            //有分页信息创建json对象
            this.data = json.content;
            this.clientData = this.data;
            this.pager.init(this, json);
        }
        else {
            this.clientData = json;
            //Client侧分页功能有效的场合
            if (this.useClientChangePage) {
                var pager = {};
                pager.pageCount = Math.ceil(parseFloat(json.length) / this.pager.pageSize);

                pager.recordCount = json.length;

                this.pager.init(this, pager);

                this.data = json.slice(0, this.pager.pageSize);

            }
            else {
                this.data = json;
            }
        }

        //非Json数组对象的场合
        if (typeof this.data == "string") {
            this.data = [];
        }

        //设定RowNo
        this.setRowNo();

        //表格行数
        this.rowCount = this.clientData.length;

        //保存表格属性列表
        this.colList = colList;

        //初期化表格标题行对象
        this.headerIns.init(this, colList);

        //保存回调函数
        this.cellCallback = cellCallback;

        //表格ID
        this.tableId = this.getTableId(this.divId);

        //表格头ID
        this.tableHeaderId = "header_table_" + this.tableId;

        //将当前对象放入table列表
        SysCmn.DataGridList[this.tableId] = this;

        //默认排序字段存在的场合
        if (this.defaultSortColName != "") {
            this.headerIns.sortColName = this.defaultSortColName;
            this.headerIns.sortMode = this.defaultSortMode;
            this.data.sort(this.headerIns.sortJson.bind(this.headerIns));
        }

        //重新计算表格各列的列宽(％→px转换)
        reCalcColumnPixelWidth(this);
    },

    //显示数据加载中Message
    showLoadingMessage: function (message) {
        if ($M.isEmpty(message)) {
            message = "列表数据加载中......";
        }
        if (!$M.isNull(this.divTable)) {
            this.divTable.innerHTML = "<img src='" + window.contextPath + "/static/images/base/loading.gif' style='width: 32px;'>&nbsp;<span>" + message + "</span>";
        }
    },

    //设定RowNo
    setRowNo: function () {
        for (var i = 0; i < this.clientData.length; i++) {
            this.clientData[i].sysRowNo = i;
            this.clientData[i].isTotalRow = false;
        }
    },

    //取得表格ID
    getTableId: function (divId) {
        return divId + "_grid";
    },

    //空格图像
    getCellSpaceImage: function () {
        return "<img border=0 src='" + window.imagesPath + "base/spacer.gif'/>";
    },

    //分页大小设定
    setPageSize: function (pageSize) {
        this.pager.setPageSize(pageSize);
    },

    //页码重置
    resetPageNum: function () {
        this.pager.pageNum = 1;
    },

    //修改当前显示页码数
    changeShowPageNum: function (showPageNum) {
        this.pager.changeShowPageNum(showPageNum);
    },

    //取得表格宽度
    getTableWidth: function () {
        var width = 0;
        for (var j = 0; j < this.colList.size(); j++) {
            var colInfo = this.colList[j];
            var w = parseInt(colInfo.width, 10) + DATATABLE_PADDING_WIDTH;
            width += isNaN(w) ? 100 : w;
        }

        width = width + this.colList.length + 1;

        if (this.useCheckOne) {
            width += 20 + DATATABLE_PADDING_WIDTH;
        }

        return width;
    },

    //创建表格关联DIV
    createDiv: function () {
        if (this.divTable == null) {
            //清空
            $E(this.divId).innerHTML = "";

            //分页表示用DIV
            if (this.showTopPager && this.divTopPager == null) {
                this.divTopPager = document.createElement("div");
                $E(this.divId).appendChild(this.divTopPager);
                $(this.divTopPager).hide();
                $(this.divTopPager).css("margin-top", "-10px");
            }

            //表格Header表示用DIV
            this.divHeader = document.createElement("div");
            $E(this.divId).appendChild(this.divHeader);
            this.jHeader = $(this.divHeader);

            //表格Body表示用DIV
            this.divTable = document.createElement("div");
            $E(this.divId).appendChild(this.divTable);
            this.jTable = $(this.divTable);

            //分页表示用DIV
            if (this.divBottomPager == null) {
                this.divBottomPager = document.createElement("div");
                $(this.divBottomPager).hide();
                $E(this.divId).appendChild(this.divBottomPager);
            }
        }
    },

    //表格清空
    clearDataGrid: function () {
        if (!$M.isNull(this.jTable)) {
            this.jTable.html("");
        }
    },

    //重置表格高度
    resetHeight: function () {
        //表格Scroll功能有效的场合
        if (this.useScroll && this.hasData()) {
            //表格头部 与 表格体的高度 小于外围DIV高度时，处理中止
            if (Element.getDimensions($E(this.tableId)).height + Element.getDimensions($E(this.tableHeaderId)).height < Element.getDimensions($E(this.divId)).height) {
                return;
            }

            this.jTable.css("width", "100%");
            this.jTable.css("overflow-y", "auto");
            this.jTable.css("overflow-x", "hidden");
        }
        try {
            this.jTable.height(Element.getDimensions($E(this.divId)).height - parseInt(this.headerHeight, 10) - 10);
        }
        catch (e) {
            //display=none的场合执行以下的代码
            var h = parseInt(Element.getDimensions($E(this.divId)).height, 10);
            if (h <= 0 && isNaN(h)) {
                return;
            }
            h = (h - parseInt(this.headerHeight, 10) - 10);
            if (h <= 0) {
                return;
            }
            this.jTable.css("height", (h - parseInt(this.headerHeight, 10) - 10));
        }
    },

    //创建表格头部
    createHeader: function () {
        ///Scroll功能有效　&&　数据存在的场合
        if (this.useScroll && this.hasData()) {
            this.jHeader.show();
            this.jHeader.html(this.headerIns.create());
        }
        else {
            this.jHeader.html("");
            this.jHeader.hide();
        }
    },

    //创建分页
    createPager: function (divPager) {
        if (!$M.isNull(divPager)) {
            this._createPager(divPager);
            return;
        }

        //显示列表上部分页信息
        if (this.showTopPager) {
            if (this.pager.pageSize > 10) {
                this._createPager(this.divTopPager);
            }
            else {
                var jPager = $($E(this.divTopPager));
                //隐藏分页对象
                jPager.html("");
                jPager.hide();
            }
        }
        this._createPager(this.divBottomPager);
    },

    _createPager: function (divPager) {
        if ($M.isEmpty(divPager) || $M.isNull($E(divPager))) {
            return;
        }

        var jPager = $($E(divPager));

        //数据存在的场合
        if (this.hasData()) {
            var html = this.pager.create();
            jPager.html(html);
            if ($M.isEmpty(html)) {
                jPager.hide();
            }
            else {
                jPager.show();
            }
        }
        else {
            //隐藏分页对象
            jPager.html("");
            jPager.hide();
        }
    },

    //初期化表格关联属性
    initGridValue: function () {
        //选中的行号[单选]
        this.selectedIndex = -1;

        //选中的行号[多选]
        this.selectedIndexs = [];

        //CSS设定
        this.jDiv.css("clear", "both");

        //表格作成用的HTML文字列
        this.tableHtml = [];
    },

    //创建表格主体
    createBody: function () {
        //Scroll功能有效的场合、重置DIV的高度
        if (this.useScroll && this.jTable.attr("scrollheight") != null) {
            this.jTable.height(this.jTable.attr("scrollheight"));
        }

        //重置Scroll位置
        this.divTable.scrollTop = "0px";

        var tableStyle = "";
        //数据不存在 || Scroll功能无效 场合
        if (!this.hasData() || !this.useScroll) {
            if (!this.fixedWidth) {
                tableStyle = " width='100%' ";
                this.tableWidth100Percent = true;
            }
        }

        this.tableHtml.push("<table " + tableStyle + " id='" + this.tableId + "' border='0' cellpadding='0' cellspacing='0' class='" + this.cssClass + "'><tbody>");

        //数据存在的场合
        if (this.hasData()) {
            //Scroll功能无效场合、创建表格头部
            if (!this.useScroll) {
                this.tableHtml.push(this.headerIns.create());
            }

            //创建表格数据行
            this.createAllRow();
        }
        else {
            //创建表格头部
            this.tableHtml.push(this.headerIns.create());
            var colspan = this.colList.length;

            //显示数据不存在的提示消息
            this.tableHtml.push("<tr><td colspan='" + colspan + "'><div class='message-nodata'>没有相关数据。<div></td></tr>");
        }

        this.tableHtml.push("</tbody></table>");

        this.divTable.innerHTML = this.tableHtml.join("");

        if (this.useOddStyle) {
            //奇数行样式
            $("#" + this.tableId + " tr:nth-child(odd)").each(function () {
                $(this).addClass("odd");
                $(this).attr("is_odd", 1);
            });
        }

        //数据存在的场合
        if (this.hasData()) {
            //表格行处理事件及CSS设定
            this.setEventAndStyle();
        }

        this.resetWidthAndHeight();

        //设置数据表格第一列的边框样式
        this.setFirstColumnClass();
    },

    //设置数据表格第一列的边框样式
    setFirstColumnClass: function (tableId) {
        if ($M.isEmpty(tableId)) {
            tableId = this.tableId;
        }
        //设置数据表格第一列的边框样式
        $("#" + tableId + " tr").each(function () {
            $(this).children("th:first").addClass("first-td-border");
            $(this).children("td:first").addClass("first-td-border");
        })

        //Scroll功能有効　并且　数据存在的场合
        if (this.useScroll && this.hasData()) {
            //设置头部标题表格第一列的边框样式
            $("#header_table_" + tableId + " tr").each(function () {
                $(this).children("th:first").addClass("first-td-border");
            })
        }
    },

    //表格行处理事件与CSS设定
    setEventAndStyle: function () {
        if (this.useRowMouseEvent) {
            //行MouseMove事件处理
            this.setRowMouseMoveEvent();
        }
        else {
            //表格行CssStyle设定
            this.setRowStyle();
        }

        //行Click事件处理
        this.setRowClickEvent();
    },

    //重置表格宽度及高度
    resetWidthAndHeight: function () {
        //Scroll功能有效　&&　没有滚动条的场合、重置表格宽度及高度
        if (this.useScroll && Element.getDimensions($E(this.tableId)).height < Element.getDimensions(this.divTable).height) {
            //重置表格最后列宽
            this.resetLastColumnWidth();

            //备份DIV的原始高度
            this.jTable.attr("scrollheight", this.jTable.height());

            //DIV的高度 = 表格的高度
            this.jTable.height((Element.getDimensions($E(this.tableId)).height + 4));
        }

        //Scroll功能無効的场合
        if (!this.useScroll) {
            //重置表格最后列宽
            this.resetLastColumnWidth();
        }
    },

    //重置表格最后列宽
    resetLastColumnWidth: function () {
        if (this.resizeLastColumnWidth == false || this.useScroll == true) return;
        this._resetLastColumnWidth();
        window.setTimeout(this._resetLastColumnWidth.bind(this), 1);
    },

    _resetLastColumnWidth: function () {
        //DIV的宽度与Table的宽度的差值
        var deviation = ($("#" + this.divId).width() - $("#" + this.tableId).width());

        //100%宽 && 正向偏差的场合 处理中止
        if (this.tableWidth100Percent && deviation >= 0) {
            return;
        }

        var lastColObj = $("#" + this.divId + " .tb-last-column");

        var lastColWidth = lastColObj.width();

        //调整最后列的宽度
        lastColObj.css("width", lastColWidth + deviation);
    },

    //创建表格
    create: function () {
        //行号重置
        this.rowNo = 1;

        if (!this.showNoDataTable && !this.hasData()) {
            return;
        }
        //初期值设定
        this.initGridValue();

        //创建表格关联DIV
        this.createDiv();

        //创建表格头部
        this.createHeader();

        //创建表格主体
        this.createBody();

        //创建合计行数据
        this.createTotalRow();

        //创建分页
        this.createPager();

        //表格Scroll功能有效的场合
        if (this.useScroll && this.hasData()) {
            this.resetHeight();
        }

        dataGridCheckOne(this.tableId);
    },

    //表格行CssStyle设定
    setRowStyle: function () {
        $("#" + this.tableId + " tr:gt(0)").css("cursor", "pointer");
    },

    //创建表格数据行
    createAllRow: function () {
        var length = this.data.length;
        //循环行数据
        for (var i = 0; i < length; i++) {
            //使用多表头显示的场合
            if (this.useMultiHeader) {
                //每X行创建一个表头
                if (i > 0 && (i % this.multiHeaderLineStep) == 0 && (length - i) > 5) {
                    this.tableHtml.push(this.headerIns.create());
                }
            }

            //生成行数据
            this.tableHtml.push(this.createRow(this.data[i], this.data[i].sysRowNo));
        }
    },

    getRowNo: function (rowNo) {
        if (rowNo >= (this.pager.pageNum - 1) * this.pager.pageSize) {
            rowNo = rowNo - (this.pager.pageNum - 1) * this.pager.pageSize;
        }
        return (((this.pager.pageNum - 1) * this.pager.pageSize) + rowNo + 1);
    },

    //生成行数据
    //  rowData        json行数据
    //  rowNo          行号
    createRow: function (rowData, rowNo) {
        if (typeof this.createRowCallback == "function") {
            //行无需显示的场合，返回为空
            if (this.createRowCallback(rowData, rowNo) == false) {
                return "";
            }
        }

        //合计行样式类
        var totalRowClass = rowData.isTotalRow == true ? " total-row " : "";
        //合计行属性
        var isTotalRowAttr = rowData.isTotalRow == true ? "isTotalRow='true'" : "";

        //定义行Html
        var strTr = "<tr " + isTotalRowAttr + " rowNo='" + rowNo + "' class='table-default-row " + getDataGirdClassUID(this, rowNo) + totalRowClass + "' >";

        strTr += this.createCell(rowData, rowNo);

        //添加行标签结束标记
        return strTr + "</tr>";
    },

    //创建合计行数据
    createTotalRow: function () {
        //合计列存在的场合
        if (this.totalColList.size() > 0 && this.hasData()) {
            var thisObj = this;
            var totalRow = {};

            //合计行标志位
            totalRow.isTotalRow = true;

            //循环设置合计列数据
            $.each(this.totalColList, function (index, colName) {
                totalRow[colName] = thisObj.getTotal(colName)
            });

            //循环补全其他列属性
            for (var j = 0; j < this.colList.size(); j++) {
                var colInfo = this.colList[j];
                if ($.inArray(colInfo.colName, this.totalColList) == -1) {
                    totalRow[colInfo.colName] = "";
                }
            }

            totalRow.sysRowNo = 0;

            //生成合计行
            $("#" + this.tableId).append(this.createRow(totalRow, totalRow.sysRowNo));
        }
    },

    //创建Checkbox
    createCheckboxCell: function (rowData, rowNo, rowCssClass) {
        var chkHtml = "";
        //行选择功能有效的场合
        if (this.useCheckOne) {
            var disabled = false;

            if (typeof this.createCheckboxDisabledCallback == "function") {
                disabled = this.createCheckboxDisabledCallback(rowData);
            }

            if (disabled) {
                disabled = "disabled"
            }

            var checked = "";
            if (rowData.sysChecked) {
                checked = " checked "
            }

            if (typeof this.createCheckboxCallback == "function") {
                if (this.createCheckboxCallback(rowData)) {
                    checked = " checked ";
                }
            }

            if (this.useDefaultSelectedAll) {
                checked = " checked ";
            }

            var showCheckOne = true;
            if (typeof this.showCheckOneCallback == "function") {
                showCheckOne = this.showCheckOneCallback(rowData);
            }

            chkHtml = "<td class='" + rowCssClass + "' align='center'><div class='tb-cell-content table-cell-20px'>";

            if (showCheckOne && rowData.isTotalRow != true) {
                chkHtml += "<input " + checked + disabled + " class='dataGridCheckAll_" + this.tableId + "' type='checkbox' rowNo='" + rowNo + "' onclick=\"dataGridCheckOne('" + this.tableId + "')\"/>";
            }

            chkHtml += "<div></td>"
        }

        return chkHtml;
    },

    //取得行CSSClass
    getRowCssClass: function (rowData, rowNo) {
        var rowCssClass = "";

        if (typeof this.rowClassCallback == "function") {
            rowCssClass = this.rowClassCallback(rowData, rowNo);
        }
        return rowCssClass;
    },

    //创建单元格
    createCell: function (rowData, rowNo) {
        //取得行CSSClass
        var rowCssClass = this.getRowCssClass(rowData, rowNo);
        //创建Checkbox
        var strTd = this.createCheckboxCell(rowData, rowNo, rowCssClass);

        if (this.useRowNo) {
            var SN = this.getRowNo(rowNo);
            var w = "35px";
            if (SN >= 10000) {
                w = "50px";
            }

            if (typeof this.rowNoCallback == "function") {
                SN = this.rowNoCallback(rowData, rowNo);
            }

            var className = rowCssClass;

            //合计行的场合
            if (rowData.isTotalRow == true) {
                SN = "";
            }
            strTd += "<td nowrap align='center' width='" + w + "' class='" + className + "'>" + SN + "</td>";
        }

        var rowHeight = "20px";

        if (typeof this.rowHeightCallback == "function") {
            rowHeight = this.rowHeightCallback(rowData);
        }

        //循环列数据
        for (var j = 0; j < this.colList.size(); j++) {
            //获取当前列属性对象
            var colInfo = this.colList[j];

            //获取当前列属性中单元格位置（左、中、右）
            var align = colInfo.cellAlign;

            //获取当前列的原始值
            var original_value = rowData[colInfo.colName];

            //subCol对象存在的场合，通过subCol取得具体值
            if (!$M.isEmpty(colInfo.subCol)) {
                //无数组索引的场合
                if ($M.isNull(colInfo.index)) {
                    original_value = rowData[colInfo.subCol][colInfo.colName];
                }
                //有数组索引的场合
                else {
                    original_value = rowData[colInfo.subCol][colInfo.index][colInfo.colName];
                }
            }

            var value = original_value;

            //单元格Callback方法存在的场合
            if (typeof this.cellCallback == "function") {
                try {
                    //合计行的场合
                    if (rowData.isTotalRow == true) {
                        //只有合计列才执行 单元格Callback方法
                        if ($.inArray(colInfo.colName, this.totalColList) != -1) {
                            value = this.cellCallback(rowData, colInfo.colName, rowNo, colInfo);
                        }
                    }
                    else {
                        value = this.cellCallback(rowData, colInfo.colName, rowNo, colInfo);
                    }
                }
                catch (e) {
                    console.warn(e.message + "(colName:" + colInfo.colName + ")");
                }
            }
            //文字列的场合
            else if (typeof value == "string") {
                value = value.escapeHTML();
            }

            if (typeof value == "string") {
                //过滤<script>脚本
                value = value.stripScripts();
            }

            //过滤HTML/SCRIPT标签
            if ($M.isNull(this.htmlFilterDiv)) {
                this.htmlFilterDiv = document.createElement("div");
            }
            $(this.htmlFilterDiv).html(value)

            //获取当前列的值
            var tips = $(this.htmlFilterDiv).text();

            //单元格ToolTips标题Callback方法存在的场合
            if (typeof this.cellToolTipsCallback == "function") {
                tips = this.cellToolTipsCallback(rowData, colInfo.colName, rowNo);
            }

            var value = (value == null || $.trim(value.toString()) == "") ? this.getCellSpaceImage() : value;

            var cellClass = rowCssClass;

            //单元格样式单优先
            if (typeof this.cellClassCallback == "function") {
                var temp = this.cellClassCallback(rowData, colInfo.colName, colInfo);
                if (!$M.isEmpty(temp)) {
                    cellClass = temp;
                }
            }

            //单元格合并处理判断
            var colspan = 1;
            if (typeof this.colSpanCallback == "function") {
                colspan = this.colSpanCallback(rowData, colInfo.colName, rowNo);
            }
            if ($M.isNull(colspan)) {
                colspan = 1;
            }

            //行合并处理判断
            var rowspan = 1;
            if (typeof this.rowSpanCallback == "function") {
                rowspan = this.rowSpanCallback(rowData, colInfo.colName, rowNo);
            }
            if ($M.isNull(rowspan)) {
                rowspan = 1;
            }

            var width = parseInt(colInfo.width, 10);
            //有单元格合并处理的场合，合并单元格的宽度再计算
            if (colspan > 1) {
                for (var k = j + 1; k < j + colspan; k++) {
                    width += parseInt(this.colList[k].width, 10) + DATATABLE_PADDING_WIDTH + 1;
                }
            }
            width = width + "px";
            //判断是否是合并单元格的后续单元格
            if (typeof this.isSubsequentCellCallback == "function") {
                //后续单元格的场合，本单元格处理中止
                if (this.isSubsequentCellCallback(rowData, colInfo.colName, rowNo)) {
                    continue;
                }
            }

            //判断是否是合并行的后续行
            if (typeof this.isSubsequentRowCallback == "function") {
                //后续单元格的场合，本单元格处理中止
                if (this.isSubsequentRowCallback(rowData, colInfo.colName, rowNo)) {
                    continue;
                }
            }

            var lastColClass = "";
            //设置最后列的样式类，为表格宽度自动调整用[resetLastColumnWidth方法内使用]
            if (j == this.colList.size() - 1) {
                lastColClass = " tb-last-column";
            }

            if ($M.isNull(tips) || colInfo.colName == "operation") {
                tips = "";
            }

            if (tips.length > 256) {
                tips = tips.substring(0, 256);
            }

            var text_overflow = "";
            //省略表示的场合
            if ((this.ellipsisAllCol && colInfo.colName != "operation" && $.inArray(colInfo.colName, this.totalColList) == -1) || $.inArray(colInfo.colName, this.ellipsisColList) != -1) {
                if (this.contentNoBr) {
                    value = "<NOBR>" + value + "</NOBR>";
                }
                text_overflow = " text-overflow:ellipsis";
            }

            var titleTips = "";
            //Tips表示的场合
            if ((this.ellipsisAllCol && colInfo.colName != "operation" && $.inArray(colInfo.colName, this.totalColList) == -1) || $.inArray(colInfo.colName, this.ellipsisColList) != -1) {
                if (!$M.isEmpty(tips)) {
                    titleTips = " title='" + tips.escapeHTML() + "'";
                }
            }
            if ($.inArray(colInfo.colName, this.tipsColList) != -1) {
                if (!$M.isEmpty(tips)) {
                    titleTips = " title='" + tips.escapeHTML() + "'";
                }
            }

            //合计单元格的场合
            if (rowData.isTotalRow && $.inArray(colInfo.colName, this.totalColList) != -1) {
                cellClass += " total-cell";
            }

            //合计单元格属性
            var is_total_cell_attr = rowData.isTotalRow == true ? "is_total_cell='true'" : "";

            //使用百分比宽度的场合
            if (this.usePercentWidth) {
                strTd += "<td width='" + colInfo.width + "' " + is_total_cell_attr + " colspan=" + colspan + " rowspan=" + rowspan + " height=" + rowHeight +
                    "      onclick=\"dataGridCellClick('" + this.tableId + "', " + rowNo + ", '" + colInfo.colName + "');\" " +
                    "      class='" + getDataGirdClassUID(this, colInfo.colName) + " " + cellClass + "' align='" + align + "' " + titleTips + ">" + value +
                    "</td>";
            }
            else {
                //允许拖放列宽的场合
                if (this.allowResizeColumnWidth) {

                }
                //非数据输入列的场合
                if ($.inArray(colInfo.colName, this.inputColList) == -1) {
                    strTd += "<td " + is_total_cell_attr + " colspan=" + colspan + " rowspan=" + rowspan + " height=" + rowHeight +
                        "      onclick=\"dataGridCellClick('" + this.tableId + "', " + rowNo + ", '" + colInfo.colName + "');\" " +
                        "      class='" + getDataGirdClassUID(this, colInfo.colName) + " " + cellClass + "' align='" + align + "' " + titleTips + ">" +
                        "      <div class='tb-cell-content" + lastColClass + "' style='width: " + width + "; overflow: hidden; " + text_overflow + "'>" + value + "</div>" +
                        "</td>";
                }
                else {
                    strTd += "<td " + is_total_cell_attr + " colspan=" + colspan + " rowspan=" + rowspan + " height=" + rowHeight +
                        "      onclick=\"dataGridCellClick('" + this.tableId + "', " + rowNo + ", '" + colInfo.colName + "');\" " +
                        "      class='" + getDataGirdClassUID(this, colInfo.colName) + " " + cellClass + "' align='" + align + "' " + titleTips + ">" +
                        "      <div class='tb-cell-content" + lastColClass + "' style='width: " + width + ";'>" + value + "</div>" +
                        "</td>";
                }
            }
        }
        return strTd;
    },

    //创建合计单元格
    createTotalCell: function () {
        var strTd = "";
        //行选择功能有效的场合
        if (this.useCheckOne) {
            strTd += "<td style='border:#FFFFFF;' align='center'></td>";
        }

        //RowNo功能有效的场合
        if (this.useRowNo) {
            strTd += "<td style='border:#FFFFFF;' align='center'></td>";
        }

        //循环列数据
        for (var j = 0; j < this.colList.size(); j++) {
            //获取当前列属性对象
            var colInfo = this.colList[j];

            if ($.inArray(colInfo.colName, this.totalColList) != -1) {
                strTd += "<td align='right' style='border:#FFFFFF;font-weight:bold;'><div class='" + getDataGirdClassUID(this, "totalCol", colInfo.colName) + "'>" + this.getTotal(colInfo.colName) + "<div></td>";
            }
            else {
                strTd += "<td align='right' style='border:#FFFFFF;'></td>";
            }
        }
        return strTd;
    },

    //取得指定列的合计值
    getTotal: function (colName) {
        var total = 0.0;
        if (this.hasData()) {
            //循环行数据
            for (var i = 0; i < this.data.length; i++) {
                var v = parseFloat(this.data[i][colName]);
                total += isNaN(v) ? 0 : v;
            }
        }
        return total;
    },

    //行MouseMove事件处理
    setRowMouseMoveEvent: function () {
        //定义选择器
        var selector = "#" + this.tableId + " tr";

        //解除mouseover/mouseout事件
        $(selector).unbind("mouseover");
        $(selector).unbind("mouseout");

        //鼠标移入/移出
        $(selector).bind("mouseover mouseout", function () {
            //行未选择的场合
            if ($M.isEmpty($(this).attr("selected"))) {
                if ($(this).attr("is_odd") == 1) {
                    $(this).toggleClass("odd");
                }
                $(this).toggleClass("table-mouseover-row");
            }
        });
    },

    //行Click事件处理
    setRowClickEvent: function () {
        var datagrid = this;

        //定义选择器
        var selector = "#" + this.tableId + " tr";

        //解除Click事件
        $(selector).unbind("click");

        //行单击事件
        $(selector).click(function () {
            var rowNo = $(this).attr("rowNo");
            //表格Header的场合、处理中止
            if ($M.isEmpty(rowNo)) {
                return;
            }

            if (!datagrid.useCheckOne) {
                if (datagrid.selectedIndex == rowNo) {
                    //设定行号
                    datagrid.selectedIndex = -1;
                }
                else {
                    //设定行号
                    datagrid.selectedIndex = rowNo;
                }
            }

            //行ClickCallback存在的场合
            if (typeof datagrid.rowOnClickCallback == "function") {
                datagrid.rowOnClickCallback(datagrid.getRowData(rowNo), rowNo);
            }

            if (!datagrid.useRowClickEvent) {
                return;
            }

            if (datagrid.useCheckOne) {
                return;
            }

            //指定行双击标志位
            var doubleClick = false;

            //选中的行再度Click的场合、解除选择状态
            if ($(this).attr("selected") == "selected") {
                $(this).removeAttr("selected");
                doubleClick = true;
                Element.removeClassName(this, "table-selected-row");
                Element.removeClassName(this, "table-mouseover-row");
            }
            else {
                $(selector).each(function () {
                    if ($(this).attr("selected") == "selected") {
                        Element.removeClassName(this, "table-selected-row");
                        Element.removeClassName(this, "table-mouseover-row");
                    }
                });

                $(selector).removeAttr("selected");
            }

            //行选择切换的场合
            if (!doubleClick) {
                Element.removeClassName(this, "table-mouseover-row");
                Element.addClassName(this, "table-selected-row");

                $(this).attr("selected", "selected").end();
            }
        })
    },

    //取得分页条件JSON字符串
    getConditionJson: function () {
        return "pagerJson=" + this.getConditionString();
    },

    getConditionString: function () {
        var sortColumn = this.headerIns.sortColName;
        if (this.headerIns.camelFlag) {
            sortColumn += COL_CAMEL_FLAG;
        }
        return "{ \"sortColumn\": \"" + sortColumn + "\", \"sortMode\":\"" + this.headerIns.sortMode + "\", \"pageSize\": \"" + this.pager.pageSize + "\", \"pageNum\":\"" + this.pager.pageNum + "\"}";
    },

    //隐藏指定列
    hideColumn: function (cols) {
        if (typeof cols == "string") {
            $("." + getDataGirdClassUID(this, cols)).hide();
        }
        else if (typeof cols == "object") {
            var datagrid = this;
            $.each(cols, function (key, val) {
                $("." + getDataGirdClassUID(datagrid, val)).hide();
            });
        }
    },

    //显示指定列
    showColumn: function (cols) {
        if (typeof cols == "string") {
            $("." + getDataGirdClassUID(this, cols)).show();
        }
        else if (typeof cols == "object") {
            var datagrid = this;
            $.each(cols, function (key, val) {
                $("." + getDataGirdClassUID(datagrid, val)).show();
            });
        }
    },

    //设置列宽度
    resizeColumn: function (col, width) {
        $("." + getDataGirdClassUID(this, col) + " .tb-cell-content").css("width", width);
    },


    //行追加
    //  rowData        json行数据
    //  rowCssClass    追加行的CssClass
    //  addRowCallBack 追加处理Callback事件
    addRow: function (rowData, rowCssClass, addRowCallBack) {
        if (!this.hasData()) {
            rowData.sysRowNo = this.rowCount;
            this.data.push(rowData);
            this.create();
            this.rowCount++;
            return;
        }
        this._insertRow(-1, null, rowData, rowCssClass, addRowCallBack);
    },

    //指定行前追加
    //  rowNo          行番号
    //  rowData        json行数据
    //  rowCssClass    追加行的CssClass
    //  addRowCallBack 追加处理Callback事件
    insertRowBefore: function (rowNo, rowData, rowCssClass, addRowCallBack) {
        this._insertRow(rowNo, "before", rowData, rowCssClass, addRowCallBack);
    },

    //指定行后追加
    //  rowNo          行番号
    //  rowData        json行数据
    //  rowCssClass    追加行的CssClass
    //  addRowCallBack 追加处理Callback事件
    insertRowAfter: function (rowNo, rowData, rowCssClass, addRowCallBack) {
        this._insertRow(rowNo, "after", rowData, rowCssClass, addRowCallBack);
    },

    _insertRow: function (rowNo, type, rowData, rowCssClass, addRowCallBack) {
        rowData.sysRowNo = this.rowCount;

        //删除[数据不存在]消息提示
        if (!$M.isNull($(".message-nodata"))) {
            $(".message-nodata").remove();
        }

        if (rowNo == -1) {
            //table行添加
            $("#" + this.tableId).append(this.createRow(rowData, this.rowCount));
            //行尾追加
            this.data.push(rowData);
        }
        else if (type == "after") {
            $("." + getDataGirdClassUID(this, rowNo)).after(this.createRow(rowData, this.rowCount));

            //指定行后追加
            for (var i = 0; i < this.data.length; i++) {
                if (this.data[i].sysRowNo == rowNo) {
                    var array1 = this.data.slice(0, i + 1);
                    array1.push(rowData);

                    var array2 = this.data.slice(i + 1);

                    this.data = array1.concat(array2);
                    break;
                }
            }
        }
        else if (type == "before") {
            $("." + getDataGirdClassUID(this, rowNo)).before(this.createRow(rowData, this.rowCount));

            //指定行前追加
            for (var i = 0; i < this.data.length; i++) {
                if (this.data[i].sysRowNo == rowNo) {
                    var array1 = this.data.slice(0, i);
                    array1.push(rowData);

                    var array2 = this.data.slice(i);

                    this.data = array1.concat(array2);
                    break;
                }
            }
        }

        //表格行处理事件及CSS设定
        this.setEventAndStyle();

        //判断回调函数是否为方法
        if (typeof addRowCallBack == "function") {
            //调用回调函数返回添加的行号
            addRowCallBack(rowData, this.rowCount);
        }

        if ($M.isEmpty(rowCssClass)) {
            rowCssClass = "table-list-inserted";
        }

        //追加行的CssClass设定
        $("." + getDataGirdClassUID(this, this.rowCount)).each(function () {
            $(this).addClass(rowCssClass);
        });

        this.rowCount++;

        //显示表格头部的Checkbox
        $("." + getDataGirdClassUID(this, "header_checkbox")).css("display", "");

        //创建合计行数据
        this.createTotalRow();
    },

    //削除指定行
    deleteRow: function (rowNo) {
        if (typeof rowNo == "number") {
            $("." + getDataGirdClassUID(this, rowNo)).remove();

            for (var i = 0; i < this.data.length; i++) {
                if (this.data[i].sysRowNo == rowNo) {
                    this.data.splice(i, 1);
                }
            }
        }
        else if (typeof rowNo == "object") {
            var datagrid = this;
            $.each(rowNo, function (key, val) {
                $("." + getDataGirdClassUID(datagrid, val)).remove();

                for (var i = 0; i < datagrid.data.length; i++) {
                    if (datagrid.data[i].sysRowNo == val) {
                        datagrid.data.splice(i, 1);
                        i--;
                    }
                }
            });
        }
    },

    //隐藏指定行
    hideRow: function (rowNo) {
        if (typeof rowNo == "number") {
            $("." + getDataGirdClassUID(this, rowNo)).hide();
        }
        else if (typeof rowNo == "object") {
            var datagrid = this;
            $.each(rowNo, function (key, val) {
                $("." + getDataGirdClassUID(datagrid, val)).hide();
            });
        }
    },

    //显示指定行
    showRow: function (rowNo) {
        if (typeof rowNo == "number") {
            $("." + getDataGirdClassUID(this, rowNo)).show();
        }
        else if (typeof rowNo == "object") {
            var datagrid = this;
            $.each(rowNo, function (key, val) {
                $("." + getDataGirdClassUID(datagrid, val)).show();
            });
        }
    },

    //重画指定行
    redrawRow: function (rowNo, callback) {
        if (typeof rowNo == "number") {
            $("." + getDataGirdClassUID(this, rowNo)).html(this.createCell(this.getRowData(rowNo), rowNo));
        }
        else if (typeof rowNo == "object") {
            var datagrid = this;
            $.each(rowNo, function (key, val) {
                $("." + getDataGirdClassUID(datagrid, val)).html(datagrid.createCell(datagrid.getRowData(val), val));
            });
        }

        if (typeof callback == "function") {
            callback();
        }
    },

    //行交換处理(Json数据也一起交换)
    wrapRow: function (srcRowNo, descRowNo, callback) {
        this.wrapData(srcRowNo, descRowNo);

        this.redrawRow(srcRowNo);
        this.redrawRow(descRowNo);

        if (typeof callback == "function") {
            callback();
        }
    },

    //行Json数据交换
    wrapData: function (srcRowNo, descRowNo, callback) {
        var srcData = this.getRowData(srcRowNo);
        var descData = this.getRowData(descRowNo);

        //循环行数据
        for (var i = 0; i < this.data.length; i++) {
            //找到源数据后设置为目标数据
            if (this.data[i].sysRowNo == srcRowNo) {
                this.data[i] = descData;
                continue;
            }

            //找到目标数据后设置为源数据
            if (this.data[i].sysRowNo == descRowNo) {
                this.data[i] = srcData;
            }
        }

        //交换源数据及目标数据的rowNo
        srcData.sysRowNo = descRowNo;
        descData.sysRowNo = srcRowNo;

        if (typeof callback == "function") {
            callback();
        }
    },

    //指定行样式类追加
    addClass: function (rowNo, rowCssClass) {
        $("." + getDataGirdClassUID(this, rowNo)).each(function () {
            $(this).addClass(rowCssClass);
        });
    },

    //指定行样式类删除
    removeClass: function (rowNo, rowCssClass) {
        $("." + getDataGirdClassUID(this, rowNo)).each(function () {
            $(this).removeClass(rowCssClass);
        });
    },

    //重置表格列宽
    resizeTable: function () {
        var datagrid = this;
        for (var j = 0; j < this.colList.size(); j++) {
            var colInfo = this.colList[j];
            $("." + getDataGirdClassUID(datagrid, colInfo.colName) + " .tb-cell-content").css("width", colInfo.width);
        }

        //无滚动条、重置表格宽度
        if (this.useScroll && !$M.isNull($E(this.tableId)) && Element.getDimensions($E(this.tableId)).height <= Element.getDimensions(this.divTable).height) {
            //重置表格最后列宽
            this.resetLastColumnWidth();
        }
    },

    //取得选中行数据[单选]
    getSelectedRow: function () {
        return this.getRowData(this.selectedIndex);
    },

    //取得选中行数据[多选]
    getSelectedRows: function () {
        var selectedRows = [];

        for (var i = 0; i < this.selectedIndexs.length; i++) {
            selectedRows.push(this.getRowData(this.selectedIndexs[i]));
        }

        return selectedRows;
    },

    //取得行数据
    //  rowNo   行番号
    getRowData: function (rowNo) {
        if (rowNo > -1 && this.hasData()) {
            //循环行数据
            for (var i = 0; i < this.data.length; i++) {
                if (this.data[i].sysRowNo == rowNo) {
                    return this.data[i];
                }
            }
        }
        return null;
    },

    clearCheck: function () {
        //循环行数据
        for (var i = 0; i < this.data.length; i++) {
            this.data[i].sysChecked = false;
        }

        //选中的行号[单选]
        this.selectedIndex = -1;

        //选中的行号[多选]
        this.selectedIndexs = [];
    },

    //是否有数据？
    hasData: function () {
        if (!$M.isNull(this.data) && !$M.isNull(this.data.length) && this.data.length > 0) {
            return true;
        }
        return false;
    },

    //设置选择状态
    setCheckStatus: function (rowNo, status) {
        var datagrid = this;

        if (typeof rowNo == "number") {
            $(".dataGridCheckAll_" + datagrid.tableId).each(function () {
                datagrid._setCheckStatus(this, rowNo, status);
            });
        }
        else if (typeof rowNo == "object") {
            $.each(rowNo, function (key, val) {
                $(".dataGridCheckAll_" + datagrid.tableId).each(function () {
                    datagrid._setCheckStatus(this, val, status);
                });
            });
        }

        dataGridCheckOne(this.tableId);
    },

    _setCheckStatus: function (obj, rowNo, status) {
        var tempRowNo = $(obj).attr("rowNo");
        if (tempRowNo == rowNo) {
            obj.checked = status;
        }
    }
};
//DataGrid.js类文件定义结束--------------------------------------------------------------//


//DataGridColumn.js类文件定义开始--------------------------------------------------------//
//共通Table列属性定义类
//   title          列标题
//   colName        列字段名称
//   width          宽度
//   sortFlag       排序许可标志[可选]
//   cellAlign      表示位置(LEFT/RIGHT/CENTER)[可选，默认LEFT]
//   onlyForExport  仅供数据导出列[可选，默认false]
//   camelFlag      驼峰命名字段[可选，默认false]
var ColInfo = function (title, colName, width, sortFlag, cellAlign, onlyForExport, camelFlag) {
    width = new String(width);
    this.title = title;

    this.colName = colName;

    this.onlyForExport = onlyForExport;
    if ($M.isEmpty(this.onlyForExport)) {
        this.onlyForExport = false;
    }

    this.camelFlag = camelFlag;
    if ($M.isEmpty(this.camelFlag)) {
        this.camelFlag = false;
    }

    //絶対幅表示[像素宽度表示]
    if (width.indexOf("%") == -1) {
        //未指定px的场合
        if (width.indexOf("px") == -1) {
            this.width = width + "px";
        }
        else {
            this.width = width;
        }
    }
    else {
        this.width = width;
    }

    //原始宽度[记录程序初期设置的具体宽度]
    this.originalWidth = this.width;

    this.sortFlag = sortFlag;

    if (cellAlign == null || typeof cellAlign == undefined) {
        this.cellAlign = "left";
    }
    else {
        this.cellAlign = cellAlign;
    }

    this.sortMode = "asc";

    this.originalColName = this.colName;

    var dot_index = colName.indexOf(".");
    //对象属性分割符存在的场合
    if (dot_index > 0) {
        this.colName = this.originalColName.substr(dot_index + 1);

        //设置列子属性
        this.subCol = this.originalColName.substr(0, dot_index);

        var bracket_left = this.subCol.indexOf("[");
        var bracket_right = this.subCol.indexOf("]");

        //数组的场合
        if (bracket_left > 0 && bracket_right > 0) {
            this.index = parseInt(this.subCol.substr(bracket_left + 1, (bracket_right - bracket_left - 1)), 10);

            this.subCol = this.subCol.substr(0, bracket_left);
        }
    }
}
//DataGridColumn.js类文件定义结束--------------------------------------------------------//


//DataGridPager.js类文件定义开始---------------------------------------------------------//
var PAGE_FIRST = "第一页";
var PAGE_PREV = "上一页";
var PAGE_NEXT = "下一页";
var PAGE_LAST = "最末页";

//分页类
SysCmn.DataGridPager = function () {
    this.dom = {};
    this.params = {};

    //JSON数据
    this.data = null;

    //表格对象实例
    this.datagrid = null;

    //每页记录数
    this.pageSize = 10;

    //页码
    this.pageNum = 1;

    //表示的分页数
    this.showPageNum = 5;

    //数据记录信息表示Flag
    this.showRecordInfo = true;
};

//定义分页属性
SysCmn.DataGridPager.prototype = {
    //初期化处理
    init: function (datagrid, data) {
        this.datagrid = datagrid;
        this.data = data;
        if (!$M.isEmpty(data.sort)) {
            this.data.sortMode = data.sort[0].direction;
            this.data.sortColumn = data.sort[0].property;
        }
        else {
            this.data.sortMode = "desc";
            this.data.sortColumn = "uid";
        }
        this.pageSize = data.size;
        this.pageNum = data.number + 1;
    },

    //设定每页记录数
    setPageSize: function (pageSize) {
        this.pageSize = pageSize;
    },

    //修改当前显示页码数
    changeShowPageNum: function (showPageNum) {
        this.showPageNum = showPageNum;
    },

    //创建分页对象HTML字符串
    create: function () {
        //数据不存在的、处理中止
        if (this.data == null) {
            return "";
        }

        //无分页数据的场合、处理中止
        if (this.data.pageCount <= 1 || this.data.totalPages <= 1) {
            //显示数据记录信息 && 记录数超过10条
            if (this.showRecordInfo && this.data.totalElements > 10 && !this.datagrid.useScroll) {
                return "<div class='pagination-record-info-no-page'>共 <b>" + this.data.totalElements + "</b> 条记录，当前第 <b>" + this.pageNum + "/" + this.data.totalPages + "</b> 页</div>";
            }
            else {
                return "";
            }
        }

        //获取当前页码
        var currPage = this.pageNum;

        //获取总页数
        var pageCount = this.data.totalPages;

        var html = "";
        if (this.showRecordInfo && !this.datagrid.useScroll) {
            html = "<div class='pagination-record-info'>共 <b>" + this.data.totalElements + "</b> 条记录，当前第 <b>" + this.pageNum + "/" + this.data.totalPages + "</b> 页</div>"
        }

        //页标签Html初期化
        html += "<ul class='pagination'>";

        //生成[首页]A标签
        html += this.bindPageProperty(PAGE_FIRST, currPage, pageCount);
        //生成[上一页]A标签
        html += this.bindPageProperty(PAGE_PREV, currPage, pageCount);

        //开始显示的页号
        var pageNum = 1;

        if (pageCount > this.showPageNum) {
            if (currPage > pageCount - Math.floor(this.showPageNum / 2)) {
                //当前页大于要显示的页码号时
                pageNum = pageCount - this.showPageNum + 1;
            }
            //当前页大于三页时
            else if (currPage > this.showPageNum - 2) {
                pageNum = currPage - Math.floor(this.showPageNum / 2);
            }
        }

        //循环生成页码标签
        for (var i = 0; i < this.showPageNum; i++) {
            //i = 0 并且 当前页码 > 3
            if (pageCount > this.showPageNum && i == 0 && currPage > 3) {
                //添加第一页信息
                html += this.bindPageProperty(1);
                //添加...
                html += this.bindPageProperty("...");
            }
            //选择的页不是当前页
            if (currPage != pageNum) {
                //追加A标签内容
                html += this.bindPageProperty(pageNum);
            }
            else {
                //追加A标签内容
                html += this.bindPageProperty(pageNum, currPage);
            }

            //i = 显示最大页码 并且 当前页码 + 2 < 总页数
            if (i == this.showPageNum - 1 && parseInt(currPage, 10) + 2 < pageCount && this.showPageNum < pageCount) {
                //添加...
                html += this.bindPageProperty("...");
                //添加最后一页信息
                html += this.bindPageProperty(pageCount);
            }

            if (pageNum++ >= pageCount) {
                break;
            }
        }

        //生成[下一页]A标签
        html += this.bindPageProperty(PAGE_NEXT, currPage, pageCount);
        //生成[末页]A标签
        html += this.bindPageProperty(PAGE_LAST, currPage, pageCount);

        html += "</ul>";

        if (this.showRecordInfo) {
            if (this.data.totalPages > 30) {
                var pageInput = "<label>跳转到</label><input onchange=\"return changePagInputNum('" + this.datagrid.tableId + "',this);\" maxlength='5' value='" + this.pageNum + "' data-total-pages='" + this.data.totalPages + "' class='form-control' style='width: 50px;'/><label>页</label>";

                //返回生成的页标签Html
                return html + "<div class='pagination-input-info form-group form-inline'>" + pageInput + "</div>";
            }
            else {
                var pageSelect = "<select onchange=\"return changePagSelectNum('" + this.datagrid.tableId + "',this);\" class='form-control'>";
                for (var i = 0; i < this.data.totalPages; i++) {
                    if (i + 1 == this.pageNum) {
                        pageSelect += "<option selected value=" + (i + 1) + ">第 " + (i + 1) + " 页</option>";
                    }
                    else {
                        pageSelect += "<option value=" + (i + 1) + ">第 " + (i + 1) + " 页</option>";
                    }
                }
                pageSelect += "</select>";
                //返回生成的页标签Html
                return html + "<div class='pagination-select-info form-group form-inline'>" + pageSelect + "</div>";
            }
        }
        else {
            return html;
        }
    },

    //分页页码生成
    //  id         A标签ID号
    //  currPage   当前页码
    //  pageCnt    最大页码
    bindPageProperty: function (id, currPage, pageCnt) {
        //定义页码
        var pageNum = id;
        //第一页
        if (id == PAGE_FIRST) {
            //当前页码设置为1
            pageNum = 1;
        }
        //上一页
        else if (id == PAGE_PREV) {
            //当前页码减1
            pageNum = parseInt(this.pageNum, 10) - 1;
        }
        //下一页
        else if (id == PAGE_NEXT) {
            //当前页码加1
            pageNum = parseInt(this.pageNum, 10) + 1;
        }
        //最末页
        else if (id == PAGE_LAST) {
            //当前页码设置为最大页码
            pageNum = pageCnt;
        }

        var html = "<li><a id='" + this.datagrid.tableId + "_pager_" + id + "' ";

        //（页码为1 && (ID为[第一页] OR[上一页])）  或 （页码为最末页 && (id为[下一页] OR [最末页])）
        if (id == "..." || (currPage == 1 && (id == PAGE_FIRST || id == PAGE_PREV)) || (currPage == pageCnt && (id == PAGE_NEXT || id == PAGE_LAST))) {
            //添加不可用属性
            html = "<li class='disabled'><a id='" + this.datagrid.tableId + "_pager_" + id + "'>";
        }
        else if (currPage == id) {
            //添加不可用属性
            html = "<li class='active'><a id='" + this.datagrid.tableId + "_pager_" + id + "'>";
        }
        else {
            //设置a标签链接属性及单击事件
            html += "href='#' onclick=\"return changePageNum('" + this.datagrid.tableId + "','" + pageNum + "');\">";
        }

        //设置表示文本
        html += id;

        //添加闭合标签
        html += "</a></li>";

        return html;
    },

    //分页处理
    changePageNum: function () {
        //Client侧分页
        if (this.datagrid.useClientChangePage) {
            var start = (parseInt(this.pageNum, 10) - 1) * this.pageSize;
            var end = parseInt(this.pageNum, 10) * this.pageSize;

            this.datagrid.data = this.datagrid.clientData.slice(start, end);
            this.datagrid.create();
            return;
        }

        if (typeof this.datagrid.changePageCallback == "function") {
            //调用table对象中的changePageCallback方法
            this.datagrid.changePageCallback();
        }
    }
};

//分页处理
function changePageNum(tableId, pageNum) {
    var datagrid = SysCmn.DataGridList[tableId];

    datagrid.pager.pageNum = pageNum;

    datagrid.pager.changePageNum();

    return false;
}
function changePagSelectNum(tableId, dom) {
    return changePageNum(tableId, $(dom).val());
}
function changePagInputNum(tableId, dom) {
    var pageNo = parseInt($(dom).val(), 10);
    var totalPages = $(dom).data("total-pages");
    //页码校验
    if (pageNo >= 1 && pageNo <= totalPages) {
        return changePageNum(tableId, pageNo);
    }

    SysCmn.CmnMsg.showMessage("页码输入错误（请输入1-" + totalPages + "之间的页码）。", MSG_TYPE_ERROR);
    return false;
}
//DataGridPager.js类文件定义结束---------------------------------------------------------//


//DataGridHeader.js类文件定义开始--------------------------------------------------------//
//定义table标题对象
SysCmn.DataGridHeader = function () {
    //全选按钮标志
    this.chkFlag = true;

    //table对象
    this.datagrid = null;

    //列属性列表
    this.colList = {};

    //排序列名称
    this.sortColName = "";

    //排序列方式（asc：正序；desc：倒序）
    this.sortMode = "asc";

    this.camelFlag = false;
};

//定义table标题对象属性
SysCmn.DataGridHeader.prototype = {

    //初期化标题对象
    init: function (datagrid, colList) {
        //保存table对象
        this.datagrid = datagrid;
        //保存列属性列表对象
        this.colList = colList;

        //初期化排序字段及排序方式
        if (!$M.isNull(datagrid.pager) && !$M.isNull(datagrid.pager.data)) {
            this.sortColName = datagrid.pager.data.sortColumn;
            if (this.sortColName.indexOf(COL_CAMEL_FLAG) > 0) {
                this.sortColName = this.sortColName.substring(0, this.sortColName.indexOf(COL_CAMEL_FLAG));
                this.camelFlag = true;
            }
            this.sortMode = datagrid.pager.data.sortMode.toLowerCase();
        }
    },

    //创建标题行
    create: function () {
        var header = "";

        //自定义表头
        if (typeof this.datagrid.customCreateHeader == "function") {
            return this.datagrid.customCreateHeader(this.colList);
        }

        //行选择功能有効的场合
        if (this.datagrid.useCheckOne && this.datagrid.hasData()) {
            var attr = " class='" + getDataGirdClassUID(this.datagrid, "header_checkbox") + "' ";

            //使用百分比宽度的场合
            if (this.datagrid.usePercentWidth) {
                header += "<th " + attr + " align='center' style='width: 20px; height: " + this.datagrid.headerHeight + "'>";
            }
            else {
                header += "<th " + attr + " align='center' style='width: 20px; height: " + this.datagrid.headerHeight + "'>" +
                    "    <div class='tb-cell-content' style='width: 20px;overflow: hidden;'>";
            }

            //全选择功能有効的场合
            if (this.datagrid.useCheckAll) {
                var checked = "";
                if (this.datagrid.useDefaultSelectedAll) {
                    checked = " checked "
                }
                header += "<input type='checkbox' " + checked + " onclick=\"dataGridCheckAll(this, '" + this.datagrid.tableId + "');\" />";
            }
            else {
                header += "&nbsp;";
            }

            //使用百分比宽度的场合
            if (this.datagrid.usePercentWidth) {
                header += "</th>";
            }
            else {
                header += "</div></th>";
            }
        }

        //显示行序号的场合
        if (this.datagrid.useRowNo && this.datagrid.hasData()) {
            var w = "35px";
            if (this.datagrid.getRowNo(1) >= 10000) {
                w = "50px";
            }
            header += "<th nowrap width='" + w + "'>序号</th>";
        }

        //循环table标题属性
        for (var j = 0; j < this.colList.size(); j++) {
            //获取当前列属性信息
            var colInfo = this.colList[j];

            //标题不为空
            if (colInfo.title != "") {
                var className = getDataGirdClassUID(this.datagrid, colInfo.colName);

                var strTh = "<th nowrap height='" + this.datagrid.headerHeight + "' align='center' class='" + className + "' ";

                //定义默认单元格合并列数为1
                var spanColCnt = 1;
                var width = parseInt(colInfo.width, 10);

                //循环判断空表头信息
                for (var k = j + 1; k < this.colList.size(); k++) {
                    //当前表头为空
                    if (this.colList[k].title == "") {
                        //单元格合并列数+1
                        spanColCnt++;
                        width += parseInt(this.colList[k].width, 10);
                        //继续下一次循环
                        continue;
                    }
                    //跳出当前循环
                    break;
                }

                //单元格合并列数>1
                if (spanColCnt > 1) {
                    width += (spanColCnt - 1) * DATATABLE_PADDING_WIDTH - (spanColCnt - 1);
                    //设置单元格colspan属性
                    strTh += " colspan='" + spanColCnt + "' ";
                    strTh += " width='" + width + "px'";
                }
                else {
                    //有数据的场合，无条件设定各列的宽度
                    if (this.datagrid.hasData()) {
                        strTh += " width='" + width + "px'";
                    }
                    //无数据的场合，最后一列不写入列宽度[IE8特殊对应]
                    else {
                        //非最后一列的场合，写入列宽度
                        if (j < this.colList.size() - 1) {
                            strTh += " width='" + width + "px'";
                        }
                    }
                }

                //循环获取属性列对象
                for (var proItem in colInfo) {

                    //列属性对象名称和当前对象排序名称一致
                    if (colInfo.colName == this.sortColName) {

                        //设置列属性对象排序方式
                        colInfo.sortMode = this.sortMode;
                    }

                    //幅的场合、处理中止
                    if (proItem == "width") {
                        continue;
                    }

                    if (proItem != "title") {
                        //标题标签添加属性
                        strTh += proItem + "='" + colInfo[proItem] + "' ";
                    }
                }

                //列属性对象中可排序为真
                if (colInfo.sortFlag && this.datagrid.hasData()) {
                    if (this.datagrid.sortOne && this.sortColName == colInfo.colName) {
                        //标题标签添加样式（手形状）
                        strTh += " style='cursor:pointer; text-align:center;' ";
                    }
                    else {
                        //标题标签添加样式（手形状）、排序单击事件
                        strTh += " style='cursor:pointer; text-align:center;' ";
                    }
                }
                else {
                    strTh += " style='text-align:center;' ";
                }

                var isLastCol = false;
                if (j == this.colList.length - 1) {
                    isLastCol = true;
                }

                //添加标题显示文字
                strTh += this.addTitleData(j, colInfo, width, isLastCol);

                //添加标题结束标记
                strTh += "</th>";

                //标题标签添加到标题Html
                header += strTh;
            }
        }

        //定义标题HTML
        if (this.datagrid.showHeader) {
            header = "<tr id='header_tr_" + this.datagrid.tableId + "' tableId='" + this.datagrid.tableId + "'>" + header + "</tr>";
        }
        else {
            header = "<tr style='display:none;' id='header_tr_" + this.datagrid.tableId + "' tableId='" + this.datagrid.tableId + "'>" + header + "</tr>";
        }

        var tableStyle = "";

        //Scroll功能有効　并且　数据存在的场合
        if (this.datagrid.useScroll && this.datagrid.hasData()) {
            header = "<table " + tableStyle + " id='header_table_" + this.datagrid.tableId + "' border='0' cellpadding='0' cellspacing='0' class='" + this.datagrid.cssClass + "'><tbody>" + header + "</tbody></table>";
        }

        //返回标题HTML
        return header;
    },

    //添加标题显示文字
    addTitleData: function (colNo, colInfo, width, isLastCol) {
        var title = colInfo.title;
        if (this.datagrid.hasData()) {
            //添加标题排序图标
            title += this.addTitleSortImg(colInfo);
        }

        var thWidth = colInfo.width;
        //表头有合并的场合
        if (width > parseInt(colInfo.width, 10)) {
            thWidth = width + "px";
        }

        var lastColClass = "";
        //设置最后列的样式类，为表格宽度自动调整用[resetLastColumnWidth方法内使用]
        if (isLastCol) {
            lastColClass = " tb-last-column";
        }

        var thStyle = "text-align:center;";

        if (this.datagrid.headerTextAlignMode == "left") {
            thStyle = "text-align:left;";
        }
        else if (this.datagrid.headerTextAlignMode == "right") {
            thStyle = "text-align:right;";
        }
        //与ColInfo定义一致
        else if (this.datagrid.headerTextAlignMode == "autoWithColInfo") {
            thStyle = "text-align:" + colInfo.cellAlign + ";";
        }

        //使用固定宽度的场合
        if (!this.datagrid.usePercentWidth) {
            var titleTips = colInfo.title;
            //标题中包含HTML字符串的场合，标题的Tips提示置空
            if (titleTips.indexOf("<") != -1 && titleTips.indexOf(">") != -1) {
                titleTips = "";
            }

            thStyle += "overflow: hidden; text-overflow:ellipsis;";

            //允许拖放列宽的场合
            if (this.datagrid.allowResizeColumnWidth) {
                thStyle += "min-width:" + thWidth + ";";
                thWidth = "100%";
            }
            thStyle += "width:" + thWidth + ";";

            if (colInfo.sortFlag) {
                thStyle += "cursor:pointer;";
                title = "<div onclick=\"sortDataGrid('" + this.datagrid.tableId + "', this, " + colInfo.camelFlag + ");\" title='" + titleTips + "' class='tb-cell-content" + lastColClass + "' style='" + thStyle + "'><NOBR>" + title + "</NOBR></div>";
            }
            else {
                title = "<div title=\"" + titleTips + "\" class='tb-cell-content" + lastColClass + "' style='" + thStyle + "'><NOBR>" + title + "</NOBR></div>";
            }
        }
        //标题添加列属性、列名称
        return "id='" + this.datagrid.tableId + colInfo.colName + "' colNo='" + colNo + "' style=' height:" + this.datagrid.headerHeight + ";' >" + title;
    },

    //添加标题排序图片
    addTitleSortImg: function (colInfo) {
        var strImg = "";
        //当前对象排序名称和当前列对象属性列名称一致
        if (this.sortColName == colInfo.colName) {
            //添加排序图片
            strImg += "<img src='" + window.imagesPath + "base/sort_" + this.sortMode + ".gif' />";
        }

        //返回图片Html
        return strImg;
    },

    //数据排序
    sortData: function () {
        //Client侧排序功能有効的场合
        if (this.datagrid.useClientSort) {
            //Client侧改分页功能有効的场合
            if (this.datagrid.useClientChangePage) {
                if (typeof this.customSortJsonData == "function") {
                    this.datagrid.clientData.sort(this.customSortJsonData.bind(this));
                }
                else {
                    this.datagrid.clientData.sort(this.sortJson.bind(this));
                }
                this.datagrid.data = this.datagrid.clientData.slice(0, this.datagrid.pager.pageSize);
            }
            else {
                if (typeof this.customSortJsonData == "function") {
                    this.datagrid.data.sort(this.customSortJsonData.bind(this));
                }
                else {
                    this.datagrid.data.sort(this.sortJson.bind(this));
                }
            }

            //行号再设定
            this.datagrid.setRowNo();

            this.datagrid.create();

            if (typeof this.datagrid.clientSortCallback == "function") {
                this.datagrid.clientSortCallback();
            }
            return;
        }

        if (typeof this.datagrid.sortDataCallback == "function") {
            this.datagrid.sortDataCallback();
        }
    },

    //JSON数据排序处理
    sortJson: function (a, b) {
        if (this.sortMode == "asc") {
            return a[this.sortColName] > b[this.sortColName] ? 1 : a[this.sortColName] == b[this.sortColName] ? 0 : -1;
        }
        else {
            return b[this.sortColName] > a[this.sortColName] ? 1 : b[this.sortColName] == a[this.sortColName] ? 0 : -1;
        }
    }
};

//排序方法
function sortDataGrid(tableId, obj, camelFlag) {
    obj = obj.parentNode;
    //获取当前table对象
    var datagrid = SysCmn.DataGridList[tableId];

    //获取列对象名称
    datagrid.headerIns.sortColName = obj.id.replaceAll(datagrid.tableId, "");
    //获取标题对象“sortMode”属性
    datagrid.headerIns.sortMode = $(obj).attr("sortMode").toLowerCase();

    datagrid.headerIns.camelFlag = camelFlag;

    //设定排序后页码（1），保存在对象中
    datagrid.resetPageNum();

    //判断排序方式
    if (datagrid.headerIns.sortMode == null || datagrid.sortOne) {
        datagrid.headerIns.sortMode = "asc";
        $(obj).attr("sortMode", datagrid.headerIns.sortMode);
    }
    else if (datagrid.headerIns.sortMode == "asc") {
        //正序时，倒序
        datagrid.headerIns.sortMode = "desc";
    }
    else if (datagrid.headerIns.sortMode == "desc") {
        //倒序时，正序
        datagrid.headerIns.sortMode = "asc";
    }

    //调用table对象方法
    datagrid.headerIns.sortData();
}
//DataGridHeader.js类文件定义结束--------------------------------------------------------//


