package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * 用户注册来源枚举
 *
 * @author YoungerYang-Y
 */
@Getter
public enum UserSourceEnum {

    /** 管理员创建 */
    ADMIN("管理员创建"),

    /** 用户自助注册 */
    REGISTER("自助注册");

    /** 描述 */
    private final String desc;

    UserSourceEnum(String desc) {
        this.desc = desc;
    }
}
