package com.illegalaccess.link.server;

import java.lang.annotation.*;

/**
* 访问的接口是否要权限控制
 标记了该注解的接口，都需要校验appkey
 标记了要校验方法级别的校验时，才需要校验是否有访问方法的权限
*/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthApi {

    String apiMethodName() default ""; // 默认不校验方法级别的权限
}
