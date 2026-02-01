package com.yggdrasil.labs.adapter.web.request;

import lombok.Data;

/**
 * 分页查询用户请求
 *
 * @author YoungerYang-Y
 */
@Data
public class PageUserRequest {

    /** 用户名（模糊匹配） */
    private String username;

    /** 状态：0-禁用，1-启用 */
    private Integer status;

    /** 页码（从1开始） */
    private Integer pageNum = 1;

    /** 每页数量 */
    private Integer pageSize = 10;
}
