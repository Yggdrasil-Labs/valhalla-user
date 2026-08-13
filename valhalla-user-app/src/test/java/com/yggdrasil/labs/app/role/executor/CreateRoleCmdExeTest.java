package com.yggdrasil.labs.app.role.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
import com.yggdrasil.labs.app.role.dto.cmd.CreateRoleCmd;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

/** {@link CreateRoleCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class CreateRoleCmdExeTest {

    @Mock private RoleRepository roleRepository;

    @InjectMocks private CreateRoleCmdExe createRoleCmdExe;

    private CreateRoleCmd buildCmd() {
        CreateRoleCmd cmd = new CreateRoleCmd();
        cmd.setRoleCode("ADMIN");
        cmd.setRoleName("Administrator");
        cmd.setDescription("desc");
        cmd.setMetadata("{}");
        return cmd;
    }

    @Test
    void execute_whenRoleCodeExists_shouldReturnFailure() {
        when(roleRepository.existsByRoleCode("ADMIN")).thenReturn(true);

        Response response = createRoleCmdExe.execute(buildCmd());

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_ROLE_CODE_EXISTS.getErrCode(), response.getErrCode());
        verify(roleRepository, never()).save(any());
    }

    @Test
    void execute_whenValid_shouldSaveRoleWithFields() {
        Response response = createRoleCmdExe.execute(buildCmd());

        assertTrue(response.isSuccess());
        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository, times(1)).save(captor.capture());
        Role saved = captor.getValue();
        assertEquals("ADMIN", saved.getRoleCode());
        assertEquals("Administrator", saved.getRoleName());
        assertEquals("desc", saved.getDescription());
        assertEquals("{}", saved.getMetadata());
        assertFalse(saved.getIsSystem());
        assertNull(saved.getPermissionIds());
        assertNotNull(saved.getCreateTime());
        assertNotNull(saved.getUpdateTime());
    }

    @Test
    void execute_whenIsSystemTrue_shouldSetIsSystem() {
        CreateRoleCmd cmd = buildCmd();
        cmd.setIsSystem(true);

        createRoleCmdExe.execute(cmd);

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(captor.capture());
        assertTrue(captor.getValue().getIsSystem());
    }

    @Test
    void execute_whenPermissionIdsProvided_shouldAssign() {
        CreateRoleCmd cmd = buildCmd();
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        cmd.setPermissionIds(ids);

        createRoleCmdExe.execute(cmd);

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(captor.capture());
        assertEquals(ids, captor.getValue().getPermissionIds());
    }

    @Test
    void execute_whenIsSystemFalse_shouldSetIsSystemFalse() {
        CreateRoleCmd cmd = buildCmd();
        cmd.setIsSystem(false);

        createRoleCmdExe.execute(cmd);

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(captor.capture());
        assertFalse(captor.getValue().getIsSystem());
    }

    @Test
    void execute_whenPermissionIdsEmpty_shouldNotAssign() {
        CreateRoleCmd cmd = buildCmd();
        cmd.setPermissionIds(List.of());

        createRoleCmdExe.execute(cmd);

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(captor.capture());
        assertNull(captor.getValue().getPermissionIds());
    }
}
