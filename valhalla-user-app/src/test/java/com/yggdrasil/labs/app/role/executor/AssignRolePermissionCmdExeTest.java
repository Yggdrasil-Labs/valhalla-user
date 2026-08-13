package com.yggdrasil.labs.app.role.executor;

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
import com.yggdrasil.labs.app.role.dto.cmd.AssignRolePermissionCmd;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

/** {@link AssignRolePermissionCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class AssignRolePermissionCmdExeTest {

    @Mock private RoleRepository roleRepository;

    @InjectMocks private AssignRolePermissionCmdExe assignRolePermissionCmdExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        AssignRolePermissionCmd cmd = new AssignRolePermissionCmd();
        cmd.setRoleId(1L);
        cmd.setPermissionIds(Arrays.asList(1L));
        when(roleRepository.findById(1L)).thenReturn(null);

        Response response = assignRolePermissionCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_ROLE_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(roleRepository, never()).update(any());
    }

    @Test
    void execute_whenValid_shouldAssignAndUpdate() {
        Role role = new Role();
        role.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(role);

        AssignRolePermissionCmd cmd = new AssignRolePermissionCmd();
        cmd.setRoleId(1L);
        cmd.setPermissionIds(Arrays.asList(10L, 20L));

        Response response = assignRolePermissionCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        assertEquals(Arrays.asList(10L, 20L), role.getPermissionIds());
        verify(roleRepository).update(role);
    }
}
