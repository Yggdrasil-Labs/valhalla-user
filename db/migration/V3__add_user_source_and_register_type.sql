-- Active: 1765029360536@@8.134.198.8@3306@valhalla-user
-- 为 user 表增加注册来源与注册类型字段
-- 迁移版本: V3
-- 描述: 新增 source 与 register_type 字段

ALTER TABLE `user`
    ADD COLUMN `source` VARCHAR(32) NOT NULL DEFAULT 'ADMIN' COMMENT '注册来源：ADMIN-管理员创建，REGISTER-自助注册' AFTER `status`,
    ADD COLUMN `register_type` VARCHAR(32) NULL COMMENT '注册类型：EMAIL/WECHAT/PHONE，自助注册场景使用' AFTER `source`;


