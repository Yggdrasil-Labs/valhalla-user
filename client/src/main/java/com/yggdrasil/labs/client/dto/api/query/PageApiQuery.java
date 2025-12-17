package com.yggdrasil.labs.client.dto.api.query;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询API
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageApiQuery extends Query {

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
