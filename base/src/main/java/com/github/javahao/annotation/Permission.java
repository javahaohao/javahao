package com.github.javahao.annotation;



import java.lang.annotation.*;

/**
 * use for:资源
 * Created by javahao on 2017/2/20.
 * auth:JavaHao
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    /**
     * 资源
     * @return
     */
    String[] value() default "";

    /**
     * 关系
     * @return
     */
    Relation relation() default Relation.AND;
}
