package com.yggdrasil.labs.domain.permission.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/** {@link Permission} 单元测试 */
class PermissionTest {

    @Test
    void generatePermissionCode_withAllParts_shouldProduceModuleResourceAction() {
        Permission permission = new Permission();
        permission.setModule("user");
        permission.setResource("account");
        permission.setAction("read");

        permission.generatePermissionCode();

        assertEquals("user:account:read", permission.getPermissionCode());
    }

    @Test
    void generatePermissionCode_withMissingParts_shouldNotSetCode() {
        Permission permission = new Permission();
        permission.setModule("user");
        permission.setResource(null);
        permission.setAction("read");

        permission.generatePermissionCode();

        assertNull(permission.getPermissionCode());
    }

    @Test
    void assignApis_withNonNullList_shouldCopyElements() {
        Permission permission = new Permission();
        List<Long> apiIds = new ArrayList<>(Arrays.asList(100L, 200L));
        permission.assignApis(apiIds);

        assertEquals(apiIds, permission.getApiIds());
        apiIds.add(300L);
        assertEquals(2, permission.getApiIds().size());
    }

    @Test
    void assignApis_withNull_shouldSetEmptyList() {
        Permission permission = new Permission();
        permission.assignApis(null);
        assertEquals(0, permission.getApiIds().size());
    }
}
