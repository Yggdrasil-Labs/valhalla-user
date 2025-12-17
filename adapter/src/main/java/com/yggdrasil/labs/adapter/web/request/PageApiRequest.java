package com.yggdrasil.labs.adapter.web.request;

import lombok.Data;

/**
 * 分页查询API请求
 *
 * @author YoungerYang-Y
 */
@Data
public class PageApiRequest {

    /** 接口代码（模糊匹配） */
    private String apiCode;

    /** 资源路径（模糊匹配） */
    private String resourcePath;

    /** HTTP方法 */
    private String resourceMethod;

    /** 页码（从1开始） */
    private Integer pageNum = 1;

    /** 每页数量 */
    private Integer pageSize = 10;
}
