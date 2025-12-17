package com.yggdrasil.labs.domain.api.repository;

import java.util.List;

import com.yggdrasil.labs.domain.api.model.Api;

/**
 * API仓储接口
 *
 * @author YoungerYang-Y
 */
public interface ApiRepository {

    /**
     * 根据ID查询API
     *
     * @param id API ID
     * @return API实体，不存在返回null
     */
    Api findById(Long id);

    /**
     * 根据接口代码查询API
     *
     * @param apiCode 接口代码
     * @return API实体，不存在返回null
     */
    Api findByApiCode(String apiCode);

    /**
     * 根据资源路径和HTTP方法查询API
     *
     * @param resourcePath 资源路径
     * @param resourceMethod HTTP方法
     * @return API实体，不存在返回null
     */
    Api findByResourcePathAndMethod(String resourcePath, String resourceMethod);

    /**
     * 保存API（新增）
     *
     * @param api API实体
     */
    void save(Api api);

    /**
     * 更新API
     *
     * @param api API实体
     */
    void update(Api api);

    /**
     * 删除API（软删除）
     *
     * @param id API ID
     */
    void delete(Long id);

    /**
     * 根据条件分页查询API列表
     *
     * @param apiCode 接口代码（可选，模糊匹配）
     * @param resourcePath 资源路径（可选，模糊匹配）
     * @param resourceMethod HTTP方法（可选）
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页数量
     * @return API列表
     */
    List<Api> findPage(
            String apiCode,
            String resourcePath,
            String resourceMethod,
            Integer pageNum,
            Integer pageSize);

    /**
     * 统计符合条件的API数量
     *
     * @param apiCode 接口代码（可选，模糊匹配）
     * @param resourcePath 资源路径（可选，模糊匹配）
     * @param resourceMethod HTTP方法（可选）
     * @return API数量
     */
    long count(String apiCode, String resourcePath, String resourceMethod);

    /**
     * 检查接口代码是否存在
     *
     * @param apiCode 接口代码
     * @return true-存在，false-不存在
     */
    boolean existsByApiCode(String apiCode);

    /**
     * 检查资源路径和HTTP方法组合是否存在
     *
     * @param resourcePath 资源路径
     * @param resourceMethod HTTP方法
     * @return true-存在，false-不存在
     */
    boolean existsByResourcePathAndMethod(String resourcePath, String resourceMethod);
}
