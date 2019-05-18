<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/demo/employee/EmployeeList.js?${version}"></script>

<title>人员信息表一览</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper EmployeeList-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title">人员信息表一览</div>
            <ctag:HelpIcon tips="操作说明"></ctag:HelpIcon>
            <ctag:SearchSettingIcon tips="检索条件自定义设定"></ctag:SearchSettingIcon>
            <ctag:ColumnSettingIcon tips="一览显示自定义列设定"></ctag:ColumnSettingIcon>
        </div>
        <div class="panel-body">
            <button id="btnAdd" type="button" class="btn btn-primary">
                <i class="fa fa-plus"></i>新增人员信息
            </button>
            <div class="clear-both dashed-line">
            </div>
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm">
                <div class="col-md-2 form-group">
                    <label>登录账号</label>
                    <input id="userCd" data-alias-table="main" type="text" maxlength="64" class="form-control" data-title="登录账号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>姓名</label>
                    <input id="userName" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="姓名"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>工号</label>
                    <input id="userNumber" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="工号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>性别</label>
                    <select id="sexCd" data-alias-table="main" class="form-control" data-title="性别">
                        <option value="">请选择</option>
                    </select>
                </div>
                <div class="col-md-2 form-group">
                    <label>身份证号</label>
                    <input id="idCardNo" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="身份证号"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>入职日期(起)</label>
                    <ctag:CalendarSelect id="entryDate$from_search" title="入职日期(起)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>入职日期(止)</label>
                    <ctag:CalendarSelect id="entryDate$to_search" title="入职日期(止)"></ctag:CalendarSelect>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属基地</label>
                    <input id="baseName" data-alias-table="base" type="text" maxlength="32" class="form-control" data-title="所属基地"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>所属部门</label>
                    <input id="deptName" data-alias-table="dept" type="text" maxlength="32" class="form-control" data-title="所属部门"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>岗位</label>
                    <input id="positionName" data-alias-table="position" type="text" maxlength="32" class="form-control" data-title="岗位"/>
                </div>
                <div class="col-md-2 form-group">
                    <label>人员状态</label>
                    <ctag:ComboCheckTree valueDomId="statusCd" textDomId="statusName" parentInstance="SysApp.Demo.EmployeeListIns" dataTitle="人员状态" searchMode="in"
                                         nodeName="subName" idKey="subCd" parentIdKey="parentSubCd"/>
                </div>

                <ctag:Fold id="divOtherSearch" name="其他查询" foldIconClass="fa-caret-up"></ctag:Fold>
                <div id="divOtherSearch" style="display:none;" class="separate-block clearfix">
                    <div class="col-md-2 form-group">
                        <label>民族</label>
                        <select id="ethnicityCd" data-alias-table="main" class="form-control" data-title="民族">
                            <option value="">请选择</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>出生日期(起)</label>
                        <ctag:CalendarSelect id="birthday$from_search" title="出生日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>出生日期(止)</label>
                        <ctag:CalendarSelect id="birthday$to_search" title="出生日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>籍贯</label>
                        <input id="ancestralNativePlace" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="籍贯"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>户口所在地</label>
                        <input id="registeredResidence" data-alias-table="main" type="text" maxlength="128" class="form-control" data-title="户口所在地"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>政治面貌</label>
                        <ctag:ComboCheckTree valueDomId="policyCd" textDomId="policyName" parentInstance="SysApp.Demo.EmployeeListIns" dataTitle="政治面貌" searchMode="in"
                                             nodeName="subName" idKey="subCd" parentIdKey="parentSubCd"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>学历</label>
                        <ctag:ComboCheckTree valueDomId="educationCd" textDomId="educationName" parentInstance="SysApp.Demo.EmployeeListIns" dataTitle="学历" searchMode="in"
                                             nodeName="subName" idKey="subCd" parentIdKey="parentSubCd"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>学位</label>
                        <ctag:ComboCheckTree valueDomId="educationalDegreeCd" textDomId="educationalDegreeName" parentInstance="SysApp.Demo.EmployeeListIns" dataTitle="学位" searchMode="in"
                                             nodeName="subName" idKey="subCd" parentIdKey="parentSubCd"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>血型</label>
                        <ctag:ComboCheckTree valueDomId="bloodTypeCd" textDomId="bloodTypeName" parentInstance="SysApp.Demo.EmployeeListIns" dataTitle="血型" searchMode="in"
                                             nodeName="subName" idKey="subCd" parentIdKey="parentSubCd"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>婚姻状况</label>
                        <ctag:ComboCheckTree valueDomId="maritalStatusCd" textDomId="maritalStatusName" parentInstance="SysApp.Demo.EmployeeListIns" dataTitle="婚姻状况" searchMode="in"
                                             nodeName="subName" idKey="subCd" parentIdKey="parentSubCd"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>国籍</label>
                        <input id="nationality" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="国籍"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>毕业院校</label>
                        <input id="graduateInstitutions" data-alias-table="main" type="text" maxlength="128" class="form-control" data-title="毕业院校"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>所学专业</label>
                        <input id="major " data-alias-table="main" type="text" maxlength="128" class="form-control" data-title="所学专业"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>户口类型</label>
                        <input id="residenceType" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="户口类型"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>职等</label>
                        <input id="positionsGrade" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="职等"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>参加工作日期(起)</label>
                        <ctag:CalendarSelect id="firstJobDate$from_search" title="参加工作日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>参加工作日期(止)</label>
                        <ctag:CalendarSelect id="firstJobDate$to_search" title="参加工作日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>转正日期(起)</label>
                        <ctag:CalendarSelect id="positiveDate$from_search" title="转正日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>转正日期(止)</label>
                        <ctag:CalendarSelect id="positiveDate$to_search" title="转正日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>离职日期(起)</label>
                        <ctag:CalendarSelect id="retireDate$from_search" title="离职日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>离职日期(止)</label>
                        <ctag:CalendarSelect id="retireDate$to_search" title="离职日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>用工方式</label>
                        <input id="employmentType" data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="用工方式"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>合同开始日期(起)</label>
                        <ctag:CalendarSelect id="contractStartDate$from_search" title="合同开始日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>合同开始日期(止)</label>
                        <ctag:CalendarSelect id="contractStartDate$to_search" title="合同开始日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>合同结束日期(起)</label>
                        <ctag:CalendarSelect id="contractEndDate$from_search" title="合同结束日期(起)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>合同结束日期(止)</label>
                        <ctag:CalendarSelect id="contractEndDate$to_search" title="合同结束日期(止)"></ctag:CalendarSelect>
                    </div>
                    <div class="col-md-2 form-group">
                        <label>星座</label>
                        <input id="constellation " data-alias-table="main" type="text" maxlength="32" class="form-control" data-title="星座"/>
                    </div>
                </div>

                <div class="clear-both dashed-line">
                </div>

                <div>
                    <button id="btnSearch" type="button" class="btn btn-primary">
                        <i class="fa fa-search"></i>查询
                    </button>
                    <shiro:hasRole name="DataExport">
                        <button id="btnExport" type="button" class="btn btn-primary">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>
                    </shiro:hasRole>
                    <button id="btnClear" type="button" class="btn btn-default">
                        <i class="fa fa-eraser"></i>清空
                    </button>
                    <ctag:PagerSettingIcon pageInstance="SysApp.Demo.EmployeeListIns"/>
                </div>
            </form>
            <input type="hidden" id="jsonListData" value="${jsonDataList_Employee}"/>
            <%--上次检索条件--%>
            <input type="hidden" id="jsonSearchCondition" value="${jsonSearchCondition_Employee}"/>
            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Employee}"/>
            <%--自定义检索条件设置--%>
            <input type="hidden" id="jsonSearchSetting" value="${jsonSearchSetting_Employee}"/>
            <%--自定义一览列设置--%>
            <input type="hidden" id="jsonColumnSetting" value="${jsonColumnSetting_Employee}"/>
            <div class="data-grid-scroll" id="divDataGridScroll">
                <div class="margin-top-space" id="divList">
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //<![CDATA[
    function EmployeeList_Page_Load() {
        SysApp.Demo.EmployeeListIns = new SysApp.Demo.EmployeeList();
        var instance = SysApp.Demo.EmployeeListIns;

        instance.selfInstance = "SysApp.Demo.EmployeeListIns";
        instance.controller = "${ctx}/demo/employee/";
        instance.inputUrl = "${ctx}/demo/employee/input";
        instance.clientID = "EmployeeList";
        instance.tableName = "demo_employee";
        instance.detailInstance = SysApp.Demo.EmployeeDetailIns;

        instance.init();
    }

    $(function () {
        EmployeeList_Page_Load();
    });
    //]]>
</script>

<%--POPUP详细--%>
<%@ include file="/WEB-INF/views/demo/employee/EmployeePopupDetail.jsp" %>
<ctag:SearchSettingPopup pageInstance="SysApp.Demo.EmployeeListIns" width="1000px"/>
<ctag:ColumnSettingPopup pageInstance="SysApp.Demo.EmployeeListIns" width="1000px"/>
<%--操作说明--%>
<ctag:HelpPopup pageInstance="SysApp.Demo.EmployeeListIns"/>