package com.illegalaccess.link.db.service;

import com.illegalaccess.link.db.dao.AppKeyAuthDao;
import com.illegalaccess.link.db.entity.AppKeyAuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppKeyAuthService {
    
    @Autowired
    private AppKeyAuthDao appKeyAuthDao;
    
    public boolean saveAppKeyAuth(List<AppKeyAuthEntity> entities) {
        return appKeyAuthDao.batchInsert(entities) > 0;
    }
    
    public List<AppKeyAuthEntity> queryByAppKey(String appKey) {
        return appKeyAuthDao.queryAllByKey(appKey);
    }
    
    public AppKeyAuthEntity queryByAppKey(String appKey, String authName) {
        return appKeyAuthDao.queryByAppKeyAndAuthName(appKey, authName);
    }
}
