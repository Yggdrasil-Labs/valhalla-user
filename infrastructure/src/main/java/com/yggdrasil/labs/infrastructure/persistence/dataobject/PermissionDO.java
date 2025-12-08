package com.yggdrasil.labs.infrastructure.persistence.dataobject;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yggdrasil.labs.mybatis.annotation.AutoMybatis;

import lombok.Data;

/**
 * 权限表 DO
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("permission")
@AutoMybatis
public class PermissionDO {

    /** 权限ID（雪花ID） */
    @TableId(type = IdType.INPUT)
    private Long id;

    /** 模块（如user） */
    private String module;

    /** 资源（如profile） */
    private String resource;

    /** 操作（如update） */
    private String action;

    /** 权限代码（格式：模块:资源:操作，如user:profile:update） */
    private String permissionCode;

    /** 权限名称 */
    private String permissionName;

    /** 权限描述 */
    private String description;

    /** 扩展信息（JSON） */
    private String metadata;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 软删除标记：0-未删除, >0-删除时间戳 */
    @TableLogic(value = "0", delval = "UNIX_TIMESTAMP(NOW(3)) * 1000")
    private Long deletedAt;
}
