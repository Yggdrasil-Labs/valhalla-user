package com.yggdrasil.labs.client.dto.enums;

import lombok.Getter;

/**
 * 错误码枚举
 *
 * <p>统一管理所有业务错误码和错误消息
 *
 * @author YoungerYang-Y
 */
@Getter
public enum ErrorCode {

    // ========== 用户相关错误码 ==========
    /** 用户不存在 */
    B_USER_NOT_FOUND("USER_NOT_FOUND", "用户不存在"),
    /** 用户名已存在 */
    B_USER_USERNAME_EXISTS("USERNAME_EXISTS", "用户名已存在"),
    /** 邮箱已存在 */
    B_USER_EMAIL_EXISTS("EMAIL_EXISTS", "邮箱已存在"),
    /** 手机号已存在 */
    B_USER_PHONE_EXISTS("PHONE_EXISTS", "手机号已存在"),
    /** 邮箱已被其他用户使用 */
    B_USER_EMAIL_USED_BY_OTHER("EMAIL_EXISTS", "邮箱已被其他用户使用"),
    /** 手机号已被其他用户使用 */
    B_USER_PHONE_USED_BY_OTHER("PHONE_EXISTS", "手机号已被其他用户使用"),

    // ========== 角色相关错误码 ==========
    /** 角色不存在 */
    B_ROLE_NOT_FOUND("ROLE_NOT_FOUND", "角色不存在"),
    /** 角色代码已存在 */
    B_ROLE_CODE_EXISTS("ROLE_CODE_EXISTS", "角色代码已存在"),
    /** 系统角色不能删除 */
    B_ROLE_IS_SYSTEM("ROLE_IS_SYSTEM", "系统角色不能删除"),

    // ========== 权限相关错误码 ==========
    /** 权限不存在 */
    B_PERMISSION_NOT_FOUND("PERMISSION_NOT_FOUND", "权限不存在"),
    /** 权限已存在 */
    B_PERMISSION_EXISTS("PERMISSION_EXISTS", "权限已存在"),

    // ========== API相关错误码 ==========
    /** 接口不存在 */
    B_API_NOT_FOUND("API_NOT_FOUND", "接口不存在"),
    /** 接口代码已存在 */
    B_API_CODE_EXISTS("API_CODE_EXISTS", "接口代码已存在"),
    /** 接口已存在 */
    B_API_EXISTS("API_EXISTS", "接口已存在");

    /** 错误码 */
    private final String errCode;

    /** 错误描述 */
    private final String errDesc;

    ErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }
}
