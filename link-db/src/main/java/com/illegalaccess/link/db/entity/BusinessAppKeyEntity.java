package com.illegalaccess.link.db.entity;

import java.time.LocalDateTime;

/**
 * 给业务分配的appkey实体
 */
public class BusinessAppKeyEntity {

    private Long id; // pk

    private String appKey; // 唯一值, 可阅读的值

    private String appId; // UUID

    private String appSecret; // 签名时使用的盐值

    private String appDesc; // appkey的说明，即服务说明

    private String appOwner; // app的负责人，多个人用逗号分隔，存储邮箱

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
