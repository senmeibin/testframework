//定义数据一览画面JS类
SysApp.Crm.WorkManualInnerList = Class.create();

SysApp.Crm.WorkManualInnerList.prototype = Object.extend(new SysApp.Crm.CommonList(), {
    //初期化回调处理
    initCallback: function () {
        this.initCommonCallback();
        this.autoSearch = true;
        this.bindEvent("btnSearchFileName", "click", this.getList.bind(this, true));
        this.datagrid.headerIns.sortColName = "insertDate";
        this.datagrid.headerIns.sortMode = "DESC";
    },

    //预览
    preview: function (uid) {
        SysApp.Cmn.FilePreviewIns.preview(uid);
        var thisObj = this;
        setTimeout(function () {
            thisObj.getList(true, false);
        }, 3000);
    },

    //自定义显示
    customCreateList: function (json) {
        var html = "";
        var thisObj = this;
        var dataList = json.content;

        //无数据场合
        if (dataList.length == 0) {
            this.$("divList").html("<div class='text-center data-not-exist'>暂无符合条件的工作手册</div>");
            this.$("divBottomPager").html("");
            return;
        }

        //循环处理
        dataList.each(function (manual) {
            var iconName = "no-data.png";
            if (manual.fileName.endsWith(".ppt") || manual.fileName.endsWith(".pptx") || manual.fileName.endsWith(".ppsm")) {
                iconName = "ppt-icon.jpg"
            } else if (manual.fileName.endsWith(".doc") || manual.fileName.endsWith(".docx")) {
                iconName = "word-icon.jpg"
            } else if (manual.fileName.endsWith(".xls") || manual.fileName.endsWith(".xlsx")) {
                iconName = "excel-icon.jpg"
            }

            html += '<li>' +
                '   <label class="client-logo" style="padding: 9px"><img style="height: 60px" src="/static/images/crm/' + iconName + '"></label>' +
                '   <div>' +
                '       <a data-title="预览附件" href="#" class="hover-underline title-prompt-box" data-placement="top" onclick="SysApp.Crm.WorkManualInnerListIns.preview(\'' + manual.uid + '\')"><strong>[' + manual.moduleName + ']' + manual.fileName + '</strong></a>' +
                '       <p class="clear-both">' + manual.updateDate.formatMDHM() + '</p>' +
                '       <p class="clear-both">' + manual.insertUserName + '</p>' +
                '   </div>' +
                '   <div class="desktop-innner-list-upper-right-corner"><span><i>' + manual.fileSize + '</i> </span> </div>' +
                '   <div class="desktop-innner-list-lower-right-corner"><span>预览 <i onclick="SysApp.Sys.AttachmentAccessLogPopupListIns.showAccessLogList(\'' + manual.uid + '\',0)">' + manual.previewCount + '</i> </span> </div>' +
                '</li>'
        });

        this.$("divList").html(html);
        this.datagrid.createPager(this.getDom("divBottomPager"));

        //工作手册-列表-滚动条-刷新
        window.iScrollWorkManualInnerList.refresh();

        //Title提示框
        this.titlePromptBox();
    },

    //Ajax提交前，附加客户化请求参数
    customRequestParams: function () {
        //检索参数
        var relationUid = this.getValue("workManualRelation");
        return "relationUid=" + relationUid;
    }
})