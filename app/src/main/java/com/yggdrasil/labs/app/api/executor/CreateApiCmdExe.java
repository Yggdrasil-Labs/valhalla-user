package com.yggdrasil.labs.app.api.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.api.cmd.CreateApiCmd;
import com.yggdrasil.labs.client.dto.enums.ApiStatusEnum;
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
        // 设置默认值
        String version = cmd.getVersion();
        if (version == null || version.trim().isEmpty()) {
            version = "v1";
        }
        String status = cmd.getStatus();
        if (status == null || status.trim().isEmpty()) {
            status = ApiStatusEnum.ENABLED.name();
        }

        log.info(
                "创建API: apiCode={}, version={}, resourcePath={}, resourceMethod={}",
                cmd.getApiCode(),
                version,
                cmd.getResourcePath(),
                cmd.getResourceMethod());

        // 检查接口代码和版本组合是否已存在
        if (apiRepository.existsByApiCodeAndVersion(cmd.getApiCode(), version)) {
            return Response.buildFailure(
                    ErrorCode.B_API_CODE_VERSION_EXISTS.getErrCode(),
                    ErrorCode.B_API_CODE_VERSION_EXISTS.getErrDesc());
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
        api.setVersion(version);
        api.setApiName(cmd.getApiName());
        api.setResourcePath(cmd.getResourcePath());
        api.setResourceMethod(cmd.getResourceMethod());
        api.setStatus(status);
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
