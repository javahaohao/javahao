package com.github.javahao.annotation;

import java.lang.annotation.*;

/**
 * use for:资源模块
 * Created by javahao on 2017/2/20.
 * auth:JavaHao
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    String value() default "";
}
