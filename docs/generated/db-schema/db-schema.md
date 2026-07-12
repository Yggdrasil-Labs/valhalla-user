# 数据库表结构

<!-- ⚠️ 本文件由工具自动生成，请勿手动编辑 -->

Last generated: 2026-07-12

数据库：MySQL 8.4 | 字符集：utf8mb4 | 排序规则：utf8mb4_unicode_ci

---

## user — 用户表

> 密码由认证服务管理，不在此表存储

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | NOT NULL, PK | 用户ID（雪花ID） |
| username | VARCHAR(64) | NOT NULL, UNIQUE | 用户名 |
| email | VARCHAR(100) | NULL, UNIQUE | 邮箱 |
| phone | VARCHAR(20) | NULL, UNIQUE | 电话 |
| nickname | VARCHAR(50) | NOT NULL, DEFAULT '' | 昵称 |
| avatar | VARCHAR(255) | NOT NULL, DEFAULT '' | 头像URL |
| status | TINYINT | NOT NULL, DEFAULT 1 | 状态：0-禁用，1-启用 |
| source | VARCHAR(32) | NOT NULL, DEFAULT 'ADMIN' | 注册来源：ADMIN-管理员创建，REGISTER-自助注册 |
| register_type | VARCHAR(32) | NULL | 注册类型：EMAIL/WECHAT/PHONE，自助注册场景使用 |
| metadata | JSON | NULL | 扩展信息（JSON） |
| create_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| deleted_at | BIGINT | NOT NULL, DEFAULT 0 | 软删除时间戳（0-未删除） |
| version | INT | NOT NULL, DEFAULT 0 | 乐观锁版本号 |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | id |
| uk_username | UNIQUE | username |
| uk_email | UNIQUE | email |
| uk_phone | UNIQUE | phone |
| idx_status_deleted | INDEX | status, deleted_at |

---

## role — 角色表

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | NOT NULL, PK | 角色ID（雪花ID） |
| role_code | VARCHAR(50) | NOT NULL, UNIQUE | 角色代码：ADMIN-管理员，USER-用户，GUEST-游客 |
| role_name | VARCHAR(50) | NOT NULL | 角色名称 |
| description | VARCHAR(255) | NOT NULL, DEFAULT '' | 角色描述 |
| is_system | TINYINT(1) | NOT NULL, DEFAULT 0 | 是否系统角色（不可删除） |
| metadata | JSON | NULL | 扩展信息（JSON） |
| create_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| deleted_at | BIGINT | NOT NULL, DEFAULT 0 | 软删除时间戳（0-未删除） |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | id |
| uk_role_code | UNIQUE | role_code |
| idx_deleted_at | INDEX | deleted_at |

---

## permission — 权限表

> 动作级权限，模块、资源、操作分开存储，扁平结构，不支持层级和权限组

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | NOT NULL, PK | 权限ID（雪花ID） |
| module | VARCHAR(50) | NOT NULL | 模块（如user） |
| resource | VARCHAR(50) | NOT NULL | 资源（如profile） |
| action | VARCHAR(50) | NOT NULL | 操作（如update） |
| permission_code | VARCHAR(128) | NOT NULL, UNIQUE | 权限代码（格式：模块:资源:操作，如user:profile:update） |
| permission_name | VARCHAR(100) | NOT NULL | 权限名称 |
| description | VARCHAR(255) | NOT NULL, DEFAULT '' | 权限描述 |
| metadata | JSON | NULL | 扩展信息（JSON） |
| create_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| deleted_at | BIGINT | NOT NULL, DEFAULT 0 | 软删除时间戳（0-未删除） |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | id |
| uk_permission_code | UNIQUE | permission_code |
| uk_module_resource_action | UNIQUE | module, resource, action |
| idx_module_deleted | INDEX | module, deleted_at |

---

## api — 接口表（API实现）

> 系统接口定义，模块、资源、操作通过权限接口关联表获取

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | NOT NULL, PK | 接口ID（雪花ID） |
| api_code | VARCHAR(128) | NOT NULL | 接口能力代码（稳定，不含版本） |
| version | VARCHAR(32) | NOT NULL | 接口版本（v1 / v2 / v3） |
| api_name | VARCHAR(100) | NOT NULL | 接口名称 |
| resource_path | VARCHAR(255) | NOT NULL | 资源路径（API路径，用于接口匹配） |
| resource_method | VARCHAR(10) | NOT NULL | HTTP方法（GET/POST/PUT/DELETE等） |
| status | VARCHAR(32) | NOT NULL, DEFAULT 'ENABLED' | 接口状态（ENABLED / DEPRECATED / DISABLED） |
| description | VARCHAR(255) | NOT NULL, DEFAULT '' | 接口描述 |
| metadata | JSON | NULL | 扩展信息（JSON） |
| create_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| deleted_at | BIGINT | NOT NULL, DEFAULT 0 | 软删除时间戳（0-未删除） |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | id |
| uk_api_code_version | UNIQUE | api_code, version, deleted_at |
| uk_path_method_deleted | UNIQUE | resource_path, resource_method, deleted_at |
| idx_api_code | INDEX | api_code |

---

## permission_api — 权限接口关联表

> 权限与接口的一对多关系

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | NOT NULL, PK, AUTO_INCREMENT | 主键ID |
| permission_id | BIGINT | NOT NULL | 权限ID |
| api_id | BIGINT | NOT NULL | 接口ID |
| create_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间 |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | id |
| uk_permission_api | UNIQUE | permission_id, api_id |
| idx_api_id | INDEX | api_id |

---

## role_permission — 角色权限关联表

> 角色与权限的多对多关系

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | NOT NULL, PK, AUTO_INCREMENT | 主键ID |
| role_id | BIGINT | NOT NULL | 角色ID |
| permission_id | BIGINT | NOT NULL | 权限ID |
| create_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间 |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | id |
| uk_role_permission | UNIQUE | role_id, permission_id |
| idx_permission_id | INDEX | permission_id |

---

## user_role — 用户角色关联表

> 用户与角色的多对多关系

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | NOT NULL, PK, AUTO_INCREMENT | 主键ID |
| user_id | BIGINT | NOT NULL | 用户ID |
| role_id | BIGINT | NOT NULL | 角色ID |
| create_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | NOT NULL, DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

**索引：**

| 索引名 | 类型 | 字段 |
|--------|------|------|
| PRIMARY | PRIMARY KEY | id |
| uk_user_role | UNIQUE | user_id, role_id |
| idx_role_id | INDEX | role_id |
