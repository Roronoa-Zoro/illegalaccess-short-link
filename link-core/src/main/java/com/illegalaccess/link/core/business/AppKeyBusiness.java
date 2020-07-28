package com.illegalaccess.link.core.business;

import com.illegalaccess.link.core.cache.AppKeyCache;
import com.illegalaccess.link.core.dto.AppKeyAuthVO;
import com.illegalaccess.link.db.entity.AppKeyAuthEntity;
import com.illegalaccess.link.db.entity.BusinessAppKeyEntity;
import com.illegalaccess.link.db.service.AppKeyAuthService;
import com.illegalaccess.link.db.service.AppKeyService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class AppKeyBusiness {

    @Autowired
    private AppKeyService appKeyService;
    @Autowired
    private AppKeyAuthService appKeyAuthService;
    @Autowired
    private AppKeyCache appkeyCache;

    /**
     * 判断appkey是否存在
     * @param appKey
     * @return
     */
    public boolean isAppKeyExist(String appKey) {
        String val = appkeyCache.getCacheAppKey(appKey);
        if (!StringUtils.isEmpty(val)) {
        	return true;
        }
        BusinessAppKeyEntity appKeyObj = appKeyService.queryByAppKey(appKey);
        if (appKeyObj == null) {
        	log.info("can not find info from db for appkey:{}", appKey);
            return false;
        }
        
        appkeyCache.cacheAppKey(appKey);
        return true;
    }
    
    public boolean clearAppKeyCache(String appKey) {
    	appkeyCache.clearAppKey(appKey);
        return true;
    }
    
    public boolean checkAppKeyAuth(String appKey, String authName) {
    	AppKeyAuthVO auth = appkeyCache.getAppKeyAuth(appKey, authName);
        if (auth != null) {
            return true;
        }
        
        AppKeyAuthEntity authEntity = appKeyAuthService.queryByAppKey(appKey, authName);
        if (authEntity == null) {
        	log.info("can not find auth info from db for appkey:{}", appKey);
           return false;
        }
        
        appkeyCache.cacheAppKeyAuth(appKey, authName, authEntity);
        return true;
    }

    // 根据app_key_auth的id，把状态置为失效
    public boolean invalidateAuth(Long authId) {
        // todo
        return true;
    }
}
