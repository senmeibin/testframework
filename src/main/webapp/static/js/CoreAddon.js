//AutoComplete.js类文件定义开始----------------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.AutoComplete = Class.create();

SysApp.Cmn.AutoComplete.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        //共通Property初期化
        this.initCommonProperty();
    },

    //初期化处理
    init: function () {
        var thisObj = this;

        //输入内容被清空的场合，ID值同步清空
        this.$(this.selectLabel).blur(function () {
            if (this.value == "") {
                thisObj.setValue(thisObj.selectId, "");
            }
        });

        this.$(this.selectLabel).autocomplete({
            minLength: thisObj.searchMinLength,
            source: function (request, response) {
                $.ajax({
                    url: thisObj.url + "?ajaxTime=" + new Date().getTime(),
                    type: 'post',
                    async: false,
                    data: thisObj.paramName + "=" + request.term,
                    dataType: 'json',
                    success: function (ajaxResult) {
                        //将数据交给autocomplete去展示
                        response(ajaxResult.content);

                        var callback = eval(thisObj.autoCompleteSuccessCallback);
                        var pageInstance = eval(thisObj.pageInstance);

                        if (pageInstance.isFunction(callback)) {
                            callback.call(pageInstance, ajaxResult.content);
                        } else {
                            if (thisObj.isEmpty(ajaxResult.content)) {
                                thisObj.setValue(thisObj.selectId, "");
                            }
                        }
                    }
                });
            },
            select: function (event, ui) {
                var callback = eval(thisObj.autoCompleteSelectCallback);
                var pageInstance = eval(thisObj.pageInstance);
                if (pageInstance.isFunction(callback)) {
                    callback.call(pageInstance, event, ui);
                }
                else {
                    thisObj.setValue(thisObj.selectLabel, ui.item.label);
                    thisObj.setValue(thisObj.selectId, ui.item.uid);
                }
                // 必须阻止默认行为，因为autocomplete默认会把ui.item.value设为输入框的value值
                event.preventDefault();
            }
        });
    }
});
//AutoComplete.js类文件定义结束----------------------------------------------------------//


//BrowserVersion.js类文件定义开始--------------------------------------------------------//
//定义数据输入画面JS类
SysApp.Cmn.BrowserVersion = Class.create();

SysApp.Cmn.BrowserVersion.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //初期化回调处理
    initCallback: function () {
        //绑定popovers事件
        this.bindPopoversEvent();
    },

    show: function () {
        if ($M.isIE7() || $M.isIE8() || $M.isIE9()) {
            SysCmn.EstopLayer.show(this.getFrame());
            $(this.getFrame()).fadeIn();
            SysCmn.CtrlUtil.SetPopupPosition(this.getFrame(), "center");
            $(this.getFrame()).css("z-index", 99999);
        }
    }
});
//BrowserVersion.js类文件定义结束--------------------------------------------------------//


//ColumnSetting.js类文件定义开始---------------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.ColumnSetting = Class.create();

SysApp.Cmn.ColumnSetting.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },
    
    //初期化回调处理
    initCallback: function () {
        //JS实例对象字符串
        this.instanceString = this.pageInstance;

        this.showSaveDataSuccessMessage = false;
        this.setValue("pageInstance", this.pageInstance);

        //自定义宽度设置的场合
        if (this.isNotEmpty(this.width)) {
            $(this.getFrame()).width(this.width);
        }
    },

    //客户化数据保存回调方法
    customSaveDataCallback: function () {
        //清空列信息
        this.pageInstance.colList = new Array();

        //初期化一览数据列信息
        this.pageInstance.initTableColList();

        //初期化自定义列设定
        this.pageInstance.initColumnSetting();

        //创建检索一览数据
        this.pageInstance.createList(this.pageInstance.data);

        //保存成功后刷新整个画面
        //window.location.reload();
    },

    customSaveConfirmMessage: function () {
        var flag = false;
        $(".column-setting-list").each(function () {
            if (this.checked) {
                flag = true;
            }
        });

        if (flag == false) {
            return "您未选择任何列信息，将自动恢复默认显示方式，<br />确定要保存吗？";
        }
        else {
            return "确定要保存吗？";
        }
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        this.$("ctlTitle").html("显示列设定");

        this.pageInstance = eval(this.pageInstance);

        this.allColList = this.pageInstance.allColList;
        this.myColumnSetting = this.pageInstance.myColumnSetting;

        $(this.getDom("columnSettingList")).empty();
        var thisObj = this;
        if (this.allColList) {
            $.each(this.allColList, function (index, item) {
                if (!item.onlyForExport) {
                    thisObj.appendColumnItem(item);
                }
            })
        }

        $(this.getDom("columnSettingList")).append('<div style="clear:both;"><input id="checkAllColumnSetting" type="checkbox"/><label for="checkAllColumnSetting">全选择/全解除</label>' +
            '（<i class="fa fa-hand-o-right warning-red margin-top-space">&nbsp;全解除状态，将自动恢复系统默认列显示方式。</i>）</div>');

        //全选择/全解除 事件处理
        $("#checkAllColumnSetting").click(function () {
            var checked = this.checked;
            $(".column-setting-list").each(function () {
                this.checked = checked;
            });
        });
    },

    appendColumnItem: function (item) {
        var checked = "";

        //有自定义列设置的场合
        if (this.isNotEmpty(this.myColumnSetting) && this.myColumnSetting.length > 0) {
            //设置默认勾选项
            $.each(this.myColumnSetting, function () {
                if (this == item.colName) {
                    checked = 'checked="checked"';
                }
            });
        }
        else {
            var colList = this.pageInstance.colList;

            //设置默认勾选项
            $.each(colList, function () {
                if (this.colName == item.colName) {
                    checked = 'checked="checked"';
                }
            });
        }

        var html = '' +
            '<div style="width:182px;float: left;text-overflow:ellipsis;overflow: hidden;">' +
            '<input class="column-setting-list" id="' + item.colName + '" ' + 'type="checkbox" ' + checked + '"/>' +
            '<label for="' + item.colName + '">' + item.title.replaceAll(" ", "").replaceAll("<br/>", "/") + '</label></div>';

        $(this.getDom("columnSettingList")).append(html);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //清空所有值
        this.inputEntity = new Object();

        //画面标识
        this.inputEntity.pageInstance = this.getValue("pageInstance");
        //设置类型
        this.inputEntity.settingType = this.isEmpty(this.settingType) ? "1" : this.settingType;

        //配置参数
        var parameters = [];
        var columnSettingList = $(this.getDom("columnSettingList"));
        $(".column-setting-list").each(function () {
            if (this.checked) {
                parameters.push(this.id);
            }
        });
        this.inputEntity.parameters = JSON.stringify(parameters);

        //重置当前选择对象
        this.myColumnSetting = parameters;

        //画面模式存在的场合，附加到列表画面JS实例对象字符串后
        if (this.isNotEmpty(this.pageMode)) {
            this.inputEntity.pageInstance = this.instanceString + "_" + this.pageMode;
        }

        this.pageInstance.setValue("jsonColumnSetting", this.inputEntity.parameters);
    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});
//ColumnSetting.js类文件定义结束---------------------------------------------------------//


//ComboTree.js类文件定义开始-------------------------------------------------------------//
SysApp.Cmn.ComboTree = Class.create();


SysApp.Cmn.ComboTree.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        //共通Property初期化
        this.initCommonProperty();

        //无需初始化日历控件
        this.initCalendarFlag = false;
    },

    //初期化父画面JS实例对象
    initParentInstance: function (loaded) {
        this._initParentInstance();

        if (this.isNull(loaded)) {
            this.parentInterval = setInterval(this._initParentInstance.bind(this), 10);
        }
    },

    _initParentInstance: function () {
        if (this.isNotNull(eval(this.parentInstanceString))) {
            clearInterval(this.parentInterval);

            //父画面JS实例对象
            this.parentInstance = eval(this.parentInstanceString);

            //给父画面JS实例对象设置ComboTree实例对象
            eval(this.parentInstanceString + "." + this.valueDomId + "ComboTree = this;");

            //设置当前容器ClientID
            this.clientID = this.parentInstance.getClientID();

            this.initCombo();
        }
    },

    getTreeId: function () {
        return this.getId(this.valueDomId + "Tree");
    },

    initCombo: function (selectedValue, readOnly) {
        //readonly 属性
        this.readonly = false;
        if (this.isNotEmpty(readOnly)) {
            this.readonly = readOnly;
        }

        if (this.isEmpty(selectedValue)) {
            this.selectedValue = "";
        }
        else {
            this.selectedValue = selectedValue;
        }

        if (this.isEmpty(this.ajaxUrl)) {
            this.initComboByOptionList();
        }
        else {
            this.initComboByAjax();
        }

        //右侧主容器区域Click事件隐藏ZTree树
        Event.observe(window.document, "click", this.onBodyDown.bind(this));
    },

    initComboByOptionList: function () {
        //不从ajax 获取， 改为从OptionList获取 树的内容
        $.fn.zTree.init(this.$(this.getTreeId()),
            {
                view: {
                    dblClickExpand: false
                },
                data: {
                    key: {
                        name: this.dispName
                    },
                    simpleData: {
                        enable: true,
                        idKey: this.idKey,
                        pIdKey: this.parentIdKey,
                    }
                },
                callback: {
                    beforeClick: this.beforeClick.bind(this),
                    onAsyncSuccess: this.zTreeOnAsyncSuccess.bind(this),
                    onClick: this.onClick.bind(this)
                }
            }, this.getTreeNodes());

        //展开
        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
        //如果指定值为空，默认打开第一个节点
        var nodes = zTree.getNodes();
        if (nodes.length > 0) {
            zTree.expandNode(nodes[0], true, true);
            this.selectNodeItem(this.getValue(this.valueDomId));
        }
    },

    //初期化下拉选择框
    getTreeNodes: function () {
        var value = this.parentInstance.getValue("jsonOptionList");
        var zNodes = new Array();
        var rootNode = {};
        rootNode.subCd = "";
        rootNode.parentSubCd = "";
        rootNode.subName = "全部";
        zNodes.push(rootNode);

        if (this.isEmpty(value)) return zNodes;

        var jsonOptionList = JsonUtility.Parse(value);
        //取得该dom 所对应的数据列表
        var detailList = jsonOptionList[(this.valueDomId)];
        var thisObj = this;
        $.each(detailList, function (index, node) {
            //如果本身有父节点cd， 则不设置， 本身没有的都设置父节点（基本为 all:全部）
            if (thisObj.isEmpty(node.parentSubCd)) {
                node.parentSubCd = rootNode.subCd;
            }
            zNodes.push(node);
        });
        return zNodes;
    },

    initComboByAjax: function () {
        //Ajax请求URL地址中添加时间戳
        var time = new Date().getTime();
        var url = (this.ajaxUrl.indexOf("?") == -1) ? (this.ajaxUrl + "?ajaxTime=" + time) : (this.ajaxUrl + "&ajaxTime=" + time);
        //增加附加请求参数
        var otherParam = {};
        if (this.isNotEmpty(this.parentDomId) && this.isNotEmpty(this.parentValue)) {
            otherParam.parentUids = this.parentValue;
        }

        //Ajax处理(POST方式) -- 树内容获取
        $.fn.zTree.init(this.$(this.getTreeId()),
            {
                view: {
                    dblClickExpand: false
                },
                async: {
                    enable: true,
                    type: "post",
                    dataType: "json",
                    otherParam: otherParam,
                    url: url,
                    autoParam: []
                },
                data: {
                    key: {
                        name: this.nodeName
                    },
                    simpleData: {
                        enable: true,
                        idKey: this.idKey,
                        pIdKey: this.parentIdKey,
                        rootPid: ""
                    }
                },
                callback: {
                    beforeClick: this.beforeClick.bind(this),
                    onAsyncSuccess: this.zTreeOnAsyncSuccess.bind(this),
                    onClick: this.onClick.bind(this)
                }
            }, null);
    },

    beforeClick: function (treeId, treeNode) {
        //禁用层级存在场合
        if (this.isNotEmpty(this.disabledLevel) && this.disabledLevel.indexOf(treeNode.level) >= 0) {
            return false;
        }
        //如果本tree 不能选择父节点时处理
        else if (this.allowSelectParent == false) {
            //如果是父节点
            if (treeNode.isParent) {
                return false;
            }
        }
        return true;
    },

    zTreeOnAsyncSuccess: function (event, treeId, treeNode, msg) {
        //展开
        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());

        //如果指定值为空，默认打开第一个节点
        var nodes = zTree.getNodes();
        if (nodes.length > 0) {
            zTree.expandNode(nodes[0], true, true);
            this.selectNodeItem(this.selectedValue);
        }
        this.setReadOnly(this.readonly);
        //设置叶子节点图标
        this.setLeafIcon(nodes);

        zTree.refresh();

        //回调父对象方法
        if (this.isFunction(this.parentInstance.initTreeCallback)) {
            this.parentInstance.initTreeCallback(this);
        }
    },

    /**
     * 设置叶子节点图标
     */
    setLeafIcon: function (nodes) {
        for (var i = 0, l = nodes.length; i < l; i++) {
            var treeNode = nodes[i];
            if (treeNode.isParent) {
                this.setLeafIcon(treeNode.children);
            }
            else {
                //人员ICON
                if (this.isNotEmpty(treeNode["isPerson"]) && treeNode["isPerson"] == true) {
                    treeNode.icon = window.imagesPath + "base/person.png";
                }
                //部门ICON
                else if (this.isNotEmpty(treeNode["isDept"]) && treeNode["isDept"] == true) {

                }
            }
        }
    },

    onClick: function (e, treeId, treeNode) {
        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }

        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
        var nodes = zTree.getSelectedNodes();
        var textDomId = "";
        var selectValue = "";

        nodes.sort(function compare(a, b) {
            return a[this.idKey] - b[this.idKey];
        });

        for (var i = 0, l = nodes.length; i < l; i++) {
            textDomId += nodes[i][this.dispName] + ",";
            selectValue += nodes[i][this.idKey] + ",";
        }
        if (textDomId.length > 0) {
            textDomId = textDomId.substring(0, textDomId.length - 1);
            selectValue = selectValue.substring(0, selectValue.length - 1);
        }

        this.setValue(this.textDomId, textDomId);
        this.setValue(this.valueDomId, selectValue);

        //回调父对象方法
        if (this.isFunction(this.parentInstance.onComboSelected)) {
            this.parentInstance.onComboSelected(selectValue, textDomId, treeNode);
        }
        //关闭选择页
        this.hideMenu();
    },

    showMenu: function () {
        var targetWidth = $($(".combo-tree-" + this.uniqueID).get(0)).width();
        targetWidth = targetWidth - 12;
        this.$(this.getTreeId()).width(targetWidth + "px");

        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }
        //验证是否有上级联动dom 如果有 自己重新初始化
        if (this.isNotEmpty(this.parentDomId)) {
            this.parentValue = this.getValue(this.parentDomId);
            if (this.isEmpty(this.parentValue)) {
                this.setValue(this.valueDomId, "");
                this.showErrorMessage("请先选择" + this.parentDataTitle);
                this.hideMenu();
                return;
            } else {
                this.inited = false;
                this.initParentInstance(true)
            }
        }

        if (this.isNotEmpty(this.ajaxUrl) && this.reloadFlag == "true") {
            this.initComboByAjax();
        }
        this.$(this.valueDomId + "MenuContent").slideDown("fast");
    },

    hideMenu: function () {
        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }
        this.$(this.valueDomId + "MenuContent").fadeOut("fast");
    },

    onBodyDown: function (event) {
        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }
        var idPrefix = this.getId(this.valueDomId);
        var fullLabel = this.getId(this.textDomId);
        if (!(event.target.id == idPrefix + "MenuBtn" || event.target.id == idPrefix + "FaMenuBtn" || event.target.id == fullLabel || event.target.id == idPrefix + "MenuContent" || this.$(event.target).parents("#" + idPrefix + "MenuContent").length > 0)) {
            this.hideMenu();
        }
    },

    /**
     * 设置combotree的默认选择节点
     */
    selectNodeItem: function (selectValue) {
        if (this.isEmpty(selectValue)) {
            return;
        }
        else {
            var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
            this.setValue(this.valueDomId, selectValue);
            //如果指定值为空，默认打开第一个节点
            var nodes = zTree.getNodes();
            if (nodes.length > 0) {
                for (var i = 0, l = nodes.length; i < l; i++) {
                    //获取所有节点
                    this.selectSpecNode(zTree, nodes[i], selectValue);
                }
            }
        }
    },

    /**
     * 选中value指定的node
     */
    selectSpecNode: function (zTree, treeNode, selectValue) {
        if (treeNode.isParent) {
            if (treeNode[this.idKey] == selectValue) {
                var labelValue = this.getValue(this.textDomId);
                labelValue = treeNode[this.dispName];
                this.setValue(this.textDomId, labelValue);
                zTree.selectNode(treeNode, false, false);
                return;
            }
            var nodes = treeNode.children;
            for (var i = 0, l = nodes.length; i < l; i++) {
                this.selectSpecNode(zTree, nodes[i], selectValue);
            }
        }
        else {
            //叶子节点的时候
            if (treeNode[this.idKey] == selectValue) {
                var labelValue = this.getValue(this.textDomId);
                labelValue = treeNode[this.dispName];
                this.setValue(this.textDomId, labelValue);
                zTree.selectNode(treeNode, false, false);
                return;
            }
            return;
        }
    },

    /**
     * 设置combocheck tree 为已读
     */
    setReadOnly: function (readonlyFlg) {
        //把readonly属性设定为true
        this.readonly = readonlyFlg;
    }
});
//ComboTree.js类文件定义结束-------------------------------------------------------------//


//ComboCheckTree.js类文件定义开始--------------------------------------------------------//
SysApp.Cmn.ComboCheckTree = Class.create();

SysApp.Cmn.ComboCheckTree.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        //共通Property初期化
        this.initCommonProperty();

        //无需初始化日历控件
        this.initCalendarFlag = false;

        this.inited = false;
    },

    //初期化父画面JS实例对象
    initParentInstance: function (loaded) {
        this._initParentInstance();

        if (this.isNull(loaded)) {
            this.parentInterval = setInterval(this._initParentInstance.bind(this), 10);
        }
    },

    _initParentInstance: function () {
        if (this.isNotNull(eval(this.parentInstanceString))) {
            clearInterval(this.parentInterval);

            //父画面JS实例对象
            this.parentInstance = eval(this.parentInstanceString);

            //给父画面JS实例对象设置ComboTree实例对象
            eval(this.parentInstanceString + "." + this.valueDomId + "ComboTree = this;");

            //设置当前容器ClientID
            this.clientID = this.parentInstance.getClientID();

            this.initComboCheck();
        }
    },

    getTreeId: function () {
        return this.getId(this.valueDomId + "Tree");
    },

    initComboCheck: function (parentCheck, readOnly) {
        //默认 ： true （父节点显示有checkbox）， false： 父节点不显示checkbox
        if (this.isEmpty(parentCheck)) {
            this.parentCheck = true;
        }
        else {
            this.parentCheck = parentCheck;
        }
        //readonly 属性
        this.readonly = false;
        if (this.isNotEmpty(readOnly)) {
            this.readonly = readOnly;
        }

        if (this.inited == true) return;
        if (this.isEmpty(this.ajaxUrl)) {
            this.initComboByOptionList();
        }
        else {
            this.initComboByAjax();
        }

        //右侧主容器区域Click事件隐藏ZTree树
        Event.observe(window.document, "click", this.onBodyDown.bind(this));
    },

    initComboByOptionList: function () {
        //不从ajax 获取， 改为从OptionList获取 树的内容
        $.fn.zTree.init(this.$(this.getTreeId()),
            {
                check: {
                    enable: true,
                    chkboxType: {"Y": "ps", "N": "ps"}
                },
                view: {
                    dblClickExpand: false
                },
                data: {
                    key: {
                        name: this.dispName
                    },
                    simpleData: {
                        enable: true,
                        idKey: this.idKey,
                        pIdKey: this.parentIdKey,
                    }
                },
                callback: {
                    beforeClick: this.beforeClick.bind(this),
                    onCheck: this.onCheck.bind(this)
                }
            }, this.getTreeNodes());

        //展开
        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
        //如果指定值为空，默认打开第一个节点
        var nodes = zTree.getNodes();
        if (nodes.length > 0) {
            zTree.expandNode(nodes[0], true, true);
            //选中指定value的下拉框
            this.checkNodes(zTree);
        }
    },

    initComboByAjax: function () {
        //Ajax请求URL地址中添加时间戳
        var time = new Date().getTime();

        var url = (this.ajaxUrl.indexOf("?") == -1) ? (this.ajaxUrl + "?ajaxTime=" + time) : (this.ajaxUrl + "&ajaxTime=" + time);

        if (this.isFunction(this.parentInstance.customDataUrlParams) && this.reloadFlag == "true") {
            var params = this.parentInstance.customDataUrlParams();
            //参数无变化的场合，无需发送Ajax数据请求
            if (this.previousCustomDataUrlParams == params) {
                return;
            }
            this.previousCustomDataUrlParams = params;
            url += "&" + params;
        }

        //增加附加请求参数
        var otherParam = {};
        if (this.isNotEmpty(this.parentDomId) && this.isNotEmpty(this.parentValue)) {
            otherParam.parentUids = this.parentValue;
        }

        //Ajax处理(POST方式) -- 树内容获取
        $.fn.zTree.init(this.$(this.getTreeId()),
            {
                check: {
                    enable: true,
                    chkboxType: {"Y": "ps", "N": "ps"}
                },
                view: {
                    dblClickExpand: false
                },
                async: {
                    enable: true,
                    type: "post",
                    dataType: "json",
                    otherParam: otherParam,
                    url: url,
                    autoParam: []
                },
                data: {
                    key: {
                        name: this.nodeName
                    },
                    simpleData: {
                        enable: true,
                        idKey: this.idKey,
                        pIdKey: this.parentIdKey,
                    }
                },
                callback: {
                    beforeClick: this.beforeClick.bind(this),
                    onAsyncSuccess: this.zTreeOnAsyncSuccess.bind(this),
                    onCheck: this.onCheck.bind(this)
                }
            }, null);
    },

    beforeClick: function (treeId, treeNode) {
        return false;
    },

    zTreeOnAsyncSuccess: function (event, treeId, treeNode, msg) {
        //展开
        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
        //如果指定值为空，默认打开第一个节点
        var nodes = zTree.getNodes();
        if (nodes.length > 0) {
            zTree.expandNode(nodes[0], true, true);
            //父节点不显示checkbox
            if (!this.parentCheck) {
                for (var i = 0, l = nodes.length; i < l; i++) {
                    //获取所有节点
                    this.setParentNoCheck(nodes[i]);
                }
            }
            this.setReadOnly(this.readonly);
            //选中指定value的下拉框
            this.checkNodes(zTree);
            //设置叶子节点图标
            this.setLeafIcon(nodes);
        }

        zTree.refresh();

        //设置节点禁用
        this.disabledNodes(zTree);

        this.inited = true;

        //回调父对象方法
        if (this.isFunction(this.parentInstance.initTreeCallback)) {
            this.parentInstance.initTreeCallback(this);
        }
    },

    /**
     * 设置节点禁用
     */
    disabledNodes: function (zTree) {
        //禁用层级不为空场合
        if (this.isNotEmpty(this.disabledLevel)) {
            var levelArray = this.disabledLevel.split(",");
            for (var i = 0; i < levelArray.length; i++) {
                //获取层级下的所有节点
                var disabledNodes = zTree.getNodesByParam("level", parseInt(levelArray[i]));
                //循环设置节点禁用
                for (var j = 0; j < disabledNodes.length; j++) {
                    zTree.setChkDisabled(disabledNodes[j], true);
                    disabledNodes[j].nocheck = true;
                    zTree.updateNode(disabledNodes[j]);
                }
            }
        }
    },

    /**
     * 设置叶子节点图标
     */
    setLeafIcon: function (nodes) {
        for (var i = 0, l = nodes.length; i < l; i++) {
            var treeNode = nodes[i];
            if (treeNode.isParent) {
                this.setLeafIcon(treeNode.children);
            }
            else {
                //人员ICON
                if (this.isNotEmpty(treeNode["isPerson"]) && treeNode["isPerson"] == true) {
                    treeNode.icon = window.imagesPath + "base/person.png";
                }
                //部门ICON
                else if (this.isNotEmpty(treeNode["isDept"]) && treeNode["isDept"] == true) {

                }
            }
        }
    },

    onCheck: function (e, treeId, treeNode) {
        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }

        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());

        var nodes = zTree.getCheckedNodes(true);
        var selectLabel = "";
        var selectValue = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            //如果父节点需要赋值， 或者是叶节点
            if (this.canCheckParent == "true" || !nodes[i].isParent) {
                selectLabel += nodes[i][this.dispName] + ",";
                selectValue += nodes[i][this.idKey] + ",";
            }
        }
        if (selectLabel.length > 0) {
            selectLabel = selectLabel.substring(0, selectLabel.length - 1);
            selectValue = selectValue.substring(0, selectValue.length - 1);
        }
        this.setValue(this.textDomId, selectLabel);
        this.setValue(this.valueDomId, selectValue);

        //回调父对象方法
        if (this.isFunction(this.parentInstance.onCheck)) {
            this.parentInstance.onCheck(selectLabel, selectValue, treeNode);
        }
    },

    showMenu: function () {
        var targetWidth = $($(".combo-tree-" + this.uniqueID).get(0)).width();
        targetWidth = targetWidth - 12;
        if (this.isNotEmpty(this.comboTreeWidth)) {
            targetWidth = this.comboTreeWidth;
        }
        this.$(this.getTreeId()).width(targetWidth + "px");

        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }
        //验证是否有上级联动dom 如果有 自己重新初始化
        if (this.isNotEmpty(this.parentDomId)) {
            this.parentValue = this.getValue(this.parentDomId);
            if (this.isEmpty(this.parentValue)) {
                this.setValue(this.valueDomId, "");
                this.showErrorMessage("请先选择" + this.parentDataTitle);
                this.hideMenu();
                return;
            } else {
                this.inited = false;
                this.initParentInstance(true)
            }
        }

        if (this.isNotEmpty(this.ajaxUrl) && this.reloadFlag == "true") {
            this.initComboByAjax();
        }

        this.$(this.valueDomId + "MenuContent").slideDown("fast");
    },

    hideMenu: function () {
        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }
        this.$(this.valueDomId + "MenuContent").fadeOut("fast");
    },

    onBodyDown: function (event) {
        if (this.alwaysShowTree == "true") return;

        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }
        var idPrefix = this.getId(this.valueDomId);
        var fullLabel = this.getId(this.textDomId);
        if (!(event.target.id == idPrefix + "MenuBtn" || event.target.id == idPrefix + "FaMenuBtn" || event.target.id == fullLabel || event.target.id == idPrefix + "MenuContent" || this.$(event.target).parents("#" + idPrefix + "MenuContent").length > 0)) {
            this.hideMenu();
        }
    },

    /**
     * 选中已经赋值的节点
     */
    checkNodes: function (zTree) {
        if (this.isEmpty(zTree)) {
            zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
        }
        //获取所有节点
        var nodes = zTree.getNodes();
        //默认节点未设定的场合【原来逻辑】
        if (this.isEmpty(this.getValue(this.valueDomId + "DefaultSelectedNode"))) {
            var selectValue = this.getValue(this.valueDomId);

            if (this.isEmpty(selectValue)) {
                return;
            }
            this.setValue(this.textDomId, "");
            var valueArray = selectValue.split(",");
            for (var i = 0, l = nodes.length; i < l; i++) {
                this.selectSpecNode(zTree, nodes[i], valueArray);
            }
        }
        //默认节点有设定的场合
        else {
            for (var i = 0, l = nodes.length; i < l; i++) {
                this.selectParentNode(zTree, nodes[i]);
            }
        }
    },

    /**
     * 递归取到默认节点
     */
    selectParentNode: function (zTree, treeNode) {
        //节点值等于指定的值
        if (treeNode[this.idKey] == this.getValue(this.valueDomId + "DefaultSelectedNode")) {
            //子节点全部选中
            zTree.checkNode(treeNode, true, true);
            //叶子节点值拼接
            this.selectSpecNode(zTree, treeNode);
        }
        //当前节点有子节点场合
        else if (treeNode.isParent) {
            var nodes = treeNode.children;
            for (var i = 0, l = nodes.length; i < l; i++) {
                this.selectParentNode(zTree, nodes[i]);
            }
        }
    },

    /**
     * 选中value指定的node
     */
    selectSpecNode: function (zTree, treeNode, valueArray) {
        if (treeNode.isParent) {
            var nodes = treeNode.children;
            for (var i = 0, l = nodes.length; i < l; i++) {
                this.selectSpecNode(zTree, nodes[i], valueArray);
            }
        }
        else {
            //指定值为空场合【所有叶子节点值都要拼接】
            if (this.isNull(valueArray)) {
                var labelValue = this.getValue(this.textDomId);
                var idValue = this.getValue(this.valueDomId);
                if (this.isEmpty(labelValue)) {
                    labelValue = treeNode[this.dispName];
                    idValue = treeNode[this.idKey];
                }
                else {
                    labelValue = labelValue + "," + treeNode[this.dispName];
                    idValue = idValue + "," + treeNode[this.idKey];
                }
                this.setValue(this.textDomId, labelValue);
                this.setValue(this.valueDomId, idValue);
            }
            else {
                //叶子节点的时候
                for (var j = 0, jl = valueArray.length; j < jl; j++) {
                    //如果相同，设为选中
                    if (treeNode[this.idKey] == valueArray[j]) {
                        var labelValue = this.getValue(this.textDomId);
                        if (this.isEmpty(labelValue)) {
                            labelValue = treeNode[this.dispName];
                        }
                        else {
                            labelValue = labelValue + "," + treeNode[this.dispName];
                        }
                        this.setValue(this.textDomId, labelValue);
                        zTree.checkNode(treeNode, true, false);
                        break;
                    }
                }
                return;
            }
        }
    },

    /**
     * 只有子节点可以选择
     */
    setParentNoCheck: function (treeNode) {
        //获取所有节点
        if (treeNode.isParent) {
            treeNode.nocheck = true;
            var childNodes = treeNode.children;
            for (var i = 0, l = childNodes.length; i < l; i++) {
                this.setParentNoCheck(childNodes[i]);
            }
        }
        else {
            //如果是叶子节点， 不处理
            return;
        }
    },

    /**
     * 设置combocheck tree 为已读
     */
    setReadOnly: function (readonlyFlg) {
        //把readonly属性设定为true
        this.readonly = readonlyFlg;
    },

    //初期化下拉选择框
    getTreeNodes: function () {
        var value = this.parentInstance.getValue("jsonOptionList");
        var zNodes = new Array();
        var rootNode = {};
        rootNode.subCd = "";
        rootNode.parentSubCd = "";
        rootNode.subName = "全部";
        zNodes.push(rootNode);

        if (this.isEmpty(value)) return zNodes;

        var jsonOptionList = JsonUtility.Parse(value);
        //取得该dom 所对应的数据列表

        var idKey = this.idKey;
        var detailList = jsonOptionList[(this.valueDomId)];
        $.each(detailList, function (index, node) {
            //idKey为subName场合
            if (idKey == "subName") {
                //设置父节点（基本为 all:全部）
                node.parentSubCd = rootNode.subName;
            } else {
                //设置父节点（基本为 all:全部）
                node.parentSubCd = rootNode.subCd;
            }
            zNodes.push(node);
        });
        return zNodes;
    },

    //勾选 或 取消勾选 全部节点
    //  checked = true 表示勾选全部节点
    //  checked = false 表示全部节点取消勾选
    checkAllNodes: function (checked) {
        $.fn.zTree.getZTreeObj(this.getTreeId()).checkAllNodes(checked);
    }
});
//ComboCheckTree.js类文件定义结束--------------------------------------------------------//


//DeptComboCheckTree.js类文件定义开始----------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.DeptComboCheckTree = Class.create();

SysApp.Cmn.DeptComboCheckTree.prototype = Object.extend(new SysCmn.CoreInput(), {
    //初期化父画面JS实例对象
    initParentInstance: function (loaded) {
        this._initParentInstance();

        if (this.isNull(loaded)) {
            this.parentInterval = setInterval(this._initParentInstance.bind(this), 10);
        }
    },

    _initParentInstance: function () {
        if (this.isNotNull(eval(this.parentInstanceString))) {
            clearInterval(this.parentInterval);
            //父画面JS实例对象
            this.parentInstance = eval(this.parentInstanceString);
        }
    },
});
//DeptComboCheckTree.js类文件定义结束----------------------------------------------------//


//Header.js类文件定义开始----------------------------------------------------------------//
//定义数据一览画面JS类
SysApp.Cmn.Header = Class.create();

SysApp.Cmn.Header.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        //共通Property初期化
        this.initCommonProperty();

        //无需初始化日历控件
        this.initCalendarFlag = false;
    },

    //初期化处理
    init: function () {
        this.bindEvent(window.document, "click", this.closeSubMenu.bind(this));
        this.bindEvent("main-content", "mouseover", this.closeSubMenu.bind(this));

        var thisObj = this;
        $(".top-main-menu").click(function (event) {
            thisObj.showSubMenu($(this).attr("sub_menu"));
        });

        $(".top-main-menu").mousemove(function (event) {
            thisObj.showSubMenu($(this).attr("sub_menu"));
        });

        //显示消息详情信息
        $(".header-notice-message").click(function () {
            thisObj.showMessageDetailPopupList();
        });

        //弹层提示框 绑定 关闭按钮 事件
        $(".popup-prompt-head i").click(function () {
            $(".popup-prompt").slideUp();
        });

        //初期化DWR客户端处理
        this.initDwr();
    },

    //初期化DWR客户端处理
    initDwr: function () {
        try {
            dwr.engine.setActiveReverseAjax(true);
            dwr.engine.setNotifyServerOnPageUnload(true);

            //load时把用户放入dwr session
            this.onPageLoad();
        } catch (e) {
        }
    },

    onPageLoad: function () {
        MessagePushService.onPageLoad(this.userUid);
        //未读件数获取
        this.getUnreadCount();
    },

    getUnreadCount: function () {
        AjaxIns.sendPOST(this.getUnreadCountController, this.getUnreadCountCallback.bind(this));
    },

    getUnreadCountCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        this.setUnreadCount(ajaxResult.content);
    },

    setUnreadCount: function (recordCount) {
        if (parseInt(recordCount) >= 100) {
            $("#spanUnreadCounts").html("99+");
        }
        else {
            $("#spanUnreadCounts").html(recordCount);
        }
    },

    showNoticeMessage: function (msg) {
        var obj = JsonUtility.Parse(msg);
        //如果有消息返回则显示
        if (this.isNotEmpty(obj) && this.isNotEmpty(obj.uid)) {
            //this.showInfoMessage("您有未读消息[" + obj.messageTitle + "]；<br/>请点击消息提示栏查看详细。");

            $(".popup-prompt-title").html(obj.messageTitle);
            $(".popup-prompt-content").html(obj.messageContent);

            $(".popup-prompt").slideDown();
            setTimeout(function () {
                $(".popup-prompt").slideUp();
            }, 15000);

            this.setUnreadCount(obj.currentUserUnreadCount);
        }
    },

    showSubMenu: function (className) {
        this.closeSubMenu();
        $(className).show();
        return false;
    },

    closeSubMenu: function () {
        $(".personal-setting").hide();
        $(".top-menu-report").hide();
    },

    showMessageDetailPopupList: function () {
        this.messageDetailPopupIns = SysApp.Cmn.MessageDetailPopupListIns;
        this.messageDetailPopupIns.show();
        this.messageDetailPopupIns.getList(true);
    }
});
//Header.js类文件定义结束----------------------------------------------------------------//


//Help.js类文件定义开始------------------------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.Help = Class.create();

SysApp.Cmn.Help.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //初期化回调处理
    initCallback: function () {
        this.popupPosition = "firstTop";

        //JS实例对象字符串
        this.instanceString = this.pageInstance;

        //编辑事件
        this.bindEvent("btnEdit", "click", this.onClick_Edit.bind(this));
    },

    showCallback: function () {
        $(this.getFrame()).width($M.getClientWidth() - 100);
        $(this.getFrame()).css("left", "50px");
        this.$("divParameters").height($M.getClientHeight() - 145);

        //非【技术支持】及【系统管理员】权限的场合，直接显示帮助内容后处理中止
        if (!this.isItSupport && !this.isSystemManagement) {
            this.$("divParameters").css("overflow-y", "auto");
            this.$("divParameters").html(this.inputEntity.parameters);
            return;
        }

        //每次显示的时候，都重新创建UEditor
        var content = this.isNotNull(this.editor.parameters) ? this.editor.parameters.getContent() : this.inputEntity.parameters;
        if (this.isNotNull(this.editor.parameters)) {
            UE.delEditor("parameters");
        }
        this.$("divParameters").html("<textarea id='parameters'></textarea>");
        this.$("parameters").val(content);
        //初始化百度编辑器
        this.initUEditor("parameters");

        var thisObj = this;
        //编辑器加载完成后，默认设置为不可修改，设置高度,隐藏工具栏
        this.editor.parameters.addListener('ready', function () {
            //高度
            this.setHeight($M.getClientHeight() - 145);
            //判断关闭前页面状态-编辑/显示
            if (thisObj.isHelpEditMode) {
                //可修改
                this.setEnabled();
                //显示工具栏
                $(".edui-editor-toolbarboxouter").show();
                $(".edui-editor-bottomContainer").show();
                //设置编辑器的高度(初始为编辑时)
                this.setHeight(this.container.clientHeight - 5);
            }
            else {
                //不可修改
                this.setDisabled();
                //隐藏工具栏
                $(".edui-editor-toolbarboxouter").hide();
                $(".edui-editor-bottomContainer").hide();
            }

        });
    },

    //画面DOM初期化前处理
    initInputFormBefore: function () {

    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        this.$("Help_ctlTitle").html("操作说明");
        //隐藏保存按钮
        if (!this.isHelpEditMode) {
            this.displayDom("btnSave", false);
        }
    },

    onClick_Edit: function () {
        //处理显示
        this.displayDom("btnSave", true);
        this.displayDom("btnEdit", false);

        //富文本可编辑，显示工具栏
        this.editor.parameters.enable();
        //隐藏工具栏
        $(".edui-editor-toolbarboxouter").show();
        $(".edui-editor-bottomContainer").show();

        this.isHelpEditMode = true;
        //设置编辑器的高度(编辑时)
        this.editor.parameters.setHeight(this.editor.parameters.container.clientHeight - 200);
    },

    //最大化窗口大小方法回调处理
    windowMaximizationCallback: function (isMaximization) {
        //最大化的场合
        if (isMaximization) {
            //编辑器编辑模式的场合
            if (this.isHelpEditMode) {
                this.editor.parameters.setHeight($M.getClientHeight() - 140 - 80);
            }
            else {
                this.editor.parameters.setHeight($M.getClientHeight() - 140);
            }
        }
        else {
            //编辑器编辑模式的场合
            if (this.isHelpEditMode) {
                this.editor.parameters.setHeight(this.windowProperty.height - 140 - 100);
            }
            else {
                this.editor.parameters.setHeight(this.windowProperty.height - 140);
            }
        }
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //配置参数
        this.inputEntity.parameters = this.editor.parameters.getContent();
        //画面标识
        this.inputEntity.pageInstance = this.instanceString;
        //画面模式存在的场合，附加到列表画面JS实例对象字符串后
        if (this.isNotEmpty(this.pageMode)) {
            this.inputEntity.pageInstance = this.instanceString + "_" + this.pageMode;
        }
        //设置自定义类型为画面帮助内容设定 4
        this.inputEntity.settingType = "4";
    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //验证后处理
    validateFormAfter: function () {
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    },

    //画面内容保存[Callback后处理]
    saveDataCallback: function (response) {
        //Ajax错误处理(会话超时 OR 系统异常)
        if ($M.isAjaxFail(response.responseText)) {
            return;
        }

        var ajaxResult = JsonUtility.Parse(response.responseText);

        //处理失败
        if (ajaxResult.result == -1) {
            this.showMessage(ajaxResult.message, MSG_TYPE_ERROR);
        }
        else {
            SysCmn.CmnMsg.hide(true);
        }

        //处理显示
        this.displayDom("btnEdit", true);
        this.displayDom("btnSave", false);

        //禁用编辑，隐藏工具栏
        this.editor.parameters.disable();
        $(".edui-editor-toolbarboxouter").hide();
        $(".edui-editor-bottomContainer").hide();

        this.isHelpEditMode = false;
        //设置编辑器的高度(保存成功后)
        this.editor.parameters.setHeight(this.editor.parameters.container.clientHeight + 100);
    }
});
//Help.js类文件定义结束------------------------------------------------------------------//


//Menu.js类文件定义开始------------------------------------------------------------------//
$(function () {
    //操作菜单
    if ($.fn.dcAccordion) {
        $('#nav-accordion').dcAccordion({
            eventType: 'click',
            autoClose: true,
            saveState: true,
            disableLink: true,
            speed: 'fast',
            showCount: false,
            autoExpand: true,
            classExpand: 'dcjq-current-parent'
        });
    }

    //左侧菜单收缩状态的场合
    if ($.cookie("left_menu_expanded") == "true") {
        $(".sidebar").toggleClass("left-menu-mini");
        $(".sidebar-expand>a>i").toggleClass("mini-icon-shut mini-icon-open");
    }

    var h = $(window).height();

    //侧导航高度
    $(".sidebar-border").height(h - 50);

    //内容区高度
    $(".wrapper").height(h - 80);

    //点击横向折叠
    $(".sidebar-expand").click(function () {
        $(".sidebar").toggleClass("left-menu-mini");

        $(".sidebar-expand>a>i").toggleClass("mini-icon-shut mini-icon-open");

        var expanded = $(this).parentsUntil("sidebar").hasClass("left-menu-mini");
        $.cookie("left_menu_expanded", expanded);
    });

    $(".dcjq-parent").click(function () {
        //清空所有节点的展开状态图标
        $(".dcjq-parent").each(function () {
            $("i", this).addClass("fa-caret-right");
            $("i", this).removeClass("fa-caret-down");
        });

        //设置Active样式的展开状态图标
        if ($(this).hasClass("active")) {
            $("i", this).addClass("fa-caret-down");
            $("i", this).removeClass("fa-caret-right");
        }
    });

    //画面初次加载时设置默认展开状态图标
    $(".dcjq-parent").each(function () {
        if ($(this).hasClass("active")) {
            $("i", this).addClass("fa-caret-down");
            $("i", this).removeClass("fa-caret-right");
        }
        else {
            $("i", this).addClass("fa-caret-right");
            $("i", this).removeClass("fa-caret-down");
        }
    });

    //鼠标经过提示信息
    $(".sidebar-menu li a").mouseover(function () {
        var promptTip = $(this).children("span").text();
        var promptTop = $(this).offset().top;
        $(".sidebar-nav-prompt em").text(promptTip).parent().css('top', promptTop + 3).show();
        if ($(".sidebar").hasClass("left-menu-mini")) {
            $(".sidebar-nav-prompt").css('left', "55px");
        } else {
            $(".sidebar-nav-prompt").css('left', "155px");
        }
    });


    $(".sidebar-menu li a").mouseout(function () {
        $(".sidebar-nav-prompt").hide();
    });

    //二级折叠菜单展开及收缩功能
    $(".sidebar-sub-menu ol").hide().prev("a").css("cursor","default").find("i").removeClass("fa-angle-down").addClass("fa-angle-right");
    $(".sidebar-sub-menu ol li.active").parents("ol").show().prev("a").find("i").removeClass("fa-angle-right").addClass("fa-angle-down");
    $(".sidebar-sub-menu li li a").click(function () {
        if ($(this).next("ol").css("display") == "none") {
            $(this).next("ol").slideDown();
            $(this).find("i").removeClass("fa-angle-right").addClass("fa-angle-down");
        } else if($(this).next("ol").css("display") == "block") {
            $(this).next("ol").slideUp();
            $(this).find("i").removeClass("fa-angle-down").addClass("fa-angle-right");
        }
    });
});
//Menu.js类文件定义结束------------------------------------------------------------------//


//MultiTabView.js类文件定义开始----------------------------------------------------------//
/*
 {
 "TabsId": "MultiTabViewInstance_Tabs",
 "ViewsId": "MultiTabViewInstance_Views",
 "ActiveViewIndex": 0,
 "CssClass": "tmv-customize",
 "DefaultViewCssClassTag": "Customize",
 "TabViewCssClasses": [
 {
 "Tag": "SystemAdmin",
 "EnableOut": "enableout",
 "EnableOver": "enableover",
 "Active": "active",
 "Unable": "unable"
 },
 {
 "Tag": "ApplcationAdmin",
 "EnableOut": "enableout",
 "EnableOver": "enableover",
 "Active": "active",
 "Unable": "unable"
 },
 {
 "Tag": "Application",
 "EnableOut": "enableout",
 "EnableOver": "enableover",
 "Active": "active",
 "Unable": "unable"
 },
 {
 "Tag": "Customize",
 "EnableOut": "enableout",
 "EnableOver": "enableover",
 "Active": "active",
 "Unable": "unable"
 }
 ],
 "TabViews": [
 {
 "TabId": "MultiTabViewInstance_Tab0",
 "ViewId": "MultiTabViewInstance_View0",
 "CssClassTag": "",
 "Unable": false
 },
 {
 "TabId": "MultiTabViewInstance_Tab1",
 "ViewId": "MultiTabViewInstance_View1",
 "CssClassTag": "",
 "Unable": false
 }
 ]
 }
 */
//类名：
//    SysTabMultiView控件Client端类 
//    
//事件接口：
//    this.OnActiveViewChanged, 活动View改变之前发生的事件句柄, 出参：(SysTabView数据对象, SysTabView索引值).
//    
//方法接口:
//    this.setViewUnable(index), 设置View为无效状态 
//    this.setViewEnable(index), 设置View为有效状态 
//    this.setViewActive(index), 设置View为活动状态 

//实例化：
//    SysTabMultiView控件在"Client"模式的场合，自动产生一个Client端实例(控件HTML代码尾部).
//    通过<SysTabMultiView控件ID>.GetJSInstance()方法，取得Client端实例名.

/// <summary>
/// SysTabMultiView控件Client端类 
/// 构造参数：controlId -- 控件UniqueID
///           tabCount -- Tab个数
/// <summary>
SysTabMultiViewClientClass = function (controlId, tabCount, autoAdjustTabWidth) {
    this.controlId = controlId;
    this.tabCount = tabCount;
    this.autoAdjustTabWidth = autoAdjustTabWidth;

    //初试化Tab数据
    this.data = {
        "TabsId": this.controlId + "_Tabs",
        "ViewsId": this.controlId + "_Views",
        "ActiveViewIndex": 0,
        "CssClass": "tmv-customize",
        "DefaultViewCssClassTag": "Customize",
        "TabViewCssClasses": [
            {
                "Tag": "Customize",
                "EnableOut": "enableout",
                "EnableOver": "enableover",
                "Active": "active",
                "Unable": "unable"
            }
        ],
        "TabViews": []
    };

    for (var i = 0; i < this.tabCount; i++) {
        this.data["TabViews"].push({
            "TabId": this.controlId + "_Tab" + i,
            "ViewId": this.controlId + "_View" + i,
            "CssClassTag": "",
            "Unable": false
        });
    }

    //OnActiveViewChanged事件句柄，外部使用
    //出参：(SysTabView数据对象, SysTabView索引值)
    this.OnActiveViewChanged = null;

    //初始化时，数据拆箱 
    this._unboxJsonData();

    this.adjustTabWidth();
    this.bindEvent();
}

SysTabMultiViewClientClass.prototype = {
    /// <summary>
    /// 状态对象 
    /// <summary>
    State: {
        EnableOut: "1",
        EnableOver: "2",
        Active: "3",
        Unable: "4",

        /// <summary>
        /// 验证状态值 
        /// <summary>
        isValid: function (state) {
            if (state == this.EnableOut || state == this.EnableOver || state == this.Active || state == this.Unable) {
                return true;
            } else {
                return false;
            }
        },

        /// <summary>
        /// 初期化状态对象 
        /// <summary>
        initialize: function (cssClasses, defaultTag) {
            this.cssClasses = cssClasses;
            this.defaultTag = defaultTag;
        },

        /// <summary>
        /// 取得状态样式 
        /// <summary>
        getCssClass: function (tag, state) {
            function getClassName(viewCssClass, state) {
                var name = "";
                switch (state) {
                    case this.EnableOut:
                        name = viewCssClass.EnableOut;
                        break;
                    case this.EnableOver:
                        name = viewCssClass.EnableOver;
                        break;
                    case this.Active:
                        name = viewCssClass.Active;
                        break;
                    case this.Unable:
                        name = viewCssClass.Unable;
                        break;
                }

                return (!!name) ? name : "";
            };

            var cssClass = "";
            var defaultIndex = -1, tagIndex = -1;

            for (var i = 0; i < this.cssClasses.length; i++) {
                if (this.cssClasses[i].Tag == this.defaultTag) {
                    defaultIndex = i;
                }
                if (this.cssClasses[i].Tag == tag) {
                    tagIndex = i;
                }
            }

            if (tagIndex != -1)
                cssClass = getClassName.apply(this, [this.cssClasses[tagIndex], state]);
            if (!cssClass && defaultIndex != -1)
                cssClass = getClassName.apply(this, [this.cssClasses[defaultIndex], state]);

            return cssClass;
        }
    },

    /// <summary>
    /// View的onmouseover鼠标事件响应方法
    /// <summary>
    _onViewMouseover: function (eventArgument) {
        var index = parseInt(eventArgument, 10);
        if (this.data.TabViews[index].Unable || this.data.ActiveViewIndex == index) {
            return false;
        }
        this._setViewState(this.State.EnableOver, index);
    },

    /// <summary>
    /// View的onmouseout鼠标事件响应方法
    /// <summary>
    _onViewMouseout: function (eventArgument) {
        var index = parseInt(eventArgument, 10);
        if (this.data.TabViews[index].Unable || this.data.ActiveViewIndex == index) {
            return false;
        }

        this._setViewState(this.State.EnableOut, index);
    },

    /// <summary>
    /// View的onclick鼠标事件响应方法
    /// <summary>
    _onActiveViewChanged: function (eventArgument) {
        var index = parseInt(eventArgument, 10);
        this.setViewActive(index);
    },

    /// <summary>
    /// 数据装箱 
    /// <summary>
    _boxJsonData: function () {
        var jsonString = JsonUtility.ToJSON(this.data);
    },

    /// <summary>
    /// 数据拆箱 
    /// <summary>
    _unboxJsonData: function () {
        this.State.initialize(this.data.TabViewCssClasses, this.data.DefaultViewCssClassTag);

        for (var i = 0; i < this.data.TabViews.length; i++) {
            if (this.data.TabViews[i].Unable) {
                this._setViewState(this.State.Unable, i);
            } else {
                if (this.data.ActiveViewIndex == i) {
                    this._setViewState(this.State.Active, i);
                } else {
                    this._setViewState(this.State.EnableOut, i);
                }
            }
        }
    },

    /// <summary>
    /// 根据Dom对象取得对应的View数据对象  
    /// <summary>
    _getViewByDom: function (element) {
        while (!!element) {
            for (var i = 0; i < this.data.TabViews.length; i++) {
                if (!!element.id && element.id == this.data.TabViews[i].TabId) {
                    return this.data.TabViews[i];
                }
            }
            element = element.parentNode;
        }
        return null;
    },

    /// <summary>
    /// 设置View的状态 
    /// 参数：state -- 状态常量 
    ///       index -- View索引值
    /// <summary>
    _setViewState: function (state, index) {
        var view = this.data.TabViews[index];
        var tabDom = document.getElementById(view.TabId);
        var viewDom = document.getElementById(view.ViewId);
        tabDom.className = this.State.getCssClass(view.CssClassTag, state);
        if (!$M.isNull(viewDom)) {
            viewDom.style.display = (index == this.data.ActiveViewIndex) ? "" : "none";
        }
    },

    /// <summary>
    /// 设置View为无效状态 
    /// <summary>
    setViewUnable: function (index) {
        if (!this.data.TabViews[index]) {
            return false;
        }
        if (this.data.TabViews[index].Unable) {
            return false;
        }

        this._setViewState(this.State.Unable, index);
        this.data.TabViews[index].Unable = true;
        this._boxJsonData();
    },

    /// <summary>
    /// 设置View为有效状态 
    /// <summary>
    setViewEnable: function (index) {
        if (!this.data.TabViews[index]) {
            return false;
        }
        if (!this.data.TabViews[index].Unable) {
            return false;
        }

        this._setViewState(this.State.EnableOut, index);
        this.data.TabViews[index].Unable = false;
        this._boxJsonData();
    },

    /// <summary>
    /// 设置View为活动状态 
    /// <summary>
    setViewActive: function (index) {
        if (!this.data.TabViews[index]) {
            return false;
        }
        if (this.data.TabViews[index].Unable) {
            return false;
        }
        if (this.data.ActiveViewIndex == index) {
            return false;
        }

        var activeIndex = this.data.ActiveViewIndex;
        this.data.ActiveViewIndex = index;
        this._setViewState(this.State.EnableOut, activeIndex);
        this._setViewState(this.State.Active, index);
        this._boxJsonData();

        if (!!this.OnActiveViewChanged) {
            if (this.OnActiveViewChanged(this.data.TabViews[index], index) == false) {
                return false;
            }
        }
    },

    bindEvent: function () {
        if (this.tabCount == 0) {
            return;
        }

        for (var i = 0; i < this.tabCount; i++) {
            var tab = $E(this.controlId + "_Tab" + i);
            Event.observe(tab, 'mouseover', this._onViewMouseover.bind(this, i))
            Event.observe(tab, 'mouseout', this._onViewMouseout.bind(this, i))
            Event.observe(tab, 'click', this._onActiveViewChanged.bind(this, i))
        }
    },

    adjustTabWidth: function () {
        if (this.tabCount == 0) {
            return;
        }

        var width = 0;
        for (var i = 0; i < this.tabCount; i++) {
            width += Element.getDimensions($E(this.controlId + "_Tab" + i)).width
        }

        if (this.autoAdjustTabWidth && width > 100) {
            $E(this.controlId + "_Tabs_LiContainer").style.width = (width + 50) + "px";
        }
    },

    //客户端tab动态追加
    append: function (tabName, mode) {
        var tabId = this.controlId + "_Tab" + this.tabCount;
        var viewId = this.controlId + "_View" + this.tabCount;

        //前追加的场合
        if (mode == "before") {
            $($E(this.controlId + "_Tabs_LiContainer")).prepend("<li class='enableout' id='" + tabId + "'>" + tabName + "</li>");
        }
        else {
            $($E(this.controlId + "_Tabs_LiContainer")).append("<li class='enableout' id='" + tabId + "'>" + tabName + "</li>");
        }

        $($E(this.controlId + "_Views")).append("<div id='" + viewId + "' style='display:none;'></div>");

        //构建Tab对象数据
        var view = {"TabId": tabId, "ViewId": viewId, "CssClassTag": "", "Unable": false};
        this.data.TabViews.push(view);

        //绑定事件
        var tab = $E(tabId);
        Event.observe(tab, 'mouseover', this._onViewMouseover.bind(this, this.tabCount))
        Event.observe(tab, 'mouseout', this._onViewMouseout.bind(this, this.tabCount))
        Event.observe(tab, 'click', this._onActiveViewChanged.bind(this, this.tabCount))

        this.tabCount++;

        this.adjustTabWidth();
    }
}

//其他说明： 
// 
//客户端与服务器端的数据交换格式，传递方式：Json <--> Object
//SysTabMultiView
//{
//   TabsId:(string),
//   ViewsId:(string),
//   ActiveViewIndex:(int),
//   CssClass:(string),
//   DefaultViewCssClassTag:(string),
//   TabViewCssClasses:
//   [
//       {Tag:(string), EnableOut:(string), EnableOver:(string), Active:(string), Unable:(string)},
//       ......
//       {Tag:(string), EnableOut:(string), EnableOver:(string), Active:(string), Unable:(string)},
//   ],
//   TabViews:
//   [
//       {TabId:(string), ViewId:(string), CssClassTag:(string), Unable:(bool)},
//       ......
//       {TabId:(string), ViewId:(string), CssClassTag:(string), Unable:(bool)}
//   ]
//}
//MultiTabView.js类文件定义结束----------------------------------------------------------//


//PagerSetting.js类文件定义开始----------------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.PagerSetting = Class.create();

SysApp.Cmn.PagerSetting.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },
    
    //初期化回调处理
    initCallback: function () {
        this.showSaveDataSuccessMessage = false;

        //JS实例对象字符串
        this.instanceString = this.pageInstance;
        //取页面自定义条数列表，为空则使用默认条数列表
        if (this.isEmpty(this.pageListParams)) {
            this.pageListParams = [10, 20, 50, 100];
        } else {
            this.pageListParams = this.pageListParams.split(",");
        }
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        var thisObj = this;
        var html = '<div class="btn-group-xs">';
        $.each(this.pageListParams, function (index, item) {
            var btnClass = thisObj.pageSize == item ? 'btn-primary' : 'btn-default'
            html += '<div class="btn ' + btnClass + '" onclick= "' + thisObj.selfInstance + '.toggleSelectPage(' + "this" + ')" style="vertical-align: baseline">' + item + '</div>'
        });
        html += '&nbsp;<span><b>条/页</b></span></div>'
        $(this.getDom("divPagerSetting")).append(html);
    },

    toggleSelectPage: function (item) {
        this.pageInstance = eval(this.pageInstance);

        var btnPagerSetting = eval(this.getDom("btnPagerSetting"));
        $(item).parent()[0].select("div").each(function (element) {
            $(element).attr("class", "btn btn-default");
        });
        $(item).attr("class", "btn btn-primary");
        var currentPageSize = $.trim($(item).text());
        this.pageInstance.setPageSize(currentPageSize);

        this.pageInstance.onClick_Search();
        //设置类型(3：每页显示记录数自定义设置)
        this.inputEntity.settingType = 3;
        this.inputEntity.title = "每页显示记录条数";
        this.inputEntity.pageInstance = this.instanceString;

        //画面模式存在的场合，附加到列表画面JS实例对象字符串后
        if (this.isNotEmpty(this.pageMode)) {
            this.inputEntity.pageInstance = this.instanceString + "_" + this.pageMode;
        }
        this.inputEntity.parameters = currentPageSize;
        this.saveData();
    }
});
//PagerSetting.js类文件定义结束----------------------------------------------------------//


//ProgressModal.js类文件定义开始---------------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.ProgressModal = Class.create();

SysApp.Cmn.ProgressModal.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //初期化回调处理
    initCallback: function () {
        //绑定关闭按钮
        this.bindEvent("btnCloseProgressModal", "click", this.closeProgressModal.bind(this));

        if (this.isEmpty(this.delayTime)) {
            this.delayTime = 2000;
        }
        else {
            this.delayTime = this.parseInt(this.delayTime);
        }
    },

    //进度条关闭事件 清空数据
    closeProgressModal: function () {
        //清空记录
        this.$("progressBar").css("width", "0px");
        this.$("progressBar").text("");
        this.$("messageContent").css("color", "#008BB9");
        this.$("messageContent").empty();
        this.hide();
    },

    //启动进度条
    start: function (progressId, title) {
        this.progressId = progressId;
        //设置筋进度条自定义Title
        if (this.isNotEmpty(title)) {
            this.$("ctlTitle").html(title);
        }

        this.show();
        console.log("启动进度：" + this.progressId);

        this.$("messageContent").text("进度信息处理中，请耐心等待。");

        this.interval = setInterval(this.getProgressDetail.bind(this), this.delayTime);
    },

    //取得进度详细
    getProgressDetail: function () {
        var params = {progressId: this.progressId};
        AjaxIns.sendPOST(this.ajaxUrl + "/detail", this.getProgressDetailCallback.bind(this), params);
    },

    //取得进度详细[回调方法]
    getProgressDetailCallback: function (response) {
        var ajaxResult = response.responseJSON;

        if (ajaxResult.result > 0) {
            //进度条信息
            var processInfo = JsonUtility.Parse(ajaxResult.content);
            //进度参数不存在场合，直接返回
            if (this.isEmpty(processInfo.totalNum)) {
                return;
            }
            var progressBar = this.getDom("progressBar");
            //计算进度并设置进度文字等
            var percent = processInfo.currentNum * 100 / processInfo.totalNum + "%";
            $(progressBar).attr("style", "min-width: 120px;width: " + percent);
            $(progressBar).text(processInfo.progressText);

            var messageContent = this.$("messageContent");
            //正常消息提示
            if (processInfo.messageType == 1) {
                messageContent.css("color", "#008BB9");
            }
            //警告消息提示
            else if (processInfo.messageType == 2) {
                messageContent.css("color", "#ED8600");
            }
            //错误消息提示
            else if (processInfo.messageType == 3) {
                messageContent.css("color", "#E96A59");
            }
            messageContent.text(processInfo.messageContent);

            //读取到的总进度为0，进度条充满
            if (processInfo.progressStatus == 1 && processInfo.currentNum == 0 && processInfo.totalNum == 0) {
                $(progressBar).attr("style", "min-width: 120px;width: 100%");
                this.clearInterval();
            }
            else if (processInfo.progressStatus == 1) {
                $(progressBar).attr("style", "min-width: 120px;width: 100%");
                this.clearInterval();
            }
        } else {
            this.clearInterval();
            this.showErrorMessage(ajaxResult.message);
        }
    },

    //清除进度条定时任务
    clearInterval: function () {
        clearInterval(this.interval);
        this.removeProgress();
    },

    //清除进度
    removeProgress: function () {
        if (this.isEmpty(this.progressId)) {
            return;
        }
        var params = {progressId: this.progressId};
        AjaxIns.sendPOST(this.ajaxUrl + "/removeProgress", this.removeProgressCallback.bind(this), params);
    },

    //清除进度[回调方法]
    removeProgressCallback: function (response) {
        var ajaxResult = response.responseJSON;

        if (ajaxResult.result > 0) {
            console.log("移除进度：" + this.progressId);
        } else {
            this.showErrorMessage(ajaxResult.message);
        }
    }
});
//ProgressModal.js类文件定义结束---------------------------------------------------------//


//SearchSetting.js类文件定义开始---------------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.SearchSetting = Class.create();

SysApp.Cmn.SearchSetting.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //初期化回调处理
    initCallback: function () {
        //JS实例对象字符串
        this.instanceString = this.pageInstance;

        this.showSaveDataSuccessMessage = false;

        this.setValue("pageInstance", this.pageInstance);

        //自定义宽度设置的场合
        if (this.isNotEmpty(this.width)) {
            $(this.getFrame()).css("width", this.width);
        }

        //检索条件Index(默认第一个)
        this.searchIndex = 1;

        this.saveConfirmMessage = "确定要将当前设定作为默认检索条件吗？";
    },

    //客户化数据保存回调方法
    customSaveDataCallback: function (ajaxResult) {
        //添加模式的场合，重置UID
        if (this.isEmpty(this.inputEntity.uid)) {
            this.inputEntity.uid = ajaxResult.pk;
        }

        //重置当前选择对象
        var jsonSearchSetting = this.pageInstance.getJsonSearchSetting();
        jsonSearchSetting[this.searchIndex - 1] = this.inputEntity;

        //重新初始化检索条件
        this.pageInstance.initSearchSetting();
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        this.$("ctlTitle").html("检索条件自定义设定");

        //父级画面
        this.pageInstance = eval(this.pageInstance);
        var pageInstance = this.pageInstance;
        var thisObj = this;

        //检索form
        var mainForm = pageInstance.getDom("MainForm");
        var searchList = {};

        //查找折叠功能内的查询条件
        $.each(mainForm.select(".popovers > button"), function (index, button) {
            var container = pageInstance.getDom($(button).data("container"));
            $.each(container.select("input[type='text'], select"), function (index, item) {
                if (pageInstance.isSearchCondition(item)) {
                    if ($(item).data("title")) {
                        searchList[$(item).attr("id")] = {title: $(item).data("title"), label: $.trim($(button).text())};
                    }
                }
            });
        });

        //填充默认条件
        $.each(mainForm.select("input[type='text'], select"), function (index, item) {
            if (pageInstance.isSearchCondition(item)) {
                if ($(item).data("title") && !searchList[$(item).attr("id")]) {
                    searchList[$(item).attr("id")] = {title: $(item).data("title"), label: ""};
                }
            }
        });

        //清空当前检索标签
        var searchSettingList = $(this.getDom("searchSettingList"))[0];
        $(this.getDom("searchSettingList")).empty();

        $.each(searchList, function (colName, colInfo) {
            var checked = "";
            var disabled = "";

            if (thisObj.$(colName).data("required") == true) {
                checked = " checked=\"true\"";
                disabled = " disabled=\"true\"";
            }
            var html = '<div style="width:180px;float: left;text-overflow:ellipsis;overflow: hidden;">' +
                '<input class="search-setting-list" id="search_' + colName + '" type="checkbox"' + checked + disabled + '/>' +
                '<label for="search_' + colName + '">' + colInfo.title + '</label></div>';

            if (searchSettingList.select(".screen-title > :contains(基本查询)").length == 0) {
                var baseHtml = '<div class="screen-title"><h4 onclick="' + thisObj.selfInstance + '.toggleInputCheck(' + "this" + ')">基本查询</h4><span></span></div><div class="screen-content clearfix"></div>';
                $(searchSettingList).append(baseHtml);
            }

            var targetDom;
            if (thisObj.isEmpty(colInfo.label)) {
                //基本查询
                targetDom = $(searchSettingList.select(".screen-title >:contains(基本查询)")).parent().next();
            }
            else {
                if (searchSettingList.select(".screen-title > :contains(" + colInfo.label + ")").length == 0) {
                    var extHtml = '<div class="screen-title"><h4 onclick="' + thisObj.selfInstance + '.toggleInputCheck(' + "this" + ')">' + colInfo.label + '</h4><span></span></div><div class="screen-content clearfix"></div>';
                    $(searchSettingList).append(extHtml);
                }
                targetDom = $(searchSettingList.select(".screen-title > :contains(" + colInfo.label + ")")).parent().next();
            }
            targetDom.append(html);
        });

        //增加全选择/全解除
        this.drawCheckAll();

        //触发一下检索条件变更
        this.changeSearchSetting(this.searchIndex);
    },

    /**
     * 切换分组条件内复选框的check状态
     * @param item 分组条件元素
     */
    toggleInputCheck: function (item) {
        item.parentNode.nextElementSibling.select('input').each(function (input) {
            //必须条件的场合
            if (input.disabled) return;

            input.checked = !input.checked;
        });
    },

    //绘制自定义检索条件标题
    drawSearchConditionTitle: function () {
        //清空标题
        $(this.getDom("searchConditionTitle")).empty();

        var jsonSearchSetting = this.pageInstance.getJsonSearchSetting();

        //默认最多5个检索条件设置
        for (var i = 0; i < 5; i++) {
            if (this.isNull(jsonSearchSetting[i])) {
                jsonSearchSetting[i] = new Object();
            }
            if (this.isNull(jsonSearchSetting[i].settingTitle)) {
                jsonSearchSetting[i].settingTitle = "自定义检索条件" + (i + 1);
            }
            var activeClass = (this.searchIndex == i + 1) ? "active" : "";
            $(this.getDom("searchConditionTitle")).append('<li class="' + activeClass + '"><a href="#" ' + 'onclick="' + this.selfInstance + '.changeSearchSetting(' + (i + 1) + ')">' + jsonSearchSetting[i].settingTitle + '</a></li>');
        }
    },

    //全选择/全解除 事件处理
    drawCheckAll: function () {
        this.bindEvent("checkAll", 'click', function () {
            var checked = this.checked;
            $(".search-setting-list").each(function () {
                //必须条件的场合
                if (this.disabled) return;

                this.checked = checked;
            });
        });
    },

    autoChecked: function () {
        //有自定义列设置的场合
        var parameters = JsonUtility.Parse(this.inputEntity.parameters);
        $(".search-setting-list").each(function (listIndex, listItem) {
            //必须条件的场合
            if (listItem.disabled) return;

            $(listItem).prop("checked", false);

            $.each(parameters, function (index, colName) {
                if (colName == $(listItem).attr("id")) {
                    $(listItem).prop("checked", true);
                }
            });
        });
    },

    changeSearchSetting: function (searchIndex) {
        this.searchIndex = searchIndex;

        //取得父级画面检索自定义配置
        var jsonSearchSetting = this.pageInstance.getJsonSearchSetting();

        //绘制自定义检索条件标题
        this.drawSearchConditionTitle();

        //取得当前实体内容
        this.inputEntity = jsonSearchSetting[this.searchIndex - 1];

        //变更标签内容
        this.setValue("settingTitle", this.inputEntity.settingTitle || "自定义检索条件" + this.searchIndex);

        //选中默认值
        this.autoChecked();
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //画面标识
        this.inputEntity.pageInstance = this.getValue("pageInstance");
        //标题设置
        this.inputEntity.settingTitle = this.getValue("settingTitle");
        //设置自定义类型为检索 2
        this.inputEntity.settingType = "2";

        //配置参数
        var parameters = [];

        $(".search-setting-list").each(function () {
            if (this.checked) {
                parameters.push(this.id);
            }
        });

        this.inputEntity.parameters = JSON.stringify(parameters);

        //画面模式存在的场合，附加到列表画面JS实例对象字符串后
        if (this.isNotEmpty(this.pageMode)) {
            this.inputEntity.pageInstance = this.instanceString + "_" + this.pageMode;
        }

        return this.inputEntity;
    },

    //验证前处理
    validateFormBefore: function () {
        var flag = false;
        $(".search-setting-list").each(function () {
            if (this.checked) {
                flag = true;
            }
        });

        if (flag == false) {
            this.showErrorMessage("请至少选择一个检索条件进行设置。")
            return false;
        }

        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});
//SearchSetting.js类文件定义结束---------------------------------------------------------//


//BlockSetting.js类文件定义开始----------------------------------------------------------//
//BlockSetting.js类文件定义开始---------------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.BlockSetting = Class.create();

SysApp.Cmn.BlockSetting.prototype = Object.extend(new SysCmn.CoreInput(), {
    //取得表结构的主键名称[注意：必须实现的方法]
    getPrimaryKey: function () {
        return "uid";
    },

    //初期化回调处理
    initCallback: function () {
        //JS实例对象字符串
        this.instanceString = this.pageInstance;

        this.showSaveDataSuccessMessage = false;

        this.setValue("pageInstance", this.pageInstance);

        //自定义宽度设置的场合
        if (this.isNotEmpty(this.width)) {
            $(this.getFrame()).css("width", this.width);
        }

        //iframe内部的场合
        if (self != top) {
            this.popupPosition = "firstTop";
            SysCmn.CmnMsg.popupPosition = "top";
        }

        this.saveConfirmMessage = "确定要保存当前设定吗？";
    },

    //客户化数据保存回调方法
    customSaveDataCallback: function (ajaxResult) {
        //页面刷新
        location.reload();
    },

    //重绘区块
    drawBlock: function () {
        var parameters = JsonUtility.Parse(this.inputEntity.parameters);

        //循环处理区块设定
        for (var i = 0; i < parameters.length; i++) {
            var parameter = parameters[i];

            //区块重绘
            this.pageInstance.displayDom(parameter.id, parameter.checked);
            //快捷导航重复
            this.pageInstance.displayDom(parameter.id + "Li", parameter.checked);
        }
    },

    /**********************以下方法为可选方法**********************/
    //画面DOM初期化前处理
    initInputFormBefore: function () {
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
        this.$("ctlTitle").html("区块自定义显示/隐藏设定");

        //父级画面
        this.pageInstance = eval(this.pageInstance);

        var blockSetting = this.pageInstance.getValue("jsonBlockSetting");

        //已设定场合
        if (this.isNotEmpty(blockSetting)) {
            this.inputEntity = JsonUtility.Parse(blockSetting)[0];
            //重绘区块
            this.drawBlock();
        }

        //清空内容
        $(this.getDom("settingTable")).empty();

        //动态加载列表
        this.customerTable();

        //开关插件设置
        $('[name="status"]').bootstrapSwitch({
            onText: "显示",
            offText: "隐藏",
            onColor: "success",
            offColor: "default",
            size: "small"
        });
    },

    //动态加载列表
    customerTable: function () {
        var html = '<tr>' +
            '<th width="5%">序号</th>' +
            '<th width="30%">功能模块名称</th>' +
            '<th width="50%">功能模块描述</th>' +
            '<th width="15%">显示/隐藏设置</th>' +
            '</tr>';
        var index = 1;
        var thisObj = this;

        //获取id为divBlock开头的div
        $("div[id^='divBlock']").each(function () {
            html += "<tr>" +
                '<td style="text-align: center;line-height: 30px;">' + index + '</td>' +
                '<td style="line-height: 30px;">' + thisObj.$(this.id).data('title') + '</td>' +
                '<td style="line-height: 30px;">' + thisObj.$(this.id).data('content') + '</td>';

            //uid为空场合
            if (thisObj.isNull(thisObj.inputEntity) || thisObj.isEmpty(thisObj.inputEntity.uid)) {
                html += '<td style="text-align: center"><input name="status" checked="true" type="checkbox" id=' + this.id + ' data-size="small"></td>';
            }
            //uid不为空场合
            else {
                //区块设定标识
                var isExist = false;
                var parameters = JsonUtility.Parse(thisObj.inputEntity.parameters);

                //循环处理区块设定
                for (var i = 0; i < parameters.length; i++) {
                    var parameter = parameters[i];
                    if (parameter.id == this.id) {
                        isExist = true;
                        //显示场合
                        if (parameter.checked) {
                            html += '<td style="text-align: center"><input name="status" checked="true" type="checkbox" id=' + this.id + ' data-size="small"></td>';
                        }
                        //隐藏场合
                        else {
                            html += '<td style="text-align: center"><input name="status" type="checkbox" id=' + this.id + ' data-size="small"></td>';
                        }
                    }
                }

                //区块为设定场合
                if (!isExist) {
                    html += '<td style="text-align: center"><input name="status" checked="true" type="checkbox" id=' + this.id + ' data-size="small"></td>';
                }
            }
            html += '</tr>';
            index++;
        });

        this.$("settingTable").append(html);
    },

    //Ajax提交前，InputEntity数据模型自定义设定
    customInputEntity: function () {
        //画面标识
        this.inputEntity.pageInstance = this.getValue("pageInstance");
        //设置自定义类型为 4
        this.inputEntity.settingType = this.settingType;

        //配置参数
        var parameters = [];

        //循环处理是否显示
        $("input[id^='divBlock']").each(function () {
            var blockDivObj = {
                id: this.id,
                checked: this.checked
            };
            parameters.push(blockDivObj);
        });

        this.inputEntity.parameters = JSON.stringify(parameters);

        //画面模式存在的场合，附加到列表画面JS实例对象字符串后
        if (this.isNotEmpty(this.pageMode)) {
            this.inputEntity.pageInstance = this.instanceString + "_" + this.pageMode;
        }

        return this.inputEntity;
    },

    //验证前处理
    validateFormBefore: function () {
        return true;
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        return "";
    }
});
//BlockSetting.js类文件定义结束----------------------------------------------------------//


//TimeSelect.js类文件定义开始------------------------------------------------------------//
// 定义数据输入画面JS类
SysApp.Cmn.TimeSelect = Class.create();

SysApp.Cmn.TimeSelect.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        //共通Property初期化
        this.initCommonProperty();
    },

    //初期化处理
    init: function () {
        //显示小时的场合
        if (this.showHour) {
            this.appendOption(this.getHourDomId(), "", "时");

            for (var i = this.fromHour; i <= this.toHour;) {
                var value = i;
                if (i < 10) {
                    value = "0" + i;
                }
                this.appendOption(this.getHourDomId(), value, value);
                i = i + this.stepHour;
            }
        }

        //显示分钟的场合
        if (this.showMinute) {
            this.appendOption(this.getMinuteDomId(), "", "分");
            for (var i = this.fromMinute; i <= this.toMinute;) {
                var value = i;
                if (i < 10) {
                    value = "0" + i;
                }
                this.appendOption(this.getMinuteDomId(), value, value);
                i = i + this.stepMinute;
            }
        }
    },

    //取得小时下拉选择DOMID
    getHourDomId: function () {
        return this.id + "Hour";
    },

    //取得分钟下拉选择DOMID
    getMinuteDomId: function () {
        return this.id + "Minute";
    },

    //设置选择值
    setSelectedValue: function (selectedValue) {
        var times = [];
        //13:30 的场合
        if (this.isNotEmpty(selectedValue) && selectedValue.length == 5 && selectedValue.indexOf(":") == 2) {
            times = selectedValue.split(":");
        }
        //yyyy/mm/dd hh:mm:ss的场合
        else if (this.isNotEmpty(selectedValue) && selectedValue.length > 5 && selectedValue.indexOf(" ") > 0) {
            selectedValue = selectedValue.split(" ")[1].substr(0, 5);
            times = selectedValue.split(":");
        }

        if (times.length == 2) {
            this.setValue(this.getHourDomId(), times[0]);
            this.setValue(this.getMinuteDomId(), times[1]);
        }
        else {
            this.setValue(this.getHourDomId(), "");
            this.setValue(this.getMinuteDomId(), "");
        }
    },

    //取得默认值
    getSelectedValue: function (endFlag) {
        if (this.isEmpty(endFlag)) endFlag = false;

        var hour = this.getValue(this.getHourDomId());
        var minute = this.getValue(this.getMinuteDomId());

        if (this.isEmpty(hour)) hour = endFlag ? "23" : "00";
        if (this.isEmpty(minute)) minute = endFlag ? "59" : "00";
        var second = endFlag ? "59" : "00";

        return hour + ":" + minute + ":" + second;
    },

    //有效化处理
    enabled: function (flag) {
        this.disabledDom(this.getHourDomId(), !flag);
        this.disabledDom(this.getMinuteDomId(), !flag);
    }
});
//TimeSelect.js类文件定义结束------------------------------------------------------------//


//TreeView.js类文件定义开始--------------------------------------------------------------//
SysApp.Cmn.TreeView = Class.create();

SysApp.Cmn.TreeView.prototype = Object.extend(new SysCmn.Core(), {
    initialize: function () {
        //共通Property初期化
        this.initCommonProperty();

        //无需初始化日历控件
        this.initCalendarFlag = false;
    },

    //初期化父画面JS实例对象
    initParentInstance: function () {
        this._initParentInstance();

        this.parentInterval = setInterval(this._initParentInstance.bind(this), 10);
    },

    _initParentInstance: function () {
        if (this.isNotNull(eval(this.parentInstanceString))) {
            clearInterval(this.parentInterval);

            //父画面JS实例对象
            this.parentInstance = eval(this.parentInstanceString);

            //给父画面JS实例对象设置Tree实例对象
            eval(this.parentInstanceString + "." + this.treeId + "Tree = this;");

            this.initTree();
        }
    },

    getTreeId: function () {
        return this.getId(this.treeId + "Tree");
    },

    //初始化树
    initTree: function (selectedValue, readOnly) {
        //readonly 属性
        this.readonly = false;
        if (this.isNotEmpty(readOnly)) {
            this.readonly = readOnly;
        }

        if (this.isNotEmpty(selectedValue)) {
            this.selectedValue = selectedValue;
        }

        //Ajax处理(POST方式) -- 树内容获取
        $.fn.zTree.init(this.$(this.getTreeId()),
            {
                view: {
                    dblClickExpand: false
                },
                async: {
                    enable: true,
                    type: "post",
                    dataType: "json",
                    url: this.ajaxUrl,
                    autoParam: []
                },
                data: {
                    key: {
                        name: this.nodeName
                    },
                    simpleData: {
                        enable: true,
                        idKey: this.idKey,
                        pIdKey: this.parentIdKey,
                        rootPid: ""
                    }
                },
                callback: {
                    beforeClick: this.beforeClick.bind(this),
                    onAsyncSuccess: this.zTreeOnAsyncSuccess.bind(this),
                    onClick: this.onClickTreeNode.bind(this),
                }
            }, null);
    },

    beforeClick: function (treeId, treeNode) {
        return true;
    },

    zTreeOnAsyncSuccess: function (event, treeId, treeNode, msg) {
        //取得Tree对象
        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());

        //默认打开第一层节点
        var nodes = zTree.getNodes();
        if (nodes.length > 0) {
            zTree.expandNode(nodes[0], true, false);

            //展开第二层节点
            var nodes = nodes[0].children;
            for (var i = 0, l = nodes.length; i < l; i++) {
                zTree.expandNode(nodes[i], true, false);
            }
        }

        //设置默认选择值
        if (this.isNotEmpty(this.selectedValue)) {
            this.selectNodeItem(this.selectedValue);
        }

        //回调父对象方法
        if (this.isFunction(this.parentInstance.initTreeCallback)) {
            this.parentInstance.initTreeCallback(this);
        }
    },

    onClickTreeNode: function (e, treeId, treeNode) {
        //如果是readonly 的，什么都不处理
        if (this.readonly) {
            return;
        }

        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
        var nodes = zTree.getSelectedNodes();
        var textDomId = "";
        var selectValue = "";

        nodes.sort(function compare(a, b) {
            return a[this.idKey] - b[this.idKey];
        });

        for (var i = 0, l = nodes.length; i < l; i++) {
            textDomId += nodes[i][this.dispName] + ",";
            selectValue += nodes[i][this.idKey] + ",";
        }
        if (textDomId.length > 0) {
            textDomId = textDomId.substring(0, textDomId.length - 1);
            selectValue = selectValue.substring(0, selectValue.length - 1);
        }

        //父对象实例存在 onClickTreeNodeCallback 方法的场合
        if (this.isFunction(this.parentInstance.onClickTreeNodeCallback)) {
            this.parentInstance.onClickTreeNodeCallback(selectValue, textDomId);
        }
    },

    //选择TreeView节点
    selectNodeItem: function (selectValue) {
        if (this.isEmpty(selectValue)) {
            return;
        }
        else {
            var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
            //如果指定值为空，默认打开第一个节点
            var nodes = zTree.getNodes();
            if (nodes.length > 0) {
                for (var i = 0, l = nodes.length; i < l; i++) {
                    //获取所有节点
                    this.selectSpecNode(zTree, nodes[i], selectValue);
                }
            }
        }
    },

    //选中value指定的node
    selectSpecNode: function (zTree, treeNode, selectValue) {
        if (treeNode.isParent) {
            if (treeNode[this.idKey] == selectValue) {
                var labelValue = treeNode[this.dispName];
                zTree.selectNode(treeNode, false, false);
                return;
            }
            var nodes = treeNode.children;
            for (var i = 0, l = nodes.length; i < l; i++) {
                this.selectSpecNode(zTree, nodes[i], selectValue);
            }
        }
        else {
            //叶子节点的时候
            if (treeNode[this.idKey] == selectValue) {
                var labelValue = treeNode[this.dispName];
                zTree.selectNode(treeNode, false, false);
                return;
            }

            return;
        }
    },

    //异步重新加载树
    reAsyncChildNodes: function (selectValue) {
        var zTree = $.fn.zTree.getZTreeObj(this.getTreeId());
        zTree.reAsyncChildNodes(null, "refresh");
        this.selectedValue = selectValue;
    }
});
//TreeView.js类文件定义结束--------------------------------------------------------------//


