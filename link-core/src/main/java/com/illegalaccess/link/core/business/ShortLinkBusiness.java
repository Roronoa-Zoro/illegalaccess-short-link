package com.illegalaccess.link.core.business;

import com.illegalaccess.link.api.model.ShortLinkDto;
import com.illegalaccess.link.api.model.ShortLinkResp;
import com.illegalaccess.link.core.ConverterUtil;
import com.illegalaccess.link.core.cache.ShortLinkCache;
import com.illegalaccess.link.core.dto.RedirectShortLinkResp;
import com.illegalaccess.link.core.dto.ShortLinkVO;
import com.illegalaccess.link.core.prop.RedirectLinkProp;
import com.illegalaccess.link.db.entity.ShortLinkEntity;
import com.illegalaccess.link.db.service.ShortLinkService;
import com.illegalaccess.link.sequence.SequenceEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Component
public class ShortLinkBusiness {

    @Autowired
    private ConverterUtil converterUtil;
    @Autowired
    private SequenceEmitter sequenceEmitter;
    @Autowired
    private ShortLinkService shortLinkService;
    @Autowired
    private RedirectLinkProp redirectLinkProp;
    @Autowired
    private ShortLinkCache shortLinkCache;
    @Autowired
    private AsyncBusiness asyncBusiness;

    private final String appKey = "shortLink";

    public Set<ShortLinkResp> generateShortLink(List<ShortLinkDto> request) {
        // todo aop校验appkey合法性和是否能访问该方法
        Set<ShortLinkResp> existed = new HashSet<>();
        Set<ShortLinkDto> newReq = new HashSet<>();
        request.forEach(req -> {
            String shortUrl = shortLinkCache.getCachedShortLink(req.getLongUrl());
            if (!StringUtils.isEmpty(shortUrl)) {
                // todo 延长1小时
            	shortLinkCache.extendLongUrlExpire(req.getLongUrl());
                existed.add(ShortLinkResp.builder().longUrl(req.getLongUrl()).shortUrl(shortUrl).build());
            } else {
                newReq.add(req);
            }
        });

        Set<ShortLinkResp> new4Cache = new HashSet<>();
        List<ShortLinkEntity> new4Db = new ArrayList<>();
        // todo 把newReq生成短链，转成db entity，然后入库
        newReq.forEach(req -> {
            Long val = sequenceEmitter.getNextSequence(appKey);
            String shortLink = converterUtil.toShortLink(val);
            ShortLinkResp resp = ShortLinkResp.builder().longUrl(req.getLongUrl()).shortUrl(shortLink).build();
            new4Cache.add(resp);
            existed.add(resp);
            new4Db.add(converterUtil.toShortLinkEntity(req, shortLink));
        });
        shortLinkService.saveAll(new4Db);
        // 新的长链到短链的映射关系入cache, in失败不回滚db
        asyncBusiness.cacheNewUrls(new4Cache, new4Db);
        // todo 新的短链到长链的映射关系入cache，失败不回滚db

        return existed;
    }

    /**
     * @param shortLinkCode 短链接后面的字符串
     */
    public RedirectShortLinkResp getRedirectLink(String shortLinkCode) {
        String shortLink = converterUtil.toShortLink(shortLinkCode);
        ShortLinkVO shortLinkVo = shortLinkCache.getCachedShortLinkVO(shortLink);
        if (shortLinkVo != null) {
            return buildRedirectResp(shortLinkVo);
        }

        // 可能得加个布隆过滤器或者布谷鸟过滤器
        ShortLinkEntity shortLinkEntity = shortLinkService.queryByShortLink(shortLink);
        if (shortLinkEntity == null) { // 无效的链接或者已经失效
            // 跳转到无效的页面
            return RedirectShortLinkResp.builder().redirectUrl(redirectLinkProp.getInvalidUrl())
                    .redirectOri(false)
                    .build();
        }

        shortLinkVo = converterUtil.toLinkVO(shortLinkEntity);
        LocalDateTime future = LocalDateTime.now().plusDays(3);
        LocalDateTime expire;
        if (future.isAfter(shortLinkVo.getExpireTime())) {
            expire = shortLinkVo.getExpireTime();
        } else {
            expire = future;
        }

        long mins = Duration.between(LocalDateTime.now(), expire).toMinutes();

//        int seconds = Duration.between(LocalDateTime.now(), expire).toSeconds();
        // 计算当前时间到过期时间直接的时间差，若超过3天，则缓存中记录的过期时间为3天，不足3天则缓存时间的过期时间
        shortLinkCache.cacheShortLinkVO(shortLinkVo, mins, TimeUnit.MINUTES);
        // 数据入缓存，返回响应

        // todo
        return buildRedirectResp(shortLinkVo);
    }

    private RedirectShortLinkResp buildRedirectResp(ShortLinkVO shortLinkVo) {
        String configuredLink = redirectLinkProp.getRedirectLink(shortLinkVo.getAppKey());
        if (!StringUtils.isEmpty(configuredLink)) {
            return RedirectShortLinkResp.builder()
                    .redirectUrl(configuredLink)
                    .redirectOri(false)
                    .redirectAttrValue(shortLinkVo.getLongUrl())
                    .build();
        }
        return RedirectShortLinkResp.builder().redirectUrl(shortLinkVo.getLongUrl()).build();
    }


}
