package com.yggdrasil.labs.domain.user.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.cola.domain.Entity;

import lombok.Data;

/**
 * 用户实体（聚合根）
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class User {

    /** 用户ID（雪花ID） */
    private Long id;

    /** 用户名 */
    private String username;

    /** 邮箱 */
    private String email;

    /** 电话 */
    private String phone;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 状态 */
    private UserStatus status;

    /** 扩展信息（JSON） */
    private String metadata;

    /** 角色ID列表 */
    private List<Long> roleIds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    private Integer version;

    /** 启用用户 */
    public void enable() {
        this.status = UserStatus.ENABLED;
    }

    /** 禁用用户 */
    public void disable() {
        this.status = UserStatus.DISABLED;
    }

    /**
     * 分配角色
     *
     * @param roleIds 角色ID列表
     */
    public void assignRoles(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            this.roleIds = new ArrayList<>();
            return;
        }
        this.roleIds = new ArrayList<>(roleIds);
    }

    /**
     * 是否启用
     *
     * @return true-启用，false-禁用
     */
    public boolean isEnabled() {
        return UserStatus.ENABLED.equals(this.status);
    }
}
