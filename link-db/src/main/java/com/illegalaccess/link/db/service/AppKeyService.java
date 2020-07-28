package com.illegalaccess.link.db.service;

import com.illegalaccess.link.db.dao.AppKeyDao;
import com.illegalaccess.link.db.entity.BusinessAppKeyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppKeyService {
  
  @Autowired
  private AppKeyDao appKeyDao;
  
  public BusinessAppKeyEntity queryByAppKey(String appKey) {
      return appKeyDao.queryAppKey(appKey);    
  }
  
  public boolean inactivateAppKey(BusinessAppKeyEntity appKey) {
      int update = appKeyDao.updateAppKey(appKey); 
      return update == 1;
  }
  
  public List<BusinessAppKeyEntity> queryAll() {
      return appKeyDao.queryAll(); 
  }
  
  public int saveAll(List<BusinessAppKeyEntity> list) {
      return appKeyDao.batchInsert(list); 
  }
}
