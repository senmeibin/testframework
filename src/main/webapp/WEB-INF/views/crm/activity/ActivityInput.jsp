<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${staticContentsServer}/static/js/crm/activity/ActivityInput.js?${version}"></script>

<title>活动编辑</title>
<nav class="right-side-quick-nav">
    <ul class="nav">
        <li class="active"><a href="#divBaseInfoAnchor">基本信息</a></li>
        <li><a href="#divApplyInfoAnchor">申请信息</a></li>
        <li><a href="#divActivityContentsAnchor">活动内容</a></li>
        <li><a href="#divAuditInfoAnchor">审批信息</a></li>
        <li><a href="#divCampusInfoAnchor">参与校区</a></li>
    </ul>
</nav>
<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div data-spy="scroll" data-target=".right-side-quick-nav" class="wrapper ActivityInput-MainContent" style="position: relative;">
    <div class="panel content-min-height">
        <div class="panel-heading">
            <em></em>
            <div class="panel-title" id="ctlTitle">活动编辑</div>
        </div>
        <div class="panel-body">
            <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
            <form id="MainForm" class="form-horizontal">
                <input id="uid" type="hidden">
                <div id="divBaseInfoAnchor" data-client-id="false"></div>
                <ctag:Fold id="divBaseInfo" name="基本信息" foldIconClass="fa-caret-up" tipsContent="活动基本信息设置"></ctag:Fold>
                <div id="divBaseInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动分类</label>
                        <div class="col-sm-9">
                            <select id="categoryCd" class="form-control required" data-title="活动分类" style="width: 500px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="categoryCd_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动种类</label>
                        <div class="col-sm-9">
                            <select id="typeCd" class="form-control required" data-title="活动种类" style="width: 500px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="typeCd_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动主题</label>
                        <div class="col-sm-9">
                            <input id="title" type="text" class="form-control required" data-title="活动主题" maxlength="128" data-rangelength="[0,128]" style="width: 500px;"/>
                            <label id="title_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动目的</label>
                        <div class="col-sm-9">
                            <textarea id="purpose" rows="3" cols="30" class="form-control required" data-title="活动目的" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                            <label id="purpose_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动开始日期</label>
                        <div class="col-sm-9">
                            <ctag:CalendarSelect id="startDate" title="活动开始日期" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动结束日期</label>
                        <div class="col-sm-9">
                            <ctag:CalendarSelect id="endDate" title="活动结束日期" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动地址</label>
                        <div class="col-sm-9">
                            <input id="address" type="text" class="form-control" data-title="活动地址" maxlength="256" data-rangelength="[0,256]" style="width: 500px;"/>
                            <label id="address_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动对象</label>
                        <div class="col-sm-9">
                            <textarea id="target" rows="3" cols="30" class="form-control required" data-title="活动对象" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                            <label id="target_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">所需物资</label>
                        <div class="col-sm-9">
                            <textarea id="requiredMaterials" rows="3" cols="30" class="form-control" data-title="所需物资" style="width: 500px;"></textarea>
                            <label id="requiredMaterials_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">负责人</label>
                        <div class="col-sm-9">
                            <select id="responsibleUserUid" class="form-control" data-title="负责人" style="width: 500px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="responsibleUserUid_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">表示顺序</label>
                        <div class="col-sm-9">
                            <input id="dispSeq" type="text" class="form-control" data-title="表示顺序" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 100px;"/>
                            <label id="dispSeq_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-9">
                            <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                            <label id="remark_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <div id="divApplyInfoAnchor" data-client-id="false"></div>
                <ctag:Fold id="divApplyInfo" name="申请信息" foldIconClass="fa-caret-up" marginTop="10px"></ctag:Fold>
                <div id="divApplyInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">申请校区</label>
                        <div class="col-sm-9">
                            <select id="applyCampusUid" class="form-control" data-title="申请校区" style="width: 500px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="applyCampusUid_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">申请日期</label>
                        <div class="col-sm-9">
                            <ctag:CalendarSelect id="applyDate" title="申请日期" required="required" showValidateError="true" width="120px"></ctag:CalendarSelect>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">申请人</label>
                        <div class="col-sm-9">
                            <select id="applyUserUid" class="form-control" data-title="申请人" style="width: 500px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="applyUserUid_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <div id="divActivityContentsAnchor" data-client-id="false"></div>
                <ctag:Fold id="divActivityContents" name="活动内容" foldIconClass="fa-caret-up" tipsContent="活动内容富文本编辑" marginTop="15px"></ctag:Fold>
                <div id="divActivityContents" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">活动内容</label>
                        <div class="col-sm-9">
                            <textarea id="contents" rows="10" cols="30" class="required" data-title="活动内容" style="width: 800px;"></textarea>
                            <label id="contents_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <div id="divAuditInfoAnchor" data-client-id="false"></div>
                <ctag:Fold id="divAuditInfo" name="审批信息" foldIconClass="fa-caret-up" marginTop="10px"></ctag:Fold>
                <div id="divAuditInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">审批时间</label>
                        <div class="col-sm-9">
                            <ctag:CalendarSelect id="auditDate" title="审批时间" showValidateError="true" width="120px"></ctag:CalendarSelect>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">审批人</label>
                        <div class="col-sm-9">
                            <select id="auditUserUid" class="form-control" data-title="审批人" style="width: 500px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="auditUserUid_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">审批状态</label>
                        <div class="col-sm-9">
                            <select id="auditStatusCd" class="form-control" data-title="审批状态" style="width: 500px;">
                                <option value="">请选择</option>
                            </select>
                            <label id="auditStatusCd_Error" class="validator-error"></label>
                        </div>
                    </div>

                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">审批意见</label>
                        <div class="col-sm-9">
                            <textarea id="auditComment" rows="3" cols="30" class="form-control" data-title="审批意见" data-rangelength="[0,256]" style="width: 500px;"></textarea>
                            <label id="auditComment_Error" class="validator-error"></label>
                        </div>
                    </div>
                </div>

                <div id="divCampusInfoAnchor" data-client-id="false"></div>
                <ctag:Fold id="divCampusInfo" name="参与校区" foldIconClass="fa-caret-up" marginTop="10px"></ctag:Fold>
                <div id="divCampusInfo" class="separate-block clearfix">
                    <div class="form-group form-inline">
                        <label class="col-sm-2 control-label">参与校区</label>
                        <div class="col-sm-9" style="height: 280px;">
                            <ctag:ComboCheckTree valueDomId="campusUids" required="required" textDomId="campusNames" parentInstance="SysApp.Crm.ActivityInputIns" dataTitle="参与校区" style="width:461px;"
                                                 idKey="subCd" parentIdKey="parentSubCd" nodeName="subName" alwaysShowTree="true" dropdownHeight="250px"/>
                        </div>
                    </div>
                </div>
            </form>

            <div class="text-center margin-top-space">
                <button type="button" class="btn btn-primary btn-100px" id="btnSave"><i class="fa fa-save"></i>保存</button>
                <button type="button" class="btn btn-danger btn-100px" id="btnDelete"><i class="fa fa-trash-o"></i>删除</button>
                <button type="button" class="btn btn-default btn-100px" id="btnBack"><i class="fa fa-angle-left fa-lg"></i>返回一览</button>
            </div>

            <%--原始对象Json字符串[实体属性校验用]--%>
            <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_Activity}"/>

            <%--下拉框选项数据集合--%>
            <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Activity}"/>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function ActivityInput_Page_Load() {
        SysApp.Crm.ActivityInputIns = new SysApp.Crm.ActivityInput();
        var instance = SysApp.Crm.ActivityInputIns;
        instance.selfInstance = "SysApp.Crm.ActivityInputIns";
        instance.controller = "${ctx}/crm/activity/";
        instance.listUrl = "${ctx}/crm/activity/list";
        instance.clientID = "ActivityInput";
        instance.entry = "${entry}";

        instance.init();
    }

    ActivityInput_Page_Load();
    //]]>
</script>