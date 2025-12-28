-- Active: 1765029360536@@8.134.198.8@3306@valhalla-user
-- 为 api 表增加版本和状态字段
-- 迁移版本: V4
-- 描述: 新增 version 与 status 字段，调整唯一索引约束

-- 1. 新增 version 字段（在 api_code 之后）
ALTER TABLE `api`
    ADD COLUMN `version` VARCHAR(32) NOT NULL DEFAULT 'v1' COMMENT '接口版本（v1 / v2 / v3）' AFTER `api_code`;

-- 2. 新增 status 字段（在 resource_method 之后）
ALTER TABLE `api`
    ADD COLUMN `status` VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '接口状态（ENABLED / DEPRECATED / DISABLED）' AFTER `resource_method`;

-- 3. 为现有数据设置默认值（确保数据完整性）
UPDATE `api` SET `version` = 'v1' WHERE `version` IS NULL OR `version` = '';
UPDATE `api` SET `status` = 'ENABLED' WHERE `status` IS NULL OR `status` = '';

-- 4. 删除旧索引
ALTER TABLE `api` DROP INDEX `uk_path_method`;
ALTER TABLE `api` DROP INDEX `idx_api_code_deleted`;

-- 5. 新增索引
-- 同一能力下，不允许重复版本（考虑软删除）
ALTER TABLE `api`
    ADD UNIQUE KEY `uk_api_code_version` (`api_code`, `version`, `deleted_at`);

-- 防止接口实现重复注册（考虑软删除）
ALTER TABLE `api`
    ADD UNIQUE KEY `uk_path_method_deleted` (`resource_path`, `resource_method`, `deleted_at`);

-- 查询能力接口族
ALTER TABLE `api`
    ADD KEY `idx_api_code` (`api_code`);

