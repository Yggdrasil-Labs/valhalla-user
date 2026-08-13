package com.yggdrasil.labs.app.permission.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.PageResponse;
import com.yggdrasil.labs.app.permission.assembler.PermissionAssembler;
import com.yggdrasil.labs.app.permission.dto.co.PermissionCO;
import com.yggdrasil.labs.app.permission.dto.query.PagePermissionQuery;
import com.yggdrasil.labs.domain.common.PageResult;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

/** {@link PagePermissionQueryExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class PagePermissionQueryExeTest {

    @Mock private PermissionRepository permissionRepository;

    @Mock private PermissionAssembler permissionAssembler;

    @InjectMocks private PagePermissionQueryExe pagePermissionQueryExe;

    @Test
    void execute_shouldReturnPagedPermissionsWithApiIdsConverted() {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setPermissionCode("user:user:read");
        permission.setApiIds(Arrays.asList(1L, 2L));
        when(permissionRepository.findPage("m", "n", 1, 10))
                .thenReturn(new PageResult<>(Arrays.asList(permission), 1L));
        PermissionCO co = new PermissionCO();
        when(permissionAssembler.toCO(permission)).thenReturn(co);

        PagePermissionQuery query = new PagePermissionQuery();
        query.setModule("m");
        query.setPermissionName("n");
        query.setPageNum(1);
        query.setPageSize(10);

        PageResponse<PermissionCO> response = pagePermissionQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals(1L, response.getTotalCount());
        assertEquals("1", response.getData().get(0).getApiIds().get(0));
        assertEquals("2", response.getData().get(0).getApiIds().get(1));
    }

    @Test
    void execute_whenApiIdsNull_shouldLeaveCoApiIdsNull() {
        Permission permission = new Permission();
        permission.setId(2L);
        when(permissionRepository.findPage(null, null, 1, 10))
                .thenReturn(new PageResult<>(Arrays.asList(permission), 1L));
        PermissionCO co = new PermissionCO();
        when(permissionAssembler.toCO(permission)).thenReturn(co);

        PagePermissionQuery query = new PagePermissionQuery();

        PageResponse<PermissionCO> response = pagePermissionQueryExe.execute(query);

        assertNull(response.getData().get(0).getApiIds());
    }
}
