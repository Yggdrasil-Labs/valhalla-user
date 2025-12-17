package com.yggdrasil.labs.domain.api.model;

import java.time.LocalDateTime;

import com.alibaba.cola.domain.Entity;

import lombok.Data;

/**
 * API实体（聚合根）
 *
 * @author YoungerYang-Y
 */
@Data
@Entity
public class Api {

    /** 接口ID（雪花ID） */
    private Long id;

    /** 接口代码 */
    private String apiCode;

    /** 接口名称 */
    private String apiName;

    /** 资源路径（API路径） */
    private String resourcePath;

    /** HTTP方法（GET、POST、PUT、DELETE等） */
    private String resourceMethod;

    /** 接口描述 */
    private String description;

    /** 扩展信息（JSON） */
    private String metadata;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
