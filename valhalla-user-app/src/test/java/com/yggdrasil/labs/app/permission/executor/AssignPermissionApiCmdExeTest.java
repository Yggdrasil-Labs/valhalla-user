package com.yggdrasil.labs.app.permission.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.app.permission.dto.cmd.AssignPermissionApiCmd;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

/** {@link AssignPermissionApiCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class AssignPermissionApiCmdExeTest {

    @Mock private PermissionRepository permissionRepository;

    @InjectMocks private AssignPermissionApiCmdExe assignPermissionApiCmdExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        AssignPermissionApiCmd cmd = new AssignPermissionApiCmd();
        cmd.setPermissionId(1L);
        cmd.setApiIds(Arrays.asList(1L));
        when(permissionRepository.findById(1L)).thenReturn(null);

        Response response = assignPermissionApiCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_PERMISSION_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(permissionRepository, never()).update(any());
    }

    @Test
    void execute_whenValid_shouldAssignAndUpdate() {
        Permission permission = new Permission();
        permission.setId(1L);
        when(permissionRepository.findById(1L)).thenReturn(permission);

        AssignPermissionApiCmd cmd = new AssignPermissionApiCmd();
        cmd.setPermissionId(1L);
        cmd.setApiIds(Arrays.asList(10L, 20L));

        Response response = assignPermissionApiCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        assertEquals(Arrays.asList(10L, 20L), permission.getApiIds());
        verify(permissionRepository).update(permission);
    }
}
