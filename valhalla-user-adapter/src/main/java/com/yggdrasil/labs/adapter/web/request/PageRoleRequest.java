package com.yggdrasil.labs.adapter.web.request;

import lombok.Data;

/**
 * 分页查询角色请求
 *
 * @author YoungerYang-Y
 */
@Data
public class PageRoleRequest {

    /** 角色名称（模糊匹配） */
    private String roleName;

    /** 角色代码（模糊匹配） */
    private String roleCode;

    /** 页码（从1开始） */
    private Integer pageNum = 1;

    /** 每页数量 */
    private Integer pageSize = 10;
}
