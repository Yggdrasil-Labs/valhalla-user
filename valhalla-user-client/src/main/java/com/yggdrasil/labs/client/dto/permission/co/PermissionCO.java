package com.yggdrasil.labs.client.dto.permission.co;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.cola.dto.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限客户对象
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionCO extends DTO {

    /** 权限ID */
    private String id;

    /** 模块 */
    private String module;

    /** 资源 */
    private String resource;

    /** 操作 */
    private String action;

    /** 权限代码（格式：模块:资源:操作） */
    private String permissionCode;

    /** 权限名称 */
    private String permissionName;

    /** 权限描述 */
    private String description;

    /** 扩展信息（JSON） */
    private String metadata;

    /** API ID列表 */
    private List<String> apiIds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
