package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * 用户注册类型枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum UserRegisterTypeEnum {

    /** 邮箱注册 */
    EMAIL("邮箱注册"),

    /** 微信注册 */
    WECHAT("微信注册"),

    /** 手机号注册 */
    PHONE("手机号注册");

    /** 描述 */
    private final String desc;

    UserRegisterTypeEnum(String desc) {
        this.desc = desc;
    }
}
