package com.illegalaccess.link.db.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppKeyAuthEntity {
    
    private Long id;
    
    private String appKey;
    
    private String accessMethod;
    
    private Integer status;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
