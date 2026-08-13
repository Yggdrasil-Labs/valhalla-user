package com.yggdrasil.labs.domain.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

/** {@link User} 单元测试 */
class UserTest {

    @Test
    void enable_shouldSetStatusToEnabled() {
        User user = new User();
        user.disable();
        user.enable();
        assertTrue(user.isEnabled());
        assertEquals(UserStatus.ENABLED, user.getStatus());
    }

    @Test
    void disable_shouldSetStatusToDisabled() {
        User user = new User();
        user.enable();
        user.disable();
        assertFalse(user.isEnabled());
        assertEquals(UserStatus.DISABLED, user.getStatus());
    }

    @Test
    void isEnabled_shouldBeFalseWhenStatusNull() {
        User user = new User();
        user.setStatus(null);
        assertFalse(user.isEnabled());
    }

    @Test
    void assignRoles_withNonNullList_shouldCopyElements() {
        User user = new User();
        List<Long> roleIds = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        user.assignRoles(roleIds);

        assertEquals(roleIds, user.getRoleIds());
        // 应为深拷贝，修改原列表不影响实体内部副本
        roleIds.add(4L);
        assertEquals(3, user.getRoleIds().size());
    }

    @Test
    void assignRoles_withNull_shouldSetEmptyList() {
        User user = new User();
        user.assignRoles(null);
        assertEquals(0, user.getRoleIds().size());
    }

    @Test
    void assignRoles_withEmptyList_shouldSetEmptyList() {
        User user = new User();
        user.assignRoles(Collections.emptyList());
        assertEquals(0, user.getRoleIds().size());
    }
}
