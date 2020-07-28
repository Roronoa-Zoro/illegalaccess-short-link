package com.illegalaccess.link.core.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedirectShortLinkResp {
  // 重定向的url，如果是非原始的长链接，则下面的属性有值
  private String redirectUrl;
  // 是否重定向到原始长链接的标识，默认true
  private boolean redirectOri = true;
  // 重定向到配置的特定的url时，把原始长链接当成一个属性传递过去，这个字段标识属性的name
  private String redirectAttrName;
  // 重定向到配置的特定的url时，把原始长链接当成一个属性传递过去，这个字段标识原始长链接的值
  private String redirectAttrValue;
}
