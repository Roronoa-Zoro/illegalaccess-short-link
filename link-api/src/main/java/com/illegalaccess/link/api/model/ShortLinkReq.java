package com.illegalaccess.link.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkReq extends BaseReq {

    private static final long serialVersionUID = -1213387718881402116L;
    List<ShortLinkDto> shortLinkDtos;
}
