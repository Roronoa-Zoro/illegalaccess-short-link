package com.illegalaccess.link.server.aop;

import com.illegalaccess.link.core.exception.InvalidShortLinkException;
import com.illegalaccess.link.exception.NoApiPermissionException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ControllerExceptionHandler {
  
    @ExceptionHandler(InvalidShortLinkException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleResourceNotFound() {}
    
    
    @ExceptionHandler(NoApiPermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleNoApiPermission() {}
}
