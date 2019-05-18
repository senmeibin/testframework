package com.bpms.core.sms;

import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.consts.SmsConsts;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.utils.DesUtils;
import com.bpms.core.utils.HttpUtils;
import com.bpms.sys.dao.SmsLogDao;
import com.bpms.sys.entity.ext.SmsLogExt;
import com.bpms.sys.service.ParameterService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
class SendSmsCoreService {
    @Autowired
    private ParameterService parameterService;

    @Autowired
    private SmsLogDao smsLogDao;

    /**
     * 短信供应商
     */
    private String smsVendor;

    /**
     * 应用编号
     */
    private String appCode;

    /**
     * 发送SMS短信【系统平台账户发送】
     *
     * @param appCode 应用编号
     * @param mobile  手机号码
     * @param message 短信内容
     * @param extCode 扩展码
     * @return 发送结果
     */
    public boolean smsSend(String appCode, String mobile, String message, String extCode) throws UnsupportedEncodingException {
        this.appCode = appCode;

        //短信供应商
        this.smsVendor = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_VENDOR);

        //国都场合
        if (StringUtils.equals(this.smsVendor, "GUODU")) {
            return guoDuSmsSend(mobile, message, extCode);
        }
        //鸿联场合
        else if (StringUtils.equals(this.smsVendor, "HL")) {
            return hlSmsSend(mobile, message, extCode);
        }

        throw new ServiceException("短信供应商还未配置，请与系统管理员联系。");
    }

    /**
     * 发送SMS短信【指定短信账号】
     *
     * @param appCode   应用编号
     * @param smsVendor 供应商类型
     * @param url       短信接口URL
     * @param epid      账号唯一对应【鸿联专用】
     * @param account   短信账号
     * @param password  密码
     * @param mobile    手机号码
     * @param message   短信内容
     * @param extCode   扩展码
     * @return 发送结果
     */
    public boolean smsSend(String appCode, String smsVendor, String url, String epid, String account, String password, String mobile, String message, String extCode) throws UnsupportedEncodingException {
        this.appCode = appCode;
        this.smsVendor = smsVendor;

        //国都场合
        if (StringUtils.equals(smsVendor, "GUODU")) {
            return guoDuSmsSend(url, account, password, mobile, message, extCode);
        }
        //鸿联场合
        else if (StringUtils.equals(smsVendor, "HL")) {
            return hlSmsSend(epid, url, account, password, mobile, message, extCode);
        }

        throw new ServiceException("短信供应商还未配置，请与系统管理员联系。");
    }

    /**
     * 国都短信发送【系统平台账户发送】
     *
     * @param mobile  手机号码
     * @param message 短信内容
     * @param extCode 扩展码
     * @return 发送结果 true：成功
     */
    private boolean guoDuSmsSend(String mobile, String message, String extCode) throws UnsupportedEncodingException {
        //短信账号
        String account = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_ACCOUNT);

        //账号密码（需要解密）
        String password = DesUtils.decrypt(this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_PASSWORD));

        //国都短信平台url
        String url = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_URL);

        return guoDuSmsSend(url, account, password, mobile, message, extCode);
    }

    /**
     * 国都短信发送【指定url，短信账号】
     *
     * @param url      接口地址
     * @param account  短信账号
     * @param password 密码
     * @param mobile   手机号码
     * @param message  短信内容
     * @param extCode  扩展码
     * @return 返回结果
     * @throws UnsupportedEncodingException
     */
    private boolean guoDuSmsSend(String url, String account, String password, String mobile, String message, String extCode) throws UnsupportedEncodingException {
        String result = null;

        try {
            //拼装接口所需参数
            Map<String, Object> map = new HashMap<>();
            map.put("OperID", account);
            map.put("OperPass", password);
            map.put("DesMobile", mobile);
            map.put("Content", URLEncoder.encode(message, "GBK"));
            map.put("ContentType", 15);

            //扩展码
            if (!StringUtils.isEmpty(extCode)) {
                map.put("AppendID", extCode);
            }

            result = HttpUtils.doGet(url, "GBK", map);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        boolean resultCode = false;
        String sendCode = StringUtils.EMPTY;
        String resultMessage = StringUtils.EMPTY;

        //转换返回结果
        if (result != null) {
            try {
                //返回结果转化为Document
                Document doc = DocumentHelper.parseText(result);
                Element root = doc.getRootElement();

                //读取返回结果码code
                Element code = root.element("code");

                //code对应值
                if (code != null) {
                    sendCode = code.getTextTrim();
                    if ("01".equals(sendCode)) {
                        resultMessage = "批量短信提交成功";
                        resultCode = true;
                    }
                    else if ("02".equals(sendCode)) {
                        resultMessage = "IP限制";
                    }
                    else if ("03".equals(sendCode)) {
                        resultMessage = "单条短信提交成功";
                        resultCode = true;
                    }
                    else if ("04".equals(sendCode)) {
                        resultMessage = "用户名错误";
                    }
                    else if ("05".equals(sendCode)) {
                        resultMessage = "密码错误";
                    }
                    else if ("07".equals(sendCode)) {
                        resultMessage = "发送时间错误";
                    }
                    else if ("08".equals(sendCode)) {
                        resultMessage = "信息内容为黑内容";
                    }
                    else if ("09".equals(sendCode)) {
                        resultMessage = "该用户的该内容【受同天内】内容不能重复发送限制";
                    }
                    else if ("10".equals(sendCode)) {
                        resultMessage = "扩展号错误";
                    }
                    else if ("97".equals(sendCode)) {
                        resultMessage = "短信参数有误";
                    }
                    else if ("11".equals(sendCode)) {
                        resultMessage = "余额不足";
                    }
                    else if ("-1".equals(sendCode)) {
                        resultMessage = "程序异常";
                    }
                }
            }
            catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        //保存短信发送日志
        this.saveSmsLog(this.appCode, this.smsVendor, mobile, message, (resultCode ? 1 : -1), sendCode + ":" + resultMessage);

        return resultCode;
    }

    /**
     * 鸿联九五供应商短信发送【系统平台账户发送】
     *
     * @param mobile  手机号码
     * @param message 短信内容
     * @param extCode 扩展码 01：XXXXXX/02：XXX系统/03：XXX集团
     * @return 发送结果 true：成功
     */
    private boolean hlSmsSend(String mobile, String message, String extCode) throws UnsupportedEncodingException {
        //短信内容未添加正确签名场合
        if (!(message.indexOf("【XXX系统】") > -1 || message.indexOf("【XXXXXX】") > -1 || message.indexOf("【XXX集团】") > -1)) {
            //默认添加签名 【XXXXXX】
            message += "【XXXXXX】";
            //默认扩展码 01：XXXXXX
            extCode = "01";
        }
        //签名
        String signName = StringUtils.EMPTY;
        //为防止签名与扩展码不对应 在这里校验调整
        if (message.length() >= 6) {
            signName = message.substring(message.length() - 6, message.length());
            //XXX系统场合
            if (StringUtils.equals(signName, "【XXX系统】")) {
                extCode = "02";
            }
            //XXXXXX场合
            else if (StringUtils.equals(signName, "【XXXXXX】")) {
                extCode = "01";
            }
            //XXX集团场合
            else if (StringUtils.equals(signName, "【XXX集团】")) {
                extCode = "03";
            }
            //签名不正确场合 无法发送
            else {
                return false;
            }
        }

        //短信账号
        String account = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_ACCOUNT);

        //账号密码（需要解密）
        String password = DesUtils.decrypt(this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_PASSWORD));

        //账号唯一对应 epid
        String epid = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_HL_EPID);

        String url = this.parameterService.getValue(AppCodeConsts.APP_COMMON, SmsConsts.SMS_URL);

        return hlSmsSend(epid, url, account, password, mobile, message, extCode);
    }

    /**
     * 鸿联九五短信发送【指定url，短信账号】
     *
     * @param epid     账号唯一ID
     * @param url      接口地址
     * @param account  短信账号
     * @param password 密码
     * @param mobile   手机号码
     * @param message  短信内容
     * @param extCode  扩展码
     * @return 返回结果
     * @throws UnsupportedEncodingException
     */
    private boolean hlSmsSend(String epid, String url, String account, String password, String mobile, String message, String extCode) throws UnsupportedEncodingException {
        String result = null;

        try {
            //拼装接口所需参数
            Map<String, Object> map = new HashMap<>();
            map.put("username", account);
            map.put("password", password);
            map.put("phone", mobile);
            map.put("message", URLEncoder.encode(message, "UTF-8"));
            //账号唯一ID 对应短信账号
            map.put("epid", epid);
            map.put("subcode", extCode);

            result = HttpUtils.doGet(url, "UTF-8", map);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        boolean resultCode = false;
        String resultMessage = StringUtils.EMPTY;

        //转换返回结果
        if (StringUtils.isNotEmpty(result)) {
            if ("00".equals(result)) {
                resultMessage = "提交成功";
                resultCode = true;
            }
            else if ("1".equals(result)) {
                resultMessage = "参数不完整";
            }
            else if ("2".equals(result)) {
                resultMessage = "鉴权失败（包括：用户状态不正常、密码错误、用户不存在、地址验证失败，黑户）";
            }
            else if ("3".equals(result)) {
                resultMessage = "号码数量超出50条";
            }
            else if ("4".equals(result)) {
                resultMessage = "发送失败";
            }
            else if ("5".equals(result)) {
                resultMessage = "余额不足";
            }
            else if ("6".equals(result)) {
                resultMessage = "发送内容含屏蔽词";
            }
            else if ("7".equals(result)) {
                resultMessage = "短信内容超出350个字";
            }
            else if ("72".equals(result)) {
                resultMessage = "内容被审核员屏蔽";
            }
            else if ("8".equals(result)) {
                resultMessage = "号码列表中没有合法的手机号码";
            }
            else if ("9".equals(result)) {
                resultMessage = "夜间管理，不允许一次提交超过20个号码";
            }
            else if (result.indexOf("ERR") > -1) {
                resultMessage = "IP验证未通过";
            }
        }
        else {
            resultMessage = "返回结果为空";
        }

        //保存短信发送日志
        this.saveSmsLog(this.appCode, this.smsVendor, mobile, message, (resultCode ? 1 : -1), result + ":" + resultMessage);

        return resultCode;
    }

    /**
     * 保存短信发送日志
     *
     * @param appCode  应用编号
     * @param vendor   短信供应商
     * @param contents 邮件内容
     * @param mobile   手机号码
     * @param result   发送结果
     * @param error    错误消息
     */
    private void saveSmsLog(String appCode, String vendor, String mobile, String contents, int result, String error) {
        SmsLogExt smsLog = new SmsLogExt();
        smsLog.setAppCode(appCode);
        smsLog.setVendor(vendor);
        smsLog.setMessage(contents);
        smsLog.setMobile(mobile);
        smsLog.setSendDate(new Date());
        smsLog.setSendResult(result);
        smsLog.setErrorMessage(error);
        smsLog.setInsertUser(CmnConsts.ADMIN_USER_UID);
        smsLog.setUpdateUser(CmnConsts.ADMIN_USER_UID);
        smsLog.setRecordStatus(1);

        this.smsLogDao.save(smsLog);
    }
}
