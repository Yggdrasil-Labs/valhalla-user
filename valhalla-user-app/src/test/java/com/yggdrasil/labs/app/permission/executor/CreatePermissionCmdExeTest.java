package com.yggdrasil.labs.app.permission.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.app.permission.dto.cmd.CreatePermissionCmd;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

/** {@link CreatePermissionCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class CreatePermissionCmdExeTest {

    @Mock private PermissionRepository permissionRepository;

    @InjectMocks private CreatePermissionCmdExe createPermissionCmdExe;

    private CreatePermissionCmd buildCmd() {
        CreatePermissionCmd cmd = new CreatePermissionCmd();
        cmd.setModule("user");
        cmd.setResource("user");
        cmd.setAction("read");
        cmd.setPermissionName("读取用户");
        cmd.setDescription("desc");
        cmd.setMetadata("{}");
        return cmd;
    }

    @Test
    void execute_whenModuleResourceActionExists_shouldReturnFailure() {
        when(permissionRepository.existsByModuleResourceAction("user", "user", "read"))
                .thenReturn(true);

        Response response = createPermissionCmdExe.execute(buildCmd());

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_PERMISSION_EXISTS.getErrCode(), response.getErrCode());
        verify(permissionRepository, never()).save(any());
    }

    @Test
    void execute_whenValid_shouldSaveWithGeneratedCode() {
        Response response = createPermissionCmdExe.execute(buildCmd());

        assertTrue(response.isSuccess());
        ArgumentCaptor<Permission> captor = ArgumentCaptor.forClass(Permission.class);
        verify(permissionRepository).save(captor.capture());
        Permission saved = captor.getValue();
        assertEquals("user:user:read", saved.getPermissionCode());
        assertEquals("读取用户", saved.getPermissionName());
        assertNull(saved.getApiIds());
        assertNotNull(saved.getCreateTime());
        assertNotNull(saved.getUpdateTime());
    }

    @Test
    void execute_whenApiIdsProvided_shouldAssign() {
        CreatePermissionCmd cmd = buildCmd();
        cmd.setApiIds(Arrays.asList(1L, 2L));

        createPermissionCmdExe.execute(cmd);

        ArgumentCaptor<Permission> captor = ArgumentCaptor.forClass(Permission.class);
        verify(permissionRepository).save(captor.capture());
        assertEquals(Arrays.asList(1L, 2L), captor.getValue().getApiIds());
    }

    @Test
    void execute_whenApiIdsEmpty_shouldNotAssign() {
        CreatePermissionCmd cmd = buildCmd();
        cmd.setApiIds(List.of());

        createPermissionCmdExe.execute(cmd);

        ArgumentCaptor<Permission> captor = ArgumentCaptor.forClass(Permission.class);
        verify(permissionRepository).save(captor.capture());
        assertNull(captor.getValue().getApiIds());
    }
}
