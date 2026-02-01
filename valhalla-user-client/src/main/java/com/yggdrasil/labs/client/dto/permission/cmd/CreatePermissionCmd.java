package com.yggdrasil.labs.client.dto.permission.cmd;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建权限命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreatePermissionCmd extends Command {

    /** 模块 */
    @NotBlank(message = "模块不能为空")
    private String module;

    /** 资源 */
    @NotBlank(message = "资源不能为空")
    private String resource;

    /** 操作 */
    @NotBlank(message = "操作不能为空")
    private String action;

    /** 权限名称 */
    @NotBlank(message = "权限名称不能为空")
    private String permissionName;

    /** 权限描述 */
    private String description;

    /** 扩展信息（JSON） */
    private String metadata;

    /** API ID列表 */
    private List<Long> apiIds;
}
