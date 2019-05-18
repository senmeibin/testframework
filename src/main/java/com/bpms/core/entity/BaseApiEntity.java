package com.bpms.core.entity;

import java.io.Serializable;

/**
 * api专用Entity
 */
public class BaseApiEntity implements Serializable {
    private static final long serialVersionUID = 6720864657040231966L;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 数据
     */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
