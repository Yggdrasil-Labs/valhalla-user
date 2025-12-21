package com.yggdrasil.labs.client.dto.role.co;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.cola.dto.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色客户对象
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleCO extends DTO {

    /** 角色ID */
    private String id;

    /** 角色代码 */
    private String roleCode;

    /** 角色名称 */
    private String roleName;

    /** 角色描述 */
    private String description;

    /** 是否系统角色（不可删除） */
    private Boolean isSystem;

    /** 扩展信息（JSON） */
    private String metadata;

    /** 权限ID列表 */
    private List<String> permissionIds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
