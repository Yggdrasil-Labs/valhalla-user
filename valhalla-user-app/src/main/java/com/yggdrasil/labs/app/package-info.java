/**
 * 应用层（Application Layer）
 *
 * <p>职责：编排领域服务，暴露 ApplicationService 接口，供适配层直接调用。
 *
 * <p>结构：按聚合划分目录，包含 ApplicationService、Executor（Cmd/Query）、Assembler、DTO 等。
 *
 * <p>依赖：依赖 Domain；不依赖 Client（Client 仅作为外部契约层）。
 */
package com.yggdrasil.labs.app;
