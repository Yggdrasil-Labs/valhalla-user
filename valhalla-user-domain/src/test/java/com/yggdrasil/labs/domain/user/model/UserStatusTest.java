package com.yggdrasil.labs.domain.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** {@link UserStatus} 单元测试 */
class UserStatusTest {

    @Test
    void fromCode_shouldMapKnownCodes() {
        assertEquals(UserStatus.ENABLED, UserStatus.fromCode(1));
        assertEquals(UserStatus.DISABLED, UserStatus.fromCode(0));
    }

    @Test
    void fromCode_withNull_shouldReturnNull() {
        assertNull(UserStatus.fromCode(null));
    }

    @Test
    void fromCode_withUnknownCode_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> UserStatus.fromCode(99));
    }

    @Test
    void getCode_shouldReturnPersistedValue() {
        assertEquals(1, UserStatus.ENABLED.getCode());
        assertEquals(0, UserStatus.DISABLED.getCode());
    }
}
