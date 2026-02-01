package com.yggdrasil.labs.domain.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 分页结果
 *
 * @param <T> 数据类型
 * @author YoungerYang-Y
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

    /** 数据列表 */
    private List<T> data;

    /** 总数 */
    private long total;
}
