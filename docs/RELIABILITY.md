---
updated: 2026-07-12
---

# 可靠性标准

<!--!
  本文件让智能体理解系统的可靠性要求和可观测性基础设施。
  智能体在编写涉及性能、日志、监控、错误处理的代码时必须参考此文件。
-->

## SLO（服务级别目标）

| 服务 | 可用性 | 延迟 (p99) | 错误率 | 智能体检查方式 |
|------|--------|-----------|--------|---------------|
| REST API `/api/v1/**` | 99.9% | < 500ms | < 0.1% | Actuator health + 日志错误率 |
| 分页查询 | - | < 1000ms | < 0.5% | SQL 慢查询日志 |

## 可观测性要求

- 日志格式：mimir-boot-starter-log 提供结构化 JSON 日志输出
- 健康检查：Spring Boot Actuator 暴露 `/actuator/health` 和 `/actuator/info`
- Docker 健康检查：每 30s 探测一次，启动等待 60s，连续 3 次失败标记不健康
- SQL 日志：dev 环境开启 Mapper DEBUG 级别日志（`com.yggdrasil.labs.**.mapper: DEBUG`）
- 生产日志级别：`root: WARN`，`com.yggdrasil.labs.*: WARN`

## 智能体如何验证可靠性

- 本地启动服务后通过 `curl http://localhost:8081/actuator/health` 验证服务健康
- 检查 SQL 日志确认查询是否命中索引
- 分页查询应通过 `PageResult.total` 验证分页元数据正确性
- 乐观锁冲突场景需通过并发测试验证 version 字段递增

## 性能红线

| 操作 | 红线 | 强制执行方式 |
|------|------|-------------|
| 单条 CRUD 操作 | < 200ms p99 | 索引覆盖 + MyBatis-Plus 简单查询 |
| 分页查询（1000 条/页） | < 1000ms p99 | 确保 WHERE 条件命中索引 |
| 用户角色分配 | < 500ms | 事务内批量插入，避免 N+1 |
| 服务启动时间 | < 30s | Actuator 健康检查 start-period=60s |

## 容错设计

- **乐观锁**：User 实体通过 `version` 字段防止并发更新丢失
- **软删除**：所有删除操作为逻辑删除，数据可恢复
- **幂等设计**：角色分配（assignUserRole）采用先删后增模式，重复调用结果一致
- **唯一约束**：username/email/phone 通过数据库唯一索引兜底，防止并发创建重复数据

## 事件响应

- Runbook 位置：暂未建立，计划存放于 `docs/runbooks/`
- 值班升级路径：开发团队 → 平台运维
