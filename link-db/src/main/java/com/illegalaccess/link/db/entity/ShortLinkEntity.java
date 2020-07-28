package com.illegalaccess.link.db.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短链接实体
 */
@Data
public class ShortLinkEntity {

    private Long id;
    /**
     * 长链接
     */
    private String longUrl;
    /**
     * 短链接
     */
    private String shortUrl;
    /**
     * 业务appkey
     */
    private String appKey;
    /**
     * 短链接是否有效 0-有效，1-无效
     */
    private Integer status;

    private LocalDateTime expireTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
