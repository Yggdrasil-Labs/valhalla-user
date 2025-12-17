-- RBAC表结构设计
-- 字符集: utf8mb4
-- 排序规则: utf8mb4_unicode_ci
-- 数据库: MySQL 8.4

-- 用户表（密码由认证服务管理，不在此表存储）
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL COMMENT '用户ID（雪花ID）',
    `username` VARCHAR(64) NOT NULL COMMENT '用户名',
    `email` VARCHAR(100) NULL COMMENT '邮箱',
    `phone` VARCHAR(20) NULL COMMENT '电话',
    `nickname` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '昵称',
    `avatar` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '头像URL',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `metadata` JSON NULL COMMENT '扩展信息（JSON）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除时间戳（0-未删除）',
    `version` INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_status_deleted` (`status`, `deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `role` (
    `id` BIGINT NOT NULL COMMENT '角色ID（雪花ID）',
    `role_code` VARCHAR(50) NOT NULL COMMENT '角色代码：ADMIN-管理员，USER-用户，GUEST-游客',
    `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
    `description` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '角色描述',
    `is_system` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否系统角色（不可删除）',
    `metadata` JSON NULL COMMENT '扩展信息（JSON）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除时间戳（0-未删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`),
    KEY `idx_deleted_at` (`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 权限表（动作级权限，模块、资源、操作分开存储，扁平结构，不支持层级和权限组）
CREATE TABLE IF NOT EXISTS `permission` (
    `id` BIGINT NOT NULL COMMENT '权限ID（雪花ID）',
    `module` VARCHAR(50) NOT NULL COMMENT '模块（如user）',
    `resource` VARCHAR(50) NOT NULL COMMENT '资源（如profile）',
    `action` VARCHAR(50) NOT NULL COMMENT '操作（如update）',
    `permission_code` VARCHAR(128) NOT NULL COMMENT '权限代码（格式：模块:资源:操作，如user:profile:update）',
    `permission_name` VARCHAR(100) NOT NULL COMMENT '权限名称',
    `description` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '权限描述',
    `metadata` JSON NULL COMMENT '扩展信息（JSON）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除时间戳（0-未删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_permission_code` (`permission_code`),
    UNIQUE KEY `uk_module_resource_action` (`module`, `resource`, `action`),
    KEY `idx_module_deleted` (`module`, `deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- 接口表（系统接口定义，模块、资源、操作通过权限接口关联表获取）
CREATE TABLE IF NOT EXISTS `api` (
    `id` BIGINT NOT NULL COMMENT '接口ID（雪花ID）',
    `api_code` VARCHAR(128) NOT NULL COMMENT '接口代码',
    `api_name` VARCHAR(100) NOT NULL COMMENT '接口名称',
    `resource_path` VARCHAR(255) NOT NULL COMMENT '资源路径（API路径，用于接口匹配）',
    `resource_method` VARCHAR(10) NOT NULL COMMENT 'HTTP方法（GET、POST、PUT、DELETE等，用于接口匹配）',
    `description` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '接口描述',
    `metadata` JSON NULL COMMENT '扩展信息（JSON）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除时间戳（0-未删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_path_method` (`resource_path`, `resource_method`),
    KEY `idx_api_code_deleted` (`api_code`, `deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口表';

-- 权限接口关联表（权限与接口的一对多关系）
CREATE TABLE IF NOT EXISTS `permission_api` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `permission_id` BIGINT NOT NULL COMMENT '权限ID',
    `api_id` BIGINT NOT NULL COMMENT '接口ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_permission_api` (`permission_id`, `api_id`),
    KEY `idx_api_id` (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限接口关联表';

-- 角色权限关联表（多对多）
CREATE TABLE IF NOT EXISTS `role_permission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT NOT NULL COMMENT '权限ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
    KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- 用户角色关联表（多对多）
CREATE TABLE IF NOT EXISTS `user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

