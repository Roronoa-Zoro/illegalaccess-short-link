package com.illegalaccess.link.core;

import com.google.common.base.Joiner;
import com.illegalaccess.link.api.model.ShortLinkDto;
import com.illegalaccess.link.api.model.ShortLinkReq;
import com.illegalaccess.link.core.dto.AppKeyAuthVO;
import com.illegalaccess.link.core.dto.ShortLinkVO;
import com.illegalaccess.link.db.entity.AppKeyAuthEntity;
import com.illegalaccess.link.db.entity.ShortLinkEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConverterUtil {
    private final String CHARS = "ruDnRJACS93vdXxeLPOosWVbMyfkpzYcF46UGahqt5ZmKwgNQB8HjI271lT0Ei";

    @Value("${link.domain}")
    private String shortLinkDomain;
    
    /**
     * 进制转换比率
     */
    private int SCALE = 62;
    /**
     * 十进制数字转为62进制字符串
     *
     * @param val 十进制数字
     * @return 62进制字符串
     */
    public String encode10To62(long val) {
        if (val < 0) {
            throw new IllegalArgumentException("this is an Invalid parameter:" + val);
        }
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (Math.abs(val) > SCALE - 1) {
            //从最后一位开始进制转换，取转换后的值，最后反转字符串
            remainder = Long.valueOf(val % SCALE).intValue();
            sb.append(CHARS.charAt(remainder));
            val = val / SCALE;
        }
        //获取最高位
        sb.append(CHARS.charAt(Long.valueOf(val).intValue()));
        return sb.reverse().toString();
    }
    
    
    public String toShortLink(Long incrId) {
        String code =   encode10To62(incrId);
        return Joiner.on("/").join(shortLinkDomain, code);
    }
    
    public String toShortLink(String shortLinkCode) {
        return Joiner.on("/").join(shortLinkDomain, shortLinkCode);
    }
    
    public ShortLinkEntity toShortLinkEntity(ShortLinkDto req, String appKey) {
        // todo
        ShortLinkEntity entity = new ShortLinkEntity();
        entity.setLongUrl(req.getLongUrl());

        return entity;
    }
    
    public List<ShortLinkEntity> toShortLinkEntityList(List<ShortLinkReq> list) {
        // todo
        return null;
    }

    public ShortLinkVO toLinkVO(ShortLinkEntity shortLinkEntity) {
        return null;
    }
    
    public List<ShortLinkVO> toLinkVOList(List<ShortLinkEntity> shortLinkEntityList) {
        return null;
    }
    
    public AppKeyAuthVO toAppKeyAuthVO(AppKeyAuthEntity entity) {
    	// todo
    	return null;
    }
}
