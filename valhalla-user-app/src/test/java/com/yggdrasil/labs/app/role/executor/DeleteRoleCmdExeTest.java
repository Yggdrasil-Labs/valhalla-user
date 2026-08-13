package com.yggdrasil.labs.app.role.executor;

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
import com.yggdrasil.labs.app.role.dto.cmd.DeleteRoleCmd;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

/** {@link DeleteRoleCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class DeleteRoleCmdExeTest {

    @Mock private RoleRepository roleRepository;

    @InjectMocks private DeleteRoleCmdExe deleteRoleCmdExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        DeleteRoleCmd cmd = new DeleteRoleCmd();
        cmd.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(null);

        Response response = deleteRoleCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_ROLE_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(roleRepository, never()).delete(any());
    }

    @Test
    void execute_whenSystemRole_shouldReturnFailure() {
        Role role = new Role();
        role.setId(2L);
        role.setIsSystem(true);
        when(roleRepository.findById(2L)).thenReturn(role);

        DeleteRoleCmd cmd = new DeleteRoleCmd();
        cmd.setId(2L);

        Response response = deleteRoleCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_ROLE_IS_SYSTEM.getErrCode(), response.getErrCode());
        verify(roleRepository, never()).delete(any());
    }

    @Test
    void execute_whenNormalRole_shouldDelete() {
        Role role = new Role();
        role.setId(3L);
        role.setIsSystem(false);
        when(roleRepository.findById(3L)).thenReturn(role);

        DeleteRoleCmd cmd = new DeleteRoleCmd();
        cmd.setId(3L);

        Response response = deleteRoleCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        verify(roleRepository).delete(3L);
    }
}
