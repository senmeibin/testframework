<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/student/StudentPopupList.js?${version}"></script>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content StudentPopupList-MainContent" style="width: 1200px; display: none;">
    <ctag:ModalHeader modalTitle="学员信息一览"></ctag:ModalHeader>

    <div class="modal-body">
        <button id="btnAdd" type="button" class="btn btn-primary">
            <i class="fa fa-plus"></i>新增学员
        </button>
        <div class="clear-both dashed-line">
        </div>
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm">
            <div class="col-md-2 form-group">
                <label>学员姓名</label>
                <input id="name" data-camel-field="true" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="学员姓名"/>
            </div>
            <div class="col-md-2 form-group">
                <label>性别</label>
                <select id="genderCd" data-alias-table="main" class="form-control" data-title="性别">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>照片</label>
                <input id="picture" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="照片"/>
            </div>
            <div class="col-md-2 form-group">
                <label>证件类型</label>
                <select id="cardTypeCd" data-alias-table="main" class="form-control" data-title="证件类型">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>证件号码</label>
                <input id="cardNumber" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="证件号码"/>
            </div>
            <div class="col-md-2 form-group">
                <label>出生年月(起)</label>
                <ctag:CalendarSelect id="birthday$from_search" camelField="true" title="出生年月(起)"></ctag:CalendarSelect>
            </div>
            <div class="col-md-2 form-group">
                <label>出生年月(止)</label>
                <ctag:CalendarSelect id="birthday$to_search" camelField="true" title="出生年月(止)"></ctag:CalendarSelect>
            </div>
            <div class="col-md-2 form-group">
                <label>围棋基础</label>
                <select id="baseLevelCd" data-alias-table="main" class="form-control" data-title="围棋基础">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>咨询方式</label>
                <select id="consultMethodCd" data-alias-table="main" class="form-control" data-title="咨询方式">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>信息来源</label>
                <select id="sourceTypeCd" data-alias-table="main" class="form-control" data-title="信息来源">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>家长姓名</label>
                <input id="parentName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="家长姓名"/>
            </div>
            <div class="col-md-2 form-group">
                <label>与学员关系</label>
                <select id="relationshipTypeCd" data-alias-table="main" class="form-control" data-title="与学员关系">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>手机号码</label>
                <input id="mobile" data-camel-field="true" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="手机号码"/>
            </div>
            <div class="col-md-2 form-group">
                <label>电子邮件</label>
                <input id="email" data-camel-field="true" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="电子邮件"/>
            </div>
            <div class="col-md-2 form-group">
                <label>家庭住址</label>
                <input id="homeAddress" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="家庭住址"/>
            </div>
            <div class="col-md-2 form-group">
                <label>邮政编码</label>
                <input id="zipCode" data-alias-table="main" type="text" maxlength="6" class="form-control" data-title="邮政编码"/>
            </div>
            <div class="col-md-2 form-group">
                <label>之前学校</label>
                <input id="beforeSchool" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="之前学校"/>
            </div>
            <div class="col-md-2 form-group">
                <label>学员状态</label>
                <select id="studentStatusCd" data-alias-table="main" class="form-control" data-title="学员状态">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="col-md-2 form-group">
                <label>备注</label>
                <input id="remark" data-camel-field="true" data-alias-table="main" type="text" maxlength="256" class="form-control" data-title="备注"/>
            </div>
            <div class="col-md-2 form-group" style="width: 300px;">
                <label>
                    &nbsp;
                </label>
                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                </div>
            </div>
        </form>
        <%--上次检索条件--%>
        <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Student}"/>
        <%--下拉框选项数据集合--%>
        <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Student}"/>
        <div class="margin-top-space" id="divList" style="width: 100%">
        </div>
    </div>

    <ctag:ModalFooter></ctag:ModalFooter>
</div>

<script type="text/javascript">
    //<![CDATA[
    function StudentPopupList_Page_Load() {
        SysApp.Crm.StudentPopupListIns = new SysApp.Crm.StudentPopupList();
        var instance = SysApp.Crm.StudentPopupListIns;

        instance.selfInstance = "SysApp.Crm.StudentPopupListIns";
        instance.clientID = "StudentPopupList";
        instance.tableName = "crm_student";
        instance.controller = "${ctx}/crm/student/";
        instance.entry = "${entry}";

        instance.init();
    }

    $(function () {
        StudentPopupList_Page_Load();
    });
    //]]>
</script>

