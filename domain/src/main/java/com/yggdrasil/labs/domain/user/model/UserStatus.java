package com.yggdrasil.labs.domain.user.model;

/**
 * 用户状态枚举
 *
 * @author YoungerYang-Y
 */
public enum UserStatus {

    /** 禁用 */
    DISABLED(0),

    /** 启用 */
    ENABLED(1);

    private final Integer code;

    UserStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * 根据代码获取状态
     *
     * @param code 状态代码
     * @return 用户状态
     */
    public static UserStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown user status code: " + code);
    }
}
