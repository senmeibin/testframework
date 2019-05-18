package com.bpms.crm.controller;

import com.bpms.cmn.service.CmnUserService;
import com.bpms.cmn.service.SubwayService;
import com.bpms.core.bean.BatchImportResult;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.excel.ExcelUtils;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.JsonUtils;
import com.bpms.core.utils.StringUtils;
import com.bpms.crm.bean.excel.StudentExcelEntity;
import com.bpms.crm.consts.CrmConsts;
import com.bpms.crm.entity.ext.StudentExt;
import com.bpms.crm.service.StudentService;
import com.bpms.sys.entity.ext.AttachmentImportExt;
import com.bpms.sys.service.CounterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学员信息控制器类
 */
@Controller
@RequestMapping("/crm/student")
public class StudentController extends CrmBaseController<StudentExt> {
    /**
     * Service对象
     */
    @Autowired
    private StudentService studentService;

    @Autowired
    private CmnUserService cmnUserService;

    @Autowired
    private CounterService counterService;

    @Autowired
    private FollowupController followupController;

    @Autowired
    private ReservationController reservationController;

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private PaymentController paymentController;

    @Autowired
    private SubwayService subwayService;

    /**
     * 取得Service对象
     */
    @Override
    public StudentService getService() {
        return studentService;
    }

    /**
     * 数据一览画面初期化
     *
     * @param model Model对象
     * @return 数据一览JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException {
        return super.list(model);
    }

    /**
     * 批量导入学员信息
     *
     * @param file 导入文件
     * @return 导入结果
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "importStudent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "批量导入学员信息")
    @ResponseBody
    public AjaxResult importStudent(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        AttachmentImportExt attachment = new AttachmentImportExt();
        AjaxResult ajaxResult = null;
        BatchImportResult importResult = new BatchImportResult();
        try {
            Map<String, List<StudentExcelEntity>> result = ExcelUtils.read(StudentExcelEntity.class, file.getInputStream());
            if (result.isEmpty()) {
                ajaxResult = AjaxResult.createFailResult("导入数据为空，请确认。");
                attachment.setImportResult("导入数据为空");
            }
            else {
                //List<Map>转换为List<Entity>
                List<StudentExcelEntity> importDetailList = getService().convertToStudentList(result);
                //保存学员信息
                getService().saveStudent(importDetailList, importResult);
                attachment.setImportResultDetailMessage(JsonUtils.toJSON(importResult));
                attachment.setImportResult("导入完成");
                ajaxResult = AjaxResult.createSuccessResult("导入完成。", importResult);
            }
        }
        catch (ServiceException e) {
            //业务异常 信息直接返回
            ajaxResult = AjaxResult.createFailResult(e.getMessage());
            attachment.setImportResult("导入失败");
            attachment.setImportResultDetailMessage("异常信息：" + e.getMessage());
        }
        catch (Exception e) {
            log.warn("学员信息导入文件解析异常{}", e.getMessage(), e);
            ajaxResult = AjaxResult.createFailResult("学员信息导入文件解析异常，请确认您上传的文件是否与模板文件格式要求一致。错误：" + e.getMessage());
            attachment.setImportResult("导入失败");
            attachment.setImportResultDetailMessage("异常信息：" + e.getMessage());
        }
        //必须与jsp 中ctag:FileImportList 指定的moduleName 一致
        attachment.setModuleName("学员信息导入");
        //01:学员信息导入
        attachment.setBatchNo(this.counterService.getCounter("01", "学员信息导入").toString());
        this.saveAttachment(file, attachment);
        return ajaxResult;
    }

    /**
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    @Override
    public void initOptionList(Model model) throws IllegalAccessException, IOException {
        Map<String, Object> dicMap = new HashMap<>();
        //性别
        dicMap.put("genderCd", this.getDictionaryList("CRM006"));
        //证件类型
        dicMap.put("cardTypeCd", this.getDictionaryList("CRM007"));
        //围棋基础
        dicMap.put("baseLevelCd", this.getDictionaryList("CRM008"));
        //咨询方式
        dicMap.put("consultMethodCd", this.getDictionaryList("CRM009"));
        //信息来源
        dicMap.put("sourceTypeCd", this.getDictionaryList("CRM010"));
        //与学员关系
        dicMap.put("relationshipTypeCd", this.getDictionaryList("CRM011"));
        //学员状态
        dicMap.put("studentStatusCd", this.getDictionaryList("CRM012"));

        List<DropdownEntity> campusList = this.getDropdownList("crm_campus", "uid", "name");
        //所属校区
        dicMap.put("studentBelongCampusUid", campusList);

        List<DropdownEntity> userList = this.cmnUserService.getUserSelect(DropdownEntity.class, false, null);
        //课程顾问
        dicMap.put("studentBelongConsultantUserUid", userList);

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));

        //初始化下拉框选项内容[跟进记录]
        this.followupController.initOptionList(model);

        //初始化下拉框选项内容[预约记录]
        this.reservationController.initOptionList(model);

        //初始化下拉框选项内容[报名记录]
        this.registrationController.initOptionList(model);

        //初始化下拉框选项内容[缴费记录]
        this.paymentController.initOptionList(model);
    }

    /**
     * 根据学员UID取得学员相关的所有信息
     *
     * @param studentUid 学员UID
     * @return AjaxResult 学员信息
     */
    @ResponseBody
    @RequestMapping(value = "/getStudentInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "根据学员UID取得学员相关的所有信息")
    public AjaxResult getStudentInfo(@RequestParam String studentUid) {
        if (StringUtils.isEmpty(studentUid)) {
            return AjaxResult.createFailResult("非法访问，学员UID不能为空。");
        }
        long startTime = System.currentTimeMillis();
        StudentExt student = this.studentService.getStudentInfo(studentUid);
        return AjaxResult.createSuccessResult(String.format("根据学员UID取得学员相关的所有信息（处理时间：%s毫秒）。", System.currentTimeMillis() - startTime), student);
    }

    /**
     * 根据手机号码取得学员相关的所有信息
     *
     * @param mobile 手机号码
     * @return AjaxResult 学员信息
     */
    @ResponseBody
    @RequestMapping(value = "/getStudentInfoByMobile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "根据手机号码取得学员相关的所有信息")
    public AjaxResult getStudentInfoByMobile(@RequestParam String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return AjaxResult.createFailResult("非法访问，手机号码不能为空。");
        }
        long startTime = System.currentTimeMillis();
        StudentExt student = this.studentService.getStudentInfoByMobile(mobile);
        if (student == null) {
            return AjaxResult.createFailResult(String.format("根据输入的的手机号码（%s）未查询到任何学员。", mobile));
        }
        return AjaxResult.createSuccessResult(String.format("根据学员UID取得学员相关的所有信息（处理时间：%s毫秒）。", System.currentTimeMillis() - startTime), student);
    }

    /**
     * 城市地铁线路信息取得
     */
    @RequestMapping(value = "getSubwayStationInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AjaxResult getSubwayStationInfo() {
        return AjaxResult.createSuccessResult("城市地铁线路信息取得成功", subwayService.getAllSubwayMap(this.parameterService.getValue(CrmConsts.APP_CRM, "SUBWAY_CITY_NAME_LIST", "上海市")));
    }
}
