# valhalla-user

> 英灵殿 - 用户管理服务（核心服务）

## 项目简介

Valhalla（英灵殿）用户管理服务，是 Yggdrasil Labs 生态系统中的核心服务，负责：

- **用户信息的完整生命周期管理**：用户创建、更新、查询、删除等基础操作
- **用户操作行为的审计和追踪**：记录用户所有操作行为，支持审计和问题排查
- **系统级的用户访问控制**：强制下线、用户禁用/启用等安全控制功能
- **基于角色的权限管理（RBAC）**：角色与权限的配置和管理
- **菜单和权限的配置管理**：系统菜单结构与权限的关联配置

## 技术栈

- **基础框架**: mimir-boot (Spring Boot 3.3.13 + Java 17)
- **架构模式**: Cola 5.0 DDD 分层架构
- **服务注册与发现**: Nacos
- **RPC 框架**: Apache Dubbo
- **数据库**: MySQL 8.4
- **缓存**: Redis（用户信息缓存、在线状态管理）
- **ORM**: MyBatis-Plus（使用 @AutoMybatis 自动生成 Mapper）
- **对象转换**: MapStruct

## 架构设计

采用 COLA 5.0 DDD 分层架构：

```
┌─────────────────────────────────────────┐
│  Start（启动层）                         │  ← 启动 + 配置
├─────────────────────────────────────────┤
│  Adapter（适配层）                       │  ← 协议适配（HTTP、RPC、MQ）
├─────────────────────────────────────────┤
│  Client（客户端层）                      │  ← 对外契约
├─────────────────────────────────────────┤
│  App（应用服务层）                       │  ← 业务编排
├─────────────────────────────────────────┤
│  Domain（领域层）                        │  ← 业务规则
├─────────────────────────────────────────┤
│  Infrastructure（基础设施层）            │  ← 技术实现
└─────────────────────────────────────────┘
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.4+
- Redis 6.0+
- Nacos 2.x

### 启动步骤

```bash
# 1. 配置数据库和 Redis
# 修改 start/src/main/resources/application-dev.yml

# 2. 启动服务
./mvnw spring-boot:run

# 或使用 Maven
mvn spring-boot:run
```

### 开发文档

- 各包的 `package-info.java` 包含详细架构说明和代码示例

## 相关资源

- [COLA 架构](https://github.com/alibaba/COLA)
- [MyBatis-Plus 文档](https://baomidou.com/)
- [MapStruct 文档](https://mapstruct.org/)

## 贡献指南

遵循 [Conventional Commits](https://www.conventionalcommits.org/) 规范提交代码。
