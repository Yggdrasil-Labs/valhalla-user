package com.yggdrasil.labs.client.dto.user.cmd;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新用户命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateUserCmd extends Command {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long id;

    /** 邮箱 */
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "邮箱格式不正确")
    private String email;

    /** 电话 */
    private String phone;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 状态：0-禁用，1-启用 */
    private Integer status;

    /** 扩展信息（JSON） */
    private String metadata;

    /** 乐观锁版本号 */
    private Integer version;
}
