package com.illegalaccess.link.core.prop;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RedirectLinkProp {

    private String invalidUrl;

    /**
     * 根据appkey，找到配置的需要进行重定向的链接
     * @param appKey
     * @return
     */
    public String getRedirectLink(String appKey) {
        // todo
        return null;
    }
}
