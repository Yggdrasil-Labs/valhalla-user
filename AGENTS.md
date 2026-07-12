# AGENTS.md

本文件是智能体的唯一入口，保持为"地图而不是手册"。

## 项目概述

valhalla-user 是 Valhalla 平台的用户管理微服务，负责用户 CRUD、RBAC 权限体系（角色/权限/接口三级模型）。服务面向平台管理员和其他内部微服务（通过 Dubbo RPC）。技术选型：Java 17 + Spring Boot 3.3（mimir-boot 2.1.0）+ COLA 5.0 DDD 分层架构 + Nacos 配置中心 + MySQL 8.4 + MyBatis-Plus + MapStruct。

## 全局规范

1. 智能体优先遵循项目规范（`AGENTS.md`、`ARCHITECTURE.md`、`docs/design-docs/`）。项目约束 > 智能体全局约束。
2. Git Conventional Commits，message 中文。格式：`<type>(<scope>): <中文描述>`。
3. 文档与代码冲突时以代码为准并回写文档。

## 导航

### A. 长期约束（只读，修改需架构 RFC）

- 系统边界与依赖方向：[`ARCHITECTURE.md`](./ARCHITECTURE.md)
- 工程信条：[`docs/design-docs/core-beliefs.md`](./docs/design-docs/core-beliefs.md)
- 业务领域划分：[`docs/DOMAINS.md`](./docs/DOMAINS.md)
- 安全策略：[`docs/SECURITY.md`](./docs/SECURITY.md)
- 可靠性标准：[`docs/RELIABILITY.md`](./docs/RELIABILITY.md)

### B. 流转文档

- 活跃版本：[`docs/active/index.md`](./docs/active/index.md)
- 版本归档：[`docs/archive/index.md`](./docs/archive/index.md)
- 技术债：[`docs/active/tech-debt-tracker.md`](./docs/active/tech-debt-tracker.md)
- 设计决策：[`docs/design-docs/index.md`](./docs/design-docs/index.md)

### C. 参考与产物

- 产品思维：[`docs/PRODUCT_SENSE.md`](./docs/PRODUCT_SENSE.md)

## 决策地图

| 改什么 | 去哪里 |
|--------|--------|
| 新增 REST API 端点 | `valhalla-user-adapter/src/main/java/{…}/adapter/web/controller/` |
| 新增业务命令/查询 | `valhalla-user-app/src/main/java/{…}/app/{domain}/executor/` 或 `{…}/query/` |
| 修改领域模型 | `valhalla-user-domain/src/main/java/{…}/domain/{domain}/model/` |
| 调整持久化逻辑 | `valhalla-user-infrastructure/src/main/java/{…}/infrastructure/persistence/` |
| 变更对外契约（DTO/Client 接口） | `valhalla-user-client/src/main/java/{…}/client/` |
| 新增数据库表/字段 | `db/migration/V{n}__描述.sql` |
| 修改服务配置 | `valhalla-user-start/src/main/resources/application*.yml`（本地），Nacos 配置中心（远程） |
| 新增 mimir-boot Starter 依赖 | `valhalla-user-start/pom.xml` |

## 开发命令

```bash
# 本地构建（dev profile，自动格式化）
./mvnw clean package -Pdev

# 格式化代码
./mvnw spotless:apply

# CI 构建（格式检查 + 编译，跳过测试）
./mvnw -B clean package -Pci -DskipTests

# 运行测试
./mvnw test -Pprecheck

# 格式检查（不修改文件）
./mvnw spotless:check
```
