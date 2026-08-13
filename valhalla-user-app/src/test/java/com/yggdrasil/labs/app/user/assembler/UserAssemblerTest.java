package com.yggdrasil.labs.app.user.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.yggdrasil.labs.app.user.dto.co.UserCO;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.model.UserStatus;

/** {@link UserAssembler} 单元测试（使用 MapStruct 生成的 INSTANCE） */
class UserAssemblerTest {

    @Test
    void toCO_shouldMapIdAndStatus() {
        User user = new User();
        user.setId(42L);
        user.setUsername("alice");
        user.setStatus(UserStatus.ENABLED);

        UserCO co = UserAssembler.INSTANCE.toCO(user);

        assertEquals("42", co.getId());
        assertEquals(UserStatus.ENABLED.getCode(), co.getStatus());
        assertEquals("alice", co.getUsername());
        assertNull(co.getRoleIds());
    }

    @Test
    void toCO_whenStatusNull_shouldMapNull() {
        User user = new User();
        user.setStatus(null);

        UserCO co = UserAssembler.INSTANCE.toCO(user);

        assertNull(co.getStatus());
    }

    @Test
    void toCO_whenIdNull_shouldMapNull() {
        User user = new User();

        UserCO co = UserAssembler.INSTANCE.toCO(user);

        assertNull(co.getId());
    }

    @Test
    void toCO_whenNull_shouldReturnNull() {
        assertNull(UserAssembler.INSTANCE.toCO(null));
    }
}
