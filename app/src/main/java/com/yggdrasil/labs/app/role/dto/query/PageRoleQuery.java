package com.yggdrasil.labs.app.role.dto.query;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** 分页查询角色 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageRoleQuery extends Query {

    /** 角色名称（模糊匹配） */
    private String roleName;

    /** 角色代码（模糊匹配） */
    private String roleCode;

    /** 页码（从1开始） */
    private Integer pageNum = 1;

    /** 每页数量 */
    private Integer pageSize = 10;
}
