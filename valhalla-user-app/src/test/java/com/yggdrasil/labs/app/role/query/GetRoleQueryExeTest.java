package com.yggdrasil.labs.app.role.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.app.role.assembler.RoleAssembler;
import com.yggdrasil.labs.app.role.dto.co.RoleCO;
import com.yggdrasil.labs.app.role.dto.query.GetRoleQuery;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

/** {@link GetRoleQueryExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class GetRoleQueryExeTest {

    @Mock private RoleRepository roleRepository;

    @Mock private RoleAssembler roleAssembler;

    @InjectMocks private GetRoleQueryExe getRoleQueryExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        GetRoleQuery query = new GetRoleQuery();
        query.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(null);

        SingleResponse<RoleCO> response = getRoleQueryExe.execute(query);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_ROLE_NOT_FOUND.getErrCode(), response.getErrCode());
    }

    @Test
    void execute_whenFound_shouldReturnCoWithPermissionIds() {
        Role role = new Role();
        role.setId(5L);
        role.setPermissionIds(Arrays.asList(9L));
        when(roleRepository.findById(5L)).thenReturn(role);
        RoleCO co = new RoleCO();
        when(roleAssembler.toCO(role)).thenReturn(co);

        GetRoleQuery query = new GetRoleQuery();
        query.setId(5L);

        SingleResponse<RoleCO> response = getRoleQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertEquals("9", response.getData().getPermissionIds().get(0));
    }

    @Test
    void execute_whenFoundWithNullPermissionIds_shouldReturnCoWithoutPermissionIds() {
        Role role = new Role();
        role.setId(7L);
        role.setPermissionIds(null);
        when(roleRepository.findById(7L)).thenReturn(role);
        RoleCO co = new RoleCO();
        when(roleAssembler.toCO(role)).thenReturn(co);

        GetRoleQuery query = new GetRoleQuery();
        query.setId(7L);

        SingleResponse<RoleCO> response = getRoleQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertNull(response.getData().getPermissionIds());
    }
}
