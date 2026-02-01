package com.yggdrasil.labs.domain.permission.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.cola.domain.Entity;

import lombok.Data;

/**
 * 权限实体（聚合根）
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class Permission {

    /** 权限ID（雪花ID） */
    private Long id;

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
    private List<Long> apiIds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 生成权限代码 格式：模块:资源:操作 */
    public void generatePermissionCode() {
        if (this.module != null && this.resource != null && this.action != null) {
            this.permissionCode =
                    String.format("%s:%s:%s", this.module, this.resource, this.action);
        }
    }

    /**
     * 分配API
     *
     * @param apiIds API ID列表
     */
    public void assignApis(List<Long> apiIds) {
        if (apiIds == null || apiIds.isEmpty()) {
            this.apiIds = new ArrayList<>();
            return;
        }
        this.apiIds = new ArrayList<>(apiIds);
    }
}
