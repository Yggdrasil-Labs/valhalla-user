-- Active: 1765029360536@@8.134.198.8@3306@valhalla-user
-- 初始化API、权限和角色关联数据
-- 迁移版本: V5
-- 描述: 扫描项目API接口，生成API表、权限表数据，并建立角色-权限-接口关联关系

-- ============================================
-- 1. 插入API接口数据
-- ============================================
-- API ID语义化编码规则（7位固定长度）：
--   格式：1 + 系统(1位，0-9) + 模块(2位，00-99) + 接口(3位，000-999)
--   示例：1000000 = 1(接口) + 0(系统1) + 00(模块1) + 000(接口1)
--   系统1 (valhalla-user) 对应编码0
--     模块1 (用户管理) 对应编码00，接口范围：1000000-1000999
--     模块2 (角色管理) 对应编码01，接口范围：1001000-1001999
--     模块3 (权限管理) 对应编码02，接口范围：1002000-1002999
--     模块4 (API管理)  对应编码03，接口范围：1003000-1003999
--     模块5-99: 1000400-1009999 (预留)

-- 用户管理模块 API (系统1-模块1: 1000000-1000099)
INSERT INTO `api` (`id`, `api_code`, `version`, `api_name`, `resource_path`, `resource_method`, `status`, `description`, `create_time`, `update_time`, `deleted_at`) VALUES
(1000000, 'USER_CREATE', 'v1', '创建用户', '/api/v1/users', 'POST', 'ENABLED', '创建新用户', NOW(), NOW(), 0),
(1000001, 'USER_UPDATE', 'v1', '更新用户', '/api/v1/users/{id}', 'PUT', 'ENABLED', '更新用户信息', NOW(), NOW(), 0),
(1000002, 'USER_DELETE', 'v1', '删除用户', '/api/v1/users/{id}', 'DELETE', 'ENABLED', '删除用户（软删除）', NOW(), NOW(), 0),
(1000003, 'USER_GET', 'v1', '获取用户详情', '/api/v1/users/{id}', 'GET', 'ENABLED', '根据ID获取用户详细信息', NOW(), NOW(), 0),
(1000004, 'USER_PAGE', 'v1', '分页查询用户', '/api/v1/users', 'GET', 'ENABLED', '分页查询用户列表', NOW(), NOW(), 0),
(1000005, 'USER_ASSIGN_ROLE', 'v1', '分配用户角色', '/api/v1/users/{id}/roles', 'POST', 'ENABLED', '为用户分配角色', NOW(), NOW(), 0)

ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 角色管理模块 API (系统1-模块2: 1001000-1001999)
INSERT INTO `api` (`id`, `api_code`, `version`, `api_name`, `resource_path`, `resource_method`, `status`, `description`, `create_time`, `update_time`, `deleted_at`) VALUES
(1001000, 'ROLE_CREATE', 'v1', '创建角色', '/api/v1/roles', 'POST', 'ENABLED', '创建新角色', NOW(), NOW(), 0),
(1001001, 'ROLE_UPDATE', 'v1', '更新角色', '/api/v1/roles/{id}', 'PUT', 'ENABLED', '更新角色信息', NOW(), NOW(), 0),
(1001002, 'ROLE_DELETE', 'v1', '删除角色', '/api/v1/roles/{id}', 'DELETE', 'ENABLED', '删除角色（软删除）', NOW(), NOW(), 0),
(1001003, 'ROLE_GET', 'v1', '获取角色详情', '/api/v1/roles/{id}', 'GET', 'ENABLED', '根据ID获取角色详细信息', NOW(), NOW(), 0),
(1001004, 'ROLE_PAGE', 'v1', '分页查询角色', '/api/v1/roles', 'GET', 'ENABLED', '分页查询角色列表', NOW(), NOW(), 0),
(1001005, 'ROLE_ASSIGN_PERMISSION', 'v1', '分配角色权限', '/api/v1/roles/{id}/permissions', 'POST', 'ENABLED', '为角色分配权限', NOW(), NOW(), 0)

ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 权限管理模块 API (系统1-模块3: 1002000-1002999)
INSERT INTO `api` (`id`, `api_code`, `version`, `api_name`, `resource_path`, `resource_method`, `status`, `description`, `create_time`, `update_time`, `deleted_at`) VALUES
(1002000, 'PERMISSION_CREATE', 'v1', '创建权限', '/api/v1/permissions', 'POST', 'ENABLED', '创建新权限', NOW(), NOW(), 0),
(1002001, 'PERMISSION_UPDATE', 'v1', '更新权限', '/api/v1/permissions/{id}', 'PUT', 'ENABLED', '更新权限信息', NOW(), NOW(), 0),
(1002002, 'PERMISSION_DELETE', 'v1', '删除权限', '/api/v1/permissions/{id}', 'DELETE', 'ENABLED', '删除权限（软删除）', NOW(), NOW(), 0),
(1002003, 'PERMISSION_GET', 'v1', '获取权限详情', '/api/v1/permissions/{id}', 'GET', 'ENABLED', '根据ID获取权限详细信息', NOW(), NOW(), 0),
(1002004, 'PERMISSION_PAGE', 'v1', '分页查询权限', '/api/v1/permissions', 'GET', 'ENABLED', '分页查询权限列表', NOW(), NOW(), 0),
(1002005, 'PERMISSION_ASSIGN_API', 'v1', '分配权限API', '/api/v1/permissions/{id}/apis', 'POST', 'ENABLED', '为权限分配API接口', NOW(), NOW(), 0)

ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- API管理模块 API (系统1-模块4: 1003000-1003999)
INSERT INTO `api` (`id`, `api_code`, `version`, `api_name`, `resource_path`, `resource_method`, `status`, `description`, `create_time`, `update_time`, `deleted_at`) VALUES
(1003000, 'API_CREATE', 'v1', '创建API', '/api/v1/apis', 'POST', 'ENABLED', '创建新API接口', NOW(), NOW(), 0),
(1003001, 'API_UPDATE', 'v1', '更新API', '/api/v1/apis/{id}', 'PUT', 'ENABLED', '更新API接口信息', NOW(), NOW(), 0),
(1003002, 'API_DELETE', 'v1', '删除API', '/api/v1/apis/{id}', 'DELETE', 'ENABLED', '删除API接口（软删除）', NOW(), NOW(), 0),
(1003003, 'API_GET', 'v1', '获取API详情', '/api/v1/apis/{id}', 'GET', 'ENABLED', '根据ID获取API详细信息', NOW(), NOW(), 0),
(1003004, 'API_PAGE', 'v1', '分页查询API', '/api/v1/apis', 'GET', 'ENABLED', '分页查询API列表', NOW(), NOW(), 0)

ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- ============================================
-- 2. 插入权限数据
-- ============================================
-- 权限格式：MODULE:RESOURCE:ACTION（全大写，冒号分割）
-- 权限ID语义化编码规则（7位固定长度）：
--   格式：2 + 系统(1位，0-9) + 模块(2位，00-99) + 权限(3位，000-999)
--   示例：2000000 = 2(权限) + 0(系统1) + 00(模块1) + 000(权限1)
--   系统1 (valhalla-user) 对应编码0
--     模块1 (用户管理) 对应编码00，权限范围：2000000-2000999
--     模块2 (角色管理) 对应编码01，权限范围：2001000-2001999
--     模块3 (权限管理) 对应编码02，权限范围：2002000-2002999
--     模块4 (API管理)  对应编码03，权限范围：2003000-2003999
--     模块5-99: 2000400-2009999 (预留)

-- 用户管理权限 (系统1-模块1: 2000000-2000099)
INSERT INTO `permission` (`id`, `module`, `resource`, `action`, `permission_code`, `permission_name`, `description`, `create_time`, `update_time`, `deleted_at`) VALUES
(2000000, 'USER', 'USER', 'CREATE', 'USER:USER:CREATE', '创建用户', '创建新用户的权限', NOW(), NOW(), 0),
(2000001, 'USER', 'USER', 'UPDATE', 'USER:USER:UPDATE', '更新用户', '更新用户信息的权限', NOW(), NOW(), 0),
(2000002, 'USER', 'USER', 'DELETE', 'USER:USER:DELETE', '删除用户', '删除用户的权限', NOW(), NOW(), 0),
(2000003, 'USER', 'USER', 'GET', 'USER:USER:GET', '查看用户详情', '查看用户详细信息的权限', NOW(), NOW(), 0),
(2000004, 'USER', 'USER', 'PAGE', 'USER:USER:PAGE', '查询用户列表', '分页查询用户列表的权限', NOW(), NOW(), 0),
(2000005, 'USER', 'USER', 'ASSIGN_ROLE', 'USER:USER:ASSIGN_ROLE', '分配用户角色', '为用户分配角色的权限', NOW(), NOW(), 0)

ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 角色管理权限 (系统1-模块2: 2001000-2001999)
INSERT INTO `permission` (`id`, `module`, `resource`, `action`, `permission_code`, `permission_name`, `description`, `create_time`, `update_time`, `deleted_at`) VALUES
(2001000, 'ROLE', 'ROLE', 'CREATE', 'ROLE:ROLE:CREATE', '创建角色', '创建新角色的权限', NOW(), NOW(), 0),
(2001001, 'ROLE', 'ROLE', 'UPDATE', 'ROLE:ROLE:UPDATE', '更新角色', '更新角色信息的权限', NOW(), NOW(), 0),
(2001002, 'ROLE', 'ROLE', 'DELETE', 'ROLE:ROLE:DELETE', '删除角色', '删除角色的权限', NOW(), NOW(), 0),
(2001003, 'ROLE', 'ROLE', 'GET', 'ROLE:ROLE:GET', '查看角色详情', '查看角色详细信息的权限', NOW(), NOW(), 0),
(2001004, 'ROLE', 'ROLE', 'PAGE', 'ROLE:ROLE:PAGE', '查询角色列表', '分页查询角色列表的权限', NOW(), NOW(), 0),
(2001005, 'ROLE', 'ROLE', 'ASSIGN_PERMISSION', 'ROLE:ROLE:ASSIGN_PERMISSION', '分配角色权限', '为角色分配权限的权限', NOW(), NOW(), 0)

ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 权限管理权限 (系统1-模块3: 2002000-2002999)
INSERT INTO `permission` (`id`, `module`, `resource`, `action`, `permission_code`, `permission_name`, `description`, `create_time`, `update_time`, `deleted_at`) VALUES
(2002000, 'PERMISSION', 'PERMISSION', 'CREATE', 'PERMISSION:PERMISSION:CREATE', '创建权限', '创建新权限的权限', NOW(), NOW(), 0),
(2002001, 'PERMISSION', 'PERMISSION', 'UPDATE', 'PERMISSION:PERMISSION:UPDATE', '更新权限', '更新权限信息的权限', NOW(), NOW(), 0),
(2002002, 'PERMISSION', 'PERMISSION', 'DELETE', 'PERMISSION:PERMISSION:DELETE', '删除权限', '删除权限的权限', NOW(), NOW(), 0),
(2002003, 'PERMISSION', 'PERMISSION', 'GET', 'PERMISSION:PERMISSION:GET', '查看权限详情', '查看权限详细信息的权限', NOW(), NOW(), 0),
(2002004, 'PERMISSION', 'PERMISSION', 'PAGE', 'PERMISSION:PERMISSION:PAGE', '查询权限列表', '分页查询权限列表的权限', NOW(), NOW(), 0),
(2002005, 'PERMISSION', 'PERMISSION', 'ASSIGN_API', 'PERMISSION:PERMISSION:ASSIGN_API', '分配权限API', '为权限分配API接口的权限', NOW(), NOW(), 0)

ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- API管理权限 (系统1-模块4: 2003000-2003999)
INSERT INTO `permission` (`id`, `module`, `resource`, `action`, `permission_code`, `permission_name`, `description`, `create_time`, `update_time`, `deleted_at`) VALUES
(2003000, 'API', 'API', 'CREATE', 'API:API:CREATE', '创建API', '创建新API接口的权限', NOW(), NOW(), 0),
(2003001, 'API', 'API', 'UPDATE', 'API:API:UPDATE', '更新API', '更新API接口信息的权限', NOW(), NOW(), 0),
(2003002, 'API', 'API', 'DELETE', 'API:API:DELETE', '删除API', '删除API接口的权限', NOW(), NOW(), 0),
(2003003, 'API', 'API', 'GET', 'API:API:GET', '查看API详情', '查看API详细信息的权限', NOW(), NOW(), 0),
(2003004, 'API', 'API', 'PAGE', 'API:API:PAGE', '查询API列表', '分页查询API列表的权限', NOW(), NOW(), 0)

ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- ============================================
-- 3. 建立权限-API关联关系
-- ============================================
-- permission_api 表：permission_id -> api_id

-- 用户管理权限-API关联
INSERT INTO `permission_api` (`permission_id`, `api_id`, `create_time`) VALUES
(2000000, 1000000, NOW()), -- USER:USER:CREATE -> USER_CREATE
(2000001, 1000001, NOW()), -- USER:USER:UPDATE -> USER_UPDATE
(2000002, 1000002, NOW()), -- USER:USER:DELETE -> USER_DELETE
(2000003, 1000003, NOW()), -- USER:USER:GET -> USER_GET
(2000004, 1000004, NOW()), -- USER:USER:PAGE -> USER_PAGE
(2000005, 1000005, NOW())  -- USER:USER:ASSIGN_ROLE -> USER_ASSIGN_ROLE

ON DUPLICATE KEY UPDATE `create_time` = `create_time`;

-- 角色管理权限-API关联
INSERT INTO `permission_api` (`permission_id`, `api_id`, `create_time`) VALUES
(2001000, 1001000, NOW()), -- ROLE:ROLE:CREATE -> ROLE_CREATE
(2001001, 1001001, NOW()), -- ROLE:ROLE:UPDATE -> ROLE_UPDATE
(2001002, 1001002, NOW()), -- ROLE:ROLE:DELETE -> ROLE_DELETE
(2001003, 1001003, NOW()), -- ROLE:ROLE:GET -> ROLE_GET
(2001004, 1001004, NOW()), -- ROLE:ROLE:PAGE -> ROLE_PAGE
(2001005, 1001005, NOW())  -- ROLE:ROLE:ASSIGN_PERMISSION -> ROLE_ASSIGN_PERMISSION

ON DUPLICATE KEY UPDATE `create_time` = `create_time`;

-- 权限管理权限-API关联
INSERT INTO `permission_api` (`permission_id`, `api_id`, `create_time`) VALUES
(2002000, 1002000, NOW()), -- PERMISSION:PERMISSION:CREATE -> PERMISSION_CREATE
(2002001, 1002001, NOW()), -- PERMISSION:PERMISSION:UPDATE -> PERMISSION_UPDATE
(2002002, 1002002, NOW()), -- PERMISSION:PERMISSION:DELETE -> PERMISSION_DELETE
(2002003, 1002003, NOW()), -- PERMISSION:PERMISSION:GET -> PERMISSION_GET
(2002004, 1002004, NOW()), -- PERMISSION:PERMISSION:PAGE -> PERMISSION_PAGE
(2002005, 1002005, NOW())  -- PERMISSION:PERMISSION:ASSIGN_API -> PERMISSION_ASSIGN_API

ON DUPLICATE KEY UPDATE `create_time` = `create_time`;

-- API管理权限-API关联
INSERT INTO `permission_api` (`permission_id`, `api_id`, `create_time`) VALUES
(2003000, 1003000, NOW()), -- API:API:CREATE -> API_CREATE
(2003001, 1003001, NOW()), -- API:API:UPDATE -> API_UPDATE
(2003002, 1003002, NOW()), -- API:API:DELETE -> API_DELETE
(2003003, 1003003, NOW()), -- API:API:GET -> API_GET
(2003004, 1003004, NOW())  -- API:API:PAGE -> API_PAGE

ON DUPLICATE KEY UPDATE `create_time` = `create_time`;

-- ============================================
-- 4. 建立角色-权限关联关系
-- ============================================
-- role_permission 表：role_id -> permission_id
-- 角色ID: 1=ADMIN, 2=USER, 3=GUEST

-- ADMIN角色：拥有所有权限
INSERT INTO `role_permission` (`role_id`, `permission_id`, `create_time`) VALUES
-- 用户管理权限
(1, 2000000, NOW()), (1, 2000001, NOW()), (1, 2000002, NOW()), (1, 2000003, NOW()), (1, 2000004, NOW()), (1, 2000005, NOW()),
-- 角色管理权限
(1, 2001000, NOW()), (1, 2001001, NOW()), (1, 2001002, NOW()), (1, 2001003, NOW()), (1, 2001004, NOW()), (1, 2001005, NOW()),
-- 权限管理权限
(1, 2002000, NOW()), (1, 2002001, NOW()), (1, 2002002, NOW()), (1, 2002003, NOW()), (1, 2002004, NOW()), (1, 2002005, NOW()),
-- API管理权限
(1, 2003000, NOW()), (1, 2003001, NOW()), (1, 2003002, NOW()), (1, 2003003, NOW()), (1, 2003004, NOW())

ON DUPLICATE KEY UPDATE `create_time` = `create_time`;

-- USER角色：拥有基础权限（查询、部分操作）
INSERT INTO `role_permission` (`role_id`, `permission_id`, `create_time`) VALUES
-- 用户管理：查看自己的信息、更新自己的信息
(2, 2000003, NOW()), -- USER:USER:GET
(2, 2000004, NOW()), -- USER:USER:PAGE
-- 角色管理：只读
(2, 2001003, NOW()), -- ROLE:ROLE:GET
(2, 2001004, NOW()), -- ROLE:ROLE:PAGE
-- 权限管理：只读
(2, 2002003, NOW()), -- PERMISSION:PERMISSION:GET
(2, 2002004, NOW()), -- PERMISSION:PERMISSION:PAGE
-- API管理：只读
(2, 2003003, NOW()), -- API:API:GET
(2, 2003004, NOW())  -- API:API:PAGE

ON DUPLICATE KEY UPDATE `create_time` = `create_time`;

-- GUEST角色：不具备任何权限
-- 注意：GUEST 角色不分配任何权限，仅作为系统标识使用

