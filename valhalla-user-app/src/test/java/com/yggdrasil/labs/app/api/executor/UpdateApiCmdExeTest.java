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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.api.dto.cmd.UpdateApiCmd;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.domain.api.repository.ApiRepository;

/** {@link UpdateApiCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class UpdateApiCmdExeTest {

    @Mock private ApiRepository apiRepository;

    @InjectMocks private UpdateApiCmdExe updateApiCmdExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        UpdateApiCmd cmd = new UpdateApiCmd();
        cmd.setId(1L);
        when(apiRepository.findById(1L)).thenReturn(null);

        Response response = updateApiCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_API_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(apiRepository, never()).update(any());
    }

    @Test
    void execute_whenValid_shouldUpdateFields() {
        Api api = new Api();
        api.setId(1L);
        api.setApiName("old");
        when(apiRepository.findById(1L)).thenReturn(api);

        UpdateApiCmd cmd = new UpdateApiCmd();
        cmd.setId(1L);
        cmd.setApiName("new");
        cmd.setStatus("DISABLED");
        cmd.setDescription("d");
        cmd.setMetadata("m");

        Response response = updateApiCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        assertEquals("new", api.getApiName());
        assertEquals("DISABLED", api.getStatus());
        assertEquals("d", api.getDescription());
        assertEquals("m", api.getMetadata());
        assertNotNull(api.getUpdateTime());
        verify(apiRepository).update(api);
    }

    @Test
    void execute_whenOnlyNameProvided_shouldUpdateOnlyName() {
        Api api = new Api();
        api.setId(2L);
        api.setDescription("keep");
        api.setMetadata("keepM");
        when(apiRepository.findById(2L)).thenReturn(api);

        UpdateApiCmd cmd = new UpdateApiCmd();
        cmd.setId(2L);
        cmd.setApiName("only");

        updateApiCmdExe.execute(cmd);

        assertEquals("only", api.getApiName());
        assertEquals("keep", api.getDescription());
        assertEquals("keepM", api.getMetadata());
    }

    @Test
    void execute_whenApiNameNull_shouldSkipApiName() {
        Api api = new Api();
        api.setId(3L);
        api.setApiName("baseline");
        when(apiRepository.findById(3L)).thenReturn(api);

        UpdateApiCmd cmd = new UpdateApiCmd();
        cmd.setId(3L);
        cmd.setStatus("DISABLED");
        cmd.setDescription("d");
        cmd.setMetadata("m");

        updateApiCmdExe.execute(cmd);

        assertEquals("baseline", api.getApiName());
        assertEquals("DISABLED", api.getStatus());
        assertEquals("d", api.getDescription());
        assertEquals("m", api.getMetadata());
    }
}
