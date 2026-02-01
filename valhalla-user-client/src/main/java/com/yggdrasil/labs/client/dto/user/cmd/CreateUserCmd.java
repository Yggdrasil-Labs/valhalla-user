package com.yggdrasil.labs.client.dto.user.cmd;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建用户命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateUserCmd extends Command {

    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 邮箱 */
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "邮箱格式不正确")
    private String email;

    /** 电话 */
    private String phone;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 状态：0-禁用，1-启用（默认启用） */
    private Integer status;

    /** 扩展信息（JSON） */
    private String metadata;

    /** 角色ID列表 */
    private List<Long> roleIds;
}
