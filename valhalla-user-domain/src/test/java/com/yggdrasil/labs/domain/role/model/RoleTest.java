package com.yggdrasil.labs.domain.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/** {@link Role} 单元测试 */
class RoleTest {

    @Test
    void canBeDeleted_shouldBeFalseForSystemRole() {
        Role role = new Role();
        role.setIsSystem(true);
        assertFalse(role.canBeDeleted());
    }

    @Test
    void canBeDeleted_shouldBeTrueForNonSystemRole() {
        Role role = new Role();
        role.setIsSystem(false);
        assertTrue(role.canBeDeleted());
    }

    @Test
    void canBeDeleted_shouldBeTrueWhenIsSystemNull() {
        Role role = new Role();
        role.setIsSystem(null);
        assertTrue(role.canBeDeleted());
    }

    @Test
    void assignPermissions_withNonNullList_shouldCopyElements() {
        Role role = new Role();
        List<Long> permissionIds = new ArrayList<>(Arrays.asList(10L, 20L));
        role.assignPermissions(permissionIds);

        assertEquals(permissionIds, role.getPermissionIds());
        permissionIds.add(30L);
        assertEquals(2, role.getPermissionIds().size());
    }

    @Test
    void assignPermissions_withNull_shouldSetEmptyList() {
        Role role = new Role();
        role.assignPermissions(null);
        assertEquals(0, role.getPermissionIds().size());
    }
}
