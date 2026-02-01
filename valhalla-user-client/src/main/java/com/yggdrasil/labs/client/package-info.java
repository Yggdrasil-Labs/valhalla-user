/**
 * 客户端层（Client Layer）
 *
 * <p>本包是 COLA 5.0 架构的 Client 层，定义对外契约层，不包含任何实现。
 *
 * <p><b>核心职责：</b>
 *
 * <ul>
 *   <li>定义对外提供的业务接口（Client API），供 Dubbo/Feign 等外部协议实现
 *   <li>可选：定义外部协议相关的 DTO，不包含内部应用 DTO
 *   <li>作为系统的对外契约，保持独立，内部 App/Adapter 不再依赖 Client DTO
 * </ul>
 *
 * <p><b>包结构：</b>
 *
 * <ul>
 *   <li><b>api</b>：业务接口定义（Client API），按聚合根组织，供 RPC/Feign Provider/Client 实现
 *   <li><b>dto</b>：外部协议 DTO（若有），不含内部应用 DTO；内部 Command/Query/CO/枚举已迁移至 App 层
 * </ul>
 *
 * <p><b>设计原则：</b>
 *
 * <ul>
 *   <li><b>契约稳定</b>：Client 层是对外契约，变更需谨慎，影响所有调用方
 *   <li><b>只定义不实现</b>：只包含接口和 DTO 定义，不包含任何实现代码
 *   <li><b>参数校验</b>：在 Command 和 Query 上使用 JSR 303 注解进行参数校验
 *   <li><b>业务语义</b>：接口和 DTO 命名应清晰表达业务意图
 *   <li><b>向后兼容</b>：新增字段时考虑向后兼容性
 * </ul>
 *
 * <p><b>命名规范：</b>
 *
 * <ul>
 *   <li>Client 接口：{Domain}Client（如 CustomerClient）
 *   <li>Command：{Verb}{Domain}Cmd（如 CreateCustomerCmd）
 *   <li>Query：{Verb}{Domain}Query（如 ListCustomerQuery）
 *   <li>Client Object：{Domain}CO（如 CustomerCO）
 * </ul>
 *
 * <p><b>依赖关系：</b>
 *
 * <ul>
 *   <li><b>被依赖</b>：外部协议适配器（Dubbo/Feign Provider/Client）依赖 Client 契约
 *   <li><b>依赖</b>：仅依赖 COLA 基础类和必要校验注解
 *   <li><b>不依赖</b>：不依赖 App、Domain、Infrastructure；Adapter 默认依赖 App，不再依赖 Client DTO
 * </ul>
 *
 * <p><b>注意事项：</b>
 *
 * <ul>
 *   <li>Client 层是对外契约，修改需要与调用方协商
 *   <li>DTO 对象应保持简单，使用基本类型或包装类型
 *   <li>避免在 DTO 中包含复杂的业务对象或技术细节
 *   <li>使用 Lombok 简化代码（@Data、@EqualsAndHashCode）
 * </ul>
 *
 * @author YoungerYang-Y
 */
package com.yggdrasil.labs.client;
