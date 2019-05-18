package com.bpms.sys.service;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.utils.DateUtils;
import com.bpms.sys.dao.SmsAuthCodeDao;
import com.bpms.sys.entity.ext.SmsAuthCodeExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * 短信验证码服务类
 */
@Service
public class SmsAuthCodeService extends SysBaseService<SmsAuthCodeExt> {
    @Autowired
    private SmsAuthCodeDao smsAuthCodeDao;

    @Override
    public SmsAuthCodeDao getDao() {
        return smsAuthCodeDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(SmsAuthCodeExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(SmsAuthCodeExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SmsAuthCodeExt saveBefore(SmsAuthCodeExt entity) {
        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected SmsAuthCodeExt saveAfter(SmsAuthCodeExt entity) {
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
    protected Boolean deleteBefore(SmsAuthCodeExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(SmsAuthCodeExt entity) {
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
     * 发送验证码
     *
     * @param mobile       手机号码
     * @param authCodeType 验证码类型
     */
    public void sendAuthCode(String mobile, String authCodeType) {
        SmsAuthCodeExt smsAuthCode = new SmsAuthCodeExt();
        //手机号码
        smsAuthCode.setMobile(mobile);
        //有效时间
        smsAuthCode.setValidTime(DateUtils.addMinutes(new Date(), this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "AUTH_CODE_VALID_TIME", 30)));
        //验证码类型
        smsAuthCode.setAuthCodeType(authCodeType);

        //生成验证码
        String authCode = this.createRandomAuthCode();

        //是否已验证
        smsAuthCode.setValidated(0);
        //TODO 这里需要发送短信
        //验证码
        smsAuthCode.setAuthCode(authCode);
        this.save(smsAuthCode);
    }

    /**
     * 生成4位字符数字随机验证码
     *
     * @return String
     */
    private String createRandomAuthCode() {
        //生成数据所包含的字符
        String baseNum = "0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        //生成随机数字
        for (int i = 0; i < 4; i++) {
            int number = random.nextInt(baseNum.length());
            sb.append(baseNum.charAt(number));
        }

        return sb.toString();
    }
}