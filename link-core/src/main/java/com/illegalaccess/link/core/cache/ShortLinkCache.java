package com.illegalaccess.link.core.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.illegalaccess.link.core.dto.ShortLinkVO;
import com.illegalaccess.link.core.utils.CacheKeyUtil;
import com.illegalaccess.link.core.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ShortLinkCache {

	 @Autowired
	 private StringRedisTemplate stringRedisTemplate;
	 
	 /**
	  * 根据长链接，获取缓存的短链接
	  * @param longUrl
	  * @return
	  */
	 public String getCachedShortLink(String longUrl) {
		 return stringRedisTemplate.opsForValue().get(CacheKeyUtil.createLongUrlCacheKey(longUrl));
	 }
	 
	 /**
	  * 延长长链接缓存时间1小时
	  * @param longUrl
	  * @return
	  */
	 public boolean extendLongUrlExpire(String longUrl) {
		 stringRedisTemplate.expire(CacheKeyUtil.createLongUrlCacheKey(longUrl), 1, TimeUnit.HOURS);
		 return true;
	 }
	 
	 /**
	  * 缓存短链接的信息
	  * @param map
	  * @return
	  */
	 public boolean cacheAllShortLinkVO(List<ShortLinkVO> list) {
		 
		 return true;
	 }
	 
	 public boolean cacheShortLinkVO(ShortLinkVO shortLinkVo, long expire, TimeUnit timeunit) {
		 stringRedisTemplate.opsForValue().set(shortLinkVo.getShortUrl(), JsonUtil.toJsonString(shortLinkVo), expire, timeunit);
		 return true;
	 }
	 
	 /**
	  * 获取缓存的短链接信息
	  * @param shortLink
	  * @return
	  */
	 public ShortLinkVO getCachedShortLinkVO(String shortLink) {
		 String cachedKey = CacheKeyUtil.createShortUrlCacheKey(shortLink);
		 String cachedValue = stringRedisTemplate.opsForValue().get(cachedKey);
		 if (StringUtils.isEmpty(cachedValue)) {
			 return null;
		 }
		 
		 return JsonUtil.parseObject(cachedValue, ShortLinkVO.class);
	 }
	 
	 /**
	  * 缓存所有的长链到短链的映射
	  * @param longUrlMap
	  * @return
	  */
	 public boolean cacheAllLongUrl(Map</**longUrl**/String, /**shortUrl**/String> longUrlMap) {
		 stringRedisTemplate.opsForValue().multiSet(longUrlMap);
		 return true;
	 }
}
