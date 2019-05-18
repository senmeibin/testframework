package com.bpms.core.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    /**
     * Excel 列名
     * 单个时 字段名
     * 多个时 以半角逗号分隔，譬如 企业名称,公司名称
     *
     * @return 列名
     */
    String name();

    boolean required() default false;

    String dateFormat() default "";
}
