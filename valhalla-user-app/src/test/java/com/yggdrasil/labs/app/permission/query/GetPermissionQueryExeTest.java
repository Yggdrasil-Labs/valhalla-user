package com.yggdrasil.labs.app.permission.query;

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
import com.yggdrasil.labs.app.permission.assembler.PermissionAssembler;
import com.yggdrasil.labs.app.permission.dto.co.PermissionCO;
import com.yggdrasil.labs.app.permission.dto.query.GetPermissionQuery;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

/** {@link GetPermissionQueryExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class GetPermissionQueryExeTest {

    @Mock private PermissionRepository permissionRepository;

    @Mock private PermissionAssembler permissionAssembler;

    @InjectMocks private GetPermissionQueryExe getPermissionQueryExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        GetPermissionQuery query = new GetPermissionQuery();
        query.setId(1L);
        when(permissionRepository.findById(1L)).thenReturn(null);

        SingleResponse<PermissionCO> response = getPermissionQueryExe.execute(query);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_PERMISSION_NOT_FOUND.getErrCode(), response.getErrCode());
    }

    @Test
    void execute_whenFound_shouldReturnCoWithApiIds() {
        Permission permission = new Permission();
        permission.setId(5L);
        permission.setApiIds(Arrays.asList(9L));
        when(permissionRepository.findById(5L)).thenReturn(permission);
        PermissionCO co = new PermissionCO();
        when(permissionAssembler.toCO(permission)).thenReturn(co);

        GetPermissionQuery query = new GetPermissionQuery();
        query.setId(5L);

        SingleResponse<PermissionCO> response = getPermissionQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertEquals("9", response.getData().getApiIds().get(0));
    }

    @Test
    void execute_whenFoundWithNullApiIds_shouldReturnCoWithoutApiIds() {
        Permission permission = new Permission();
        permission.setId(7L);
        permission.setApiIds(null);
        when(permissionRepository.findById(7L)).thenReturn(permission);
        PermissionCO co = new PermissionCO();
        when(permissionAssembler.toCO(permission)).thenReturn(co);

        GetPermissionQuery query = new GetPermissionQuery();
        query.setId(7L);

        SingleResponse<PermissionCO> response = getPermissionQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertNull(response.getData().getApiIds());
    }
}
