package com.yggdrasil.labs.client.dto.user.co;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.cola.dto.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户客户对象
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCO extends DTO {

    /** 用户ID */
    private String id;

    /** 用户名 */
    private String username;

    /** 邮箱 */
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

    /** 角色ID列表 */
    private List<String> roleIds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
