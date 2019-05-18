package com.bpms.crm.service;

import com.bpms.core.bean.BatchImportResult;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.*;
import com.bpms.crm.bean.excel.StudentExcelEntity;
import com.bpms.crm.consts.CrmConsts;
import com.bpms.crm.dao.StudentDao;
import com.bpms.crm.entity.ext.CampusExt;
import com.bpms.crm.entity.ext.StudentExt;
import com.bpms.sys.dao.UserInfoDao;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.entity.ext.UserInfoExt;
import com.bpms.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 学员信息服务类
 */
@Service
public class StudentService extends CrmBaseService<StudentExt> {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private CampusService campusService;

    @Autowired
    private FollowupService followupService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public StudentDao getDao() {
        return studentDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(StudentExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(StudentExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected StudentExt saveBefore(StudentExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected StudentExt saveAfter(StudentExt entity) {
        //根据手机号码查找系统用户信息
        UserExt user = userService.findByUserCd(entity.getMobile());

        //系统用户信息补全
        if (user == null) {
            user = new UserExt();
            user.setUid(entity.getUid());
            user.setUserCd(entity.getMobile());
            user.setUserName(entity.getParentName());
            user.setUserPhone(entity.getMobile());
            user.setUserMail(entity.getEmail());
            user.setForceChangePswd(CmnConsts.FORCE_CHANGE_PSWD_YES);
            user.setPassword(Digests.shaPassword256(entity.getMobile()));
            user.setDeptUid(CrmConsts.BUSINESS_DEPT_UID);
            user.setRegSystem(2);
            //设置DES密码
            user.setDesPassword(DesUtils.encrypt(entity.getMobile()));
            this.userService.getDao().save(user);
        }
        //家长姓名变更的场合
        else if (!StringUtils.equals(user.getUserName(), entity.getParentName())) {
            user.setUserName(entity.getParentName());
            this.userService.getDao().save(user);
        }

        //用户扩展信息补全
        UserInfoExt userInfo = userInfoDao.findOne(user.getUid());
        if (userInfo == null) {
            userInfo = new UserInfoExt();
            userInfo.setUid(user.getUid());
            this.userInfoDao.save(userInfo);
        }

        return entity;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(String ids) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(StudentExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(StudentExt entity) {
        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        return true;
    }

    /**
     * 获取所有在职用户
     *
     * @return 用户列表
     */
    public List<UserExt> getAllServingUser() {
        Map condition = new HashMap<>();
        //只查询内部员工
        condition.put("users.reg_system", "0");
        condition.put("users.recordStatus", "1");
        return this.userService.getDao().search(UserExt.class, this.getSQL("cmn/user/getUserSelect"), condition);
    }

    /**
     * 转换并补充学员信息
     *
     * @param list excel 读入列表
     * @return 格式转换后的列表（去掉无效sheet数据）
     */
    public List<StudentExcelEntity> convertToStudentList(Map<String, List<StudentExcelEntity>> list) {
        Object[] keys = list.keySet().toArray();
        //删除【学员信息】以外的所有数据
        for (Object key : keys) {
            if (!StringUtils.equals(key.toString(), "学员信息")) {
                list.remove(key);
            }
        }

        List<StudentExcelEntity> studentList = new ArrayList<>();
        Collection<List<StudentExcelEntity>> values = list.values();
        values.forEach(studentList::addAll);

        //性别
        List<DropdownEntity> genderList = this.dictionaryService.getDictionaryList("CRM006");
        //围棋基础
        List<DropdownEntity> baseLevelList = this.dictionaryService.getDictionaryList("CRM008");
        //咨询方式
        List<DropdownEntity> consultMethodList = this.dictionaryService.getDictionaryList("CRM009");
        //信息来源
        List<DropdownEntity> sourceTypeList = this.dictionaryService.getDictionaryList("CRM010");
        //与学员关系
        List<DropdownEntity> relationshipTypeList = this.dictionaryService.getDictionaryList("CRM011");
        //学员状态
        List<DropdownEntity> statusList = this.dictionaryService.getDictionaryList("CRM012");
        //校区一览
        List<CampusExt> campusList = this.campusService.search(CampusExt.class, null, new Sort(Sort.Direction.ASC, "main.disp_seq"));
        //课程顾问一览
        List<UserExt> userList = this.getAllServingUser();

        for (StudentExcelEntity entity : studentList) {
            StringBuilder sbMessage = new StringBuilder();

            StudentExt student;

            if (StringUtils.isEmpty(entity.getName())) {
                continue;
            }

            //性别
            if (StringUtils.isNotEmpty(entity.getGenderName())) {
                entity.setGenderCd(this.getDictionarySubCdByName(genderList, entity.getGenderName()));
                if (StringUtils.isEmpty(entity.getGenderCd())) {
                    sbMessage.append("【性别】列数据不正确，请确认模板文件是否最新。").append("<br/>");
                }
            }

            //围棋基础
            if (StringUtils.isNotEmpty(entity.getBaseLevelName())) {
                entity.setBaseLevelCd(this.getDictionarySubCdByName(baseLevelList, entity.getBaseLevelName()));
                if (StringUtils.isEmpty(entity.getBaseLevelCd())) {
                    sbMessage.append("【围棋基础】列数据不正确，请确认模板文件是否最新。").append("<br/>");
                }
            }

            //咨询方式
            if (StringUtils.isNotEmpty(entity.getConsultMethodName())) {
                entity.setConsultMethodCd(this.getDictionarySubCdByName(consultMethodList, entity.getConsultMethodName()));
                if (StringUtils.isEmpty(entity.getConsultMethodCd())) {
                    sbMessage.append("【咨询方式】列数据不正确，请确认模板文件是否最新。").append("<br/>");
                }
            }

            //信息来源
            if (StringUtils.isNotEmpty(entity.getSourceTypeName())) {
                entity.setSourceTypeCd(this.getDictionarySubCdByName(sourceTypeList, entity.getSourceTypeName()));
                if (StringUtils.isEmpty(entity.getSourceTypeCd())) {
                    sbMessage.append("【信息来源】列数据不正确，请确认模板文件是否最新。").append("<br/>");
                }
            }

            //与学员关系
            if (StringUtils.isNotEmpty(entity.getRelationshipTypeName())) {
                entity.setRelationshipTypeCd(this.getDictionarySubCdByName(relationshipTypeList, entity.getRelationshipTypeName()));
                if (StringUtils.isEmpty(entity.getRelationshipTypeCd())) {
                    sbMessage.append("【与学员关系】列数据不正确，请确认模板文件是否最新。").append("<br/>");
                }
            }

            //学员状态
            if (StringUtils.isNotEmpty(entity.getStudentStatusName())) {
                entity.setStudentStatusCd(this.getDictionarySubCdByName(statusList, entity.getStudentStatusName()));
                if (StringUtils.isEmpty(entity.getStudentStatusCd())) {
                    sbMessage.append("【学员状态】列数据不正确，请确认模板文件是否最新。").append("<br/>");
                }
            }

            //所属校区
            if (StringUtils.isNotEmpty(entity.getStudentBelongCampusName())) {
                for (CampusExt campus : campusList) {
                    if (campus.getName().indexOf(entity.getStudentBelongCampusName()) >= 0) {
                        entity.setStudentBelongCampusUid(campus.getUid());
                    }
                }
            }

            //课程顾问
            if (StringUtils.isNotEmpty(entity.getStudentBelongConsultantUserName())) {
                for (UserExt user : userList) {
                    if (user.getUserName().indexOf(entity.getStudentBelongConsultantUserName()) >= 0) {
                        entity.setStudentBelongConsultantUserUid(user.getUid());
                    }
                }
            }

            //如果没有输入错误，进行db Entity 赋值
            if (sbMessage.length() == 0) {
                //获取学员信息
                Map<String, Object> condition = new HashMap<>();
                condition.put("main.name", entity.getName());
                condition.put("main.mobile", entity.getMobile());
                boolean isNew = true;
                List<StudentExt> students = this.getDao().search(this.getSearchSQL(null), condition);
                if (CollectionUtils.isEmpty(students)) {
                    student = new StudentExt();
                    student.setUid(UniqueUtils.getUID());
                }
                else {
                    isNew = false;
                    //取得第一条学员信息
                    student = students.get(0);
                }

                //姓名
                if (StringUtils.isNotEmpty(entity.getName())) {
                    student.setName(entity.getName());
                }
                //手机号码
                if (StringUtils.isNotEmpty(entity.getMobile())) {
                    student.setMobile(entity.getMobile());
                }
                //家长姓名
                if (StringUtils.isNotEmpty(entity.getParentName())) {
                    student.setParentName(entity.getParentName());
                }

                //围棋基础
                if (StringUtils.isNotEmpty(entity.getBaseLevelCd())) {
                    student.setBaseLevelCd(entity.getBaseLevelCd());
                }

                //咨询方式
                if (StringUtils.isNotEmpty(entity.getConsultMethodCd())) {
                    student.setConsultMethodCd(entity.getConsultMethodCd());
                }

                //信息来源
                if (StringUtils.isNotEmpty(entity.getSourceTypeCd())) {
                    student.setSourceTypeCd(entity.getSourceTypeCd());
                }

                //性别
                if (StringUtils.isNotEmpty(entity.getGenderCd())) {
                    student.setGenderCd(entity.getGenderCd());
                }

                //与学员关系
                if (StringUtils.isNotEmpty(entity.getRelationshipTypeCd())) {
                    student.setRelationshipTypeCd(entity.getRelationshipTypeCd());
                }

                //学员状态
                if (StringUtils.isNotEmpty(entity.getStudentStatusCd())) {
                    student.setStudentStatusCd(entity.getStudentStatusCd());
                }

                //所属校区
                if (StringUtils.isNotEmpty(entity.getStudentBelongCampusUid())) {
                    student.setStudentBelongCampusUid(entity.getStudentBelongCampusUid());
                }

                //课程顾问
                if (StringUtils.isNotEmpty(entity.getStudentBelongConsultantUserUid())) {
                    student.setStudentBelongConsultantUserUid(entity.getStudentBelongConsultantUserUid());
                }

                //学员年龄
                if (entity.getStudentAge() != null) {
                    student.setStudentAge(entity.getStudentAge());
                    if (student.getBirthday() != null) {
                        int month = (int) (entity.getStudentAge().doubleValue() * 12);
                        student.setBirthday(DateUtils.addMonths(DateUtils.getNowDate(), -month));
                    }
                }

                if (entity.getConsultDate() != null) {
                    Date consultDatetime = entity.getConsultDate();
                    if (!StringUtils.isEmpty(entity.getConsultTime())) {
                        consultDatetime = DateUtils.parseDate(DateUtils.format(entity.getConsultDate()) + " " + entity.getConsultTime());
                    }

                    if (isNew) {
                        student.setFirstConsultTime(consultDatetime);
                    }
                    student.setRecentConsultTime(consultDatetime);
                }

                //备注
                if (StringUtils.isNotEmpty(entity.getRemark())) {
                    student.setRemark(StringUtils.substring(entity.getRemark(), 0, 256));
                }
            }
            else {
                //异常信息
                entity.setImportResult(StringUtils.removeEnd(sbMessage.toString(), "<br/>"));
                //-1：不成功
                entity.setImportResultFlag(-1);
                //本行有错， 进行下一条数据处理
                continue;
            }

            //数据解析正确
            if (StringUtils.isEmpty(entity.getImportResult())) {
                //封装实体数据
                entity.setStudent(student);
            }
        }
        return studentList;
    }

    /**
     * 保存学员信息（Excel批量导入用）
     *
     * @param dataDetailList excel导入列表
     * @param importResult   导入结果
     */
    @Transactional
    public void saveStudent(List<StudentExcelEntity> dataDetailList, BatchImportResult importResult) {
        //成功数
        int successCount = 0;
        //失败数
        int errorCount = 0;
        for (StudentExcelEntity studentExcelEntity : dataDetailList) {
            StudentExt student = studentExcelEntity.getStudent();
            //学员信息不为空
            if (student != null) {
                try {
                    this.getDao().save(student);
                    successCount++;
                    studentExcelEntity.setImportResult(String.format("学员信息导入成功（%s：%s）。", student.getName(), student.getMobile()));
                    //0：保存成功
                    studentExcelEntity.setImportResultFlag(0);
                }
                catch (ServiceException e) {
                    studentExcelEntity.setImportResult(String.format("学员信息导入失败（%s：%s）。%s", student.getName(), student.getMobile(), e.getMessage()));
                    //-1：失败
                    studentExcelEntity.setImportResultFlag(-1);
                    errorCount++;
                }
            }
            else {
                //-1：失败
                studentExcelEntity.setImportResultFlag(-1);
                errorCount++;
            }
        }

        importResult.setBatchImportDetailList(dataDetailList);
        importResult.setErrorCount(errorCount);
        importResult.setSuccessCount(successCount);
    }

    /**
     * 根据学员UID取得学员相关的所有信息
     *
     * @param studentUid 学员UID
     * @return 学员信息
     */
    public StudentExt getStudentInfo(String studentUid) {
        //学员基本信息
        StudentExt student = this.getDetail(studentUid);

        Map<String, Object> condition = new HashMap<>();
        condition.put("main.studentUid", studentUid);

        //跟进记录
        student.setFollowupList(this.followupService.search(condition));

        //预约记录
        student.setReservationList(this.reservationService.search(condition));

        //报名记录
        student.setRegistrationList(this.registrationService.search(condition));

        //缴费记录
        student.setPaymentList(this.paymentService.search(condition));

        return student;
    }

    /**
     * 根据手机号码取得学员相关的所有信息
     *
     * @param mobile 手机号码
     * @return 学员信息
     */
    public StudentExt getStudentInfoByMobile(String mobile) {

        Map<String, Object> condition = new HashMap<>();
        condition.put("main.mobile", mobile);

        List<StudentExt> list = this.search(condition);
        if (list.size() == 0) {
            return null;
        }

        //学员基本信息
        StudentExt student = list.get(0);

        condition.clear();
        condition.put("main.studentUid", student.getUid());

        //跟进记录
        student.setFollowupList(this.followupService.search(condition));

        //预约记录
        student.setReservationList(this.reservationService.search(condition));

        //报名记录
        student.setRegistrationList(this.registrationService.search(condition));

        //缴费记录
        student.setPaymentList(this.paymentService.search(condition));

        return student;
    }
}