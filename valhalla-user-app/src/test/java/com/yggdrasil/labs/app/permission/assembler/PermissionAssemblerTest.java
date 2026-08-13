package com.yggdrasil.labs.app.permission.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.yggdrasil.labs.app.permission.dto.co.PermissionCO;
import com.yggdrasil.labs.domain.permission.model.Permission;

/** {@link PermissionAssembler} 单元测试（使用 MapStruct 生成的 INSTANCE） */
class PermissionAssemblerTest {

    @Test
    void toCO_shouldMapFieldsAndConvertIdToString() {
        Permission permission = new Permission();
        permission.setId(123L);
        permission.setModule("user");
        permission.setResource("user");
        permission.setAction("read");
        permission.setPermissionCode("user:user:read");
        permission.setPermissionName("读取用户");
        permission.setDescription("D");
        permission.setMetadata("M");
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());

        PermissionCO co = PermissionAssembler.INSTANCE.toCO(permission);

        assertEquals("123", co.getId());
        assertEquals("user", co.getModule());
        assertEquals("user:user:read", co.getPermissionCode());
        assertEquals("读取用户", co.getPermissionName());
        assertEquals("D", co.getDescription());
        assertEquals("M", co.getMetadata());
        assertNull(co.getApiIds());
    }

    @Test
    void toCO_whenIdNull_shouldMapNull() {
        Permission permission = new Permission();

        PermissionCO co = PermissionAssembler.INSTANCE.toCO(permission);

        assertNull(co.getId());
    }

    @Test
    void toCO_whenNull_shouldReturnNull() {
        assertNull(PermissionAssembler.INSTANCE.toCO(null));
    }
}
