package com.yggdrasil.labs.app.role.executor;

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
import com.yggdrasil.labs.app.role.dto.cmd.UpdateRoleCmd;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

/** {@link UpdateRoleCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class UpdateRoleCmdExeTest {

    @Mock private RoleRepository roleRepository;

    @InjectMocks private UpdateRoleCmdExe updateRoleCmdExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        UpdateRoleCmd cmd = new UpdateRoleCmd();
        cmd.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(null);

        Response response = updateRoleCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_ROLE_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(roleRepository, never()).update(any());
    }

    @Test
    void execute_whenValid_shouldUpdateFields() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("old");
        when(roleRepository.findById(1L)).thenReturn(role);

        UpdateRoleCmd cmd = new UpdateRoleCmd();
        cmd.setId(1L);
        cmd.setRoleName("new");
        cmd.setDescription("d");
        cmd.setMetadata("m");

        Response response = updateRoleCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        assertEquals("new", role.getRoleName());
        assertEquals("d", role.getDescription());
        assertEquals("m", role.getMetadata());
        assertNotNull(role.getUpdateTime());
        verify(roleRepository).update(role);
    }

    @Test
    void execute_whenOnlyRoleNameProvided_shouldUpdateOnlyName() {
        Role role = new Role();
        role.setId(2L);
        role.setDescription("keep");
        role.setMetadata("keepM");
        when(roleRepository.findById(2L)).thenReturn(role);

        UpdateRoleCmd cmd = new UpdateRoleCmd();
        cmd.setId(2L);
        cmd.setRoleName("only");

        updateRoleCmdExe.execute(cmd);

        assertEquals("only", role.getRoleName());
        assertEquals("keep", role.getDescription());
        assertEquals("keepM", role.getMetadata());
    }

    @Test
    void execute_whenRoleNameNull_shouldSkipRoleName() {
        Role role = new Role();
        role.setId(3L);
        role.setRoleName("baseline");
        role.setDescription("keep");
        role.setMetadata("keepM");
        when(roleRepository.findById(3L)).thenReturn(role);

        UpdateRoleCmd cmd = new UpdateRoleCmd();
        cmd.setId(3L);
        cmd.setDescription("newD");
        cmd.setMetadata("newM");

        updateRoleCmdExe.execute(cmd);

        assertEquals("baseline", role.getRoleName());
        assertEquals("newD", role.getDescription());
        assertEquals("newM", role.getMetadata());
    }
}
