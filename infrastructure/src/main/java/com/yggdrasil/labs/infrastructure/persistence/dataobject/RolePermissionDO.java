package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * 角色权限关联表 DO
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("role_permission")
@AutoMybatis
public class RolePermissionDO {

    /** 角色ID */
    private Long roleId;

    /** 权限ID */
    private Long permissionId;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
