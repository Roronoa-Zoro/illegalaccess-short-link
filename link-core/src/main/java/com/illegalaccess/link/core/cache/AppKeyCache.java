package com.illegalaccess.link.core.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.illegalaccess.link.core.ConverterUtil;
import com.illegalaccess.link.core.dto.AppKeyAuthVO;
import com.illegalaccess.link.core.utils.CacheKeyUtil;
import com.illegalaccess.link.core.utils.JsonUtil;
import com.illegalaccess.link.db.entity.AppKeyAuthEntity;


@Slf4j
@Component
public class AppKeyCache {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ConverterUtil converterUtil;
    /**
     * 查询appkey的auth信息
     * @param appKey
     * @param authName
     * @return
     */
    public AppKeyAuthVO getAppKeyAuth(String appKey, String authName) {
    	String cacheKey = CacheKeyUtil.createHashAppKey(appKey);
    	Object auth = stringRedisTemplate.opsForHash().get(cacheKey, authName);
    	if (ObjectUtils.isEmpty(auth)) {
    		log.info("get cached appkey:{} auth:{} fail, nothing returned", appKey, authName);
    		return null;
    	}
    	log.info("get cached appkey:{} auth:{}", appKey, authName);
    	return JsonUtil.parseObject(auth.toString(), AppKeyAuthVO.class);
    }
    
    /**
     * 缓存appkey的auth信息
     * @param appKey
     * @param authName
     * @param authEntity
     * @return
     */
    public boolean cacheAppKeyAuth(String appKey, String authName, AppKeyAuthEntity authEntity) {
    	String cacheKey = CacheKeyUtil.createHashAppKey(appKey);
    	stringRedisTemplate.opsForHash().put(cacheKey, authName, JsonUtil.toJsonString(converterUtil.toAppKeyAuthVO(authEntity)));
    	log.info("cached appkey:{} and auth:{} successfully", appKey, authName);
    	return true;
    }

    /**
     * 查询缓存的appkey的标识
     * @param appKey
     * @return
     */
    public String getCacheAppKey(String appKey) {
    	String cacheKey = CacheKeyUtil.createAppKey(appKey);
    	return stringRedisTemplate.opsForValue().get(cacheKey);
    }
    
    /**
     * 缓存的appkey的标识
     * @param appKey
     * @return
     */
    public boolean cacheAppKey(String appKey) {
    	String cacheKey = CacheKeyUtil.createAppKey(appKey);
    	stringRedisTemplate.opsForValue().set(cacheKey, "1");
    	log.info("cached appkey:{}", appKey);
    	return true;
    }
    
    /**
     * 清除缓存的appkey的标识
     * @param appKey
     * @return
     */
    public boolean clearAppKey(String appKey) {
    	String cacheKey = CacheKeyUtil.createAppKey(appKey);
    	stringRedisTemplate.delete(cacheKey);
    	return true;
    }
}
