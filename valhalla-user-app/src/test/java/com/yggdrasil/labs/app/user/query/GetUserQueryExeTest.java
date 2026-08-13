package com.yggdrasil.labs.app.user.query;

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
import com.yggdrasil.labs.app.user.assembler.UserAssembler;
import com.yggdrasil.labs.app.user.dto.co.UserCO;
import com.yggdrasil.labs.app.user.dto.query.GetUserQuery;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

/** {@link GetUserQueryExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class GetUserQueryExeTest {

    @Mock private UserRepository userRepository;

    @Mock private UserAssembler userAssembler;

    @InjectMocks private GetUserQueryExe getUserQueryExe;

    @Test
    void execute_whenNotFound_shouldReturnFailure() {
        GetUserQuery query = new GetUserQuery();
        query.setId(1L);
        when(userRepository.findById(1L)).thenReturn(null);

        SingleResponse<UserCO> response = getUserQueryExe.execute(query);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_NOT_FOUND.getErrCode(), response.getErrCode());
    }

    @Test
    void execute_whenFound_shouldReturnCoWithRoleIds() {
        User user = new User();
        user.setId(5L);
        user.setRoleIds(Arrays.asList(9L));
        when(userRepository.findById(5L)).thenReturn(user);
        UserCO co = new UserCO();
        when(userAssembler.toCO(user)).thenReturn(co);

        GetUserQuery query = new GetUserQuery();
        query.setId(5L);

        SingleResponse<UserCO> response = getUserQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertEquals("9", response.getData().getRoleIds().get(0));
    }

    @Test
    void execute_whenFoundWithNullRoleIds_shouldReturnCoWithoutRoleIds() {
        User user = new User();
        user.setId(7L);
        user.setRoleIds(null);
        when(userRepository.findById(7L)).thenReturn(user);
        UserCO co = new UserCO();
        when(userAssembler.toCO(user)).thenReturn(co);

        GetUserQuery query = new GetUserQuery();
        query.setId(7L);

        SingleResponse<UserCO> response = getUserQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertNull(response.getData().getRoleIds());
    }
}
