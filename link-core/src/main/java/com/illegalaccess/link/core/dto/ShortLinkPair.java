package com.illegalaccess.link.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortLinkPair {
  private String longUrl;
  
  private String shortUrl;
}
