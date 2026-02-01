package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * API接口状态枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum ApiStatusEnum {

    /** 启用 */
    ENABLED("启用"),

    /** 废弃 */
    DEPRECATED("废弃"),

    /** 禁用 */
    DISABLED("禁用");

    /** 描述 */
    private final String desc;

    ApiStatusEnum(String desc) {
        this.desc = desc;
    }
}
