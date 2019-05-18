<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>

<%--注：为区分页面对象包含的数据范围，需要在MainContent前加上画面前缀--%>
<div id="ctlFrame" class="modal-content EnterpriseSettingInput-MainContent" style="width: 800px; display: none;">
    <%--POPUP控件Header--%>
    <ctag:ModalHeader></ctag:ModalHeader>

    <%--注：为保证客户端验证控件有效，必须设定FORM元素--%>
    <form id="MainForm" class="modal-body form-horizontal">
        <input id="uid" type="hidden">
        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">子账号数量</label>
            <div class="col-sm-8">
                <input id="setting001" type="text" class="form-control" data-title="子账号数量" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting001_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">是否允许多公司招聘</label>
            <div class="col-sm-8">
                <input type="checkbox" id="setting002" class="switch"/>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">微信公众号绑定数量</label>
            <div class="col-sm-8">
                <input id="setting003" type="text" class="form-control" data-title="微信公众号绑定数量" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting003_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">是否允许多部门管理</label>
            <div class="col-sm-8">
                <input type="checkbox" id="setting004" class="switch"/>
            </div>
        </div>

        <%--
        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置005</label>
            <div class="col-sm-8">
                <input id="setting005" type="text" class="form-control" data-title="设置005" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting005_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置006</label>
            <div class="col-sm-8">
                <input id="setting006" type="text" class="form-control" data-title="设置006" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting006_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置007</label>
            <div class="col-sm-8">
                <input id="setting007" type="text" class="form-control" data-title="设置007" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting007_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置008</label>
            <div class="col-sm-8">
                <input id="setting008" type="text" class="form-control" data-title="设置008" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting008_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置009</label>
            <div class="col-sm-8">
                <input id="setting009" type="text" class="form-control" data-title="设置009" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting009_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置010</label>
            <div class="col-sm-8">
                <input id="setting010" type="text" class="form-control" data-title="设置010" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting010_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置011</label>
            <div class="col-sm-8">
                <input id="setting011" type="text" class="form-control" data-title="设置011" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting011_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置012</label>
            <div class="col-sm-8">
                <input id="setting012" type="text" class="form-control" data-title="设置012" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting012_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置013</label>
            <div class="col-sm-8">
                <input id="setting013" type="text" class="form-control" data-title="设置013" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting013_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置014</label>
            <div class="col-sm-8">
                <input id="setting014" type="text" class="form-control" data-title="设置014" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting014_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置015</label>
            <div class="col-sm-8">
                <input id="setting015" type="text" class="form-control" data-title="设置015" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting015_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置016</label>
            <div class="col-sm-8">
                <input id="setting016" type="text" class="form-control" data-title="设置016" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting016_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置017</label>
            <div class="col-sm-8">
                <input id="setting017" type="text" class="form-control" data-title="设置017" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting017_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置018</label>
            <div class="col-sm-8">
                <input id="setting018" type="text" class="form-control" data-title="设置018" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting018_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置019</label>
            <div class="col-sm-8">
                <input id="setting019" type="text" class="form-control" data-title="设置019" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting019_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置020</label>
            <div class="col-sm-8">
                <input id="setting020" type="text" class="form-control" data-title="设置020" maxlength="8" data-rangelength="[0,8]" data-range="[0,99999999]" data-digits="true" style="width: 200px;"/>
                <label id="setting020_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置021</label>
            <div class="col-sm-8">
                <input id="setting021" type="text" class="form-control" data-title="设置021" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting021_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置022</label>
            <div class="col-sm-8">
                <input id="setting022" type="text" class="form-control" data-title="设置022" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting022_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置023</label>
            <div class="col-sm-8">
                <input id="setting023" type="text" class="form-control" data-title="设置023" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting023_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置024</label>
            <div class="col-sm-8">
                <input id="setting024" type="text" class="form-control" data-title="设置024" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting024_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置025</label>
            <div class="col-sm-8">
                <input id="setting025" type="text" class="form-control" data-title="设置025" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting025_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置026</label>
            <div class="col-sm-8">
                <input id="setting026" type="text" class="form-control" data-title="设置026" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting026_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置027</label>
            <div class="col-sm-8">
                <input id="setting027" type="text" class="form-control" data-title="设置027" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting027_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置028</label>
            <div class="col-sm-8">
                <input id="setting028" type="text" class="form-control" data-title="设置028" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting028_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置029</label>
            <div class="col-sm-8">
                <input id="setting029" type="text" class="form-control" data-title="设置029" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting029_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置030</label>
            <div class="col-sm-8">
                <input id="setting030" type="text" class="form-control" data-title="设置030" maxlength="32" data-rangelength="[0,32]" style="width: 200px;"/>
                <label id="setting030_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置031</label>
            <div class="col-sm-8">
                <input id="setting031" type="text" class="form-control" data-title="设置031" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting031_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置032</label>
            <div class="col-sm-8">
                <input id="setting032" type="text" class="form-control" data-title="设置032" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting032_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置033</label>
            <div class="col-sm-8">
                <input id="setting033" type="text" class="form-control" data-title="设置033" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting033_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置034</label>
            <div class="col-sm-8">
                <input id="setting034" type="text" class="form-control" data-title="设置034" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting034_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置035</label>
            <div class="col-sm-8">
                <input id="setting035" type="text" class="form-control" data-title="设置035" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting035_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置036</label>
            <div class="col-sm-8">
                <input id="setting036" type="text" class="form-control" data-title="设置036" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting036_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置037</label>
            <div class="col-sm-8">
                <input id="setting037" type="text" class="form-control" data-title="设置037" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting037_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置038</label>
            <div class="col-sm-8">
                <input id="setting038" type="text" class="form-control" data-title="设置038" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting038_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置039</label>
            <div class="col-sm-8">
                <input id="setting039" type="text" class="form-control" data-title="设置039" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting039_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置040</label>
            <div class="col-sm-8">
                <input id="setting040" type="text" class="form-control" data-title="设置040" maxlength="256" data-rangelength="[0,256]" style="width: 200px;"/>
                <label id="setting040_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置041</label>
            <div class="col-sm-8">
                <input id="setting041" type="text" class="form-control" data-title="设置041" maxlength="1024" data-rangelength="[0,1024]" style="width: 200px;"/>
                <label id="setting041_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置042</label>
            <div class="col-sm-8">
                <input id="setting042" type="text" class="form-control" data-title="设置042" maxlength="1024" data-rangelength="[0,1024]" style="width: 200px;"/>
                <label id="setting042_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置043</label>
            <div class="col-sm-8">
                <input id="setting043" type="text" class="form-control" data-title="设置043" maxlength="1024" data-rangelength="[0,1024]" style="width: 200px;"/>
                <label id="setting043_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置044</label>
            <div class="col-sm-8">
                <input id="setting044" type="text" class="form-control" data-title="设置044" maxlength="1024" data-rangelength="[0,1024]" style="width: 200px;"/>
                <label id="setting044_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置045</label>
            <div class="col-sm-8">
                <input id="setting045" type="text" class="form-control" data-title="设置045" maxlength="1024" data-rangelength="[0,1024]" style="width: 200px;"/>
                <label id="setting045_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置046</label>
            <div class="col-sm-8">
                <textarea id="setting046" rows="3" cols="30" class="form-control" data-title="设置046" data-rangelength="[0,0]" style="width: 200px;"></textarea>
                <label id="setting046_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置047</label>
            <div class="col-sm-8">
                <textarea id="setting047" rows="3" cols="30" class="form-control" data-title="设置047" data-rangelength="[0,0]" style="width: 200px;"></textarea>
                <label id="setting047_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置048</label>
            <div class="col-sm-8">
                <textarea id="setting048" rows="3" cols="30" class="form-control" data-title="设置048" data-rangelength="[0,0]" style="width: 200px;"></textarea>
                <label id="setting048_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置049</label>
            <div class="col-sm-8">
                <textarea id="setting049" rows="3" cols="30" class="form-control" data-title="设置049" data-rangelength="[0,0]" style="width: 200px;"></textarea>
                <label id="setting049_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">设置050</label>
            <div class="col-sm-8">
                <textarea id="setting050" rows="3" cols="30" class="form-control" data-title="设置050" data-rangelength="[0,0]" style="width: 200px;"></textarea>
                <label id="setting050_Error" class="validator-error"></label>
            </div>
        </div>

        <div class="form-group form-inline">
            <label class="col-sm-4 control-label">备注</label>
            <div class="col-sm-8">
                <textarea id="remark" rows="3" cols="30" class="form-control" data-title="备注" data-rangelength="[0,256]" style="width: 200px;"></textarea>
                <label id="remark_Error" class="validator-error"></label>
            </div>
        </div>
        --%>
    </form>

    <%--POPUP控件Footer--%>
    <ctag:ModalFooter showSaveButton="true" showDeleteButton="false"></ctag:ModalFooter>

    <%--原始对象Json字符串[实体属性校验用]--%>
    <input type="hidden" id="jsonInputEntity" value="${jsonInputEntity_EnterpriseSetting}"/>

    <%--下拉框选项数据集合--%>
    <input type="hidden" id="jsonOptionList" value="${jsonOptionList_EnterpriseSetting}"/>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/js/sys/enterprisesetting/EnterpriseSettingInput.js?${version}"></script>
<script type="text/javascript" language="javascript">
    //<![CDATA[
    function EnterpriseSettingInput_Page_Load() {
        SysApp.Sys.EnterpriseSettingInputIns = new SysApp.Sys.EnterpriseSettingInput();
        var instance = SysApp.Sys.EnterpriseSettingInputIns;
        instance.selfInstance = "SysApp.Sys.EnterpriseSettingInputIns";
        instance.clientID = "EnterpriseSettingInput";
        instance.entry = "${entry}";
        instance.controller = "${ctx}/sys/enterprisesetting/";

        instance.init();
    }

    EnterpriseSettingInput_Page_Load();
    //]]>
</script>