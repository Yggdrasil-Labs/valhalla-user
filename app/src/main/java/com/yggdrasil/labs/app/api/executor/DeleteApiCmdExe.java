package com.yggdrasil.labs.app.api.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.api.cmd.DeleteApiCmd;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 删除API命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class DeleteApiCmdExe {

    @Resource private ApiRepository apiRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(DeleteApiCmd cmd) {
        log.info("删除API: apiId={}", cmd.getId());

        // 查询API
        Api api = apiRepository.findById(cmd.getId());
        if (api == null) {
            return Response.buildFailure(
                    ErrorCode.B_API_NOT_FOUND.getErrCode(), ErrorCode.B_API_NOT_FOUND.getErrDesc());
        }

        // 删除API（软删除）
        apiRepository.delete(cmd.getId());

        log.info("API删除成功: apiId={}", cmd.getId());
        return Response.buildSuccess();
    }
}
