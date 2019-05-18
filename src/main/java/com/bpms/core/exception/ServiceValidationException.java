/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bpms.core.exception;

/**
 * Service层数据验证公用的Exception.
 * <p>
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
public class ServiceValidationException extends RuntimeException {

    private static final long serialVersionUID = 3583566093089790859L;

    public ServiceValidationException() {
        super();
    }

    public ServiceValidationException(String message) {
        super(message);
    }

    public ServiceValidationException(Throwable cause) {
        super(cause);
    }

    public ServiceValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
