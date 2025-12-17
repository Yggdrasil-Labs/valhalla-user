package com.yggdrasil.labs.app.api.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.api.cmd.UpdateApiCmd;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 更新API命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class UpdateApiCmdExe {

    @Resource private ApiRepository apiRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(UpdateApiCmd cmd) {
        log.info("更新API: apiId={}", cmd.getId());

        // 查询API
        Api api = apiRepository.findById(cmd.getId());
        if (api == null) {
            return Response.buildFailure(
                    ErrorCode.B_API_NOT_FOUND.getErrCode(), ErrorCode.B_API_NOT_FOUND.getErrDesc());
        }

        // 更新API信息
        if (cmd.getApiName() != null) {
            api.setApiName(cmd.getApiName());
        }
        if (cmd.getDescription() != null) {
            api.setDescription(cmd.getDescription());
        }
        if (cmd.getMetadata() != null) {
            api.setMetadata(cmd.getMetadata());
        }
        api.setUpdateTime(LocalDateTime.now());

        // 更新API
        apiRepository.update(api);

        log.info("API更新成功: apiId={}", api.getId());
        return Response.buildSuccess();
    }
}
