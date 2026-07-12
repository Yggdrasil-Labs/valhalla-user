---
updated: 2026-07-12
verified: 2026-07-12
---

# 核心信条

<!--!
  分层定位：
  本文件 = 为什么（不可妥协的原则）
  同目录其他文件 = 怎么做（项目级通用设计决策）
  修改本文件应走独立的架构 RFC。
-->

## 1. 仓库是唯一的真相来源

智能体需要的一切都必须在仓库中。不依赖外部 Wiki、Slack 上下文或口头知识。如果没有版本化在这里，对智能体来说它就不存在。

## 2. 执行优于记录

当一条规则重要时，将其编码为 linter、结构测试或 CI 检查。Spotless 强制格式化、Flatten Plugin 统一版本、CI validate 阶段的格式门禁——这些都是规则代码化的实例。

## 3. 渐进式披露

智能体从 AGENTS.md 开始，按需导航到 ARCHITECTURE.md、DOMAINS.md、SECURITY.md。永远不要一开始就把所有东西塞进上下文。

## 4. 约束边界，内部自由

COLA 分层严格约束依赖方向（start → adapter → app → domain ← infrastructure）。在这些边界内，每一层的实现细节（查询方式、DTO 结构、转换逻辑）可以自由演进。

## 5. 显式优于隐式

不要有魔法。依赖通过 `@Resource` 显式注入，转换通过 MapStruct 编译期生成，配置通过 application.yml 声明。智能体不应该需要猜测隐藏的行为。

## 6. 偏好无聊的技术

MyBatis-Plus 而不是自写 SQL 框架，MapStruct 而不是反射转换，COLA 而不是自创 DDD 骨架。选择生态成熟、API 稳定、训练数据覆盖良好的依赖。

## 7. 低成本纠错优于阻塞门禁

CI 跳过测试（`-DskipTests`）允许快速构建验证；dev profile 自动格式化而不是报错阻塞。偏好快速迭代，用后续 PR 修复，而不是无限期阻塞合并。

## 8. 持续垃圾回收

Dependabot 持续升级依赖，Spotless 持续保持代码风格统一，软删除保留数据可恢复。小的持续修复胜过大的重写。

## 9. 品味即代码

Google AOSP 4 空格缩进、导入顺序规范（java → javax → jakarta → org → com → cn）、删除未使用的导入——这些团队偏好已编码为 Spotless 配置，不再依赖人工审查。

## 10. 深度优先分解

CQRS 命令/查询分离、每个领域独立的 Executor/Query 类、ApplicationService 只做编排不做逻辑——将目标拆解为最小的独立构建模块，逐个解决。
