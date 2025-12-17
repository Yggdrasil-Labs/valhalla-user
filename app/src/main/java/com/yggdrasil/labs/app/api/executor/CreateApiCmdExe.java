package com.yggdrasil.labs.app.api.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.api.cmd.CreateApiCmd;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建API命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class CreateApiCmdExe {

    @Resource private ApiRepository apiRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(CreateApiCmd cmd) {
        log.info(
                "创建API: apiCode={}, resourcePath={}, resourceMethod={}",
                cmd.getApiCode(),
                cmd.getResourcePath(),
                cmd.getResourceMethod());

        // 检查接口代码是否已存在
        if (apiRepository.existsByApiCode(cmd.getApiCode())) {
            return Response.buildFailure(
                    ErrorCode.B_API_CODE_EXISTS.getErrCode(),
                    ErrorCode.B_API_CODE_EXISTS.getErrDesc());
        }

        // 检查资源路径和HTTP方法组合是否已存在
        if (apiRepository.existsByResourcePathAndMethod(
                cmd.getResourcePath(), cmd.getResourceMethod())) {
            return Response.buildFailure(
                    ErrorCode.B_API_EXISTS.getErrCode(), ErrorCode.B_API_EXISTS.getErrDesc());
        }

        // 创建API实体
        Api api = new Api();
        api.setApiCode(cmd.getApiCode());
        api.setApiName(cmd.getApiName());
        api.setResourcePath(cmd.getResourcePath());
        api.setResourceMethod(cmd.getResourceMethod());
        api.setDescription(cmd.getDescription());
        api.setMetadata(cmd.getMetadata());
        api.setCreateTime(LocalDateTime.now());
        api.setUpdateTime(LocalDateTime.now());

        // 保存API
        apiRepository.save(api);

        log.info("API创建成功: apiId={}, apiCode={}", api.getId(), api.getApiCode());
        return Response.buildSuccess();
    }
}
