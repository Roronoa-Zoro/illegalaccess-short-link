package com.illegalaccess.link.api.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseReq implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String appKey;
}
