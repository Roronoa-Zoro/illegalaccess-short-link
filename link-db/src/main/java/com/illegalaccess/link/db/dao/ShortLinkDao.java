package com.illegalaccess.link.db.dao;

import com.illegalaccess.link.db.entity.ShortLinkEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 短链接操作相关的dao
 */
public interface ShortLinkDao {

    @Select("select id, long_url,short_url,app_key,expire_time from t_short_link " +
            "where short_url=#{shortUrl} and expire_time>#{expireTime} and status=1")
    ShortLinkEntity queryByShortLink(@Param("shortUrl") String shortUrl, @Param("expireTime")LocalDateTime expireTime);

    
    @Insert({
     "<script>",
     "insert into t_short_link(long_url,short_url,app_key,expire_time,status,create_time,update_time) values ",
     "<foreach collection='list' item='item' index='index' separator=','>",
     "(#{item.longUrl}, #{item.shortUrl}, #{item.appKey}, #{item.expireTime}, #{item.status}, #{item.createTime}, #{item.updateTime})",
     "</foreach>",
     "</script>"
    })
    int batchInsert(@Param("list") List<ShortLinkEntity> list);

}
