package com.yggdrasil.labs.app.api.query;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.api.assembler.ApiAssembler;
import com.yggdrasil.labs.client.dto.api.co.ApiCO;
import com.yggdrasil.labs.client.dto.api.query.GetApiQuery;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取API查询执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class GetApiQueryExe {

    @Resource private ApiRepository apiRepository;

    @Resource private ApiAssembler apiAssembler;

    public SingleResponse<ApiCO> execute(GetApiQuery query) {
        log.info("获取API详情: apiId={}", query.getId());

        // 查询API
        Api api = apiRepository.findById(query.getId());
        if (api == null) {
            return SingleResponse.buildFailure(
                    ErrorCode.B_API_NOT_FOUND.getErrCode(), ErrorCode.B_API_NOT_FOUND.getErrDesc());
        }

        // 转换为CO
        ApiCO apiCO = apiAssembler.toCO(api);

        return SingleResponse.of(apiCO);
    }
}
