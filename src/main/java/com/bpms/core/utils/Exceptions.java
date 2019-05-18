package com.bpms.core.utils;

/**
 * 关于异常的工具类.
 */
public class Exceptions {

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Throwable ex) {
        if (ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        }
        else {
            return new RuntimeException(ex);
        }
    }
}
