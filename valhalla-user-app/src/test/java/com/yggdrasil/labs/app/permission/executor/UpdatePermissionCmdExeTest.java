package com.yggdrasil.labs.app.permission.executor;

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
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.app.permission.dto.cmd.UpdatePermissionCmd;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

/** {@link UpdatePermissionCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class UpdatePermissionCmdExeTest {

    @Mock private PermissionRepository permissionRepository;

    @InjectMocks private UpdatePermissionCmdExe updatePermissionCmdExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        UpdatePermissionCmd cmd = new UpdatePermissionCmd();
        cmd.setId(1L);
        when(permissionRepository.findById(1L)).thenReturn(null);

        Response response = updatePermissionCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_PERMISSION_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(permissionRepository, never()).update(any());
    }

    @Test
    void execute_whenValid_shouldUpdateFields() {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermissionName("old");
        when(permissionRepository.findById(1L)).thenReturn(permission);

        UpdatePermissionCmd cmd = new UpdatePermissionCmd();
        cmd.setId(1L);
        cmd.setPermissionName("new");
        cmd.setDescription("d");
        cmd.setMetadata("m");

        Response response = updatePermissionCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        assertEquals("new", permission.getPermissionName());
        assertEquals("d", permission.getDescription());
        assertEquals("m", permission.getMetadata());
        assertNotNull(permission.getUpdateTime());
        verify(permissionRepository).update(permission);
    }

    @Test
    void execute_whenOnlyNameProvided_shouldUpdateOnlyName() {
        Permission permission = new Permission();
        permission.setId(2L);
        permission.setDescription("keep");
        permission.setMetadata("keepM");
        when(permissionRepository.findById(2L)).thenReturn(permission);

        UpdatePermissionCmd cmd = new UpdatePermissionCmd();
        cmd.setId(2L);
        cmd.setPermissionName("only");

        updatePermissionCmdExe.execute(cmd);

        assertEquals("only", permission.getPermissionName());
        assertEquals("keep", permission.getDescription());
        assertEquals("keepM", permission.getMetadata());
    }

    @Test
    void execute_whenPermissionNameNull_shouldSkipPermissionName() {
        Permission permission = new Permission();
        permission.setId(3L);
        permission.setPermissionName("baseline");
        permission.setDescription("keep");
        permission.setMetadata("keepM");
        when(permissionRepository.findById(3L)).thenReturn(permission);

        UpdatePermissionCmd cmd = new UpdatePermissionCmd();
        cmd.setId(3L);
        cmd.setDescription("newD");
        cmd.setMetadata("newM");

        updatePermissionCmdExe.execute(cmd);

        assertEquals("baseline", permission.getPermissionName());
        assertEquals("newD", permission.getDescription());
        assertEquals("newM", permission.getMetadata());
    }
}
