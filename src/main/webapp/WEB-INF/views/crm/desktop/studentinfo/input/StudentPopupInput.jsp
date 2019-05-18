<%@ page language="java" pageEncoding="UTF-8" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content StudentPopupInput-MainContent" style="width: 1200px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader>学员信息编辑</ctag:ModalHeader>
    <div class="iscroll-wrapper" data-iscroll-id="iScrollStudentPopupInput">
        <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
        <form id="MainForm" class="modal-body form-horizontal">
            <input id="uid" type="hidden">

            <ctag:Fold id="divBaseInfo" name="基本信息" foldIconClass="fa-caret-up" tipsContent="学员基本信息设置"></ctag:Fold>
            <div id="divBaseInfo" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">学员姓名</label>
                    <div class="col-sm-9">
                        <input id="name" type="text" class="form-control required" data-title="姓名" maxlength="32" data-rangelength="[0,32]" style="width: 500px;"/>
                        (如果学员姓名不明确，请输入某小朋友)
                        <label id="name_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">围棋基础</label>
                    <div class="col-sm-9">
                        <select id="baseLevelCd" class="form-control required" data-title="围棋基础" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="baseLevelCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-9">
                        <select id="genderCd" class="form-control" data-title="性别" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="genderCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">学员年龄</label>
                    <div class="col-sm-9">
                        <input id="studentAge" type="text" class="form-control" data-title="学员年龄" maxlength="5" data-rangelength="[0,5]" data-range="[3,20]" data-number="true" style="width: 500px;"/>
                        <label id="studentAge_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">出生年月</label>
                    <div class="col-sm-9">
                        <ctag:CalendarSelect id="birthday" title="出生年月" showValidateError="true" width="120px"></ctag:CalendarSelect>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">学员状态</label>
                    <div class="col-sm-9">
                        <select id="studentStatusCd" class="form-control" data-title="学员状态" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="studentStatusCd_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divContactInfo" name="联络信息" foldIconClass="fa-caret-up" tipsContent="联络信息设置" marginTop="10px"></ctag:Fold>
            <div id="divContactInfo" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">家长姓名</label>
                    <div class="col-sm-9">
                        <input id="parentName" type="text" class="form-control required" data-title="家长姓名" maxlength="32" data-rangelength="[0,32]" style="width: 500px;"/>
                        <label id="parentName_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">手机号码</label>
                    <div class="col-sm-9">
                        <input id="mobile" type="text" class="form-control required" data-title="手机号码" data-regex="/^1(3|4|5|6|7|8|9)\d{9}$/" data-regex-message="请输入11位有效手机号码。" maxlength="11" style="width: 500px;"/>
                        <label id="mobile_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">与学员关系</label>
                    <div class="col-sm-9">
                        <select id="relationshipTypeCd" class="form-control" data-title="与学员关系" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="relationshipTypeCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">电子邮件</label>
                    <div class="col-sm-9">
                        <input id="email" type="text" class="form-control" data-title="电子邮件" maxlength="64" data-rangelength="[0,64]" style="width: 500px;"/>
                        <label id="email_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divConsultInfo" name="咨询方式" foldIconClass="fa-caret-up" tipsContent="咨询方式设置" marginTop="10px"></ctag:Fold>
            <div id="divConsultInfo" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">咨询方式</label>
                    <div class="col-sm-9">
                        <select id="consultMethodCd" class="form-control required" data-title="咨询方式" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="consultMethodCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">信息来源</label>
                    <div class="col-sm-9">
                        <select id="sourceTypeCd" class="form-control required" data-title="信息来源" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="sourceTypeCd_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divCampusInfo" name="校区分配" foldIconClass="fa-caret-up" tipsContent="校区分配设置" marginTop="10px"></ctag:Fold>
            <div id="divCampusInfo" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">所属校区</label>
                    <div class="col-sm-9">
                        <select id="studentBelongCampusUid" class="form-control" data-title="所属校区" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="studentBelongCampusUid_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">课程顾问</label>
                    <div class="col-sm-9">
                        <select id="studentBelongConsultantUserUid" class="form-control" data-title="课程顾问" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="studentBelongConsultantUserUid_Error" class="validator-error"></label>
                    </div>
                </div>
            </div>

            <ctag:Fold id="divOtherInfo" name="其他信息" foldIconClass="fa-caret-up" marginTop="10px"></ctag:Fold>
            <div id="divOtherInfo" class="separate-block clearfix">
                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">之前学校</label>
                    <div class="col-sm-9">
                        <input id="beforeSchool" type="text" class="form-control" data-title="之前学校" maxlength="64" data-rangelength="[0,64]" style="width: 500px;"/>
                        <label id="beforeSchool_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">证件类型</label>
                    <div class="col-sm-9">
                        <select id="cardTypeCd" class="form-control" data-title="证件类型" style="width: 500px;">
                            <option value="">请选择</option>
                        </select>
                        <label id="cardTypeCd_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">所在省市区</label>
                    <div class="col-sm-1" style="width: 170px;">
                        <ctag:ComboTree valueDomId="provinceUid" textDomId="provinceName" parentInstance="SysApp.Crm.StudentPopupInputIns" dataTitle="所在省" style="width: 120px;"
                                        nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/1" selectParent="false"/>
                    </div>

                    <div class="col-sm-1" style="width: 170px;">
                        <ctag:ComboTree valueDomId="cityUid" textDomId="cityName" parentInstance="SysApp.Crm.StudentPopupInputIns" dataTitle="所在市" style="width: 120px;" parentDomId="provinceUid"
                                        parentDataTitle="校区省市区" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/2" selectParent="false"/>
                    </div>

                    <div class="col-sm-1" style="width: 170px;">
                        <ctag:ComboTree valueDomId="regionUid" textDomId="regionName" parentInstance="SysApp.Crm.StudentPopupInputIns" dataTitle="所在区" style="width: 120px;" parentDomId="cityUid"
                                        parentDataTitle="校区省市区" nodeName="regionName" idKey="uid" parentIdKey="parentUid" dataUrl="${ctx}/cmn/region/getAllRegion/3" selectParent="false"/>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">家庭住址</label>
                    <div class="col-sm-9">
                        <input id="homeAddress" type="text" class="form-control" data-title="家庭住址" maxlength="256" data-rangelength="[0,256]" style="width: 500px;"/>
                        <label id="homeAddress_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">邮政编码</label>
                    <div class="col-sm-9">
                        <input id="zipCode" type="text" class="form-control" data-title="邮政编码" maxlength="6" data-rangelength="[0,6]" style="width: 500px;"/>
                        <label id="zipCode_Error" class="validator-error"></label>
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">证件号码</label>
                    <div class="col-sm-9">
                        <input id="cardNumber" type="text" class="form-control" data-title="证件号码" maxlength="18" data-rangelength="[0,18]" style="text-transform: uppercase;width: 500px;"/>
                        <label id="cardNumber_Error" class="validator-error"></label>
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

        </form>
    </div>
    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="false"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_Student}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/student/StudentInput.js?${version}"></script>
<script type="text/javascript" src="${staticContentsServer}/static/js/crm/desktop/studentinfo/input/StudentPopupInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function StudentPopupInput_Page_Load() {
        SysApp.Crm.StudentPopupInputIns = new SysApp.Crm.StudentPopupInput();
        var instance = SysApp.Crm.StudentPopupInputIns;
        instance.selfInstance = "SysApp.Crm.StudentPopupInputIns";
        instance.clientID = "StudentPopupInput";
        instance.controller = "${ctx}/crm/student/";

        instance.init();
    }

    StudentPopupInput_Page_Load();
    //]]>
</script>