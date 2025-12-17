package com.yggdrasil.labs.app.api.query;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.MultiResponse;
import com.yggdrasil.labs.app.api.assembler.ApiAssembler;
import com.yggdrasil.labs.client.dto.api.co.ApiCO;
import com.yggdrasil.labs.client.dto.api.query.PageApiQuery;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 分页查询API执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class PageApiQueryExe {

    @Resource private ApiRepository apiRepository;

    @Resource private ApiAssembler apiAssembler;

    public MultiResponse<ApiCO> execute(PageApiQuery query) {
        log.info(
                "分页查询API: apiCode={}, resourcePath={}, resourceMethod={}, pageNum={}, pageSize={}",
                query.getApiCode(),
                query.getResourcePath(),
                query.getResourceMethod(),
                query.getPageNum(),
                query.getPageSize());

        // 查询API列表
        List<Api> apiList =
                apiRepository.findPage(
                        query.getApiCode(),
                        query.getResourcePath(),
                        query.getResourceMethod(),
                        query.getPageNum(),
                        query.getPageSize());

        // 转换为CO列表
        List<ApiCO> apiCOList = apiList.stream().map(apiAssembler::toCO).toList();

        return MultiResponse.of(apiCOList);
    }
}
