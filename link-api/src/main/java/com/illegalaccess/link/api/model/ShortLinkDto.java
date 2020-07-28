package com.illegalaccess.link.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkDto implements Serializable {

    private static final long serialVersionUID = 4643369882058045709L;
    private String longUrl;
    private int shortUrlExpire = 90; // 短链接过期时间
    private TimeUnit shortUlrExpireUnit = TimeUnit.DAYS; // 短链接过期时间单位
    private boolean cacheLongUrl = true; // 是否缓存长链接
}
