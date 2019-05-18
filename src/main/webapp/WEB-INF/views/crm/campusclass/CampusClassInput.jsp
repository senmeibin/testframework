<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/campusclass/CampusClassInput.js?${version}"></script>

<title>校区班级编辑</title>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div class="wrapper CampusClassInput-MainContent">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">校区班级编辑</div>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">所属校区</label>
                    <div class="col-sm-9">
                        <select id="campusUid" class="form-control" data-title="所属校区" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="campusUid_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">班级分类</label>
                    <div class="col-sm-9">
                        <select id="categoryCd" class="form-control required" data-title="班级分类" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="categoryCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">班级级别</label>
                    <div class="col-sm-9">
                        <select id="classLevelCd" class="form-control required" data-title="班级级别" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="classLevelCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">开班年月</label>
                    <div class="col-sm-9">
                        <select id="classYear" class="form-control required" data-title="开班年度" data-need-blank="false">
                        </select>年
                        <select id="classMonth" class="form-control required">
                            <option value="01">01</option>
                            <option value="02">02</option>
                            <option value="03">03</option>
                            <option value="04">04</option>
                            <option value="05">05</option>
                            <option value="06">06</option>
                            <option value="07">07</option>
                            <option value="08">08</option>
                            <option value="09">09</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>月
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">开班期数</label>
                    <div class="col-sm-9">
                        <select id="classSeq" class="form-control required">
                            <option value="01">1期班</option>
                            <option value="02">2期班</option>
                            <option value="03">3期班</option>
                            <option value="04">4期班</option>
                            <option value="05">5期班</option>
                            <option value="06">6期班</option>
                            <option value="07">7期班</option>
                            <option value="08">8期班</option>
                            <option value="09">9期班</option>
                            <option value="10">10期班</option>
                        </select>
                        <label id="classSeq_Error" class="validator-error"></label>(1-10期)
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">班级编号</label>
                    <div class="col-sm-9">
                        <input id="classNumber" type="text" class="form-control required" data-title="班级编号" maxlength="16" data-rangelength="[0,16]" style="width: 150px;" readonly="true"/>
                        <label id="classNumber_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">班级名称</label>
                    <div class="col-sm-9">
                        <input id="className" type="text" class="form-control required" data-title="班级名称" maxlength="32" data-rangelength="[0,32]" style="width: 500px;"/>
                        <label id="className_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">开班日期</label>
                    <div class="col-sm-9">
                        <ctag:CalendarSelect id="classStartDate" title="开班日期" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">结束日期</label>
                    <div class="col-sm-9">
                        <ctag:CalendarSelect id="classEndDate" title="结束日期" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">上课时间</label>
                    <div class="col-sm-9">
                        <input id="classTime" type="text" class="form-control required" data-title="上课时间" maxlength="32" data-rangelength="[0,32]" style="width: 500px;"/>
                        <label id="classTime_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">班级教室</label>
                    <div class="col-sm-9">
                        <input id="classroom" type="text" class="form-control required" data-title="班级教室" maxlength="32" data-rangelength="[0,32]" style="width: 500px;"/>
                        <label id="classroom_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">授课讲师</label>
                    <div class="col-sm-9">
                        <select id="teacherUserUid" class="form-control required" data-title="授课讲师" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="teacherUserUid_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">课程顾问</label>
                    <div class="col-sm-9">
                        <select id="consultantUserUid" class="form-control required" data-title="课程顾问" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="consultantUserUid_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">学员限制</label>
                    <div class="col-sm-9">
                        <input id="studentLimit" type="text" class="form-control" data-title="学员限制" maxlength="2" data-rangelength="[0,2]" data-range="[0,99]" data-digits="true" style="width: 100px;"/>
                        (0：表示无限制，最多99人)
                        <label id="studentLimit_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">备注</label>
                    <div class="col-sm-9">
                        <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                        <label id="remark_Error" class="validator-error"></label>
                    </div>
                </div>
            </form>

            <div class="text-center">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_CampusClass}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_CampusClass}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function CampusClassInput_Page_Load() {
        SysApp.Crm.CampusClassInputIns = new SysApp.Crm.CampusClassInput();
        var instance = SysApp.Crm.CampusClassInputIns;
        instance.selfInstance = "SysApp.Crm.CampusClassInputIns";
        instance.controller = "${ctx}/crm/campusclass/";
        instance.listUrl = "${ctx}/crm/campusclass/list";
        instance.clientID = "CampusClassInput";
        instance.entry = "${entry}";

        instance.init();
    }

    CampusClassInput_Page_Load();
    //]]>
</script>