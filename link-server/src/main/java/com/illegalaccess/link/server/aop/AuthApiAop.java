package com.illegalaccess.link.server.aop;

import com.illegalaccess.link.api.model.BaseReq;
import com.illegalaccess.link.core.cache.AppKeyCache;
import com.illegalaccess.link.core.dto.AppKeyAuthVO;
import com.illegalaccess.link.exception.NoApiPermissionException;
import com.illegalaccess.link.server.AuthApi;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class AuthApiAop {

    @Autowired
    private AppKeyCache appKeyCache;
    
    @Pointcut("@annotation(com.illegalaccess.link.server.AuthApi)")
    public void pointcut() {
    }
    
    @Before("pointcut()")
    public void accessCheck(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        AuthApi authApi = targetMethod.getAnnotation(AuthApi.class);
        if (authApi == null) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        BaseReq base = (BaseReq) args[0];
        String appKey = base.getAppKey();
        String apiMethod;
        if (StringUtils.isEmpty(authApi.apiMethodName())) {
        	apiMethod = methodName;
        } else {
        	apiMethod = authApi.apiMethodName();
        }
        
        AppKeyAuthVO auth = appKeyCache.getAppKeyAuth(appKey, apiMethod);
        
        if (auth == null) {
        	log.info("check appkey:{} apiMethod:{} fail, not allowed", appKey, apiMethod);
        	throw new NoApiPermissionException();
        }
        
        log.info("check appkey:{} apiMethod:{} successfully", appKey, apiMethod);
    }

}
