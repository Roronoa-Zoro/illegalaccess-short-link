package com.illegalaccess.link.db.dao;

import com.illegalaccess.link.db.entity.BusinessAppKeyEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AppKeyDao {
  
  @Insert("insert into app_key(app_key,app_id,app_secret,status,create_time,update_time) values(#{appKey}, #{appId}, #{appSecret},}#{status}, #{createTime}, #{updateTime})")
  Long saveAppKey(BusinessAppKeyEntity appKeyEntity);
  
  @Update("update app_key set status=#{status} where id=#{id}")
  int updateAppKey(BusinessAppKeyEntity appKeyEntity);

  int batchInsert(List<BusinessAppKeyEntity> list);
  
  @Select("select * from business_app_key where app_key=#{appKey} and status=1")
  BusinessAppKeyEntity queryAppKey(@Param("appKey") String appKey);
  
  @Select("select * from business_app_key where status=1")
  List<BusinessAppKeyEntity> queryAll();
}
