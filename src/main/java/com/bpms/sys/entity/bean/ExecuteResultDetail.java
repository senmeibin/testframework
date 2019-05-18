package com.bpms.sys.entity.bean;

import com.bpms.core.utils.StringUtils;

public class ExecuteResultDetail {
    /**
     * 同步返回结果 code
     */
    private String result;

    /**
     * 同步返回结果 message
     */
    private String msg;

    public ExecuteResultDetail(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public ExecuteResultDetail() {
    }

    public String getResult() {
        //秘钥验证未通过
        if (StringUtils.equals("SIGNFAIL", result)) {
            return "0";
        }
        //同步成功
        if (StringUtils.equals("SUCCESS", result)) {
            return "1";
        }
        //参数为空或者json数据格式错误
        if (StringUtils.equals("FIELDMISS", result) || StringUtils.equals("FAIL", result)) {
            return "2";
        }
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
