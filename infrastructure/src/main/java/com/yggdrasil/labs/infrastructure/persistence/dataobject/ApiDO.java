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
 * 接口表 DO
 *
 * @author YoungerYang-Y
 */
@Data
@TableName("api")
@AutoMybatis
public class ApiDO {

    /** 接口ID（雪花ID） */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 接口代码 */
    private String apiCode;

    /** 接口名称 */
    private String apiName;

    /** 资源路径（API路径，用于接口匹配） */
    private String resourcePath;

    /** HTTP方法（GET、POST、PUT、DELETE等，用于接口匹配） */
    private String resourceMethod;

    /** 接口描述 */
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
