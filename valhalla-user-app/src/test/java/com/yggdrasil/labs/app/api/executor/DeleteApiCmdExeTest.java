package com.yggdrasil.labs.app.api.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.api.dto.cmd.DeleteApiCmd;
import com.yggdrasil.labs.app.common.dto.enums.ApiStatusEnum;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

/** {@link DeleteApiCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class DeleteApiCmdExeTest {

    @Mock private ApiRepository apiRepository;

    @InjectMocks private DeleteApiCmdExe deleteApiCmdExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        DeleteApiCmd cmd = new DeleteApiCmd();
        cmd.setId(1L);
        when(apiRepository.findById(1L)).thenReturn(null);

        Response response = deleteApiCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_API_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(apiRepository, never()).delete(any());
    }

    @Test
    void execute_whenNotDisabled_shouldReturnFailure() {
        Api api = new Api();
        api.setId(2L);
        api.setStatus(ApiStatusEnum.ENABLED.name());
        when(apiRepository.findById(2L)).thenReturn(api);

        DeleteApiCmd cmd = new DeleteApiCmd();
        cmd.setId(2L);

        Response response = deleteApiCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_API_STATUS_NOT_DISABLED.getErrCode(), response.getErrCode());
        verify(apiRepository, never()).delete(any());
    }

    @Test
    void execute_whenDisabled_shouldDelete() {
        Api api = new Api();
        api.setId(3L);
        api.setStatus(ApiStatusEnum.DISABLED.name());
        when(apiRepository.findById(3L)).thenReturn(api);

        DeleteApiCmd cmd = new DeleteApiCmd();
        cmd.setId(3L);

        Response response = deleteApiCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        verify(apiRepository).delete(3L);
    }
}
