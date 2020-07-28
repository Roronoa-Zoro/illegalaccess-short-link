package com.illegalaccess.link.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkResp implements Serializable {

    private static final long serialVersionUID = 5781968023427070792L;
    private String longUrl;
    private String shortUrl;
}
