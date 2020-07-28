package com.illegalaccess.link.sequence.dao;

import com.illegalaccess.link.sequence.entity.SequenceEmitterEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 基于数据库的发号器
 */
public interface SequenceEmitterDao {

    @Select("select current_val, step_val from t_sequence_emitter " +
            "where sharding_id=#{shardingId}")
    SequenceEmitterEntity queryByShardingId(@Param("shardingId") Long shardingId);

    @Update("update t_sequence_emitter set current_val=current_val + #{incrVal} " +
            "where sharding_id=#{shardingId} and current_val=#{currentVal}")
    int updateSequence(@Param("shardingId") Long shardingId, @Param("currentVal") Long currentVal, @Param("incrVal") Long incrVal);

    /**
     * 管理后台使用，分配
     * @param list
     * @return
     */
    int batchInsert(List<SequenceEmitterEntity> list);

    @Select("select * from t_sequence_emitter where app_key=#{appKey}")
    List<SequenceEmitterEntity> queryAll(@Param("appKey") String appKey);
}
