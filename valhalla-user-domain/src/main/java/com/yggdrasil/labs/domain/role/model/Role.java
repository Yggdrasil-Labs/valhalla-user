package com.yggdrasil.labs.domain.role.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.cola.domain.Entity;

import lombok.Data;

/**
 * 角色实体（聚合根）
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class Role {

    /** 角色ID（雪花ID） */
    private Long id;

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
    private List<Long> permissionIds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /**
     * 是否可以删除
     *
     * @return true-可以删除，false-不可删除（系统角色）
     */
    public boolean canBeDeleted() {
        return !Boolean.TRUE.equals(this.isSystem);
    }

    /**
     * 分配权限
     *
     * @param permissionIds 权限ID列表
     */
    public void assignPermissions(List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            this.permissionIds = new ArrayList<>();
            return;
        }
        this.permissionIds = new ArrayList<>(permissionIds);
    }
}
