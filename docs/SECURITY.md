---
updated: 2026-07-12
---

# 安全策略

<!--!
  本文件让智能体理解系统的安全要求和约束。
  智能体在编写涉及认证、授权、用户输入、数据存储的代码时必须参考此文件。
  违反这些策略的代码不允许合并。
-->

## 认证

- **本服务不管理密码**：密码/凭证/MFA 由独立的 valhalla-auth 服务负责
- **无 Token 验证职责**：JWT 验证由网关或 valhalla-auth 完成，本服务信任上游传递的用户身份
- **服务间通信**：未来通过 Dubbo RPC 被其他微服务调用，依赖 Nacos 服务注册发现进行服务鉴权

## 授权

- **RBAC 三级模型**：用户 → 角色 → 权限 → 接口（user_role / role_permission / permission_api）
- **权限检查层级**：权限数据由本服务管理，实际的请求拦截由网关或认证服务执行
- **系统角色保护**：`role.is_system = 1` 的角色不可删除（如 ADMIN、USER、GUEST）
- **接口版本管理**：API 资源支持 ENABLED / DEPRECATED / DISABLED 三种状态，DISABLED 接口不参与权限匹配

## 智能体必须遵守的安全规则

1. **数据保护**：用户表 email/phone 字段为唯一索引，修改时需校验唯一性；日志中不打印完整手机号和邮箱
2. **输入验证**：所有 Controller 入参使用 `@Valid` + Jakarta Validation 注解校验；adapter 层拦截非法输入，不允许绕过到 domain 层
3. **软删除**：所有业务表使用 `deleted_at` 字段实现软删除（0=未删除，时间戳=已删除），唯一索引需包含 `deleted_at`
4. **乐观锁**：user 表使用 `version` 字段防止并发更新冲突
5. **ID 生成**：所有主键使用雪花算法（BIGINT），不暴露自增序列
6. **密钥管理**：数据库连接信息、Nacos 配置均通过环境变量或配置中心传递，不写入代码仓库
7. **依赖安全**：通过 Dependabot 自动检测依赖漏洞，CI 检查通过后才可合并

## 安全审查清单

- [ ] 所有用户输入已通过 Jakarta Validation 注解校验
- [ ] 无硬编码密钥或数据库连接字符串
- [ ] 错误响应使用 COLA ErrorCode 统一格式，不泄露堆栈信息
- [ ] 新增的 API 端点已在 api 表注册，并关联到对应的 permission
- [ ] 涉及用户 PII 的日志已脱敏处理
- [ ] 新增依赖已通过 Dependabot / CVE 扫描
