# db-schema 变更日志

<!-- ⚠️ 本文件由工具自动生成，请勿手动编辑 -->

## 2026-07-12 — 初始生成

基于迁移脚本 V1~V5 生成完整变更记录。

### V1 — 初始化 RBAC 表结构

创建表：
- `user` — 用户表
- `role` — 角色表
- `permission` — 权限表
- `api` — 接口表
- `permission_api` — 权限接口关联表（联合主键）
- `role_permission` — 角色权限关联表（联合主键）
- `user_role` — 用户角色关联表（联合主键）

### V2 — 初始化系统角色数据

插入默认角色：
- ADMIN（管理员，系统角色）
- USER（用户，系统角色）
- GUEST（游客，系统角色）

### V3 — 用户表新增注册来源字段

`user` 表新增字段：
- `source` VARCHAR(32) — 注册来源（ADMIN/REGISTER）
- `register_type` VARCHAR(32) — 注册类型（EMAIL/WECHAT/PHONE）

### V4 — 接口表新增版本和状态字段

`api` 表变更：
- 新增 `version` VARCHAR(32) — 接口版本
- 新增 `status` VARCHAR(32) — 接口状态（ENABLED/DEPRECATED/DISABLED）
- 删除索引 `uk_path_method`、`idx_api_code_deleted`
- 新增索引 `uk_api_code_version`（api_code, version, deleted_at）
- 新增索引 `uk_path_method_deleted`（resource_path, resource_method, deleted_at）
- 新增索引 `idx_api_code`（api_code）

关联表结构调整（rbac_schema.sql 同步更新）：
- `permission_api`、`role_permission`、`user_role` 改为自增主键 + UNIQUE 约束（替代联合主键）

### V5 — 初始化 API、权限和角色关联数据

插入 API 接口数据（23 条）：
- 用户管理模块：USER_CREATE / USER_UPDATE / USER_DELETE / USER_GET / USER_PAGE / USER_ASSIGN_ROLE
- 角色管理模块：ROLE_CREATE / ROLE_UPDATE / ROLE_DELETE / ROLE_GET / ROLE_PAGE / ROLE_ASSIGN_PERMISSION
- 权限管理模块：PERMISSION_CREATE / PERMISSION_UPDATE / PERMISSION_DELETE / PERMISSION_GET / PERMISSION_PAGE / PERMISSION_ASSIGN_API
- API管理模块：API_CREATE / API_UPDATE / API_DELETE / API_GET / API_PAGE

插入权限数据（23 条）：
- 用户管理：USER:USER:{CREATE/UPDATE/DELETE/GET/PAGE/ASSIGN_ROLE}
- 角色管理：ROLE:ROLE:{CREATE/UPDATE/DELETE/GET/PAGE/ASSIGN_PERMISSION}
- 权限管理：PERMISSION:PERMISSION:{CREATE/UPDATE/DELETE/GET/PAGE/ASSIGN_API}
- API管理：API:API:{CREATE/UPDATE/DELETE/GET/PAGE}

建立关联关系：
- 权限-API 一对一关联（23 条）
- ADMIN 角色：拥有全部 23 项权限
- USER 角色：拥有 8 项只读权限（GET/PAGE）
- GUEST 角色：不分配权限
