package com.yggdrasil.labs.adapter.web.request;

import lombok.Data;

/**
 * 分页查询权限请求
 *
 * @author YoungerYang-Y
 */
@Data
public class PagePermissionRequest {

    /** 模块 */
    private String module;

    /** 权限名称（模糊匹配） */
    private String permissionName;

    /** 页码（从1开始） */
    private Integer pageNum = 1;

    /** 每页数量 */
    private Integer pageSize = 10;
}
