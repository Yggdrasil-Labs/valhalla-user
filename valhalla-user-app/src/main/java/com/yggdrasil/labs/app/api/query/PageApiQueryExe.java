package com.yggdrasil.labs.app.api.query;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.PageResponse;
import com.yggdrasil.labs.app.api.assembler.ApiAssembler;
import com.yggdrasil.labs.app.api.dto.co.ApiCO;
import com.yggdrasil.labs.app.api.dto.query.PageApiQuery;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;
import com.yggdrasil.labs.domain.common.PageResult;

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

    public PageResponse<ApiCO> execute(PageApiQuery query) {
        log.info(
                "分页查询API: apiCode={}, version={}, resourcePath={}, resourceMethod={}, status={},"
                        + " pageNum={}, pageSize={}",
                query.getApiCode(),
                query.getVersion(),
                query.getResourcePath(),
                query.getResourceMethod(),
                query.getStatus(),
                query.getPageNum(),
                query.getPageSize());

        // 查询API列表（包含总数）
        PageResult<Api> pageResult =
                apiRepository.findPage(
                        query.getApiCode(),
                        query.getVersion(),
                        query.getResourcePath(),
                        query.getResourceMethod(),
                        query.getStatus(),
                        query.getPageNum(),
                        query.getPageSize());

        // 转换为CO列表
        List<ApiCO> apiCOList = pageResult.getData().stream().map(apiAssembler::toCO).toList();

        return PageResponse.of(
                apiCOList,
                (int) pageResult.getTotal(),
                query.getPageSize(),
                query.getPageNum() - 1);
    }
}
