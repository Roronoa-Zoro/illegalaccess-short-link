package com.illegalaccess.link.db.dao;

import com.illegalaccess.link.db.entity.AppKeyAuthEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AppKeyAuthDao {

    @Select("select id, app_key,access_method from app_key_auth where app_key=#{appKey}")
    List<AppKeyAuthEntity> queryAllByKey(@Param("appKey") String appKey);

    AppKeyAuthEntity queryByAppKeyAndAuthName(@Param("appKey") String appKey, @Param("authName") String authName);
    
    @Insert({
     "<script>",
     "insert into t_app_key_auth(app_key,access_method,status,create_time,update_time) values ",
     "<foreach collection='list' item='item' index='index' separator=','>",
     "( #{item.appKey}, #{item.accessMethod}, #{item.status}, #{item.createTime}, #{item.updateTime})",
     "</foreach>",
     "</script>"
    })
    int batchInsert(@Param("list") List<AppKeyAuthEntity> list);
}
