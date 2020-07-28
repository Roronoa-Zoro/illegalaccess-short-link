package com.illegalaccess.link.core.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShortLinkVO {
    
    private String appKey;
    private String shortUrl;
    private String longUrl;
    private LocalDateTime expireTime;
}
