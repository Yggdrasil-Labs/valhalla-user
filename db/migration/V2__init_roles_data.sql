-- 初始化系统角色数据
-- 迁移版本: V2
-- 描述: 插入默认角色数据（管理员、用户、游客）

-- 插入默认角色
-- 注意：实际ID由雪花ID生成器自动生成，这里使用固定值仅用于初始化
-- 如果使用MyBatis-Plus的IdType.ASSIGN_ID，插入时ID会自动生成
INSERT INTO `role` (`id`, `role_code`, `role_name`, `description`, `is_system`, `create_time`, `update_time`) VALUES
(1, 'ADMIN', '管理员', '系统管理员，拥有所有权限', 1, NOW(), NOW()),
(2, 'USER', '用户', '普通用户，拥有基础权限', 1, NOW(), NOW()),
(3, 'GUEST', '游客', '游客角色，拥有只读权限', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE `update_time` = NOW();

