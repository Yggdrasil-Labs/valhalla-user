package com.yggdrasil.labs.app.permission.executor;

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
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.app.permission.dto.cmd.DeletePermissionCmd;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

/** {@link DeletePermissionCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class DeletePermissionCmdExeTest {

    @Mock private PermissionRepository permissionRepository;

    @InjectMocks private DeletePermissionCmdExe deletePermissionCmdExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        DeletePermissionCmd cmd = new DeletePermissionCmd();
        cmd.setId(1L);
        when(permissionRepository.findById(1L)).thenReturn(null);

        Response response = deletePermissionCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_PERMISSION_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(permissionRepository, never()).delete(any());
    }

    @Test
    void execute_whenFound_shouldDelete() {
        Permission permission = new Permission();
        permission.setId(2L);
        when(permissionRepository.findById(2L)).thenReturn(permission);

        DeletePermissionCmd cmd = new DeletePermissionCmd();
        cmd.setId(2L);

        Response response = deletePermissionCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        verify(permissionRepository).delete(2L);
    }
}
