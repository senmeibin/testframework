//Core.js类文件定义开始------------------------------------------------------------------//
//ClientID全局数组对象
SysCmn.ClientIDList = [];

//时间选择全局数组对象
SysCmn.TimeSelect = [];

//自动填充全局变量
SysCmn.AutoComplete = [];

// jQuery 扩展方法[用于判断DOM对象是否存在]
(function ($) {
    $.fn.exist = function () {
        if ($(this).length >= 1) {
            return true;
        }
        return false;
    };
})(jQuery);

//数据检索・输入画面用共通JS类
SysCmn.Core = Class.create();

SysCmn.Core.prototype = {
    //构造函数
    initialize: function () {
        this.initCommonProperty();

        //Event对象列表
        this.eventList = {};

        //DOM无ID是自动索引编号
        this.domIDIndex = 1;

        //日历控件格式
        this.calendarFormat = "YYYY/MM/DD";

        //是否初始化日历控件
        this.initCalendarFlag = true;

        //是否显示Popup验证提示消息
        this.showValidatorPopupMessage = true;

        //用于支持自定义删除action请求
        this.deleteMethod = "delete";

        //是否显示处理中提示消息
        this.showProcessingMessage = true;
    },

    //显示帮助
    showHelp: function () {
        var helpIns = SysApp.Cmn.HelpIns;
        if (this.isNull(helpIns)) {
            this.showErrorMessage("帮助功能暂未开放，请与系统管理员联系。");
            return;
        }
        var params = "pageInstance=" + helpIns.pageInstance + "&settingType=" + helpIns.settingType;

        //画面模式存在的场合，附加到列表画面JS实例对象字符串后
        if (this.isNotEmpty(helpIns.pageMode)) {
            params = "pageInstance=" + helpIns.pageInstance + "_" + helpIns.pageMode + "&settingType=" + helpIns.settingType;
        }
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(helpIns.controller + "getHelpDetail", this.showHelpCallback.bind(this), params);
    },

    showHelpCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }
        SysApp.Cmn.HelpIns.inputEntity = JsonUtility.Parse(response.responseText);

        SysApp.Cmn.HelpIns.initInputForm(SysApp.Cmn.HelpIns.inputEntity);
        SysApp.Cmn.HelpIns.show();
    },

    //共通Property初期化
    initCommonProperty: function () {
        //POPUP控件默认使用弹出形式
        //微信HTML5网页推荐使用画面切换模式(this.popupSwitchPage = true)
        this.popupSwitchPage = false;

        this.dom = {};

        //富文本编辑器
        this.editor = {};

        //Callback方法
        this.callback = function () {
        };

        //数据输入画面用数据模型
        this.inputEntity = {};

        //检索画面用数据模型
        this.searchEntity = {};

        //数据导出方法
        this.exportMethod = "export";

        //自动隐藏Flash消息
        this.autoHideFlashMessage = false;

        //Popup控件表示位置
        this.popupPosition = "center";

        //删除处理确认消息
        this.deleteConfirmMessage = "<label style='color:#FF0000;'>数据删除后将不能恢复，确定要删除吗？</label>";

        //保存成功后是否刷新一览[默认刷新]
        this.isSaveSuccessAfterRefreshList = true;

        //删除成功后是否刷新一览[默认刷新]
        this.isDeleteSuccessAfterRefreshList = true;

        //是否显示数据保存成功后的提示消息
        if (this.isEmpty(window.ShowSaveDataSuccessMessage)) {
            this.showSaveDataSuccessMessage = true;
        }
        else {
            this.showSaveDataSuccessMessage = window.ShowSaveDataSuccessMessage;
        }

        //窗口状态（normal：正常/max：最大）
        this.windowState = "normal";

        //窗口原始属性
        this.windowProperty = {};
    },

    //事件绑定
    bindEvent: function (id, event, func, overrideBind) {
        if (this.isEmpty(overrideBind)) {
            overrideBind = false;
        }
        var dom = this.getDom(id);
        if (this.isNotNull(dom)) {
            if (this.isEmpty(dom.id)) {
                dom.id = this.getClientID() + "_randomDomID_" + this.domIDIndex;
                this.domIDIndex++;
            }

            var eventKey = dom.id + event + func;

            if (this.isEmpty(this.eventList[eventKey])) {
                //非强制绑定的场合，缓存绑定事件防止多重事件绑定
                if (overrideBind == false) {
                    this.eventList[eventKey] = 1;
                }
                Event.observe(dom, event, func);
            }
        }
    },

    //初期化回车查询功能
    initEnterSearch: function () {
        var thisObj = this;
        $(this.getMainContentClassName()).each(function () {
            console.log(thisObj.getMainFormID() + "画面回车检索事件绑定。");
            Event.observe(this, "keydown", thisObj.enterSearch.bind(thisObj));
        });
    },

    //回车自动查询
    enterSearch: function () {
        var e = $M.getEvent();
        if (this.isNull(e)) {
            console.log("event为空，回车检索处理中止。");
            return;
        }

        var enterSearch = $(Event.element(e)).data("enter-search");
        if (this.isNull(enterSearch)) {
            enterSearch = true;
        }

        if (enterSearch == false) {
            return
        }

        //回车键的场合
        if (e.keyCode == 13 || e.which == 13) {
            //检索按钮不为空的场合，在Form上Enter键按下后执行查询处理
            if (this.isNotEmpty(this.dom.btnSearch)) {
                console.log(this.getMainFormID() + "画面执行回车检索处理。");
                this.onClick_Search();
            }
            else {
                console.log(this.getMainFormID() + "画面无检索按钮，处理跳过。");
            }
        }
    },

    //初始化Select2必须选择项
    initSelect2Required: function () {
        setTimeout(this.delayInitSelect2Required.bind(this), 100);
    },

    delayInitSelect2Required: function () {
        $(".select2-container", ".select2-required").each(function () {
            $(this).addClass("required");
        });
    },

    //是否是POPUP表示模式？
    isPopupMode: function () {
        return this.isNotNull(this.getFrame());
    },

    //取得POPUP框架DOM对象
    getFrame: function () {
        return $E(this.dom.ctlFrame);
    },

    //uploadify文件上传地址
    getUploadifyUploaderURL: function () {
        return window.contextPath + "/cmn/fileupload/uploading";
    },

    // 初期化处理
    initCore: function () {
        //部门选择控件存在的场合
        if (!$M.isEmpty(this.groupSelectInstance)) {
            this.groupSelectInstance.onClick_Ok_Callback = this.groupSelectCallback.bind(this);
        }

        //用户选择控件存在的场合
        if (!$M.isEmpty(this.userSelectInstance)) {
            this.userSelectInstance.onClick_Ok_Callback = this.userSelectCallback.bind(this);
        }
    },

    //共通DOM ID参数初期化
    initCommonDomIds: function (domList) {
        var thisObj = this;
        //DOM ID循环
        $.each(domList, function (index, value) {
            //DOM ID不存在的场合
            if (thisObj.isEmpty(thisObj.dom[value])) {
                //可能的DOM ID后缀
                var suffix_array = [value, "_" + value];

                //后缀循环
                $.each(suffix_array, function (key, suffix) {
                    if (!thisObj.isNull($E(thisObj.clientID + suffix))) {
                        thisObj.dom[value] = thisObj.clientID + suffix;
                    }
                });
            }
        });
    },

    //检索条件是否改変？
    isConditionChanged: function (searchEntity1, searchEntity2) {
        if (JsonUtility.ToJSON(searchEntity1) != JsonUtility.ToJSON(searchEntity2)) {
            this.showMessage("查询条件被修改，请重新查询。", MSG_TYPE_WARNING);
            return true;
        }

        return false;
    },

    //取得检索条件
    getSearchCommonParams: function (datagrid) {
        return datagrid.getConditionJson() + "&conditionJson=" + JsonUtility.ToJSON(this.searchEntity)
    },

    //取得CSSClass名称
    getMainContentClassName: function () {
        return "." + this.getClientID() + "-MainContent";
    },

    //取得ClientID
    getClientID: function () {
        return this.clientID;
    },

    //取得MainFormID
    getMainFormID: function () {
        return "#" + this.getClientID() + "_MainForm";
    },

    //DOM值清空
    clearValue: function (obj) {
        //allow-clear指定的场合，优先判断allow-clear设定的值。
        //不允许清除的场合，处理中止
        if ($(obj).data("allow-clear") == false) {
            return true;
        }
        //允许清除的场合，清除值后处理中止
        else if ($(obj).data("allow-clear") == true) {
            //Select2的场合
            if (obj.type == "select-one" || obj.type == "select-multiple") {
                obj.selectedIndex = -1;
                this.initSelect2Selected();
            }
            //radio || checkbox的场合
            else if (obj.type == "radio" || obj.type == "checkbox") {
                obj.checked = false;
            }
            else {
                obj.value = "";
            }
            return true;
        }

        return false;
    },

    //画面内容清空
    //  clearDisabledControl Disabled状态的Form控件是否也清空？
    clearForm: function (clearDisabledControl) {
        //clearDisabledControl未指定场合、清空所有DOM控件
        if (this.isNull(clearDisabledControl)) {
            clearDisabledControl = true;
        }
        var thisObj = this;
        //文本输入框 || 密码输入框 || 下拉框 || 多文本输入域 || Checkbox多选 || Radio单选 的场合
        $("input[type='text'], input[type='number'], input[type='password'], input[type='file'], select, textarea, input[type='checkbox'], input[type='radio']", this.getMainFormID()).each(function () {
            if (thisObj.clearValue(this)) {
                return;
            }

            //无条件清楚 或者 DOM有效状态
            if (clearDisabledControl || this.disabled == false) {
                //Select2的场合
                if (this.type == "select-one" || this.type == "select-multiple") {
                    this.selectedIndex = -1;
                    thisObj.initSelect2Selected();
                }
                //Checkbox多选 || Radio单选 的场合
                else if (this.type == "checkbox" || this.type == "radio") {
                    this.checked = false;
                }
                else if (this.type == "file") {
                    this.outerHTML = this.outerHTML;
                }
                else {
                    this.value = "";
                }
            }
        });

        //隐藏对象[只清除data-allow-clear="true"的对象]
        $("input[type='hidden']", this.getMainFormID()).each(function () {
            thisObj.clearValue(this);
        });
    },

    //Select2插件选择状态设置
    initSelect2Selected: function () {
        try {
            $("select[select2]").select2();
        }
        catch (e) {
            console.log("select2插件相关js未导入。");
        }
    },

    //清空数据输入画面用的数据模型
    clearInputEntity: function () {
        this.inputEntity = {};
    },

    //清空数据检索画面用的数据模型
    clearSearchEntity: function () {
        this.searchEntity = {};
    },

    //清空处理
    onClick_Clear: function () {
        //清空前处理
        if (this.isFunction(this.clearFormBefore)) {
            this.clearFormBefore();
        }

        //画面内容清空
        this.clearForm(false);

        //清空后处理
        if (this.isFunction(this.clearFormAfter)) {
            this.clearFormAfter();
        }

        //清空Ztree选择节点
        this.clearZtreeNodes();
    },

    //清空Ztree选择节点
    clearZtreeNodes: function () {
        var thisObj = this;
        $(".combo-tree-container", this.getMainFormID()).each(function () {
            $("ul", this).each(function () {
                if (thisObj.isNotEmpty(this.id) && this.id.lastIndexOf("Tree") > 0) {
                    //构建Tree实例对象
                    var treeInstance = eval("thisObj." + this.id.replaceAll(thisObj.getClientID() + "_", "").replaceAll("Tree", "ComboTree"));
                    //清空Tree实例的选择节点
                    treeInstance.checkAllNodes(false);
                    return false;
                }
            })
        });
    },

    //关闭处理
    onClick_Close: function () {
        //关闭前处理
        if (this.isFunction(this.closePopupBefore)) {
            this.closePopupBefore();
        }

        this.hide();

        //关闭后处理
        if (this.isFunction(this.closePopupAfter)) {
            this.closePopupAfter();
        }
    },

    //隐藏处理
    hide: function () {
        //非POPUP表示的场合
        if (!this.isPopupMode()) {
            return;
        }

        //数据输入页面的场合
        if (this.isInputPage) {
            //隐藏前、控件内容清空
            this.clearForm();
        }

        //页面切换模式的场合
        if (this.popupSwitchPage == true && this.isNotNull(this.parentFrame)) {
            //隐藏子画面
            this.switchPage(false);
        }
        else {
            SysCmn.EstopLayer.hide(this.getFrame());
            $(this.getFrame()).fadeOut();
        }

        //最大化的场合，关闭窗口后最小化窗口
        if (this.windowState == "max") {
            this.onClick_Maximization();
        }

        //隐藏回调方法
        if (this.isFunction(this.hideCallback)) {
            this.hideCallback();
        }
    },

    //取得检索画面用的数据模型
    getSearchEntity: function () {
        if (this.isNull(this.getClientID())) {
            this.showMessage("Js Class ClientID is not defined.", MSG_TYPE_WARNING);
        }

        var searchEntity = {};

        //设置列表查询是否包含分组查询条件(BaseDao.search方法用判断属性，不参与实际sql条件查询)
        searchEntity["includeGroupQuery$ignore_search"] = this.includeGroupQuery;

        //设置列表查询是否包含排序查询条件(BaseDao.search方法用判断属性，不参与实际sql条件查询)
        searchEntity["includeOrderQuery$ignore_search"] = this.includeOrderQuery;

        var clientId = this.getClientID();

        //文本输入框 || 隐藏对象 || CheckBox选择 || 下拉框 || 多文本输入域
        $("input[type='text'], input[type='number'], input[type='hidden'], input[type='checkbox'], select, textarea", this.getMainFormID()).each(function () {
            //无ID的DOM对象除外
            if ($M.isEmpty(this.id) || $(this).data("ignore-search") == true) {
                return;
            }

            var aliasTable = SysCmn.CoreUtility.getAliasTableName(this);
            var camelFieldFlag = SysCmn.CoreUtility.getCamelFieldFlag(this);

            //检索模式
            var searchMode = $(this).data("search-mode");

            if ($M.isEmpty(searchMode)) {
                searchMode = "";
            }

            //日期的场合
            if (SysCmn.CoreUtility.isDateField(this)) {
                searchEntity[aliasTable + SysCmn.CoreUtility.getFieldName(clientId, this.id) + searchMode] = this.value;
            }
            else {
                //检索模式未指定的场合
                if ($M.isEmpty(searchMode)) {
                    //下拉框 || CheckBox选择 的场合、完全一致检索
                    if (this.type == "select-one" || this.type == "checkbox") {
                        searchMode = "";
                    }
                    //下拉多选的场合
                    else if (this.type == "select-multiple") {
                        searchMode = "$array_search";
                    }
                    else {
                        searchMode = "$partial_search";
                    }
                }

                //$分割号追加
                if (searchMode != "" && searchMode.indexOf("$") == -1) {
                    searchMode = "$" + searchMode;
                }

                //DOM ID中自带检索Mode的场合
                if (this.id.indexOf("$") > 0) {
                    searchMode = "";
                }

                //完全一致检索的场合
                if (searchMode == "$equal_search") {
                    searchMode = "";
                }

                var key = aliasTable + SysCmn.CoreUtility.getFieldName(clientId, this.id) + searchMode + camelFieldFlag;

                //CheckBox选择 的场合
                if (this.type == "checkbox" || this.type == "radio") {
                    var v = this.value;
                    if ($M.isEmpty(v) || v == "on") {
                        v = 1;
                    }
                    searchEntity[key] = (this.checked ? v : "");
                }
                else {
                    var v = $(this).val();

                    if ($M.isEmpty(v)) {
                        v = "";
                    }

                    //下拉多选的场合，将返回的数组转换成已逗号分隔的字符串
                    if (v instanceof Array) {
                        v = v.join(",");
                    }

                    searchEntity[key] = $M.trim(v);
                }
            }
        });

        return searchEntity;
    },

    //数据输入画面From值初期化
    initInputForm: function (entity) {
        this._initFormByEntity(entity);
    },

    //数据检索画面From值初期化
    initSearchForm: function (entity) {
        this._initFormByEntity(entity);
    },

    //数据模型⇒画面Form中转换设定
    _initFormByEntity: function (entity, prefix) {
        //从Json対象取得具体值设定到画面HTMLInput的Value中
        for (var key in entity) {
            //对象的场合、递归子对象
            if (typeof (entity[key]) === 'object') {
                this._initFormByEntity(entity[key], key);
                continue;
            }

            var domId = key;
            if (this.isNotEmpty(prefix)) {
                domId = prefix + "." + key;
            }

            try {
                var obj = $E(this.getClientID() + "_" + domId);

                //日期控件的场合，设置控件宽度
                if (SysCmn.CoreUtility.isDateField(obj) && !SysCmn.CoreUtility.isLabel(obj)) {
                    $(obj).css("width", "100px");
                }

                if (this._initDomValue(obj, entity, key)) {
                    continue;
                }
            }
            catch (e) {
                alert(domId + "\t" + e);
            }

            try {
                var obj = $E(eval("this.dom")[domId]);

                this._initDomValue(obj, entity, key);
            } catch (e) {
                alert(domId + "\t" + e);
            }
        }
    },

    _initDomValue: function (obj, entity, key) {
        //Checkbox的场合
        if (obj != null && (obj.type == "checkbox" || obj.type == "radio")) {
            obj.checked = (entity[key] == 1);
            return true;
        }
        else {
            if (SysCmn.CoreUtility.setValue(obj, entity[key])) {
                return true;
            }
        }

        return false;
    },

    //指定CssClass内Input控件无效化/有効化
    disableInput: function (className, disableFlag) {
        var thisObj = this;
        //文本输入框 || 按钮 || 多选框输入框 || 单选框输入框 || 多文本输入域 || 下拉框 || Image标签 || A标签
        $("input[type='text'], input[type='number'], input[type='button'], input[type='checkbox'], input[type='radio'], button, textarea, select, img, a", className).each(function () {
            thisObj.disabledDom(this, disableFlag);
        });
    },

    //DOM无效化处理
    disabledDom: function (obj, disableFlag) {
        //obj为domid的场合
        if (typeof obj == "string") {
            obj = this.getDom(obj);
        }

        if (this.isNull(obj)) return;

        //ID存在的场合
        if (this.isNotEmpty(obj.id)) {
            //关闭 OR 返回 按钮的场合
            if (obj.id.indexOf("_btnClose") > 0 || obj.id.indexOf("_btnBack") > 0) {
                obj.disabled = false;
                return;
            }
        }

        if (!this.isAllowDisabled(obj)) {
            return;
        }
        obj.disabled = disableFlag;
    },

    //是否允许无效化？
    isAllowDisabled: function (obj) {
        var allowDisabled = $(obj).data("allow-disabled");
        if (this.isEmpty(allowDisabled) || allowDisabled == true) {
            return true;
        }
        return false;
    },

    //创建combox
    createOption: function (selectId, jsonData, valueField, displayField, needBlank, blankItemText) {
        selectId = this.getId(selectId);
        if (this.isEmpty(blankItemText)) {
            blankItemText = "-全部-";
        }
        else {
            var title = this.$(selectId).data("title");
            if (this.isNotEmpty(title)) {
                blankItemText += title;
            }
        }
        var blankText = this.$(selectId).data("blank-text");
        if (this.isNotEmpty(blankText)) {
            blankItemText = blankText;
        }

        $E(selectId).innerHTML = "";
        var strHTML = "";

        if (this.isNotEmpty(this.$(selectId).data("need-blank"))) {
            needBlank = this.$(selectId).data("need-blank");
        }

        //需要空条目的场合、Combox中追加[-全部-]选择項目
        if (needBlank == true || needBlank == "true") {
            strHTML += "<option value=''>" + blankItemText + "</option>";
        }

        //列表JSON对象为空的场合
        if (this.isNull(jsonData)) {
            $($E(selectId)).append(strHTML);
            return;
        }

        //字典表格式的场合
        if (this.isEmpty(valueField) && this.isEmpty(displayField)) {
            $.each(jsonData, function (key, value) {
                strHTML += "<option value='" + key + "'>" + value + "</option>";
            });
        }
        else if (jsonData.length > 0) {
            //多字段分割表示处理
            var displayFields = displayField.split("|");

            for (var i = 0; i < jsonData.length; i++) {
                var displayText = "";
                for (var j = 0; j < displayFields.length; j++) {
                    //字段名称为空的场合
                    if (displayFields[j] == "") continue;

                    //多字段表示名称拼接
                    if (displayText == "") {
                        displayText = jsonData[i][displayFields[j]];
                    }
                    else {
                        displayText += "|" + jsonData[i][displayFields[j]];
                    }
                }

                strHTML += "<option value='" + jsonData[i][valueField] + "'>" + displayText + "</option>";
            }
        }

        $($E(selectId)).append(strHTML);
    },

    //追加option
    appendOption: function (id, value, text) {
        $(this.getDom(id)).append("<option value='" + value + "'>" + text + "</option>");
    },

    //取得指定Table对象中选中的ID一览
    getSelectedIds: function (datagrid, key) {
        var rows = datagrid.getSelectedRows();
        var ids = [];

        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];

            ids.push(this.getPkValue(row, key));
        }

        //单行选择有效地场合
        if (datagrid.selectedIndex != -1) {
            ids.push(this.getPkValue(datagrid.getSelectedRow(), key));
        }

        return ids.toString();
    },

    //取得主键值
    getPkValue: function (entity, key) {
        if (this.isNull(entity)) {
            return "";
        }

        if (typeof key == "string") {
            return entity[key];
        }
        else {
            var value = "";
            for (var j = 0; j < key.length; j++) {
                if (j == 0) {
                    value += entity[key[j]];
                }
                else {
                    value += "\n" + entity[key[j]];
                }
            }

            return value;
        }
    },

    //画面输入内容验证
    checkData: function () {
        //画面验证处理
        if (this.validateForm() == false) {
            return false;
        }

        return true;
    },

    //画面验证处理
    validateForm: function () {
        //验证前处理
        if (this.isFunction(this.validateFormBefore)) {
            if (!this.validateFormBefore()) {
                return false;
            }
        }

        //画面输入内容验证
        if (this.isFunction(this.initValidator) && this.isNotNull(this.validator)) {
            if (!this.validator.form()) return false;
        }

        //验证后处理
        if (this.isFunction(this.validateFormAfter)) {
            if (!this.validateFormAfter()) {
                return false;
            }
        }

        return true;
    },

    //是否是JS方法
    isFunction: function (funcName) {
        return (typeof funcName == "function");
    },

    //删除处理
    deleteData: function (keyValue) {
        if (!this.isFunction(this.getPrimaryKey)) {
            alert("请在子类中定义getPrimaryKey方法。");
            return;
        }

        // 数据输入画面的场合
        if ($M.isEmpty(keyValue) && this.isNotNull(this.inputEntity)) {
            keyValue = this.getPkValue(this.inputEntity, this.getPrimaryKey());
        }

        if (this.isNull(keyValue)) {
            keyValue = this.getSelectedIds(this.datagrid, this.getPrimaryKey());
        }
        var params = "uid=" + keyValue;

        if (this.isFunction(this.customDeleteDataParams)) {
            params += this.customDeleteDataParams();
        }

        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + this.deleteMethod, this.deleteDataCallback.bind(this), params);
    },

    //删除处理[Callback后处理]
    deleteDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //数据删除客户化回调方法存在的场合
        if (this.isFunction(this.customDeleteDataCallback)) {
            this.customDeleteDataCallback(ajaxResult);
        }

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            if (this.showSaveDataSuccessMessage) {
                if (this.isDeleteSuccessAfterRefreshList) {
                    this.showMessage(ajaxResult.message, MSG_TYPE_INFO, this.refreshList.bind(this));
                }
                else {
                    this.showMessage(ajaxResult.message, MSG_TYPE_INFO);
                }
            }
            else {
                if (this.isDeleteSuccessAfterRefreshList) {
                    this.refreshList();
                }
                else {
                    this.hideMessage();
                }
            }
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    //数据审核处理
    auditData: function (auditType, keyValue) {
        if (!this.isFunction(this.getPrimaryKey)) {
            alert("请在子类中定义getPrimaryKey方法。");
            return;
        }

        // 数据输入画面的场合
        if ($M.isEmpty(keyValue) && this.isNotNull(this.inputEntity)) {
            keyValue = this.getPkValue(this.inputEntity, this.getPrimaryKey());
        }

        if (this.isNull(keyValue)) {
            keyValue = this.getSelectedIds(this.datagrid, this.getPrimaryKey());
        }
        var params = "uid=" + keyValue + "&audit_type=" + auditType;

        //客户化参数
        if (this.isFunction(this.customRequestParams)) {
            params += "&" + this.customRequestParams();
        }

        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller, this.auditDataCallback.bind(this), params, "Audit");
    },

    //数据审核处理[Callback后处理]
    auditDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //数据审核客户化回调方法存在的场合
        if (this.isFunction(this.customAuditDataCallback)) {
            this.customAuditDataCallback(ajaxResult);
        }

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.showMessage(ajaxResult.message, MSG_TYPE_INFO, this.refreshList.bind(this));
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    //数据导出
    output: function (customColList) {
        this.searchEntity = this.getSearchCondition();

        //检索条件数据模型自定义设定的场合
        if (this.isFunction(this.customSearchEntity)) {
            this.customSearchEntity();
        }

        //自定义列导出的场合
        if (this.isFunction(this.customExportColList)) {
            customColList = this.customExportColList();
        }

        var params = "";
        //客户化参数
        if (this.isFunction(this.customRequestParams)) {
            if (this.isNotEmpty(this.customRequestParams())) {
                params = "?" + this.customRequestParams();
            }
        }

        var form = $("<form>");
        form.attr('style', 'display:none');
        form.attr('target', '_blank');
        form.attr('method', 'post');
        form.attr('action', this.controller + this.exportMethod + params);

        //检索条件
        var inputSearchCondition = $('<input>');
        inputSearchCondition.attr('type', 'hidden');
        inputSearchCondition.attr('name', "conditionJson");
        inputSearchCondition.attr('value', JsonUtility.ToJSON(this.searchEntity));

        if (this.isEmpty(this.exportFileName)) {
            this.exportFileName = this.getClientID();
        }
        //导出文件名称
        var exportFileName = $('<input>');
        exportFileName.attr('type', 'hidden');
        exportFileName.attr('name', "fileName");
        exportFileName.attr('value', this.exportFileName);

        //分页信息
        var inputPager = $('<input>');
        inputPager.attr('type', 'hidden');
        inputPager.attr('name', "pagerJson");
        var pagerJson = "";
        if (this.datagrid != null) {
            pagerJson = this.datagrid.getConditionString();
        }
        inputPager.attr('value', pagerJson);

        //默认列信息
        var inputColList = $('<input>');
        inputColList.attr('type', 'hidden');
        inputColList.attr('name', "colList");
        //自定义列导出的场合
        if (this.isNotNull(customColList)) {
            inputColList.attr('value', JsonUtility.ToJSON(customColList));

            //自定义列导出标志位
            var exportCustomColList = $('<input>');
            exportCustomColList.attr('type', 'hidden');
            exportCustomColList.attr('name', "exportCustomColList");
            exportCustomColList.attr('value', "true");
            form.append(exportCustomColList);
        }
        else {
            inputColList.attr('value', JsonUtility.ToJSON(this.colList));
        }

        //所有列信息
        var inputAllColList = $('<input>');
        inputAllColList.attr('type', 'hidden');
        inputAllColList.attr('name', "allColList");
        inputAllColList.attr('value', JsonUtility.ToJSON(this.allColList));

        //JS实例对象字符串
        var pageInstance = $('<input>');
        pageInstance.attr('type', 'hidden');
        pageInstance.attr('name', "pageInstance");
        pageInstance.attr('value', this.selfInstance);

        $('body').append(form);
        form.append(inputSearchCondition);
        form.append(exportFileName);
        form.append(inputPager);
        form.append(inputColList);
        form.append(inputAllColList);
        form.append(pageInstance);

        form.submit();
        form.remove();
    },

    //整型值输入验证
    isInteger: function (domId) {
        var parseValue = parseInt(this.getValue(domId), 10);

        if (isNaN(parseValue)) {
            return false;
        }
        else {
            this.setValue(domId, parseValue);
            return true;
        }
    },

    //浮点值输入验证
    isDouble: function (domId) {
        var parseValue = parseFloat(this.getValue(domId));

        if (isNaN(parseValue)) {
            return false;
        }
        else {
            this.setValue(domId, parseValue);
            return true;
        }
    },

    //只允许整数输入[异常输入将清空]
    allowInteger: function (dom, intLen) {
        if (this.isInteger(dom, intLen)) {
            return true;
        }

        dom.value = "";

        return false;
    },

    //只允许浮点数输入[异常输入将清空]
    allowDouble: function (dom, intLen) {
        if (this.isDouble(dom, intLen)) {
            return true;
        }

        dom.value = "";

        return false;
    },

    //数值输入检测函数
    inputNumbers: function (e) {
        var keyNum;
        var keyChar;
        var numCheck;

        // IE
        if (window.event) {
            keyNum = e.keyCode;
        }
        // Netscape/Firefox/Opera
        else if (e.which) {
            keyNum = e.which;
        }

        if (keyNum == undefined) return false;

        if ((keyNum == 8) || (keyNum == 9) || (keyNum == 37) || (keyNum == 38) || (keyNum == 39) || (keyNum == 40)) {
            return true;
        }

        keyChar = String.fromCharCode(keyNum);
        numCheck = /\d/;
        return numCheck.test(keyChar);
    },

    //数值输入检测函数(带小数点)
    inputDoubles: function (e) {
        var keyNum;
        var keyChar;
        var numCheck;
        // IE
        if (window.event) {
            keyNum = e.keyCode;
        }
        // Netscape/Firefox/Opera
        else if (e.which) {
            keyNum = e.which;
        }

        if (keyNum == undefined) return false;

        if ((keyNum == 8) || (keyNum == 9) || (keyNum == 37) || (keyNum == 38) || (keyNum == 39) || (keyNum == 46) || (keyNum == 40)) {
            return true;
        }

        keyChar = String.fromCharCode(keyNum);
        numCheck = /\d/;
        return numCheck.test(keyChar);
    },

    //数据重复校验初期化
    initValidateDuplication: function () {
        var thisObj = this;
        $(".duplication", this.getMainFormID()).each(function () {
            Event.observe(this, 'blur', thisObj.validateDuplication.bind(thisObj, this));
        });
    },

    //数据重复校验
    //  dom：被校验的目标DOM对象
    validateDuplication: function (dom) {
        //主键字段名称
        var pkName = $(dom).data("pk-name");
        if (this.isEmpty(pkName)) {
            pkName = "uid";
        }
        //关联外键字段名称
        var relationFieldName = $(dom).data("relation-field-name");
        //被校验目标字段名称
        var targetFieldName = dom.id.replace(this.getClientID() + "_", "")
        //被校验目标字段逻辑名称
        var targetFieldDesc = $(dom).data("title");
        //数据是否有效
        var recordStatusIsActive = $(dom).data("record-status-is-active") != false;

        if (this.isNull(this.getDom(pkName))) {
            alert("请设定主键字段的DOM对象。");
        }

        if (this.isEmpty(dom.value)) {
            return;
        }

        //自定义重复校验
        if (this.isFunction(this.customValidateDuplication)) {
            this.customValidateDuplication();
            return;
        }

        var params = "&pkName=" + pkName +
            "&pkValue=" + this.getValue(pkName) +
            "&targetFieldName=" + targetFieldName +
            "&targetFieldValue=" + dom.value +
            "&targetFieldDesc=" + targetFieldDesc +
            "&recordStatusIsActive=" + recordStatusIsActive;

        //关联外键字段存在的场合
        if (this.isNotEmpty(relationFieldName)) {
            params += "&relationFieldName=" + relationFieldName;
            params += "&relationFieldValue=" + this.getValue(relationFieldName);
        }

        //处理中消息提示
        //this.showProcessing();

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "validateDuplication", this.validateDuplicationCallback.bind(this, dom), params);
    },

    //数据重复校验回调方法
    validateDuplicationCallback: function (dom, response) {
        //Ajax异常判断
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }
        var ajaxResult = JsonUtility.Parse(response.responseText);

        //客户化回调方法存在的场合
        if (this.isFunction(this.customValidateDuplicationCallback)) {
            this.customValidateDuplicationCallback(ajaxResult);
            return;
        }

        //数据重复的场合
        if (ajaxResult.result == -1) {
            this.showMessage(ajaxResult.message, MSG_TYPE_WARNING, this.selectDomValue.bind(this, dom));
        }
        else {
            SysCmn.CmnMsg.hide(true);
        }
    },

    //选择目标DOM对象的值
    selectDomValue: function (dom) {
        dom.select();
    },

    //重置UID的值
    resetUIDValue: function (dom_uid, dom_name) {
        if (!$M.isEmpty($E(dom_uid))) {
            if (!$M.isEmpty($E(dom_name)) && $M.isEmpty($E(dom_name).value)) {
                $E(dom_uid).value = "";
            }
        }
    },

    //指定DOMID对象赋值
    setValue: function (id, value) {
        var dom = this.getDom(id);
        if (this.isNotNull(dom)) {
            SysCmn.CoreUtility.setValue(dom, value);
        }
    },

    //取得指定DOMID的值
    getValue: function (id, forceAppendClientID) {
        var dom = this.getDom(id, forceAppendClientID);

        if (this.isNotNull(dom)) {
            return this._getValue(dom);
        }
        return null;
    },

    //取得指定DOMID的选择文本
    getText: function (id) {
        return $(this.getDom(id)).find("option:selected").text();
    },

    //取得指定ID的DOM对象
    getDom: function (id, forceAppendClientID) {
        //DOM对象的场合，直接返回该对象
        if (typeof id == "object") {
            return id;
        }

        //DOMID为空的场合，返回NULL
        if (this.isEmpty(id)) {
            return null;
        }

        //强制追加ClientID的场合
        if (this.isNotEmpty(forceAppendClientID) && forceAppendClientID == true) {
            return $E(this.getId(id, forceAppendClientID));
        }

        //当前容器范围内的DOM优先
        var dom = $E(this.getClientID() + "_" + id);
        if (this.isNotNull(dom)) {
            return dom;
        }

        //全局DOM取得
        dom = $E(id);
        if (this.isNotNull(dom)) {
            return dom;
        }

        return null;
    },

    //取得DOMID
    getId: function (id, forceAppendClientID) {
        //强制追加ClientID的场合，直接在指定ID前追加ClientID即可
        if (this.isNotEmpty(forceAppendClientID) && forceAppendClientID == true) {
            return this.getClientID() + "_" + id;
        }

        //根据ID取得Dom后，根据Dom取得Dom实际ID
        var dom = this.getDom(id);
        if (this.isNull(dom)) {
            return null;
        }
        return dom.id;
    },

    _getValue: function (dom) {
        if (this.isNull(dom)) return "";

        if (dom.type == "checkbox" || dom.type == "radio") {
            //选中的场合
            if (dom.checked) {
                if (this.isEmpty(dom.value) || dom.value == "on") {
                    return "1";
                }

                return dom.value;
            }
            else {
                return "";
            }
        }

        //强制转化成字符串格式
        return String($(dom).val());
    },

    //Flash消息自动隐藏
    hideFlashMessage: function () {
        var thisObj = this;
        $(".flash-notice").each(function () {
            //5秒後、Flash消息自动隐藏
            setTimeout(thisObj._hideFlashMessage.bind(thisObj), 3000);
        });

        $(".flash-error").each(function () {
            //5秒後、Flash消息自动隐藏
            setTimeout(thisObj._hideFlashMessage.bind(thisObj), 3000);
        });
    },

    _hideFlashMessage: function () {
        $(".flash-notice").each(function () {
            $(this).fadeOut(1000);
        });

        $(".flash-error").each(function () {
            $(this).fadeOut(1000);
        });
    },

    //UEditor初期化
    initUEditor: function (domId, initialFrameWidth, initialFrameHeight) {
        if (this.isNull($E(domId))) {
            domId = this.getId(domId);
        }

        if (this.isNull($E(domId))) {
            alert("指定的DOMID不存在(" + domId + ")。");
            return;
        }

        if (this.isEmpty(initialFrameWidth)) {
            initialFrameWidth = "100%";
        }
        if (this.isEmpty(initialFrameHeight)) {
            initialFrameHeight = "100%";
        }

        var editorId = domId.replace(this.getClientID() + "_", "");
        //实例化编辑器
        this.editor[editorId] = UE.getEditor(domId, {
            initialFrameWidth: initialFrameWidth,
            initialFrameHeight: initialFrameHeight
        });
    },

    //UMEditor初期化
    initUMEditor: function (domId) {
        if (this.isNull($E(domId))) {
            domId = this.getId(domId);
        }

        if (this.isNull($E(domId))) {
            alert("指定的DOMID不存在(" + domId + ")。");
            return;
        }

        var editorId = domId.replace(this.getClientID() + "_", "");
        //实例化编辑器
        this.editor[editorId] = UM.getEditor(this.getDom(domId));
    },

    //页面切换
    switchPage: function (showSubPage) {
        //显示子画面的场合
        if (showSubPage) {
            //画面Title需要重置的场合
            if (this.isNotEmpty($(this.getFrame()).data("page-title"))) {
                //备份父画面Title
                $(this.parentFrame).data("parent-frame-title", $("title").html());

                //重置子画面Title
                $("title").html(window.PortalName + " - " + $(this.getFrame()).data("page-title"));
            }

            //备份父画面滚动条位置
            $(this.parentFrame).data("scroll-top", $(document).scrollTop());
            this.displayDom(this.parentFrame, false);
            this.displayDom(this.getFrame(), true);
            $(document).scrollTop(0);
        }
        else {
            this.displayDom(this.parentFrame, true);
            this.displayDom(this.getFrame(), false);

            //恢复滚动条位置
            $(document).scrollTop($(this.parentFrame).data("scroll-top"));

            //恢复画面Title
            if (this.isNotEmpty($(this.parentFrame).data("parent-frame-title"))) {
                $("title").html($(this.parentFrame).data("parent-frame-title"));
            }
        }
    },

    //Dom元素的PlaceHolder初始化
    initDomPlaceHolder: function () {
        var thisObj = this;
        $("input[type='text'], input[type='number'], input[type='password'], textarea", this.getMainFormID()).each(function () {
            //title不存在的场合，处理跳过
            if (thisObj.isEmpty($(this).data("title"))) {
                return;
            }

            //placeholder不存在的场合，重置placeholder
            if (thisObj.isEmpty($(this).attr("placeholder"))) {
                $(this).attr("placeholder", "请输入" + $(this).data("title"));
            }
        });
    },

    //DOM元素的NAME初始化
    initDomName: function () {
        var thisObj = this;

        //DOM NAME唯一性设置[子元素]
        $("input[type='text'], input[type='number'], input[type='password'], input[type='hidden'], textarea, select", this.getMainFormID()).each(function () {
            //DOM元素Name为空的场合，Name与ID保持一致
            if (thisObj.isEmpty($(this).attr("name"))) {
                $(this).attr("name", this.id);
            }
        });
    },

    //判断指定的DOMID中是否包含其他ClientID前缀字符串
    containOtherClientID: function (domId) {
        var thisObj = this;
        var contains = false;
        $.each(SysCmn.ClientIDList, function (index, value) {
            if (value != thisObj.clientID && domId.indexOf(value) == 0) {
                console.log("DOM对象（" + domId + "）中包含其他ClientID（" + value + "），请确认JSP页面的ClientID-MainContent是否嵌套设置。");
                contains = true;
                return false;
            }
        });
        return contains;
    },

    //DOM元素的ID初始化
    initDomId: function () {
        var thisObj = this;
        //DOM ID唯一性设置[子元素]
        $("form, input, button, textarea, select, div, h1, h2, h3, h4, h5, h6, label, span, ul, li, i", this.getMainContentClassName()).each(function () {
            //指定的DOMID中包含其他ClientID前缀字符串的场合，当前Dom处理跳过
            if (thisObj.containOtherClientID(this.id)) {
                return;
            }

            //无需强制追加ClientID的场合
            if ($(this).data("client-id") === false) {
                return;
            }

            if (!thisObj.isEmpty(this.id) && this.id.indexOf(thisObj.getClientID()) == -1) {
                //重置DOMID
                this.id = thisObj.getClientID() + "_" + this.id;

                //设置验证LABEL消息的FOR属性，指向对应的DOM对象
                if (this.tagName == "LABEL") {
                    $(this).attr("for", this.id.replaceAll("_Error", ""));
                }
            }

            //LABEL && for属性存在的场合，重置for属性
            if (this.tagName == "LABEL") {
                var tmp = $(this).attr("for");
                if (!thisObj.isEmpty(tmp)) {
                    $(this).attr("for", thisObj.getClientID() + "_" + tmp)
                }
            }
        });

        //DOMID唯一性设置[元素自身]
        $(this.getMainContentClassName()).each(function () {
            if (!thisObj.isEmpty(this.id) && this.id.indexOf(thisObj.getClientID()) == -1) {
                //无需强制追加ClientID的场合
                if ($(this).data("client-id") === false) {
                    return;
                }

                this.id = thisObj.getClientID() + "_" + this.id;
            }
        });
    },

    //日历控件初期化
    initCalendar: function () {
        var thisObj = this;
        try {
            $(".datetime-picker", this.getMainContentClassName()).datetimepicker({
                format: thisObj.calendarFormat,
                locale: "zh-cn",
                showTodayButton: true,
                useCurrent: false
            });
        } catch (e) {
        }
    },

    //金额输入事件绑定
    bindMoneyInputEvent: function (cssClass) {
        if (this.isEmpty(cssClass)) {
            cssClass = this.getMainContentClassName();
        }
        var thisObj = this;
        $("[data-money='true']", cssClass).each(function () {
            //绑定focus 和 blur事件
            if (SysCmn.CoreUtility.isInputMoney(this)) {
                //金额输入框事件绑定
                thisObj.bindEvent(this, 'focus', thisObj.moneyFocus.bind(thisObj, this), true);
                thisObj.bindEvent(this, 'blur', thisObj.moneyBlur.bind(thisObj, this), true);
            }
        });
    },

    //初期化验证控件
    initValidator: function (isSearch) {
        //jQuery Validation Plugin 未加载的场合，处理中止
        if ($.validator == null || $.validator.methods == null) {
            return;
        }
        var thisObj = this;

        //验证规则
        var rules = {};

        //验证消息
        var messages = {};

        //对text/password/textarea/select四类FORM控件进行验证
        $("input[type='text'], input[type='number'], input[type='password'], textarea, select", this.getMainFormID()).each(function () {
            var required = $(this).hasClass("required");
            var minlength = $(this).data("minlength");
            var maxlength = $(this).data("maxlength");
            var rangelength = $(this).data("rangelength");
            var min = $(this).data("min");
            var max = $(this).data("max");
            var range = $(this).data("range");
            var email = $(this).data("email");
            var url = $(this).data("url");
            var date = $(this).data("date");
            var number = $(this).data("number");
            var digits = $(this).data("digits");
            var regex = $(this).data("regex");

            if (thisObj.isNull(maxlength)) {
                maxlength = $(this).attr("maxlength");
            }

            //以下验证初期版本未实现
            //var remote = $(this).data("remote");
            //var dateISO = $(this).data("dateISO");
            //var creditcard = $(this).data("creditcard");
            //var equalTo = $(this).data("equalTo");
            //var step = $(this).data("step");

            //字段名称
            var title = $(this).data("title");

            //rules及messages初期化
            rules[this.id] = {};
            messages[this.id] = {};

            //必须输入验证
            if (required) {
                rules[this.id].required = true;
                if (this.type == "select-one") {
                    messages[this.id].required = $M.formatMessage("请选择{0}。", title);
                }
                else {
                    messages[this.id].required = $M.formatMessage("请输入{0}。", title);
                }
            }

            //文字范围验证
            if (!thisObj.isEmpty(rangelength)) {
                rules[this.id].rangelength = [rangelength[0], rangelength[1]];
                messages[this.id].rangelength = $M.formatMessage("请在{0}中输入[{1}-{2}]位以内的文字。", [title, rangelength[0], rangelength[1]]);
            }

            //数值范围验证
            if (!thisObj.isEmpty(range)) {
                rules[this.id].range = [range[0], range[1]];
                messages[this.id].range = $M.formatMessage("请在{0}中输入[{1}-{2}]范围内的数值。", [title, range[0], range[1]]);
            }

            //邮件验证
            if (!thisObj.isEmpty(email)) {
                rules[this.id].email = true;
                messages[this.id].email = $M.formatMessage("请在{0}中输入有效的邮件地址。", title);
            }

            //URL验证
            if (!thisObj.isEmpty(url)) {
                rules[this.id].url = true;
                messages[this.id].url = $M.formatMessage("请在{0}中输入有效的URL地址。", title);
            }

            //日期验证
            if (!thisObj.isEmpty(date)) {
                rules[this.id].date = true;
                messages[this.id].date = $M.formatMessage("请在{0}中输入有效的日期。", title);
            }

            //数字验证
            if (!thisObj.isEmpty(digits)) {
                rules[this.id].digits = true;
                messages[this.id].digits = $M.formatMessage("请在{0}中输入数字。", title);
            }

            //数值验证
            if (!thisObj.isEmpty(number)) {
                rules[this.id].number = true;
                messages[this.id].number = $M.formatMessage("请在{0}中输入数值。", title);
            }

            //MIN验证
            if (!thisObj.isEmpty(min)) {
                rules[this.id].min = min;
                messages[this.id].min = $M.formatMessage("请在{0}中输入大于等于{1}的数值。", [title, min]);
            }

            //MAX验证
            if (!thisObj.isEmpty(max)) {
                rules[this.id].max = max;
                messages[this.id].max = $M.formatMessage("请在{0}中输入小于等于{1}的数值。", [title, max]);
            }

            //MINLEN验证
            if (!thisObj.isEmpty(minlength)) {
                rules[this.id].minlength = minlength;
                messages[this.id].minlength = $M.formatMessage("请在{0}中至少输入{1}个字符。", [title, minlength]);
            }

            //MAXLEN验证
            if (!thisObj.isEmpty(maxlength)) {
                rules[this.id].maxlength = maxlength;
                messages[this.id].maxlength = $M.formatMessage("请在{0}中输入最多输入{1}个字符。", [title, maxlength]);
            }

            //正则表达式验证
            if (regex) {
                rules[this.id].regex = regex;
                messages[this.id].regex = $M.formatMessage($(this).data("regex-message"), title);
            }
        });

        //正则表达式验证
        $.validator.methods.regex = function (value, element) {
            var regex = eval($(element).data("regex"));
            return this.optional(element) || regex.test(value);
        };

        var submitButton = $(this.getId("btnSave"));

        //检索一览的场合
        if (this.isNotNull(isSearch) && isSearch == true) {
            submitButton = $(this.getId("btnSearch"));
        }

        this.validator = $(this.getMainFormID()).validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            showErrors: this.showValidatorErrors.bind(this),
            element: submitButton,
            rules: rules,
            messages: messages
        });
    },

    //required-option 验证
    validateRequiredOption: function () {
        var thisObj = this;
        //验证消息
        var errMsg = [];

        //对 required-option 控件进行验证
        $(".required-option", this.getMainFormID()).each(function () {
            if (thisObj.isEmpty($(this).val())) {
                //字段名称
                var title = $(this).data("title");

                if (this.type == "select-one") {
                    errMsg.push($M.formatMessage("<li>请选择{0}。</li>", title));
                }
                else {
                    errMsg.push($M.formatMessage("<li>请输入{0}。</li>", title));
                }
            }
        });

        if (errMsg.length == 0) {
            return true;
        }
        else {
            //存在验证错误消息 && Popup显示的场合
            if (errMsg.length > 0 && this.showValidatorPopupMessage) {
                this.showMessage("<ul>" + errMsg.join("") + "</ul>", MSG_TYPE_VALIDATION);
            }
            return false;
        }
    },

    //显示验证错误消息
    showValidatorErrors: function (errorMap) {
        var errMsg = [];

        //清空所有错误消息
        this.clearValidatorErrors();

        var thisObj = this;
        $.each(errorMap, function (inputDom, errorMessage) {
            var errorLabel = inputDom + "_Error";
            var title = thisObj.$(inputDom).data("title");
            if (errorMessage.match(/这是必填字段/) && thisObj.isEmpty(inputDom)) {
                return;
            }
            //必须输入Message重置
            if (errorMessage.match(/这是必填字段/) && !thisObj.isEmpty(title)) {
                if (inputDom == 'file') {
                    errorMessage = $M.formatMessage("请选择{0}。", title);
                } else {
                    errorMessage = $M.formatMessage("请输入{0}。", title);
                }
            }

            //POPUP模式表示验证消息
            errMsg.push("<li>" + errorMessage + "</li>");

            if (!thisObj.isNull(thisObj.getDom(errorLabel))) {
                thisObj.$(errorLabel).html(errorMessage);
                thisObj.$(errorLabel).show();
            }
        });

        //存在验证错误消息 && Popup显示的场合
        if (errMsg.length > 0 && this.showValidatorPopupMessage) {
            this.showMessage("<ul>" + errMsg.join("") + "</ul>", MSG_TYPE_VALIDATION);
        }
    },

    //清空所有错误消息
    clearValidatorErrors: function () {
        //清空所有错误消息
        $(".validator-error").each(function () {
            $(this).html("");
            $(this).hide();
        });
    },

    //初期化下拉选择框
    initOptionList: function (isList) {
        var value = this.getValue("jsonOptionList");

        if (this.isEmpty(value)) return;

        var jsonOptionList = JsonUtility.Parse(value);
        var thisObj = this;
        $.each(jsonOptionList, function (key, subList) {
            if (!thisObj.isNull(thisObj.getDom(key))) {
                //只有是下拉选择控件 && 控件自动赋值属性非False的场合，自动根据jsonOptionList的值自动初始化下拉选择项
                if ($(thisObj.getDom(key)).data("autoValue") != false && thisObj.getDom(key).tagName == 'SELECT') {
                    var needBlank = $(thisObj.getDom(key)).data("need-blank") != false;
                    var blankItemText = isList ? "" : "请选择";
                    thisObj.createOption(key, subList, "subCd", "subName", needBlank, blankItemText);
                }
            }
        });
    },

    //绑定最大化/复原处理事件
    bindMaximization: function () {
        if (this.isNull(this.getFrame())) {
            return;
        }

        var thisObj = this;
        $(".maximization", this.getFrame()).each(function () {
            //最大化/复原窗口
            thisObj.bindEvent(this, "click", thisObj.onClick_Maximization.bind(thisObj));
        });

        $(".modal-header", this.getFrame()).each(function () {
            //双击标题可最大化/复原窗口
            thisObj.bindEvent(this, "dblclick", thisObj.onClick_Maximization.bind(thisObj));
        });
    },

    onClick_Maximization: function () {
        //先记录窗口的原始状态
        if (this.windowState == "normal") {
            this.windowState = "max";
            var frame = this.getFrame();
            if (this.isEmpty(this.windowProperty.width)) {
                //宽度
                this.windowProperty.width = $(frame).outerWidth();
                //高度
                this.windowProperty.height = $(frame).outerHeight();
                //top
                this.windowProperty.top = frame.style.top;
                //left
                this.windowProperty.left = frame.style.left;

                //modal-body高度
                this.windowProperty.bodyHeight = this.parseInt($(".modal-body", this.getFrame()).css("height"));
            }
            var height = "100%";
            if (this.windowProperty.height > $M.getClientHeight()) {
                height = this.windowProperty.height;
            }
            //设置最大化窗口
            this.$("ctlFrame").animate({height: height, width: "100%", top: 0, left: 0}, "fast");
            //最大化/复原样式切换
            this.$("btnMaximization").toggleClass("fa-window-maximize fa-window-restore");

            if (this.isFunction(this.windowMaximizationCallback)) {
                this.windowMaximizationCallback(true);
            }
        }
        else {
            this.windowState = "normal";
            //复原窗口大小
            this.$("ctlFrame").animate({height: this.windowProperty.height, width: this.windowProperty.width, top: this.windowProperty.top, left: this.windowProperty.left}, "fast");

            //最大化/复原样式切换
            this.$("btnMaximization").toggleClass("fa-window-maximize fa-window-restore");

            if (this.isFunction(this.windowMaximizationCallback)) {
                this.windowMaximizationCallback(false);
            }
        }
    },

    //绑定折叠事件
    bindFoldEvent: function () {
        var thisObj = this;
        //折叠Click事件绑定
        $(".form-group-fold button", this.getMainContentClassName()).click(function () {
            var container = $(this).data("container");

            if (thisObj.isEmpty(container)) return;

            //内容显示/隐藏切换
            thisObj.$(container).toggle();

            //图标展开/收缩切换
            $(this).find(".form-group-fold-icon").toggleClass("fa-caret-down fa-caret-up");
        });
    },

    //Tab初期化处理
    initTab: function (tabContainerId, tabCount, tabOnChangeEvent, defaultTabIndex) {
        //Tab对象实例化
        this.tabInstance = new SysTabMultiViewClientClass(this.getId(tabContainerId), tabCount, true);

        if (this.isFunction(tabOnChangeEvent)) {
            //TAB页切换事件
            this.tabInstance.OnActiveViewChanged = tabOnChangeEvent.bind(this);
        }

        if (this.isNull(defaultTabIndex)) defaultTabIndex = 0;

        //显示默认TAB页
        this.tabInstance.setViewActive(defaultTabIndex);
    },

    //绑定popovers事件
    bindPopoversEvent: function () {
        try {
            $('.popovers').popover({
                html: true
            });

            //防止重复绑定，先解绑mouseout事件
            $(".popovers").unbind("mouseout");

            $(".popovers").mouseout(function () {
                $(this).css("display", "");
            });
        }
        catch (e) {
        }
    },

    //行添加
    addRow: function (obj) {
        this._copyRow(obj, false);

        //行添加后处理事件存在的场合，执行行添加后处理
        if (this.isFunction(this.addRowCallback)) {
            this.addRowCallback();
        }
    },

    //行拷贝
    copyRow: function (obj) {
        this._copyRow(obj, true);

        //行拷贝后处理事件存在的场合，执行行拷贝后处理
        if (this.isFunction(this.copyRowCallback)) {
            this.copyRowCallback(obj);
        }
    },

    //行复制
    _copyRow: function (obj, copyValue) {
        var thisObj = this;

        //克隆当前行DOM对象
        var cloneDom = $(obj).parents("tr").clone(true);

        var srcValues = [];

        var time = new Date().getTime();

        //重置克隆DOM对象的ID及Value
        $("input[type='text'], input[type='number'], input[type='hidden'], button, input[type='button'], select, input[type='checkbox'], input[type='radio'], span", cloneDom).each(function () {
            //DOMID存在的场合，重置ID与Value
            if (!thisObj.isEmpty(this.id)) {
                //UID字段值清空
                if (this.id.indexOf("uid") == 0) {
                    $(this).val("");
                }

                //旧ID，用于恢复下拉选择框复制后的默认值
                var old_id = this.id;

                var ids = this.id.split("|");
                if (ids.length == 2) {
                    //DOM ID重置[不带后缀]
                    this.id = ids[0] + "|" + time;
                }
                else {
                    //DOM ID重置[带后缀]
                    this.id = ids[0] + "|" + time + "|" + ids[2];
                }

                var tagName = this.tagName.toUpperCase();

                //记录源DOM的值，用于复制行默认值恢复用
                if (tagName == "SELECT" || tagName == "INPUT") {
                    //非UID字段值的场合
                    if (this.id.indexOf("uid") != 0) {
                        var valueInfo = {};
                        //现在的ID
                        valueInfo.id = this.id;
                        //复制源的值
                        valueInfo.value = $($E(old_id)).val();
                        srcValues.push(valueInfo);
                    }
                }
            }
        });

        //Select2相关代码移除
        $(".select2-container", cloneDom).each(function () {
            $(this).remove();
        });
        $(".select2-hidden-accessible", cloneDom).each(function () {
            $(this).removeClass("select2-hidden-accessible");
        });

        var $tr = $("<tr></tr>");
        //采用HTML形式复制
        $tr.html(cloneDom.get(0).innerHTML);
        //指定行后复制一行
        $tr.insertAfter($(obj).parents("tr"));

        //查找当前列表Table对象
        var table = $(obj).parents("table");

        //重置列表的序号列
        this.resetRowNo(table);

        //设置克隆下拉框的默认选择值
        $.each(srcValues, function (index, valueInfo) {
            var jdom = thisObj.$(valueInfo.id);

            //需要复制数据的场合
            if (copyValue) {
                jdom.val(valueInfo.value);
            }
            else {
                //允许清除DOM原值的场合
                if (jdom.data("allow-clear") != false) {
                    jdom.val("");
                }
            }

            //Select2控件初期化
            if (jdom.attr("aria-hidden")) {
                jdom.select2();
            }
        });

        //初始化Select2必须选择项
        this.initSelect2Required();
    },

    //行删除
    deleteRow: function (obj) {
        var table = $(obj).parents("table");
        if ($("tr", table).length == 2) {
            this.showMessage("最后一行数据禁止删除。", MSG_TYPE_ERROR);
            return;
        }

        //删除当前行
        var row = $(obj).parents("tr");
        $(row).remove();

        //重置列表的序号列
        this.resetRowNo(table);

        //行删除后处理事件存在的场合，执行行删除后处理
        if (this.isFunction(this.deleteRowCallback)) {
            this.deleteRowCallback();
        }
    },

    //行号重置
    resetRowNo: function (table) {
        //由于有格标题行，所以index从0开始
        var index = 0;
        $("tr", table).each(function () {
            $(".row-no", this).html(index);
            index++;
        });
    },

    //快捷方法定义
    isNull: function (obj) {
        return $M.isNull(obj);
    },
    isNotNull: function (obj) {
        return !$M.isNull(obj);
    },
    isEmpty: function (obj) {
        return $M.isEmpty(obj);
    },
    isNotEmpty: function (obj) {
        return !$M.isEmpty(obj);
    },
    formatNum: function (num, n, sign) {
        return $M.formatNum(num, n, sign);
    },
    formatCny: function (num, n) {
        if (this.isEmpty(n)) {
            n = this.amountPrecision;
        }
        return $M.formatCny(num, n);
    },
    formatJpy: function (num, n) {
        return $M.formatJpy(num, n);
    },
    formatDollar: function (num, n) {
        return $M.formatDollar(num, n);
    },
    validIDCard: function (id_card) {
        return $M.validIDCard(id_card);
    },
    parseFloat: function (v) {
        v = parseFloat(v);
        return isNaN(v) ? 0 : v;
    },
    parseInt: function (v) {
        v = parseInt(v, 10);
        return isNaN(v) ? 0 : v;
    },
    $: function (id) {
        return $(this.getDom(id));
    },
    switchOn: function (id) {
        this.$(id).bootstrapSwitch('state', true);
    },
    switchOff: function (id) {
        this.$(id).bootstrapSwitch('state', false);
    },
    displayDomByClass: function (cls, display) {
        if (display == true) {
            $(cls).show();
        }
        else {
            $(cls).hide();
        }
    },
    displayDom: function (id, display) {
        if (display == true) {
            this.$(id).show();
        }
        else {
            this.$(id).hide();
        }
    },
    isChecked: function (id) {
        if (this.isNull(this.getDom(id))) return false;
        return this.getDom(id).checked;
    },
    isDisplayed: function (obj) {
        //obj为domid的场合
        if (typeof obj == "string") {
            obj = this.getDom(obj);
        }

        if ($(obj).css("display") === 'none') {
            return false;
        }
        return true;
    },
    isWeixinBrowser: function () {
        return $M.isWeixinBrowser();
    },
    setMessageBoxWidth: function (width) {
        SysCmn.CmnMsg.setWidth(width);
    },
    setMessageBoxTitle: function (title) {
        SysCmn.CmnMsg.setTitle(title);
    },
    showMessage: function (msg, msgType, closeCallback) {
        SysCmn.CmnMsg.showMessage(msg, msgType, closeCallback);
    },
    hideMessage: function () {
        SysCmn.CmnMsg.hide();
    },
    showErrorMessage: function (msg, closeCallback) {
        SysCmn.CmnMsg.showMessage(msg, MSG_TYPE_ERROR, closeCallback);
    },
    showWarningMessage: function (msg, closeCallback) {
        SysCmn.CmnMsg.showMessage(msg, MSG_TYPE_WARNING, closeCallback);
    },
    showInfoMessage: function (msg, closeCallback) {
        SysCmn.CmnMsg.showMessage(msg, MSG_TYPE_INFO, closeCallback);
    },
    showValidationMessage: function (msg, closeCallback) {
        SysCmn.CmnMsg.showMessage(msg, MSG_TYPE_VALIDATION, closeCallback);
    },
    showProcessing: function (allowClose) {
        SysCmn.CmnMsg.showProcessing(allowClose);
    },
    showConfirm: function (msg, yesCallback, noCallback, cancelCallback) {
        SysCmn.CmnMsg.showConfirm(msg, yesCallback, noCallback, cancelCallback);
    },
    convertNum: function (num) {
        return $M.convertNum(num)
    },
    toFixed: function (num, n) {
        return $M.toFixed(num, n);
    },
    encodeValue: function (v) {
        return $M.encodeValue(v);
    },
    brToEnter: function (v) {
        return $M.brToEnter(v);
    },
    enterToBr: function (v) {
        return $M.enterToBr(v);
    },
    isEntryFromMenu: function () {
        return (this.entry == "menu");
    },
    //将金额转为数字格式[计划删除]
    convertAmount: function (obj) {
        obj.value = this.convertNum(obj.value);
    },
    //将金额转为数字格式[计划删除]
    convertNumber: function (obj) {
        this.convertAmount(obj);
    },
    //将金额转为数字格式
    convertAmountToNumber: function (obj) {
        obj.value = this.convertNum(obj.value);
    },
    //将数字转为金额格式
    convertNumberToAmount: function (obj) {
        if (Object.isUndefined(obj)) {
            return;
        }
        //非数值的场合，设置为0
        if (!this.isDouble(obj)) {
            obj.value = "0";
        } else {
            if (parseFloat(obj.value) < 0) {
                obj.value = "0";
            }
        }
        obj.value = this.formatCny(obj.value);
    },
    //金额输入内容focus
    moneyFocus: function (obj) {
        obj.value = this.convertNum(obj.value);

        if (this.isFunction(this.moneyFocusCallback)) {
            this.moneyFocusCallback(obj);
        }
    },
    //过滤JS特殊字符
    trimSpecialChar: function (v) {
        if (this.isEmpty(v)) {
            return v;
        }
        return v.replaceAll("'", "").replaceAll("\"", "");
    },
    //显示画面加载中提示
    showPageLoader: function () {
        $("#page-preloader").fadeIn();
    },
    //金额输入内容blur
    moneyBlur: function (obj) {
        //防止多重绑定先转换成数字后再格式化为金额
        obj.value = this.convertNum(obj.value);
        if (!this.isDouble(obj)) {
            this.setValue(obj, "0");
        }
        obj.value = this.formatCny(obj.value);

        if (this.isFunction(this.moneyBlurCallback)) {
            this.moneyBlurCallback(obj);
        }
    },

    //手机号码验证(true：有效的手机号码)
    isMobile: function (value) {
        if (this.isEmpty(value)) {
            return false;
        }

        if (value.match(/^1(3|4|5|6|7|8|9)\d{9}$/) == null) {
            return false;
        }
        else {
            return true;
        }
    },

    //数值输入验证
    isNumber: function (value) {
        if (this.isEmpty(value)) {
            return false;
        }

        if (value.match(/^\d+$/) == null) {
            return false;
        }
        else {
            return true;
        }
    },

    //数值范围验证
    isRange: function (value, minValue, maxValue) {
        if (!this.isNumber(value)) {
            return false;
        }

        if (value - minValue < 0 || value - maxValue > 0) {
            return false;
        }

        return true;
    },

    //英文字符验证
    isEnglishString: function (value) {
        if (this.isEmpty(value)) {
            return false;
        }
        //英字（包含空格）
        if (value.match(/^[a-zA-Z ]+$/) == null) {
            return false;
        }
        else {
            return true;
        }
    },

    //英文数字字符验证
    isEnglishOrNumber: function (value) {
        if (this.isEmpty(value)) {
            return false;
        }

        //英数字（包含空格）
        if (value.match(/^[a-zA-Z0-9 ]+$/) == null) {
            return false;
        }
        else {
            return true;
        }
    },

    //文字定长验证（等于）
    isEqualLength: function (value, intLen) {
        if (this.isEmpty(value)) {
            return false;
        }
        if (value.length != intLen) {
            return false;
        }
        return true;
    },

    //文字长度验证（小于）
    isLessLength: function (value, intLen) {
        if (this.isEmpty(value)) {
            return false;
        }
        if (value.length > intLen) {
            return false;
        }
        return true;
    },

    //文字长度验证（大于）
    isMoreLength: function (value, intLen) {
        if (this.isEmpty(value)) {
            return false;
        }
        if (value.length < intLen) {
            return false;
        }
        return true;
    },

    //ユーザーIDは256桁以内の半角英数字、.（ピリオド）、@（アットマーク）、_（アンダースコア）、-（ハイフン）のみ入力可能です。
    isValidUserCd: function (value, intLen) {
        if (this.isEmpty(value)) {
            return false;
        }

        if (value.match(/^[a-zA-Z0-9_@\.-]+$/) == null || value.length > intLen) {
            return false;
        }
        else {
            return true;
        }
    },

    //ASCII図形文字のチェック(0x00-0x1f:制御文字/0x20:空白/0x7f:制御文字(DEL))
    isASCIIWithoutControlChar: function (value) {
        if (this.isEmpty(value)) {
            return false;
        }

        //\X21(!)から\X7E(~)までの英数字記号の範囲入力チェック
        if (value.match(/^[!-~]*$/g) != null) {
            return true;
        }
        return false;
    },

    // [機  能]
    //      セキュリティ設定に応じてパスワードのチェックを行う<br />
    //      制御文字を除くASCII文字<br />
    //      文字制御で指定された条件<br />
    //      最小文字数以上<br />
    //      最大文字数以下
    // @param password      パスワード文字列
    // @param pwdMode       文字制御（1:英字、数字の組合せ必要,2:数字のみ,それ以外:通常チェック）
    // @param pwdMinLen     最小文字数
    // @param pwdMaxLen     最大文字数
    // @return チェック結果(true:成功/false:失敗)
    isValidPassword: function (password, pwdMode, pwdMinLen, pwdMaxLen) {
        //最小文字数チェック
        if (this.isMoreLength(password, pwdMinLen) == false) {
            return false;
        }

        //最大文字数チェック
        if (this.isLessLength(password, pwdMaxLen) == false) {
            return false;
        }

        //半角英数字記号入力チェック
        if (!this.isASCIIWithoutControlChar(password)) {
            return false;
        }

        //英字、数字の組合せ必要
        if (pwdMode == "1") {
            //英字、数字が含まれているかチェック
            if (password.match(/[0-9]/) != null && password.match(/[A-Za-z]/) != null) {
                return true;
            }
            else {
                return false;
            }
        }
        //数字のみ
        else if (pwdMode == "2") {
            if (!this.isNumber(password)) {
                return false;
            }
        }
        return true;
    },

    //日期验证(YYYY/MM/DD 、2004/2/30非法)
    isDate: function (value) {
        if (this.isEmpty(value)) {
            return false;
        }

        var dateObj = new Date(value);

        var year = dateObj.getFullYear();
        var mouth = dateObj.getMonth() + 1;
        if (mouth < 10) {
            mouth = "0" + mouth;
        }
        var day = dateObj.getDate();
        if (day < 10) {
            day = "0" + day;
        }
        var strDate = year + "/" + mouth + "/" + day;

        if (value == strDate) {
            return true;
        }
        else {
            return false;
        }
    },

    //获取输入月份的第一天
    getFirstDayOfMonth: function (date) {
        date.setDate(1);
        return date.formatYMD();
    },

    //获取当前月份的第一天
    getFirstDayOfCurrentMonth: function () {
        return this.getFirstDayOfMonth(new Date())
    },

    //获取输入月份的最后一天
    getLastDayOfMonth: function (date) {
        return new Date(date.getFullYear(), date.getMonth() + 1, 0).formatYMD();
    },

    //获取当前月份的最后一天
    getLastDayOfCurrentMonth: function () {
        return this.getLastDayOfMonth(new Date());
    },

    /****************************ECharts相关共通方法****************************/
    //取得ECharts标题属性
    getEChartsTitle: function (title, fontSize) {
        if (this.isEmpty(fontSize)) {
            fontSize = 14;
        }
        return {
            text: title,
            x: 'center',
            textStyle: {
                fontSize: fontSize
            },
            subtextStyle: {
                color: '#3398db'
            }
        }
    },

    //取得ECharts柱状图Toolbox属性
    getEChartsBarToolbox: function (showToolbox, showMagicType) {
        if (this.isNull(showToolbox)) {
            showToolbox = true;
        }
        if (this.isNull(showMagicType)) {
            showMagicType = true;
        }
        return {
            show: showToolbox,
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: showMagicType, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        }
    },

    //取得ECharts柱状图Tooltip属性
    getEChartsBarTooltip: function () {
        return {
            trigger: 'axis',
            //坐标轴指示器，坐标轴触发有效
            axisPointer: {
                //默认为直线，可选为：'line' | 'shadow'
                type: 'shadow'
            }
        };
    },

    //取得ECharts X轴属性
    getEChartsXAxis: function (rotate) {
        var xAxis = {
            type: 'category',
            data: []
        };

        if (this.isNotNull(rotate)) {
            xAxis.axisLabel = this.getEChartsAxisLabel(rotate);
        }
        return xAxis;
    },

    //取得ECharts Y轴属性
    getEChartsYAxis: function (name, splitNumber) {
        if (this.isNull(splitNumber)) {
            splitNumber = 3;
        }
        var yAxis = {
            type: 'value',
            name: name,
            splitNumber: splitNumber
        };
        return yAxis;
    },

    //取得ECharts grid属性
    getEChartsGridOption: function (bottom) {
        var grid = {
            left: '4.5%',
            right: '4.5%'
        }
        if (this.isNotNull(bottom)) {
            grid.bottom = bottom;
        }

        return [grid];
    },

    //取得ECharts Series系列属性
    getEChartsSeriesOption: function (name, type, barWidth, showAverage, showLabel) {
        if (this.isNull(type)) {
            type = "bar";
        }
        if (this.isNull(barWidth)) {
            barWidth = 10;
        }
        if (this.isNull(showAverage)) {
            showAverage = true;
        }
        //是否显示图形上的文本标签[默认显示]
        if (this.isNull(showLabel)) {
            showLabel = true;
        }

        var markLine = {};
        if (showAverage) {
            markLine = {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            };
        }

        var option = {
            name: name,
            barWidth: barWidth,
            barGap: '50%',
            type: type,
            data: [],
            label: {
                normal: {
                    //是否显示图形上的文本标签
                    show: showLabel,
                    position: 'top',
                    textStyle: {
                        fontSize: '8'
                    }
                }
            },
            markLine: markLine
        };

        return option;
    },

    //取得ECharts X轴Label属性
    getEChartsAxisLabel: function (rotate) {
        return {
            interval: 0,
            rotate: rotate,
            margin: 8,
            textStyle: {
                color: "#222"
            }
        }
    },

    //合计ECharts系列值
    sumEChartsSeriesValue: function (seriesValue) {
        var sum = 0;
        for (var i = 0; i < seriesValue.length; i++) {
            if (this.isNull(seriesValue[i])) continue;
            if (this.isNull(seriesValue[i].value)) {
                sum += seriesValue[i];
            }
            else {
                sum += seriesValue[i].value;
            }
        }
        return sum;
    },

    //Title提示框(需要提示的对象的class上加title-prompt-box)
    titlePromptBox: function () {
        var titlePromptButton = $(".title-prompt-box");
        //解绑所有事件
        titlePromptButton.unbind("mouseenter");
        titlePromptButton.unbind("mouseleave");

        titlePromptButton.mouseenter(function () {
            var titlePromptFloat = $(".title-prompt-float");
            //提示div存在场合
            if (titlePromptFloat.length > 0) {
                $(".prompt-float").html($(this).data('title'));
            } else {
                $("body").append("<div class='title-prompt-float'><i class='fa'></i><div class='prompt-float'>"
                    + $(this).data('title')
                    + "</div></div>");
            }
            var titlePromptFloat = $(".title-prompt-float");
            var dataPlacement = $(this).data("placement");
            var topButton = $(this).offset().top;
            var leftButton = $(this).offset().left;
            var widthButton = $(this).width();
            var heightButton = $(this).height();
            var widthBorder = titlePromptFloat.children("div").width();
            var heightBorder = titlePromptFloat.children("div").height();
            titlePromptFloat.children("i").removeClass("fa-caret-right").removeClass("fa-caret-left").removeClass("fa-caret-up").removeClass("fa-caret-down");
            if (dataPlacement == "right") {
                titlePromptFloat.addClass("title-prompt-right");
                titlePromptFloat.children("i").addClass("fa-caret-left");
                titlePromptFloat.css({"top": topButton - 10, "left": leftButton + widthButton + 25});
                titlePromptFloat.children("i").css("top", heightBorder / 2 - 1);
            }
            if (dataPlacement == "left" || dataPlacement == undefined) {
                titlePromptFloat.addClass("title-prompt-left");
                titlePromptFloat.children("i").addClass("fa-caret-right");
                titlePromptFloat.css({"top": topButton - 10, "left": leftButton - widthBorder - 42});
                titlePromptFloat.children("i").css("top", heightBorder / 2 - 1);
            }
            if (dataPlacement == "top") {
                titlePromptFloat.addClass("title-prompt-top");
                titlePromptFloat.children("i").addClass("fa-caret-down");
                titlePromptFloat.css({"top": topButton - heightBorder - 35, "left": leftButton + widthButton / 2 - widthBorder / 2 - 17});
                titlePromptFloat.children("i").css("left", widthBorder / 2 + 10);
            }
            if (dataPlacement == "bottom") {
                titlePromptFloat.addClass("title-prompt-bottom");
                titlePromptFloat.children("i").addClass("fa-caret-up");
                titlePromptFloat.css({"top": topButton + heightButton + 20, "left": leftButton - widthBorder / 2});
                titlePromptFloat.children("i").css("left", widthBorder / 2 + 15);
            }
        });
        titlePromptButton.mouseleave(function () {
            $(".title-prompt-float").remove();
        });
    },

    //iScroll初期化
    initIScroll: function () {
        $(".iscroll-wrapper").each(function () {
            var iscrollId = $(this).data('iscroll-id');
            if ($M.isEmpty(iscrollId)) {
                return;
            }
            window[iscrollId] = new IScroll(this, IScrollUtility.getGlobalOptions());
        });
    },

    /********************************************CTI电话拨打通用方法********************************************/
    //电话挂断事件
    hangUp: function (uid) {
        //挂断按钮隐藏
        this.displayDom("hangUp_" + uid, false);
        //拨打按钮显示
        this.displayDom("call_" + uid, true);
        //全局拨打的简历UID
        this.callPhoneUid = "";
        this.dropMode();
    },

    //拨打本地电话事件
    localCall: function (uid, mobile) {
        //如果没有正在拨打的电话，可进行正常拨打
        if (this.isEmpty(this.callPhoneUid)) {
            //拨打按钮隐藏
            this.displayDom("call_" + uid, false);
            //挂断按钮显示
            this.displayDom("hangUp_" + uid, true);
            //全局拨打的简历UID
            this.callPhoneUid = uid;
            this.dialMode(mobile, 1);
        } else {
            this.showErrorMessage("当前正在通话中，请先挂断电话，再拨打！")
        }
    },

    //拨打外地号码事件
    otherPlacesCall: function (uid, mobile) {
        //如果没有正在拨打的电话，可进行正常拨打
        if (this.isEmpty(this.callPhoneUid)) {
            //拨打按钮隐藏
            this.displayDom("call_" + uid, false);
            //挂断按钮显示
            this.displayDom("hangUp_" + uid, true);
            //全局拨打的简历UID
            this.callPhoneUid = uid;
            this.dialMode(mobile, 2);
        } else {
            this.showErrorMessage("当前正在通话中，请先挂断电话，再拨打！")
        }
    },

    //电话外拨触发
    dialMode: function (mobile, type) {
        var thisObj = this;
        //本地电话
        if (type == 1) {
            mobile = '9' + mobile.trim();
        } else {
            mobile = '90' + mobile.trim();
        }
        window.frames['ctiCommon'].station.dial(mobile).then(function (e) {
            console.info('外拨' + e);
        })['catch'](function (e) {
            thisObj.showErrorMessage(e.message);
        });
    },

    //电话挂断触发
    dropMode: function () {
        var thisObj = this;
        window.frames['ctiCommon'].station.drop().then(function (e) {
            console.info('挂断');
        })['catch'](function (e) {
            thisObj.showErrorMessage(e.message);
        });
    },

    //对方挂断，页面挂断按钮变为可拨打状态
    hangUpPhone: function () {
        //挂断按钮隐藏
        this.displayDom("hangUp_" + this.callPhoneUid, false);
        //拨打按钮显示
        this.displayDom("call_" + this.callPhoneUid, true);
        //全局拨打的简历UID
        this.callPhoneUid = "";
    }
}
//Core.js类文件定义结束------------------------------------------------------------------//


//CoreInput.js类文件定义开始-------------------------------------------------------------//
//数据输入画面处理共通JS类定义
SysCmn.CoreInput = Class.create();

SysCmn.CoreInput.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        //共通Property初期化
        this.initCommonProperty();

        //Callback方法
        this.callback = function () {
        };

        //編集模式（0:添加/1:編集）
        this.editMode = 0;

        //1：保存/2：保存并提交
        this.saveType = 1;

        //是否需要初期化画面Dom值？
        //true:需要根据inputEntity对象初始化画面Dom值
        this.initFormDomValue = true;

        this.saveMethod = "save";

        //数据输入页面标识符
        this.isInputPage = true;
    },

    //是否是追加模式？
    isAddMode: function () {
        return (this.editMode == 0);
    },

    //是否是編集模式？
    isEditMode: function () {
        return (this.editMode == 1);
    },

    //是否是详细模式？
    isDetailMode: function () {
        return this.isDetail;
    },

    //初期化处理
    init: function () {
        //保存ClientID到全局数组中
        SysCmn.ClientIDList.push(this.clientID);

        //DOM元素的ID初始化
        this.initDomId();

        //DOM元素的Name初始化
        this.initDomName();

        this.saveConfirmMessage = "确定要保存吗？";

        //是否显示数据保存前的确认消息
        if (this.isEmpty(window.ShowSaveDataConfirmMessage)) {
            this.showSaveDataConfirmMessage = true;
        }
        else {
            this.showSaveDataConfirmMessage = window.ShowSaveDataConfirmMessage;
        }

        //输入画面可能的共通DOM ID
        var domList = ["ctlFrame", "ctlTitle", "btnImport", "btnSave", "btnSaveSubmit", "btnDelete", "btnClear", "btnAudit", "btnAuditReturn", "btnAuditCancel",
            "btnBack", "jsonInputEntity", "jsonTableColumns", "btnClose", "btnTopClose", "btnHelp"];
        //共通DOM ID参数初期化
        this.initCommonDomIds(domList);

        //表结构定义DOM对象存在的场合
        if (this.isNotNull(this.dom.jsonTableColumns)) {
            this.tableColumns = JsonUtility.Parse($E(this.dom.jsonTableColumns).value);
        }

        //调用Core类的初期化方法
        this.initCore();

        //初期化下拉选择框
        this.initOptionList(false);

        //Popup表示控件的场合
        if (this.isPopupMode() && this.isNotNull(this.dom.ctlTitle)) {
            this.hide();

            $(this.getFrame()).css("position", "absolute");

            var thisObj = this;
            $(".modal-header", this.getFrame()).each(function () {
                thisObj.bindEvent(this, 'mousedown', function (event) {
                    SysCmn.DragDrop.Start(thisObj.getFrame().id, event)
                }.bind(thisObj));
            });
        }

        //导入 按钮
        this.bindEvent(this.dom.btnImport, 'click', this.onClick_Import.bind(this));

        //保存 按钮
        this.bindEvent(this.dom.btnSave, "click", this.onClick_Save.bind(this, 1));

        //保存并提交 按钮
        this.bindEvent(this.dom.btnSaveSubmit, "click", this.onClick_Save.bind(this, 2));

        //返回 按钮
        this.bindEvent(this.dom.btnBack, 'click', this.onClick_Back.bind(this));

        //删除 按钮
        this.bindEvent(this.dom.btnDelete, 'click', this.onClick_Delete.bind(this));

        //数据审核相关操作
        this.bindEvent(this.dom.btnAudit, 'click', this.onClick_Audit.bind(this, 1));
        this.bindEvent(this.dom.btnAuditReturn, 'click', this.onClick_Audit.bind(this, 2));
        this.bindEvent(this.dom.btnAuditCancel, 'click', this.onClick_Audit.bind(this, 3));

        //清空 按钮
        this.bindEvent(this.dom.btnClear, 'click', this.onClick_Clear.bind(this));

        //关闭 按钮
        this.bindEvent(this.dom.btnClose, 'click', this.onClick_Close.bind(this));
        this.bindEvent(this.dom.btnTopClose, 'click', this.onClick_Close.bind(this));

        //帮助 按钮
        this.bindEvent(this.dom.btnHelp, "click", this.showHelp.bind(this));

        if (this.isFunction(this.initCallback)) {
            this.initCallback();
        }

        //绑定popovers事件
        this.bindPopoversEvent();

        //画面初期值设定
        this.initInputValue();

        //详细画面表示初期化处理
        this.initDetailForm();

        if (this.autoHideFlashMessage) {
            //Flash消息自动隐藏
            this.hideFlashMessage();
        }

        //数据重复校验初期化
        this.initValidateDuplication();

        //Dom元素的PlaceHolder初始化
        this.initDomPlaceHolder();

        //初期化验证控件
        if (this.isFunction(this.initValidator)) {
            this.initValidator();
        }

        if (this.initCalendarFlag) {
            //日历控件初期化
            this.initCalendar();
        }

        //金额输入事件绑定
        this.bindMoneyInputEvent();

        //绑定折叠事件
        this.bindFoldEvent();

        //绑定最大化/最小化处理事件
        this.bindMaximization();
    },

    //详细画面表示初期化处理
    initDetailForm: function () {
        //详细表示的场合，画面Form无效化
        if (this.isDetailMode()) {
            this.disableInput(this.getMainFormID(), true);

            if (this.isNotNull($E(this.dom.btnSave))) {
                this.displayDom(this.dom.btnSave, false);
                this.displayDom(this.dom.btnSaveSubmit, false);
            }
        }
        else {
            this.disableInput(this.getMainFormID(), false);

            this.displayDom(this.dom.btnSave, true);
        }

        //POPUP模式 && 添加模式的场合，清空主键值
        if (this.isPopupMode() && this.isAddMode()) {
            if (this.isNotEmpty(this.getClientID())) {
                this.setValue(this.getClientID() + "_" + this.getPrimaryKey(), "");
            }
            else {
                this.setValue(this.getPrimaryKey(), "");
            }
        }

        //初期化后处理
        if (this.isFunction(this.initInputFormAfter)) {
            this.initInputFormAfter();
        }

        //初始化Select2必须选择项
        this.initSelect2Required();
    },

    //数据导入处理
    onClick_Import: function () {
        if (!Page_ClientValidate()) {
            return false;
        }

        this.showConfirm("确定要进行数据的导入处理吗？", this.importData.bind(this));
        return false;
    },

    //数据导入处理
    importData: function () {
        if (this.showProcessingMessage) {
            //Ajax处理前显示提示画面
            this.showProcessing(false);
        }

        this.getDom("btnImportSubmit").disabled = false;
        this.getDom("btnImportSubmit").click();
    },

    //数据保存处理
    onClick_Save: function (saveType) {
        this.saveType = saveType;

        //自定义保存的场合
        if (this.isFunction(this.customSave)) {
            this.customSave();
            return;
        }

        //自定义确认消息
        if (this.isFunction(this.customSaveConfirmMessage)) {
            this.saveConfirmMessage = this.customSaveConfirmMessage(saveType);
        }

        //数据校验通过的场合
        if (this.checkData()) {
            if (this.showSaveDataConfirmMessage) {
                this.showConfirm(this.saveConfirmMessage, this.saveData.bind(this, saveType));
            }
            else {
                this.saveData();
            }
        }
    },

    //返回处理
    onClick_Back: function () {
        this.showPageLoader();
        window.location = this.listUrl;
    },

    //数据删除处理
    onClick_Delete: function () {
        this.showConfirm(this.deleteConfirmMessage, this.deleteData.bind(this));
    },

    //数据审核处理
    onClick_Audit: function (type) {
        //数据校验未通过的场合
        if (!this.checkData()) {
            return;
        }

        if (type == 1) {
            this.showConfirm("审核通过后不能再修改审核意见，确定要审核通过吗？", this.auditData.bind(this, 1));
        }
        //审核退回
        else if (type == 2) {
            this.showConfirm("确定要审核退回吗？", this.auditData.bind(this, 2));
        }
        //反审核
        else if (type == 3) {
            this.showConfirm("确定要反审核吗？", this.auditData.bind(this, 3));
        }
    },

    //画面Form⇒数据模型中转换设定
    getEntityByCssClass: function (className, inputEntity) {
        if (this.isEmpty(inputEntity) || typeof inputEntity === "string") {
            inputEntity = {};
        }

        if (this.isNull(this.getClientID())) {
            this.showMessage("Js Class ClientID is not defined.", MSG_TYPE_WARNING);
        }
        var thisObj = this;
        var clientId = this.getClientID();

        //文本输入框 || 隐藏对象 || 密码输入框 || 下拉框 || 多文本输入域 || 单选框输入框 || 多选框输入框
        $("input[type='text'], input[type='number'], input[type='hidden'], input[type='password'], input[type='radio'], input[type='checkbox'], select, textarea", className).each(function () {
            var key = SysCmn.CoreUtility.getFieldName(clientId, this.id);

            var prefix = "";
            //子对象存在的场合
            if (key.indexOf(".") > 0) {
                prefix = key.split(".")[0];
                key = key.split(".")[1];

                //子对象不存在的场合，初期化子对象
                if (thisObj.isEmpty(inputEntity[prefix])) {
                    inputEntity[prefix] = {};
                }
            }
            //单选或多选项的场合
            if (this.type == "checkbox" || this.type == "radio") {
                var v = this.value;
                if ($M.isEmpty(v) || v == "on") {
                    v = 1;
                }
                if (thisObj.isNotEmpty(prefix)) {
                    inputEntity[prefix][key] = this.checked ? v : 0;
                }
                else {
                    inputEntity[key] = this.checked ? v : 0;
                }
            }
            else {
                //如果是输入框的金额
                if (SysCmn.CoreUtility.isInputMoney(this)) {
                    if (thisObj.isNotEmpty(prefix)) {
                        inputEntity[prefix][key] = thisObj.convertNum($M.trim(this.value));
                    }
                    else {
                        inputEntity[key] = thisObj.convertNum($M.trim(this.value));
                    }
                }
                else {
                    var v = $(this).val();

                    //下拉多选的场合，将返回的数组转换成已逗号分隔的字符串
                    if (v instanceof Array) {
                        v = v.join(",");
                    }
                    if (thisObj.isNotEmpty(prefix)) {
                        inputEntity[prefix][key] = $M.trim(v);
                    }
                    else {
                        inputEntity[key] = $M.trim(v);
                    }

                    //下拉选择框的场合，补全xxxXxxName属性字段值
                    if (this.type == "select-one" || this.type == "select-multiple") {
                        var text = "";
                        $(this).find("option:selected").each(function () {
                            if ($M.isEmpty(text)) {
                                text = $(this).text();
                            }
                            else {
                                text += "," + $(this).text();
                            }
                        });

                        var tempKey = "";
                        if (key.endsWith('Uid')) {
                            tempKey = key.substring(0, key.length - 3) + "Name";
                        }
                        if (key.endsWith('Cd')) {
                            tempKey = key.substring(0, key.length - 2) + "Name";
                        }

                        if (text.startsWith("请选择")) {
                            text = "";
                        }

                        if (thisObj.isNotEmpty(tempKey)) {
                            if (thisObj.isNotEmpty(prefix)) {
                                inputEntity[prefix][tempKey] = $M.trim(text);
                            }
                            else {
                                inputEntity[tempKey] = $M.trim(text);
                            }
                        }
                    }
                }
            }
        });

        return inputEntity;
    },

    //取得数据输入画面用的数据模型
    getInputEntity: function (inputEntity) {
        if (this.isNull(inputEntity)) {
            inputEntity = {};
        }

        return this.getEntityByCssClass(this.getMainFormID(), inputEntity);
    },

    //画面内容保存
    saveData: function (saveType) {
        this.inputEntity = this.getInputEntity(this.inputEntity);
        if (this.getPrimaryKey() == "uid" && this.isEmpty(this.inputEntity.recordStatus)) {
            this.inputEntity.recordStatus = 1;
        }
        else if (this.getPrimaryKey() != "uid" && this.isEmpty(this.inputEntity.deleted)) {
            this.inputEntity.deleted = 1;
        }

        //客户化设定数据模型方法存在的场合
        if (this.isFunction(this.customInputEntity)) {
            this.customInputEntity();
        }

        var params = "inputJson=" + JsonUtility.ToJSON(this.inputEntity) + "&editMode=" + this.editMode;

        if (!$M.isEmpty(saveType)) {
            params = params + "&saveType=" + saveType;
        }

        //客户化参数
        if (this.isFunction(this.customRequestParams)) {
            params += "&" + this.customRequestParams();
        }

        if (this.showProcessingMessage) {
            //Ajax处理前显示提示画面
            this.showProcessing(false);
        }

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + this.saveMethod, this.saveDataCallback.bind(this), params);
    },

    //画面内容保存[Callback后处理]
    saveDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //数据保存客户化回调方法存在的场合
        if (this.isFunction(this.customSaveDataCallback)) {
            this.customSaveDataCallback(ajaxResult);
        }

        //处理成功
        if (ajaxResult.result > 0) {
            if (this.showSaveDataSuccessMessage) {
                if (this.isSaveSuccessAfterRefreshList) {
                    this.showMessage(ajaxResult.message, MSG_TYPE_INFO, this.refreshList.bind(this));
                }
                else {
                    this.showMessage(ajaxResult.message, MSG_TYPE_INFO);
                }
            }
            else {
                if (this.isSaveSuccessAfterRefreshList) {
                    this.refreshList();
                }
                else {
                    this.hideMessage();
                }
            }
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    },

    //表示处理
    show: function (callback, editMode, keyValue) {
        //详细模式的场合
        if (this.isDetailMode()) {
            this.displayDom(this.dom.btnDelete, !this.isDetailMode());
            this.displayDom(this.dom.btnSave, !this.isDetailMode());
            this.displayDom(this.dom.btnClear, !this.isDetailMode());
        }
        else {

        }
        //主键传递的场合，根据主键取得详细信息
        if (this.isNotEmpty(keyValue)) {
            this.getDetail(keyValue, editMode, callback);
        }
        else {
            return this._show(callback, editMode);
        }
    },

    //根据主键取得详细信息
    getDetail: function (keyValue, editMode, callback) {
        if (this.isEmpty(editMode)) {
            editMode = 1;
        }
        var params = "uid=" + keyValue;

        if (this.showProcessingMessage) {
            //Ajax处理前显示提示画面
            this.showProcessing();
        }

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "getDetail", this.getDetailCallback.bind(this, callback, editMode), params);
    },

    //根据主键取得详细信息[Callback后处理]
    getDetailCallback: function (callback, editMode, response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        this.hideMessage();

        this.inputEntity = JsonUtility.Parse(response.responseText);

        this.initInputForm(this.inputEntity);

        this._show(callback, editMode);
    },

    //表示处理
    _show: function (callback, editMode) {
        //清空所有错误消息
        this.clearValidatorErrors();

        //POPUP表示的场合
        if (this.isPopupMode()) {
            //页面切换模式的场合
            if (this.popupSwitchPage == true && this.isNotNull(this.parentFrame)) {
                //显示子画面
                this.switchPage(true);
            }
            else {
                SysCmn.CtrlUtil.SetPopupPosition(this.getFrame(), this.popupPosition);
                SysCmn.EstopLayer.show(this.getFrame());
                $(this.getFrame()).fadeIn();
            }
        }

        if (this.isNotNull(callback)) {
            this.callback = callback;
        }

        //控件初期值设定
        this.initInputValue(editMode);

        //详细画面表示初期化处理
        this.initDetailForm();

        //Popup表示回调函数
        if (this.isPopupMode() && this.isFunction(this.showCallback)) {
            this.showCallback();
        }
    },

    //初期化输入画面表单的值
    initInputValue: function (editMode) {
        if (this.isNotNull(editMode)) {
            this.editMode = editMode;
        }

        //[注意]输入画面JSON Entity对象存在的场合，控件Popup编辑不会执行以下处理代码
        if (this.isNotNull($E(this.dom.jsonInputEntity)) && (!this.isPopupMode() || this.isPageMode)) {
            this.inputEntity = JsonUtility.Parse($E(this.dom.jsonInputEntity).value);

            var keyValue = this.getPkValue(this.inputEntity, this.getPrimaryKey());

            if (this.isNotEmpty(keyValue) && (this.inputEntity.recordStatus == 1 || this.inputEntity.recordStatus == 8 || this.inputEntity.deleted == 1)) {
                if (this.inputEntity.addMode) {
                    this.editMode = 0;
                }
                else {
                    this.editMode = 1;
                }
            }
        }

        //初期化前处理
        if (this.isFunction(this.initInputFormBefore)) {
            this.initInputFormBefore();
        }

        if (this.isNotNull(this.inputEntity) && this.initFormDomValue) {
            //画面内容初期化
            this.initInputForm(this.inputEntity);
        }
        //添加&编辑模式的场合
        if (!this.isDetailMode()) {
            this.displayDom(this.dom.btnDelete, this.isEditMode());
        }
    },

    //刷新一览
    refreshList: function () {
        //POPUP控件模式
        if ($M.isEmpty(this.listUrl)) {
            SysCmn.CmnMsg.hide(true);
            this.hide();

            if (this.isFunction(this.callback)) {
                this.callback(true, this.inputEntity);
            }
        }
        //画面模式
        else {
            this.showPageLoader();
            window.location = this.listUrl;
        }
    },

    //导入文件类型验证
    checkImportFileType: function (file) {
        var ext_name = $M.getExtension(this.getValue(file));
        if (ext_name != "xls" && ext_name != "xlsx") {
            return false;
        }
        else {
            return true;
        }
    },

    //数据类型验证初期化
    initValidateDataType: function () {
        var thisObj = this;
        $("input[type='text']", this.getMainContentClassName()).each(function () {
            //浮点数校验
            if ($(this).data("type") == "double") {
                thisObj.bindEvent(this, "blur", thisObj.allowDouble.bind(thisObj, this));
            }
            //整数值校验
            else if ($(this).data("type") == "integer") {
                thisObj.bindEvent(this, "blur", thisObj.allowInteger.bind(thisObj, this));
            }
        });
    },

    //设置画面标题
    setTitle: function (title) {
        var select = $("title");
        var titles = select.html().split("-");

        select.html(titles[0] + "-" + title)
    }
});
//CoreInput.js类文件定义结束-------------------------------------------------------------//


//CoreImport.js类文件定义开始------------------------------------------------------------//
//定义数据导入共通JS类
SysCmn.CoreImport = Class.create();
SysCmn.CoreImport.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //详细/编辑POPUP控件表示回调函数
    showCallback: function () {

    },

    //初期化回调处理
    initCommonCallback: function () {
    },

    //点击关闭后执行
    closePopupAfter: function () {
        this.clearForm();
        this.$("divSummary").html("");
        this.$("divList").html("");
    },

    //验证后处理
    validateFormAfter: function () {
        var file = this.getValue("file");
        if (this.isEmpty(file)) {
            this.showErrorMessage("请选择需要导入的文件。");
        }
        else if (!file.endsWith(".xls") && !file.endsWith(".xlsx")) {
            this.showErrorMessage("请选择后缀名为.xls或者.xlsx的Excel文件上传。");
        }
        else {
            if (this.isEmpty(this.importConfirmMessage)) {
                this.importConfirmMessage = "确定要导入文件吗？";
            }
            this.showConfirm(this.importConfirmMessage, this.uploadFile.bind(this));
        }
        return false;
    },

    uploadFile: function () {
        //Ajax处理前显示提示画面
        this.showProcessing(false);

        //注：解决AjaxForm提交【showProcessing】不能正常显示的问题，特意延迟200MS才执行AjaxForm提交
        setTimeout(this._uploadFile.bind(this), 200);
    },

    //AjaxForm上传文件
    _uploadFile: function () {
        var thisObj = this;
        var formData = new FormData(this.getDom("MainForm"));
        $.ajax({
            url: this.saveMethod,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (response) {
                //Ajax错误处理(会话超时 OR 系统异常)
                if ($M.isAjaxFail(response)) {
                    return;
                }
                var ajaxResult = JsonUtility.Parse(response);
                if (ajaxResult.result > 0) {
                    thisObj.showInfoMessage("数据导入完成，详细结果请参照数据导入结果列表。");
                    //显示导入结果
                    var importResult = ajaxResult.content;

                    //设置导入结果汇总信息
                    thisObj.setImportResultSummaryInfo(importResult);

                    //创建导入结果表格
                    thisObj.createImportResultTable(importResult.batchImportDetailList, thisObj.showSuccessResult);
                }
                else {
                    thisObj.showErrorMessage(ajaxResult.message);
                }
            },
            error: function (result) {
                thisObj.showErrorMessage(result);
            }
        });
    },

    //设置导入结果汇总信息
    setImportResultSummaryInfo: function (importResult) {
        var summary = "更新成功：<strong class='warning-blue-cell'>" + importResult.successCount + "</strong>条记录";
        summary += "，更新失败：<strong class='warning-red-cell'>" + importResult.errorCount + "</strong>条记录";
        if (importResult.spendTime > 0) {
            summary += "，花费时间：<strong class='warning-blue-cell'>" + importResult.spendTime + "</strong>秒";
        }
        if (importResult.totalSummary > 0) {
            summary += "，总计：<strong class='warning-blue-cell'>" + importResult.totalSummary + "</strong>";
        }

        this.$("divSummary").html(summary);
    },

    //创建导入结果表格
    createImportResultTable: function (importResultList, showSuccessResult) {
        //默认为true
        if (this.isEmpty(showSuccessResult)) {
            showSuccessResult = true;
        }

        this.colList = new Array();
        this.initTableColList();

        //一览表对象实例化
        this.datagrid = new SysCmn.DataGrid();

        //不显示成功结果集，去除成功的结果集，只显示错误的结果集
        if (!showSuccessResult) {
            var importErrorResultList = [];
            for (var i = 0; i < importResultList.length; i++) {
                //错误的集合场合
                if (importResultList[i].importResultFlag != 0) {
                    importErrorResultList.push(importResultList[i]);
                }
            }
            importResultList = importErrorResultList;
        }

        this.datagrid.init(this.getId("divList"), importResultList, this.colList, this.cellCallback.bind(this));

        //所有列支持省略表示
        this.datagrid.ellipsisAllCol = true;
        this.datagrid.useRowClickEvent = false;
        this.datagrid.useCheckOne = false;
        this.datagrid.useRowNo = true;

        this.datagrid.cellClassCallback = this.cellClassCallback.bind(this);

        this.datagrid.cssClass = "app-table-list app-table-list-input";
        this.datagrid.resizeLastColumnWidth = false;
        this.datagrid.create();
    },

    cellClassCallback: function (rowData, colName, colInfo) {
        if (colName == "importResult") {
            if (rowData.importResultFlag == 0) {
                return "warning-green-background-cell";
            }
            else {
                return "warning-red-background-cell";
            }
        }
    },

    // 一览数据列处理CallBack[注意：必须实现的方法]
    cellCallback: function (rowData, colName, rowNo) {
        var strHtml = rowData[colName];

        return strHtml;
    }
});
//CoreImport.js类文件定义结束------------------------------------------------------------//


//CoreList.js类文件定义开始--------------------------------------------------------------//
//数据一览画面处理共通JS类定义
SysCmn.CoreList = Class.create();

SysCmn.CoreList.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        this.initCommonProperty();

        //一览列信息
        this.colList = [];

        //初期进入列表页面自动查询标志位
        this.autoSearch = true;

        //默认PageSize
        this.defaultPageSize = 10;

        //是否使用查询条件
        this.useSearchCondition = true;

        //是否使用查询验证功能
        this.useSearchValidate = true;

        //重新初期化表格列信息
        this.reInitTableColList = false;

        //数据集合
        this.data = {};

        //数据检索方法
        this.searchMethod = "search";

        //数据导入方法
        this.importMethod = "import";

        //自动记忆检索条件
        this.autoSaveSearchCondition = true;

        //列表查询是否包含分组查询(默认：false 不包含)
        this.includeGroupQuery = false;

        //列表查询是否包含排序查询(默认：false 不包含)
        this.includeOrderQuery = false;

        //是否使用回车查询功能
        this.useEnterSearch = true;

        //DataGrid强制横向滚动
        this.datagridForceHorizontalScroll = false;

        //是否是检索按钮按下
        this.isSearchButtonClick = true;

        //只有检索按钮按下才统计记录件数
        this.onlySearchButtonClickCountRecord = false;

        //数据列表页面标识符
        this.isListPage = true;
    },

    // 初期化处理
    init: function () {
        //保存ClientID到全局数组中
        SysCmn.ClientIDList.push(this.clientID);

        //DOM元素的ID初始化
        this.initDomId();

        //DOM元素的Name初始化
        this.initDomName();

        //一览画面可能的共通DOM ID
        var domList = ["ctlFrame", "ctlTitle", "btnSearch", "btnExport", "btnClear", "btnImport", "btnAdd", "btnDelete", "btnAudit",
            "divList", "btnSelectOk", "btnClose", "btnTopClose", "jsonListData", "btnColumnSetting", "btnSearchSetting", "btnHelp"];
        //共通DOM ID参数初期化
        this.initCommonDomIds(domList);

        //调用Core类的初期化方法
        this.initCore();

        //初期化下拉选择框
        this.initOptionList(true);

        //检索 按钮
        this.bindEvent(this.dom.btnSearch, 'click', this.onClick_Search.bind(this));

        //导出 按钮
        this.bindEvent(this.dom.btnExport, 'click', this.onClick_Export.bind(this));

        //清空 按钮
        this.bindEvent(this.dom.btnClear, 'click', this.onClick_Clear.bind(this));

        //追加 按钮
        this.bindEvent(this.dom.btnAdd, 'click', this.onClick_Add.bind(this));

        //同步/导入 按钮
        this.bindEvent(this.dom.btnImport, 'click', this.onClick_Import.bind(this));

        //删除 按钮
        this.bindEvent(this.dom.btnDelete, 'click', this.onClick_Delete.bind(this));

        //数据审核 按钮
        this.bindEvent(this.dom.btnAudit, 'click', this.onClick_Audit.bind(this));

        //Popup选择确认 按钮
        this.bindEvent(this.dom.btnSelectOk, 'click', this.onClick_SelectOk.bind(this));

        //关闭 按钮
        this.bindEvent(this.dom.btnClose, 'click', this.onClick_Close.bind(this));
        this.bindEvent(this.dom.btnTopClose, 'click', this.onClick_Close.bind(this));

        //自定义列设置 按钮
        this.bindEvent(this.dom.btnColumnSetting, "click", this.showColumnSetting.bind(this));

        //自定义检索设置 按钮
        this.bindEvent(this.dom.btnSearchSetting, "click", this.showSearchSetting.bind(this));

        //帮助 按钮
        this.bindEvent(this.dom.btnHelp, "click", this.showHelp.bind(this));

        //Popup表示的场合
        if (this.isPopupMode() && this.isNotNull(this.dom.ctlTitle)) {
            $(this.getFrame()).css("position", "absolute");

            var thisObj = this;
            $(".modal-header", this.getFrame()).each(function () {
                thisObj.bindEvent(this, 'mousedown', function (event) {
                    SysCmn.DragDrop.Start(thisObj.getFrame().id, event)
                }.bind(thisObj));
            });
        }

        //一览表对象实例化
        this.datagrid = new SysCmn.DataGrid();

        //PageSize设定
        if (this.isFunction(this.getPageSize)) {
            this.datagrid.setPageSize(this.getPageSize());
        }
        else {
            this.datagrid.setPageSize(this.defaultPageSize);
        }

        //行选择功能有效
        this.datagrid.useCheckOne = true;

        //全选择功能有效
        this.datagrid.useCheckAll = true;

        //初始化DataGrid数据列表
        this.initDataGrid();

        //恢复上次的检索条件
        this.restoreSearchCondition();

        //子画面初期化回调函数设定的场合
        if (this.isFunction(this.initCallback)) {
            this.initCallback();
        }

        //绑定popovers事件
        this.bindPopoversEvent();

        //初期化自动检索的场合
        if (this.autoSearch) {
            //一览情報を取得する
            this.getList(true);
        }
        else{
            //初期化JSON数据存在的场合
            if (this.existJsonListData()) {
                this.restoreDefaultList()
            }
        }

        if (this.autoHideFlashMessage) {
            //Flash消息自动隐藏
            this.hideFlashMessage();
        }

        //数据重复校验初期化
        this.initValidateDuplication();

        //Dom元素的PlaceHolder初始化
        this.initDomPlaceHolder();

        //初期化验证控件
        if (this.isFunction(this.initValidator)) {
            this.initValidator(true);
        }

        if (this.initCalendarFlag) {
            //日历控件初期化
            this.initCalendar();
        }

        if (this.useEnterSearch) {
            //初期化回车查询功能
            this.initEnterSearch();
        }

        //金额输入事件绑定
        this.bindMoneyInputEvent();

        //初期化自定义检索条件设定
        this.initSearchSetting();

        //绑定折叠事件
        this.bindFoldEvent();

        //绑定最大化/最小化处理事件
        this.bindMaximization();
    },

    //设置DataGrid的pageSize属性
    setPageSize: function (pageSize) {
        this.pageSize = pageSize;
        this.datagrid.setPageSize(this.pageSize);
    },

    //初始化DataGrid数据列表
    initDataGrid: function () {
        if (this.isFunction(this.initAllColList)) {
            try {
                //初期化所有备选列
                this.initAllColList();
            }
            catch (e) {
                this.showErrorMessage("initAllColList调用失败：" + e);
            }
        }

        if (this.isFunction(this.initTableColList)) {
            try {
                //初期化一览数据列信息
                this.initTableColList();
            }
            catch (e) {
                this.showErrorMessage("initTableColList调用失败：" + e);
            }
        }

        try {
            //初期化自定义列设定
            this.initColumnSetting();
        }
        catch (e) {
            this.showErrorMessage("initColumnSetting调用失败：" + e);
        }
    },

    //初期化自定义列设定
    initColumnSetting: function () {
        if (this.isNull(this.allColList) || this.isNull(this.datagrid)) {
            return;
        }

        //自定义列设定列表变量
        this.myColumnSetting = [];

        if (this.isNull(this.getDom("jsonColumnSetting"))) {
            return;
        }

        var jsonColumnSetting = this.getValue("jsonColumnSetting");
        //自定义列未设置的场合
        if (this.isEmpty(jsonColumnSetting)) {
            return;
        }

        this.myColumnSetting = JsonUtility.Parse(jsonColumnSetting);

        if (this.isNull(this.myColumnSetting)) {
            return;
        }

        var thisObj = this;
        //有自定义列设定的场合，重置DataGrid的colList变量
        if (this.myColumnSetting.length > 0) {
            this.datagrid.resizeLastColumnWidth = true;

            //临时结果
            var result = [];
            $.each(this.myColumnSetting, function () {
                var colName = this;
                $.each(thisObj.allColList, function () {
                    if (this.colName == colName) {
                        result.push(this);
                    }
                });
            });

            this.colList = result;

            this.datagrid.resizeLastColumnWidth = false;
        }
    },

    //判断指定的元素是否是检索条件
    isSearchCondition: function (element) {
        if (this.isNull(element)) return false;

        //向上查查找条件所在form div组
        var step = 1;

        element = $(element);
        while (step <= 5) {
            element = element.parent();
            if (this.isNull(element)) return false;
            var cls = element.attr("class");
            if (this.isNotNull(cls) && cls.indexOf("form-group") > -1) {
                return element;
            }
            step++;
        }
        return false;
    },

    //取得自定义检索设定JSON对象
    getJsonSearchSetting: function () {
        if (this.isNull(this.jsonSearchSetting)) {
            this.jsonSearchSetting = JsonUtility.Parse(this.getValue("jsonSearchSetting"));
        }
        return this.jsonSearchSetting;
    },

    //初期化自定义检索设定
    initSearchSetting: function () {
        //强制判断指定ClientID前缀的检索条件设置DOM对象是否存在设定值，如果无设定值，处理中止
        if (this.isEmpty(this.getValue("jsonSearchSetting", true))) {
            return;
        }
        console.log("initSearchSetting...初期化自定义检索设定开始");

        //自定义列设定列表变量
        var mainForm = this.getDom("MainForm");

        var searchIndex = 1;
        if (this.isNotNull(SysApp.Cmn.SearchSettingIns)) {
            searchIndex = SysApp.Cmn.SearchSettingIns.searchIndex;
        }
        var jsonSearchSetting = this.getJsonSearchSetting();

        if (this.isNull(jsonSearchSetting)) {
            return;
        }

        var searchSetting = jsonSearchSetting[searchIndex - 1] || {};
        var parameters = JsonUtility.Parse(searchSetting.parameters || []);
        if (parameters.length == 0) {
            return;
        }

        var thisObj = this;
        $.each(mainForm.select("input[type='text'], select"), function (index, item) {
            var element = thisObj.isSearchCondition(item);
            //是检索条件的场合
            if (element) {
                //先隐藏检索条件
                element.css("display", "none");
                var showCondition = false;
                $.each(parameters, function (searchIndex, colName) {
                    if (colName == "search_" + $(item).attr("id")) {
                        showCondition = true;
                        //如果有设置则显示条件
                        element.css("display", "block");
                    }
                });

                //隐藏的检索条件的场合，清空检索条件值
                if (showCondition == false) {
                    $(item).val("");
                }
            }
        });

        //自动隐藏空白条件的标签头
        $.each(mainForm.select(".popovers > button"), function (index, button) {
            var container = thisObj.getDom($(button).data("container"));
            if (container.select("div.form-group[style='display: block;']").length == 0) {
                //隐藏标签
                $(button).parent().parent().css("display", "none");
                $(container).css("display", "none");
            } else {
                $(button).parent().parent().css("display", "block");
                //非强制折叠的场合
                if ($(container).data("force-fold") != true) {
                    $(container).css("display", "block");
                }
            }
        });
        console.log("initSearchSetting...初期化自定义检索设定结束");
    },

    //显示自定义检索条件设置
    showSearchSetting: function () {
        SysApp.Cmn.SearchSettingIns.show();
    },

    //自定义列设置
    showColumnSetting: function () {
        SysApp.Cmn.ColumnSettingIns.show();
    },

    //恢复检索条件
    restoreSearchCondition: function () {
        var searchCondition = $E(this.getClientID() + "_jsonSearchCondition");
        //上次检索条件Dom对象不存在的场合
        if (this.isNull(searchCondition)) {
            return;
        }
        //上次检索条件Dom值不存在的场合
        if (this.isEmpty(searchCondition.value)) {
            return;
        }
        var json = JsonUtility.Parse(searchCondition.value);

        var thisObj = this;
        //Json对象循环处理，将各检索条件的值赋予到画面Dom中
        $.each(json, function (key, val) {
            var aliasTable = "";
            //别名存在的场合，过滤别名
            if (key.indexOf(".") != -1) {
                aliasTable = key.substr(0, key.indexOf("."));
                key = key.substr(key.indexOf(".") + 1);
            }

            var searchMode = "";
            //检索模式存在的场合，提取检索模式
            if (key.indexOf("$") != -1) {
                var info = key.split("$");
                key = info[0];
                searchMode = info[1];
            }

            //根据Key穷举所有可能的检索条件DomID
            var domArray = [];
            //无前缀Key
            domArray.push(key);

            //带别名的无前缀Key[如：supplier_company.name]
            if (!thisObj.isEmpty(aliasTable)) {
                //有别名前缀[如：supplier_company.name]
                domArray.push(aliasTable + "." + key);
            }

            //检索模式存在的场合
            if (!thisObj.isEmpty(searchMode)) {
                //有别名前缀[如：name$=]
                domArray.push(key + "$" + searchMode);

                //有别名前缀[如：supplier_company.name$=]
                domArray.push(aliasTable + "." + key + "$" + searchMode);
            }

            //检索条件Dom赋值
            $.each(domArray, function (index, domId) {
                var searchDom = thisObj.getDom(domId);

                if (!$M.isNull(searchDom)) {
                    var allowRestore = $(searchDom).data("allow-restore");
                    //allow-restore指定的场合，优先判断allow-restore设定的值。
                    //不允许恢复的场合，处理中止
                    if (allowRestore == false) {
                        return;
                    }
                    //允许恢复的场合，恢复值后处理中止
                    else if (allowRestore == true) {
                        thisObj.restoreValue(searchDom, val);
                        return;
                    }

                    //隐藏控件的值不恢复
                    if (searchDom.type == "hidden") {
                        return;
                    }

                    //无效化控件不恢复
                    if (searchDom.disabled) {
                        return;
                    }
                    thisObj.restoreValue(searchDom, val);
                }
            });
        });
    },

    restoreValue: function (dom, val) {
        if (dom.type == "checkbox" || dom.type == "radio") {
            if (this.isNotEmpty(val) && val != 0) {
                dom.checked = true;
            }
            else {
                dom.checked = false;
            }
        }
        else if (dom.type == "select-multiple") {
            if (this.isNotEmpty(val)) {
                if (val.indexOf(",") == 0) {
                    val = val.substring(1);
                }
                var valueArray = val.split(",");
                $(dom).val(valueArray);
            }
        }
        else {
            $(dom).val(val);
        }
    },

    //表示处理
    show: function (callback) {
        this.callback = callback;

        //Popup表示的场合
        if (this.isPopupMode()) {
            //页面切换模式的场合
            if (this.popupSwitchPage == true && this.isNotNull(this.parentFrame)) {
                //显示子画面
                this.switchPage(true);
            }
            else {
                SysCmn.CtrlUtil.SetPopupPosition(this.getFrame(), this.popupPosition);
                SysCmn.EstopLayer.show(this.getFrame());
                $(this.getFrame()).fadeIn();
            }
        }

        //关闭按钮存在的场合
        if (this.isNotNull($E(this.dom.btnClose))) {
            $E(this.dom.btnClose).focus();
        }

        //初期表示回调函数设定的场合
        if (this.isFunction(this.showCallback)) {
            this.showCallback();
        }
    },

    //刷新数据表格
    refreshDataGrid: function () {
        try {
            this.datagrid.clearCheck();

            //一览作成
            this.datagrid.create();
        }
        catch (e) {
        }
    },

    //检索处理
    onClick_Search: function () {
        if (this.useSearchValidate && this.validateForm() == false) {
            return false;
        }

        //自定义检索的场合
        if (this.isFunction(this.customSearch)) {
            //检索画面Dom值自定义设定
            if (this.isFunction(this.customDomValue)) {
                this.customDomValue();
            }

            this.customSearch();
        }
        else {
            this.getList(true);
        }
    },

    //导出处理
    onClick_Export: function () {
        //无检索记录的场合，提示错误消息后处理中止
        if (!this.hasListData()) {
            this.showErrorMessage("没有相关数据，请重新调整检索条件后再导出数据。");
            return;
        }

        var totalRecord = 0;
        //带Pager分页的场合，取得总记录数
        if (this.isNotNull(this.datagrid.pager) && this.isNotNull(this.datagrid.pager.data) && this.datagrid.pager.data.totalElements > 0) {
            totalRecord = this.datagrid.pager.data.totalElements;
        }
        else {
            totalRecord = this.datagrid.data.length;
        }

        this.showConfirm("将导出<b>&nbsp;" + totalRecord + "&nbsp;</b>条数据，导出数据需要等待一定的处理时间，<br/>确定要导出数据吗？", this.exportData.bind(this));
    },

    //是否有列表数据
    hasListData: function () {
        if (this.isNotNull(this.datagrid) && this.isNotNull(this.datagrid.data) && this.datagrid.data.length > 0) {
            return true;
        }

        return false;
    },

    //数据导出处理
    exportData: function () {
        this.hideMessage();

        if (this.useSearchValidate && this.validateForm() == false) {
            return false;
        }

        //检索画面Dom值自定义设定
        if (this.isFunction(this.customDomValue)) {
            this.customDomValue();
        }

        if (this.isFunction(this.initTableColList)) {
            this.colList = [];
            //初期化默认数据列
            this.initTableColList();
        }

        if (this.isFunction(this.initAllColList)) {
            this.allColList = [];
            //初期化所有备选列
            this.initAllColList();
        }

        this.output();
    },

    //添加处理
    onClick_Add: function () {
        if (this.validateInputObject() == false) {
            return;
        }

        if (this.isFunction(this.validateAddBefore)) {
            if (this.validateAddBefore() == false) {
                return;
            }
        }

        //自定义添加处理
        if (this.isFunction(this.customAdd)) {
            this.customAdd();
            return;
        }

        //POPUP控件
        if (this.isEmpty(this.inputUrl)) {
            this.goInput();
        }
        //输入画面
        else {
            this.showPageLoader();
            window.location = this.inputUrl;
        }
    },

    //数据导入/数据同步
    onClick_Import: function () {
        //直接数据同步的场合
        if (this.isEmpty(this.importUrl)) {
            this.showConfirm("确定要同步数据吗？", this.importData.bind(this));
        }
        //数据同步画面存在的场合
        else {
            this.showPageLoader();
            window.location = this.importUrl;
        }
    },

    //数据导入/数据同步
    importData: function () {
        if (this.showProcessingMessage) {
            //Ajax处理前显示提示画面
            this.showProcessing(false);
        }

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller, this.importDataCallback.bind(this), null, this.importMethod);
    },

    //数据同步后处理
    importDataCallback: function (response) {
        //系统异常判断
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //显示处理结果消息
        this.showMessage(ajaxResult.message, MSG_TYPE_INFO, this.refreshList.bind(this));
    },

    //删除处理
    onClick_Delete: function (index, tableId) {
        //操作列行删除
        if (index >= 0) {
            var json = null;
            if (this.isNull(tableId)) {
                json = this.datagrid.data[index];
            }
            else {
                json = SysCmn.DataGridList[tableId].data[index];
            }

            var keyValue = this.getPkValue(json, this.getPrimaryKey());

            this.showConfirm(this.deleteConfirmMessage, this.deleteData.bind(this, keyValue));
        }
        //批量删除
        else {
            if (this.datagrid.selectedIndexs.length == 0 && this.datagrid.selectedIndex == -1) {
                this.showMessage("请从列表数据中至少选择一条需要删除的数据。", MSG_TYPE_WARNING);
            }
            else {
                this.showConfirm(this.deleteConfirmMessage, this.deleteData.bind(this));
            }
        }
    },

    //列保存
    onClick_RowSave: function (rowNo) {
        this.showErrorMessage("请在子类列表JS中实现onClick_RowSave方法。");
    },

    //审核处理
    onClick_Audit: function () {
        if (this.datagrid.selectedIndexs.length == 0 && this.datagrid.selectedIndex == -1) {
            this.showMessage("请选择需要审核的数据", MSG_TYPE_WARNING);
        }
        else {
            this.showConfirm("确定要审核通过吗？", this.auditData.bind(this, 1));
        }
    },

    //选择确认按钮处理事件[注意：Popup选择控件用方法]
    onClick_SelectOk: function () {
        if (this.datagrid.selectedIndex < 0 && this.datagrid.selectedIndexs.length == 0) {
            //必须选择消息
            this.showMessage("请选择数据。", MSG_TYPE_WARNING);
            return;
        }
        this.hide();

        if (this.isFunction(this.callback)) {
            //单选
            if (this.datagrid.selectedIndex != -1) {
                this.callback(this.datagrid.getSelectedRow());
            }
            //多选
            else {
                this.callback(this.datagrid.getSelectedRows());
            }
        }
    },

    //数据输入对象验证
    validateInputObject: function () {
        if (this.isEmpty(this.inputInstance) && this.isEmpty(this.inputUrl)) {
            alert("请设定this.inputInstance对象或this.inputUrl参数。");
            return false;
        }
        return true;
    },

    //Popup详细画面表示
    showDetail: function (index, tableId) {
        var json = this.getRowData(index, tableId);

        //行数据设定
        this.detailInstance.inputEntity = $.extend(true, {}, json);

        //详细表示标志位
        this.detailInstance.isDetail = true;

        if (this.isNotNull(this.getFrame())) {
            this.detailInstance.parentFrame = this.getFrame();
        }
        if (this.isNotNull(this.parentFrame)) {
            this.detailInstance.parentFrame = this.getDom(this.parentFrame);
        }
        this.detailInstance.show(this.refreshList.bind(this), 1);
    },

    //详细画面迁移
    goDetail: function (index, tableId) {
        if (this.validateInputObject() == false) {
            return;
        }
        var json = this.getRowData(index, tableId);

        var keyValue = this.getPkValue(json, this.getPrimaryKey());

        this.showPageLoader();
        window.location = this.detailUrl + "?uid=" + keyValue;
    },

    //Popup输入画面表示 OR 输入画面迁移
    goInput: function (index, detail, tableId) {
        if (this.validateInputObject() == false) {
            return;
        }
        if (this.isNull(detail)) {
            detail = 0;
        }
        var json = this.getRowData(index, tableId);

        //POPUP模式
        if (this.isNotEmpty(this.inputInstance)) {
            //行数据设定
            this.inputInstance.inputEntity = $.extend(true, {}, json);
            //列表画面JS对象
            this.inputInstance.listInstance = this;
            //当前编辑数据索引位置
            this.inputInstance.listDataIndex = index;

            //详细表示标志位
            this.inputInstance.isDetail = (detail == 1);

            var editMode = 0;
            if (this.isNotNull(index) && index >= 0) {
                editMode = 1;
            }

            //Entity中的editMode保持与全局editMode一致
            this.inputInstance.inputEntity.editMode = editMode;

            //自定义输入画面参数
            if (this.isFunction(this.customInputParams)) {
                this.customInputParams(editMode);
            }

            if (this.isNotNull(this.getFrame())) {
                this.inputInstance.parentFrame = this.getFrame();
            }
            if (this.isNotNull(this.parentFrame)) {
                this.inputInstance.parentFrame = this.getDom(this.parentFrame);
            }
            this.inputInstance.show(this.refreshList.bind(this), editMode);
        }
        //画面模式
        else {
            var keyValue = this.getPkValue(json, this.getPrimaryKey());
            var audit = "";
            //审核模式的场合
            if (detail == 2) {
                detail = 1;
                audit = "&audit=1"
            }

            this.showPageLoader();
            if (this.inputUrl.indexOf("?") == -1) {
                window.location = this.inputUrl + "?uid=" + keyValue + "&detail=" + detail + audit;
            }
            else {
                window.location = this.inputUrl + "&uid=" + keyValue + "&detail=" + detail + audit;
            }
        }
    },

    //取得Table行数据
    getRowData: function (index, tableId) {
        if (this.isNull(tableId)) {
            return this.datagrid.data[index];
        }
        else {
            return SysCmn.DataGridList[tableId].data[index];
        }
    },

    //刷新一览画面
    refreshList: function () {
        this.getList(false);
    },

    //取得检索条件
    getSearchCondition: function () {
        return this.getSearchEntity();
    },

    //日期检索字段后拼接时分秒
    prepareSearchEntityDateTime: function (searchEntityField, datetime) {
        if (this.isNotEmpty(this.searchEntity[searchEntityField]) && this.searchEntity[searchEntityField].length == 10) {
            this.searchEntity[searchEntityField] += " " + datetime;
        }
    },

    //取得检索一览数据[Ajax方式]
    getList: function (isSearchButtonClick, showProcessing) {
        this.isSearchButtonClick = isSearchButtonClick;

        if (this.isNull(this.isSearchButtonClick)) {
            this.isSearchButtonClick = false;
        }

        if (this.isEmpty(showProcessing)) {
            showProcessing = true;
        }

        //检索画面Dom值自定义设定
        if (this.isFunction(this.customDomValue)) {
            this.customDomValue();
        }

        //初期化JSON数据存在的场合
        if (this.existJsonListData()) {
            this.restoreDefaultList()
            return;
        }

        //使用检索条件的场合
        if (this.useSearchCondition) {
            //再检索的场合
            if (this.isSearchButtonClick) {
                this.searchEntity = this.getSearchCondition();

                //原始检索条件实体对象【解决customSearchEntity中检索条件被重置后，分页排序时出现 查询条件被修改 的提示消息】
                this.originalSearchEntity = jQuery.extend(true, {}, this.searchEntity);

                //再检索场合、分页対象的PageNum重置
                this.datagrid.resetPageNum();
            }
            //分页 OR 排序的场合
            else if (this.isConditionChanged(this.originalSearchEntity, this.getSearchCondition())) {
                return;
            }
        }

        //检索条件数据模型自定义设定
        if (this.isFunction(this.customSearchEntity)) {
            this.customSearchEntity();
        }

        //检索条件
        var params = this.getSearchCommonParams(this.datagrid) +
            "&autoSaveSearchCondition=" + this.autoSaveSearchCondition +
            "&isSearchButtonClick=" + this.isSearchButtonClick +
            "&onlySearchButtonClickCountRecord=" + this.onlySearchButtonClickCountRecord +
            "&selfInstance=" + this.selfInstance;

        //客户化参数
        if (this.isFunction(this.customRequestParams)) {
            params = params + "&" + this.customRequestParams();
        }

        if (showProcessing && this.showProcessingMessage) {
            //Ajax处理前显示提示画面
            this.showProcessing();
        }
        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + this.searchMethod, this.getListCallback.bind(this, showProcessing), params);
    },

    //一览JSON数据是否存在？
    existJsonListData: function () {
        //初期化JSON数据存在的场合
        if (this.isNotNull($E(this.dom.jsonListData)) && this.isNotEmpty(this.getValue(this.dom.jsonListData))) {
            return true;
        }
        return false;
    },

    //恢复从编辑画面返回一览画面的默认查询列表
    restoreDefaultList: function () {
        this.searchEntity = this.getSearchCondition();

        //原始检索条件实体对象【解决customSearchEntity中检索条件被重置后，分页排序时出现 查询条件被修改 的提示消息】
        this.originalSearchEntity = jQuery.extend(true, {}, this.searchEntity);

        this.createList(this.getValue(this.dom.jsonListData));
        this.setValue(this.dom.jsonListData, "");
    },

    //一览取得用[Callback后处理]
    getListCallback: function (showProcessing, response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }
        //表格创建成功的场合
        if (this.createList(response.responseText)) {
            if (showProcessing && this.showProcessingMessage) {
                //Ajax处理结束后隐藏提示画面
                this.hideMessage();
            }
            //POPUP控件的场合，居中表示
            if (this.isPopupMode() && this.isNull(this.parentFrame)) {
                if (this.windowState == "normal" && this.popupPosition != "top") {
                    SysCmn.CtrlUtil.SetPopupPosition(this.getFrame(), this.popupPosition);
                }
            }
        }

        //表格创建后处理
        if (this.isFunction(this.createListAfter)) {
            this.createListAfter();
        }
    },

    //重新创建检索一览数据
    reCreateList: function () {
        this.colList = [];

        //初始化DataGrid数据列表
        this.initDataGrid();

        this.createList(this.data);
    },

    //创建检索一览数据
    createList: function (jsonData) {
        var json = jsonData;
        if (typeof jsonData == "string") {
            json = JsonUtility.Parse(jsonData);
        }

        //Ajax错误的场合[权限验证等错误]
        if (this.isNotEmpty(json.result) && this.isNotEmpty(json.message)) {
            this.showMessage(json.message, MSG_TYPE_ERROR);
            return false;
        }

        //保存数据集合
        this.data = json;

        //表格创建前处理
        if (this.isFunction(this.createListBefore)) {
            this.createListBefore();
        }

        //重新初期化表格列信息
        if (this.reInitTableColList) {
            //初始化DataGrid数据列表
            this.initDataGrid();
        }

        //一览初期化
        if (this.isFunction(this.cellCallback)) {
            this.datagrid.init(this.dom.divList, json, this.colList, this.cellCallback.bind(this));
        }
        else {
            this.datagrid.init(this.dom.divList, json, this.colList);
        }

        //CheckOne无效验证
        if (this.isFunction(this.createCheckboxDisabledCallback)) {
            this.datagrid.createCheckboxDisabledCallback = this.createCheckboxDisabledCallback.bind(this);
        }

        //自定义创建一览表格的场合
        if (this.isFunction(this.customCreateList)) {
            this.customCreateList(json);
        }
        else {
            //一览作成
            this.datagrid.create();
        }

        //分页处理
        this.datagrid.changePageCallback = this.getList.bind(this);

        //排序处理
        this.datagrid.sortDataCallback = this.getList.bind(this);

        //允许拖放列宽的场合
        if (this.datagrid.allowResizeColumnWidth) {
            initDataGridColumnResizeEvent(this.datagrid.tableId);
        }

        //DataGrid强制横向滚动的场合
        if (this.datagridForceHorizontalScroll) {
            this.$("divDataGridScroll").addClass("data-grid-scroll");
        }
        else if (this.isNotNull(this.myColumnSetting)) {
            //有自定义列设置的场合，显示表格横向滚动条
            if (this.myColumnSetting.length > 0) {
                this.$("divDataGridScroll").addClass("data-grid-scroll");
            }
            else {
                this.$("divDataGridScroll").removeClass("data-grid-scroll");
            }
        }

        //明细金额输入事件绑定
        this.bindMoneyInputEvent();

        //绑定popovers事件
        this.bindPopoversEvent();

        return true;
    },

    //创建列表编辑图标
    createDetailIcon: function (rowNo) {
        var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='详细'>";
        html += "<i class='fa fa-list fa-lg fa-blue fa-width-fixed' onclick='" + this.selfInstance + ".goInput(" + rowNo + ", 1);return false;'></i></a>";
        html += "</span>";
        return html;
    },

    //创建列表编辑图标
    createEditIcon: function (rowNo) {
        var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='编辑'>";
        html += "<i class='fa fa-pencil fa-lg fa-blue fa-width-fixed' onclick='" + this.selfInstance + ".goInput(" + rowNo + ");return false;'></i></a>";
        html += "</span>";
        return html;
    },

    //创建列表删除图标
    createDeleteIcon: function (rowNo) {
        var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='删除'>";
        html += "<i onclick='" + this.selfInstance + ".onClick_Delete(" + rowNo + ");return false;' class='fa fa-trash-o fa-lg fa-blue fa-width-fixed'></i>";
        html += "</span>";
        return html;
    },

    //创建列保存图标
    createSaveIcon: function (rowNo) {
        var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='保存'>";
        html += "<i onclick='" + this.selfInstance + ".onClick_RowSave(" + rowNo + ");return false;' class='fa fa-save fa-lg fa-green fa-width-fixed'></i>";
        html += "</span>";
        return html;
    },

    //创建列表停用图标
    createLockIcon: function (rowNo) {
        var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='停用'>";
        html += "<i class='fa fa-unlock fa-lg fa-blue fa-width-fixed' onclick='" + this.selfInstance + ".onClick_UpdateStatus(" + rowNo + ");return false;'></i></a>";
        html += "</span>";
        return html;
    },

    //创建列表启用图标
    createUnlockIcon: function (rowNo) {
        var html = "<span class='popovers' data-trigger='hover' data-placement='top' data-content='启用'>";
        html += "<i class='fa fa-lock fa-lg fa-red fa-width-fixed' onclick='" + this.selfInstance + ".onClick_UpdateStatus(" + rowNo + ");return false;'></i></a>";
        html += "</span>";
        return html;
    },

    //创建编辑按钮
    createEditButton: function (rowNo, disabled, className) {
        if (disabled == true) {
            return "<button type='button' class='btn btn-default" + this.getButtonClass(className) + "' disabled='true'><i class='fa fa-pencil fa-width-fixed'></i>编辑</button>";
        }
        return "<button type='button' class='btn btn-primary" + this.getButtonClass(className) + "' onclick='" + this.selfInstance + ".goInput(\"" + rowNo + "\");return false;'><i class='fa fa-pencil fa-width-fixed'></i>编辑</button>";
    },

    //创建删除按钮
    createDeleteButton: function (rowNo, disabled, className) {
        if (this.isEmpty(className)) {
            className = "delete";
        }

        if (className != "delete") {
            className += " delete"
        }

        if (disabled == true) {
            return "<button type='button' class='btn btn-danger" + this.getButtonClass(className) + "' disabled='true'><i class='fa fa-trash-o fa-width-fixed'></i>删除</button>";
        }
        return "<button type='button' class='btn btn-danger" + this.getButtonClass(className) + "' onclick='" + this.selfInstance + ".onClick_Delete(" + rowNo + ");return false;'><i class='fa fa-trash-o fa-width-fixed'></i>删除</button>";
    },

    //创建详细按钮
    createDetailButton: function (rowNo, disabled, className, buttonText) {
        if (this.isEmpty(buttonText)) {
            buttonText = "详细";
        }
        if (disabled == true) {
            return "<button type='button' class='btn btn-default" + this.getButtonClass(className) + "' disabled='true'><i class='fa fa-list fa-width-fixed'></i>" + buttonText + "</button>";
        }
        return "<button type='button' class='btn btn-default" + this.getButtonClass(className) + "' onclick='" + this.selfInstance + ".goInput(\"" + rowNo + "\", 1);return false;'><i class='fa fa-list fa-width-fixed'></i>" + buttonText + "</button>";
    },

    //创建列表停用按钮
    createLockButton: function (rowNo, disabled, className) {
        if (disabled == true) {
            return "<button type='button' class='btn btn-danger" + this.getButtonClass(className) + "' disabled='true'><i class='fa fa-unlock fa-width-fixed'></i>停用</button>";
        }
        return "<button type='button' class='btn btn-danger" + this.getButtonClass(className) + "' onclick='" + this.selfInstance + ".onClick_UpdateStatus(" + rowNo + ");return false;'><i class='fa fa-unlock fa-width-fixed'></i>停用</button>";
    },

    getButtonClass: function (className) {
        //在样式单前追加一个空格
        return this.isEmpty(className) ? "" : (" " + className);
    },

    //创建列表启用按钮
    createUnlockButton: function (rowNo, disabled, className) {
        if (this.isEmpty(className)) {
            className = "";
        }

        if (disabled == true) {
            return "<button type='button' class='btn btn-default' disabled='true'><i class='fa fa-lock fa-width-fixed'></i>启用</button>";
        }
        return "<button type='button' class='btn btn-default' onclick='" + this.selfInstance + ".onClick_UpdateStatus(" + rowNo + ");return false;'><i class='fa fa-lock fa-width-fixed'></i>启用</button>";
    },

    //创建按钮组
    createGroupButton: function (rowNo, groupButton, className) {
        //按钮组不为空场合
        if (this.isNotEmpty(groupButton)) {
            var rowData = this.datagrid.getRowData(rowNo);

            //分组按钮HTML
            rowData.groupButton = groupButton;

            return "<button type='button' onmouseover='" + this.selfInstance + ".onMouseOver_GroupButton(" + rowNo + ", this);return false;' " +
                "onmouseout='" + this.selfInstance + ".onMouseOut_GroupButton(" + rowNo + ", this);return false;' class='btn btn-primary operation-group-button" + this.getButtonClass(className) + "'>" +
                "<i class='fa fa-cogs fa-width-fixed'></i>&nbsp;操作&nbsp;&nbsp;<span class='caret'></span></button>";
        }
        return "";
    },

    //鼠标经过操作按钮弹出GroupButton
    onMouseOver_GroupButton: function (rowNo, obj) {
        //获取表格行数据
        var rowData = this.datagrid.getRowData(rowNo);
        //添加操作按钮组
        $("#groupButtonDiv").html(rowData.groupButton);
        //显示
        $(".operation-column-group-button").show();

        //操作按钮位置高度
        var promptTop = $(obj).offset().top;

        //操作按钮左侧边距
        var promptLeft = $(obj).offset().left;

        //屏幕高度
        var windowHeight = $(window).height();

        //按钮组高度
        var groupButtonHeight = $("#groupButtonDiv").height();

        $(".operation-column-group-button").css('top', promptTop + 22);
        $(".operation-column-group-button").css('left', promptLeft - 6);

        //按钮组高度+按钮位置高度 大于屏幕高度场合
        if ((promptTop + 22 + groupButtonHeight) > windowHeight) {
            //按钮组朝上显示
            $(".operation-column-group-button").css('top', promptTop - groupButtonHeight);
        }
        $(".operation-cross").css('top', promptTop - 5);
    },

    //离开操作按钮，弹层隐藏
    onMouseOut_GroupButton: function () {
        $(".operation-column-group-button").hide();
    },

    //创建详细POPUP表示的超链接
    createDetailLink: function (rowData, colName, rowNo) {
        //详细控件实例不存在的场合
        if (this.isNull(this.detailInstance)) {
            return rowData[colName].escapeHTML();
        }
        else {
            return "<a href='#' class='detail-link' onclick='" + this.selfInstance + ".showDetail(" + rowNo + ");return false;'>" + rowData[colName].escapeHTML() + "</a>";
        }
    },

    //创建备注列
    createRemarkColumn: function (rowData, colName, popoversTitle) {
        if (this.isEmpty(colName)) {
            colName = "remark";
        }

        if (this.isEmpty(popoversTitle)) {
            popoversTitle = "备注";
        }

        var strHtml = "";
        if (this.isNotEmpty(rowData[colName])) {
            strHtml = "<span class='popovers' data-trigger='hover' data-placement='left' data-content='" + $M.enterToBr(rowData[colName].escapeHTML()) + "' data-original-title='" + popoversTitle + "'>";
            strHtml += "<i class='fa fa-comment fa-lg fa-blue'></i>";
            strHtml += "</span>";
        }
        return strHtml;
    },

    //停用/启用事件处理
    onClick_UpdateStatus: function (rowNo) {
        var json = this.datagrid.data[rowNo];
        if (json.recordStatus == 1) {
            this.showConfirm("确定要停用吗？", this.updateStatus.bind(this, json));
        }
        else {
            this.showConfirm("确定要启用吗？", this.updateStatus.bind(this, json));
        }
    },

    //停用/启用处理
    updateStatus: function (json) {
        var params = "uid=" + json.uid + "&recordStatus=" + json.recordStatus;

        if (this.showProcessingMessage) {
            //Ajax处理前显示提示画面
            this.showProcessing(false);
        }

        //Ajax处理(POST方式)
        AjaxIns.sendPOST(this.controller + "updateStatus", this.updateStatusCallback.bind(this), params);
    },

    //停用/启用处理[Callback后处理]
    updateStatusCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理成功的场合、显示处理结果消息后刷新一览画面
        if (ajaxResult.result > 0) {
            this.refreshList()
        }
        else {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
    }
});
//CoreList.js类文件定义结束--------------------------------------------------------------//


//CoreUtility.js类文件定义开始-----------------------------------------------------------//
SysCmn.CoreUtility = function () {
}

SysCmn.CoreUtility.isDateField = function (dom) {
    //日期控件的场合
    if ($(dom).data("date") == true) {
        return true;
    }
    return false;
}

SysCmn.CoreUtility.isDateTimeField = function (dom) {
    //日期控件的场合
    if ($(dom).data("datetime") == true) {
        return true;
    }
    return false;
}

//取得数据库表别名称
SysCmn.CoreUtility.getAliasTableName = function (obj) {
    //テーブル別名
    var aliasTable = $(obj).data("alias-table");

    if ($M.isEmpty(aliasTable)) {
        aliasTable = "";
    }
    else {
        aliasTable = aliasTable + ".";
    }

    return aliasTable;
}

//取得驼峰字段标识
SysCmn.CoreUtility.getCamelFieldFlag = function (obj) {
    //驼峰字段
    var camelField = $(obj).data("camel-field");

    if ($M.isEmpty(camelField) || camelField == false || camelField == "false") {
        camelField = "";
    }
    else {
        camelField = COL_CAMEL_FLAG;
    }

    return camelField;
}

//取得DB字段名称
SysCmn.CoreUtility.getFieldName = function (clientId, domId) {
    clientId = clientId + "_";

    //删除控件中的ClientId
    return domId.replaceAll(clientId, "");
}

SysCmn.CoreUtility.isLabel = function (obj) {
    try {
        var tagName = obj.tagName.toUpperCase();
        if (tagName == "LABEL" || tagName == "SPAN" || tagName == "P" || tagName == "DIV" || tagName == "EM" || tagName == "TD") {
            return true;
        }
    }
    catch (e) {
    }
    return false;
}

SysCmn.CoreUtility.isInputMoney = function (obj) {
    try {
        var tagName = obj.tagName.toUpperCase();
        if (tagName == "LABEL" || tagName == "SPAN" || tagName == "P" || tagName == "DIV" || tagName == "EM" || tagName == "TD") {
            return false;
        }

        if ($(obj).data("money") == true) {
            return true;
        }
    }
    catch (e) {
    }
    return false;
}

//指定值设定到DOM对象中
SysCmn.CoreUtility.setValue = function (obj, value) {
    if ($M.isNull(obj)) {
        return false;
    }

    var isLabel = SysCmn.CoreUtility.isLabel(obj);

    if (!$M.isNull(value) && typeof value == "string") {
        value = $.trim(value);
    }

    //日期的场合
    if (SysCmn.CoreUtility.isDateField(obj)) {
        if (isLabel) {
            obj.innerHTML = $M.isNull(value) ? "" : value.formatYMD();
        }
        else {
            obj.value = $M.isNull(value) ? "" : value.formatYMD();
        }
    }
    else if (SysCmn.CoreUtility.isDateTimeField(obj)) {
        if (isLabel) {
            obj.innerHTML = $M.isNull(value) ? "" : value.formatYMDHMS();
        }
        else {
            obj.value = $M.isNull(value) ? "" : value.formatYMDHMS();
        }
    }
    else if ($(obj).data("money") == true) {
        var precision = $(obj).data("precision");
        if (isLabel) {
            obj.innerHTML = $M.formatCny(value, precision);
        }
        else {
            obj.value = $M.formatCny(value, precision);
        }
    }
    else {
        if (isLabel) {
            if ($(obj).data("textarea") == true) {
                obj.innerHTML = $M.enterToBr(value);
            }
            else {
                obj.innerHTML = value;
            }
        }
        else {
            if (obj.type == "select-one") {
                $(obj).val(value);
            }
            else if (obj.type == "select-multiple") {
                if (value.indexOf(",") == 0) {
                    value = value.substring(1);
                }
                $(obj).val(value.split(","));
            }
            else {
                $(obj).val(value);
            }
        }
    }

    if (isLabel && $M.isEmpty(obj.innerHTML)) {
        obj.innerHTML = "&nbsp;";
    }

    return true;
}
//CoreUtility.js类文件定义结束-----------------------------------------------------------//


//CmnMessage.js类文件定义开始------------------------------------------------------------//
//種別：エラー
var MSG_TYPE_ERROR = "-1";

//種別：警告
var MSG_TYPE_WARNING = "0";

//種別：インフォメーション
var MSG_TYPE_INFO = "1";

//種別：処理中
var MSG_TYPE_PROCESSING = "2";

//種別：検証メッセージ
var MSG_TYPE_VALIDATION = "3";

//種別：確認メッセージ
var MSG_TYPE_CONFIRM = "4";

//メッセージコントロール用のJSクラスを定義する
SysCmn.CmnMsgCls = Class.create();

SysCmn.CmnMsgCls.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        this.width = 550;

        //「はい」処理Callbackイベント
        this.yesCallback = null;

        //「いいえ」処理Callbackイベント
        this.noCallback = null;

        //「キャンセル」処理Callbackイベント
        this.cancelCallback = null;

        //「閉じる」処理Callbackイベント
        this.closeCallback = null;

        //共通Property初期化
        this.initCommonProperty();
    },

    //初期化処理
    init: function () {
        this.dom.ctlFrame = $E("ctlCmnMessageFrame");
        this.dom.ctlTitle = $E("ctlCmnMessageTitle");
        this.dom.ctlTitleName = $E("ctlCmnMessageTitleName");
        this.dom.btnYes = $E("btnYesForCmnMessage");
        this.dom.btnNo = $E("btnNoForCmnMessage");
        this.dom.btnCancel = $E("btnCancelForCmnMessage");
        this.dom.btnClose = $E("btnCloseForCmnMessage");
        this.dom.divMessage = $E("divCmnMessageMessage");
        this.dom.tdCmnMessageType = $E("tdCmnMessageType");

        this.dom.divCmnMessageFooter = $E("divCmnMessageFooter");

        $(this.getFrame()).css("position", 'absolute');

        this.bindEvent(this.dom.btnYes, 'click', this.onClick_Yes.bind(this));
        this.bindEvent(this.dom.btnNo, 'click', this.onClick_No.bind(this));
        this.bindEvent(this.dom.btnCancel, 'click', this.onClick_Cancel.bind(this));
        this.bindEvent(this.dom.btnClose, 'click', this.onClick_Close.bind(this));
        this.bindEvent($E("btnTopCloseForCmnMessage"), 'click', this.onClick_Close.bind(this));

        this.bindEvent(this.dom.ctlTitle, 'mousedown', function (event) {
            SysCmn.DragDrop.Start(this.getFrame().id, event)
        }.bind(this));
    },

    //閉じる処理
    onClick_Close: function () {

        this.hide();

        if (typeof this.closeCallback == "function") {
            //コールバックメソッドを実行する
            this.closeCallback();

            //クリアする
            this.closeCallback = null;
        }
        return false;
    },

    //「はい」ボタン処理
    onClick_Yes: function () {
        if (typeof this.yesCallback == "function") {
            //コールバックメソッドを実行する
            this.yesCallback();

            //クリアする
            this.yesCallback = null;
        }
    },

    //「いいえ」ボタン処理
    onClick_No: function () {
        this.hide();
        if (typeof this.noCallback == "function") {
            //コールバックメソッドを実行する
            this.noCallback();

            //クリアする
            this.noCallback = null;
        }
        return false;
    },

    //「キャンセル」ボタン処理
    onClick_Cancel: function () {

        this.hide();
        if (typeof this.cancelCallback == "function") {
            //コールバックメソッドを実行する
            this.cancelCallback();

            //クリアする
            this.cancelCallback = null;
        }
        return false;
    },

    //表示処理
    showMessage: function (msg, msgType, closeCallback) {
        $($E("btnTopCloseForCmnMessage")).show();

        //共通消息提示功能无效化的场合，处理中止
        if (window.processingMessageDisabled == true) {
            return;
        }

        msg = $M.enterToBr(msg);
        var ua = navigator.userAgent.toLowerCase();
        //解决iphone上微信浏览器，错误消息不可见的问题
        if ((ua.match(/iphone/i) == "iphone" || ua.match(/ipad/i) == "ipad") && ua.match(/MicroMessenger/i) == "micromessenger") {
            window.scrollTo(0, 0);
        }

        this.closeCallback = closeCallback;

        //消息框宽度重置
        if (String(this.width).indexOf("%") == -1) {
            if (msgType == MSG_TYPE_PROCESSING) {
                $(this.getFrame()).css("width", "550px");
            }
            else {
                //内容超过指定长度的场合，宽度加大
                if (msg.length > 768) {
                    $(this.getFrame()).css("width", "1000px");
                }
                else if (msg.length > 512) {
                    $(this.getFrame()).css("width", "850px");
                }
                else if (msg.length > 256) {
                    $(this.getFrame()).css("width", "700px");
                }
                else {
                    $(this.getFrame()).css("width", this.width + "px");
                }
            }
        }
        else {
            $(this.getFrame()).css("width", this.width);
        }
        SysCmn.CtrlUtil.SetPopupPosition(this.getFrame(), this.popupPosition);

        if ($(this.getFrame()).css("display") == "none") {
            SysCmn.EstopLayer.show(this.getFrame());

            if ($M.isWeixinBrowser()) {
                $(this.getFrame()).show();
                //隐藏滚动条
                $(document.body).css("overflow", "hidden");
            }
            else {
                $(this.getFrame()).fadeIn();
            }

            //设置消息控件的堆叠顺序
            $(this.getFrame()).css("z-index", 9999);
        }

        try {
            //メッセージ内容を処理中に設定する
            this._setMessage(msg, msgType);
        }
        catch (e) {
        }
    },

    //確認メッセージを表示する
    showConfirm: function (msg, yesCallback, noCallback, cancelCallback) {
        //「はい」処理Callbackイベント
        this.yesCallback = yesCallback;

        //「いいえ」処理Callbackイベント
        this.noCallback = noCallback;

        //「キャンセル」処理Callbackイベント
        this.cancelCallback = cancelCallback;

        this.showMessage(msg, MSG_TYPE_CONFIRM);
    },

    //処理中メッセージを表示する
    showProcessing: function (allowClose) {
        if ($M.isNull(allowClose)) {
            allowClose = true;
        }
        if ($M.isWeixinBrowser()) {
            this.showMessage("系统处理中，请耐心等待……", MSG_TYPE_PROCESSING);
        }
        else {
            this.showMessage("系统处理中，在此期间请勿刷新页面或关闭浏览器……", MSG_TYPE_PROCESSING);
        }
        if (allowClose == false) {
            $($E("btnTopCloseForCmnMessage")).hide();
        }
    },

    //非表示処理
    hide: function (hideNow) {
        //共通消息提示功能无效化的场合，处理中止
        if (window.processingMessageDisabled == true) {
            return;
        }
        if (hideNow) {
            this._hideCmnMessage();
        }
        else {
            this._hideCmnMessage();
        }
    },

    //画面メッセージを設定する
    _setMessage: function (msg, msgType) {
        //メッセージ内容を設定する
        this.$(this.dom.divMessage).html(msg);

        //「はい」ボタンを非表示する
        this.$(this.dom.btnYes).hide();

        //「いいえ」ボタンを非表示する
        this.$(this.dom.btnNo).hide();

        //「キャンセル」ボタンを非表示する
        this.$(this.dom.btnCancel).hide();

        //閉じるボタンを非表示する
        this.$(this.dom.btnClose).hide();

        this.$(this.dom.tdCmnMessageType).show();
        $(".cmn-message-icon").hide();

        this.$(this.dom.divCmnMessageFooter).show();

        //エラーメッセージ
        if (msgType == MSG_TYPE_ERROR) {
            this.$(this.dom.divMessage).css("color", "red");
            $(".fa-warning").show();

            //閉じるボタン表示に設定する
            this.$(this.dom.btnClose).show();
            this.dom.btnClose.focus();
        }
        //警告メッセージ
        else if (msgType == MSG_TYPE_WARNING) {
            this.$(this.dom.divMessage).css("color", "red");
            $(".fa-warning").show();

            //閉じるボタン表示に設定する
            this.$(this.dom.btnClose).show();
            this.dom.btnClose.focus();
        }
        //Infoメッセージ
        else if (msgType == MSG_TYPE_INFO) {
            this.$(this.dom.divMessage).css("color", "#767676");
            $(".fa-info-circle").show();

            //閉じるボタン表示に設定する
            this.$(this.dom.btnClose).show();
            this.dom.btnClose.focus();
        }
        //処理中メッセージ
        else if (msgType == MSG_TYPE_PROCESSING) {
            this.$(this.dom.divCmnMessageFooter).hide();
            this.$(this.dom.divMessage).css("color", "#767676");
            $(".fa-spinner").show();
        }
        //検証メッセージ
        else if (msgType == MSG_TYPE_VALIDATION) {
            this.$(this.dom.divMessage).css("color", "red");
            this.$(this.dom.tdCmnMessageType).hide();

            //閉じるボタン表示に設定する
            this.$(this.dom.btnClose).show();
            this.dom.btnClose.focus();
        }
        //確認メッセージ
        else if (msgType == MSG_TYPE_CONFIRM) {
            this.$(this.dom.divMessage).css("color", "#767676");
            $(".fa-question-circle-o").show();

            //「はい」ボタンを表示する
            this.$(this.dom.btnYes).show();
            //「いいえ」ボタン表示に設定する
            this.$(this.dom.btnNo).show();

            //「キャンセル」ボタン表示に設定する
            if (this.cancelCallback != null) {
                this.$(this.dom.btnCancel).show();
            }

            this.dom.btnYes.focus();
        }
        SysCmn.CtrlUtil.SetPopupPosition(this.getFrame(), this.popupPosition);
    },

    //コントロール非表示
    _hideCmnMessage: function () {
        SysCmn.EstopLayer.hide(this.getFrame());
        if ($M.isWeixinBrowser()) {
            //恢复滚动条
            $(document.body).css("overflow", "auto");
            $(this.getFrame()).hide();
        }
        else {
            $(this.getFrame()).fadeOut();
        }
    },

    //幅を設定する
    setWidth: function (w) {
        this.width = w;
    },

    //标题设置
    setTitle: function (title) {
        this.$(this.dom.ctlTitleName).html(title);
    }
});

SysCmn.CmnMsg = new SysCmn.CmnMsgCls();
//CmnMessage.js类文件定义结束------------------------------------------------------------//


