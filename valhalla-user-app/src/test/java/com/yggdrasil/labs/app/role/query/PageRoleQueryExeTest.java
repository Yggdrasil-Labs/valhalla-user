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

import com.alibaba.cola.dto.PageResponse;
import com.yggdrasil.labs.app.role.assembler.RoleAssembler;
import com.yggdrasil.labs.app.role.dto.co.RoleCO;
import com.yggdrasil.labs.app.role.dto.query.PageRoleQuery;
import com.yggdrasil.labs.domain.common.PageResult;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

/** {@link PageRoleQueryExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class PageRoleQueryExeTest {

    @Mock private RoleRepository roleRepository;

    @Mock private RoleAssembler roleAssembler;

    @InjectMocks private PageRoleQueryExe pageRoleQueryExe;

    @Test
    void execute_shouldReturnPagedRolesWithPermissionIdsConverted() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleCode("R1");
        role.setPermissionIds(Arrays.asList(1L, 2L));
        when(roleRepository.findPage("n", "c", 1, 10))
                .thenReturn(new PageResult<>(Arrays.asList(role), 1L));
        RoleCO co = new RoleCO();
        when(roleAssembler.toCO(role)).thenReturn(co);

        PageRoleQuery query = new PageRoleQuery();
        query.setRoleName("n");
        query.setRoleCode("c");
        query.setPageNum(1);
        query.setPageSize(10);

        PageResponse<RoleCO> response = pageRoleQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals(1L, response.getTotalCount());
        assertEquals("1", response.getData().get(0).getPermissionIds().get(0));
        assertEquals("2", response.getData().get(0).getPermissionIds().get(1));
    }

    @Test
    void execute_whenPermissionIdsNull_shouldLeaveCoPermissionIdsNull() {
        Role role = new Role();
        role.setId(2L);
        when(roleRepository.findPage(null, null, 1, 10))
                .thenReturn(new PageResult<>(Arrays.asList(role), 1L));
        RoleCO co = new RoleCO();
        when(roleAssembler.toCO(role)).thenReturn(co);

        PageRoleQuery query = new PageRoleQuery();

        PageResponse<RoleCO> response = pageRoleQueryExe.execute(query);

        assertFalse(
                response.getData().get(0).getPermissionIds() != null
                        && !response.getData().get(0).getPermissionIds().isEmpty());
        assertNull(response.getData().get(0).getPermissionIds());
    }
}
