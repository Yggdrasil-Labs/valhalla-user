package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.ApiConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.ApiDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.ApiService;

/**
 * API仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class ApiRepositoryImpl implements ApiRepository {

    @Resource private ApiService apiService;

    @Resource private ApiConverter apiConverter;

    @Override
    public Api findById(Long id) {
        ApiDO apiDO = apiService.getById(id);
        if (apiDO == null) {
            return null;
        }
        return apiConverter.toEntity(apiDO);
    }

    @Override
    public Api findByApiCode(String apiCode) {
        LambdaQueryWrapper<ApiDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApiDO::getApiCode, apiCode);
        ApiDO apiDO = apiService.getOne(wrapper);
        if (apiDO == null) {
            return null;
        }
        return apiConverter.toEntity(apiDO);
    }

    @Override
    public Api findByResourcePathAndMethod(String resourcePath, String resourceMethod) {
        LambdaQueryWrapper<ApiDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApiDO::getResourcePath, resourcePath)
                .eq(ApiDO::getResourceMethod, resourceMethod);
        ApiDO apiDO = apiService.getOne(wrapper);
        if (apiDO == null) {
            return null;
        }
        return apiConverter.toEntity(apiDO);
    }

    @Override
    public void save(Api api) {
        ApiDO apiDO = apiConverter.toDO(api);
        apiService.save(apiDO);
        api.setId(apiDO.getId());
    }

    @Override
    public void update(Api api) {
        ApiDO apiDO = apiConverter.toDO(api);
        apiService.updateById(apiDO);
    }

    @Override
    public void delete(Long id) {
        apiService.removeById(id);
    }

    @Override
    public List<Api> findPage(
            String apiCode,
            String resourcePath,
            String resourceMethod,
            Integer pageNum,
            Integer pageSize) {
        LambdaQueryWrapper<ApiDO> wrapper = new LambdaQueryWrapper<>();
        if (apiCode != null && !apiCode.trim().isEmpty()) {
            wrapper.like(ApiDO::getApiCode, apiCode);
        }
        if (resourcePath != null && !resourcePath.trim().isEmpty()) {
            wrapper.like(ApiDO::getResourcePath, resourcePath);
        }
        if (resourceMethod != null && !resourceMethod.trim().isEmpty()) {
            wrapper.eq(ApiDO::getResourceMethod, resourceMethod);
        }
        Page<ApiDO> page = new Page<>(pageNum, pageSize);
        IPage<ApiDO> pageResult = apiService.page(page, wrapper);
        List<ApiDO> apiDOList = pageResult.getRecords();
        return apiDOList.stream().map(apiConverter::toEntity).toList();
    }

    @Override
    public long count(String apiCode, String resourcePath, String resourceMethod) {
        LambdaQueryWrapper<ApiDO> wrapper = new LambdaQueryWrapper<>();
        if (apiCode != null && !apiCode.trim().isEmpty()) {
            wrapper.like(ApiDO::getApiCode, apiCode);
        }
        if (resourcePath != null && !resourcePath.trim().isEmpty()) {
            wrapper.like(ApiDO::getResourcePath, resourcePath);
        }
        if (resourceMethod != null && !resourceMethod.trim().isEmpty()) {
            wrapper.eq(ApiDO::getResourceMethod, resourceMethod);
        }
        return apiService.count(wrapper);
    }

    @Override
    public boolean existsByApiCode(String apiCode) {
        LambdaQueryWrapper<ApiDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApiDO::getApiCode, apiCode);
        return apiService.count(wrapper) > 0;
    }

    @Override
    public boolean existsByResourcePathAndMethod(String resourcePath, String resourceMethod) {
        LambdaQueryWrapper<ApiDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApiDO::getResourcePath, resourcePath)
                .eq(ApiDO::getResourceMethod, resourceMethod);
        return apiService.count(wrapper) > 0;
    }
}
