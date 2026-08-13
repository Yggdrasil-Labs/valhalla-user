package com.yggdrasil.labs.app.role.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.yggdrasil.labs.app.role.dto.co.RoleCO;
import com.yggdrasil.labs.domain.role.model.Role;

/** {@link RoleAssembler} 单元测试（使用 MapStruct 生成的 INSTANCE） */
class RoleAssemblerTest {

    @Test
    void toCO_shouldMapFieldsAndConvertIdToString() {
        Role role = new Role();
        role.setId(123L);
        role.setRoleCode("RC");
        role.setRoleName("RN");
        role.setDescription("D");
        role.setIsSystem(true);
        role.setMetadata("M");
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());

        RoleCO co = RoleAssembler.INSTANCE.toCO(role);

        assertEquals("123", co.getId());
        assertEquals("RC", co.getRoleCode());
        assertEquals("RN", co.getRoleName());
        assertEquals("D", co.getDescription());
        assertTrue(co.getIsSystem());
        assertEquals("M", co.getMetadata());
        assertNull(co.getPermissionIds());
    }

    @Test
    void toCO_whenIdNull_shouldMapNull() {
        Role role = new Role();

        RoleCO co = RoleAssembler.INSTANCE.toCO(role);

        assertNull(co.getId());
    }

    @Test
    void toCO_whenNull_shouldReturnNull() {
        assertNull(RoleAssembler.INSTANCE.toCO(null));
    }
}
