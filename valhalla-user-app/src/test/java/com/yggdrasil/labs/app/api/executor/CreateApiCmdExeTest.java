package com.yggdrasil.labs.app.api.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.api.dto.cmd.CreateApiCmd;
import com.yggdrasil.labs.app.common.dto.enums.ApiStatusEnum;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

/** {@link CreateApiCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class CreateApiCmdExeTest {

    @Mock private ApiRepository apiRepository;

    @InjectMocks private CreateApiCmdExe createApiCmdExe;

    private CreateApiCmd buildCmd() {
        CreateApiCmd cmd = new CreateApiCmd();
        cmd.setApiCode("api.user.list");
        cmd.setApiName("用户列表");
        cmd.setResourcePath("/api/user/list");
        cmd.setResourceMethod("GET");
        cmd.setDescription("d");
        cmd.setMetadata("{}");
        return cmd;
    }

    @Test
    void execute_whenCodeVersionExists_shouldReturnFailure() {
        when(apiRepository.existsByApiCodeAndVersion("api.user.list", "v1")).thenReturn(true);

        Response response = createApiCmdExe.execute(buildCmd());

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_API_CODE_VERSION_EXISTS.getErrCode(), response.getErrCode());
        verify(apiRepository, never()).save(any());
    }

    @Test
    void execute_whenPathMethodExists_shouldReturnFailure() {
        when(apiRepository.existsByApiCodeAndVersion(any(), any())).thenReturn(false);
        when(apiRepository.existsByResourcePathAndMethod("/api/user/list", "GET")).thenReturn(true);

        Response response = createApiCmdExe.execute(buildCmd());

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_API_EXISTS.getErrCode(), response.getErrCode());
        verify(apiRepository, never()).save(any());
    }

    @Test
    void execute_whenValid_shouldSaveWithDefaults() {
        Response response = createApiCmdExe.execute(buildCmd());

        assertTrue(response.isSuccess());
        ArgumentCaptor<Api> captor = ArgumentCaptor.forClass(Api.class);
        verify(apiRepository).save(captor.capture());
        Api saved = captor.getValue();
        assertEquals("v1", saved.getVersion());
        assertEquals(ApiStatusEnum.ENABLED.name(), saved.getStatus());
        assertEquals("api.user.list", saved.getApiCode());
        assertNotNull(saved.getCreateTime());
        assertNotNull(saved.getUpdateTime());
    }

    @Test
    void execute_whenVersionAndStatusProvided_shouldUseThem() {
        CreateApiCmd cmd = buildCmd();
        cmd.setVersion("v2");
        cmd.setStatus(ApiStatusEnum.DISABLED.name());

        createApiCmdExe.execute(cmd);

        ArgumentCaptor<Api> captor = ArgumentCaptor.forClass(Api.class);
        verify(apiRepository).save(captor.capture());
        assertEquals("v2", captor.getValue().getVersion());
        assertEquals(ApiStatusEnum.DISABLED.name(), captor.getValue().getStatus());
    }

    @Test
    void execute_whenVersionAndStatusBlank_shouldUseDefaults() {
        CreateApiCmd cmd = buildCmd();
        cmd.setVersion(" ");
        cmd.setStatus("");

        createApiCmdExe.execute(cmd);

        ArgumentCaptor<Api> captor = ArgumentCaptor.forClass(Api.class);
        verify(apiRepository).save(captor.capture());
        assertEquals("v1", captor.getValue().getVersion());
        assertEquals(ApiStatusEnum.ENABLED.name(), captor.getValue().getStatus());
    }
}
