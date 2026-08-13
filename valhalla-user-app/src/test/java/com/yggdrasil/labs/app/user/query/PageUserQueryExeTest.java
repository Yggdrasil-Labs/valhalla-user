package com.yggdrasil.labs.app.user.query;

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
import com.yggdrasil.labs.app.user.assembler.UserAssembler;
import com.yggdrasil.labs.app.user.dto.co.UserCO;
import com.yggdrasil.labs.app.user.dto.query.PageUserQuery;
import com.yggdrasil.labs.domain.common.PageResult;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

/** {@link PageUserQueryExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class PageUserQueryExeTest {

    @Mock private UserRepository userRepository;

    @Mock private UserAssembler userAssembler;

    @InjectMocks private PageUserQueryExe pageUserQueryExe;

    @Test
    void execute_shouldReturnPagedUsersWithRoleIdsConverted() {
        User user = new User();
        user.setId(1L);
        user.setRoleIds(Arrays.asList(1L, 2L));
        when(userRepository.findPage("u", 1, 1, 10))
                .thenReturn(new PageResult<>(Arrays.asList(user), 1L));
        UserCO co = new UserCO();
        when(userAssembler.toCO(user)).thenReturn(co);

        PageUserQuery query = new PageUserQuery();
        query.setUsername("u");
        query.setStatus(1);
        query.setPageNum(1);
        query.setPageSize(10);

        PageResponse<UserCO> response = pageUserQueryExe.execute(query);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals("1", response.getData().get(0).getRoleIds().get(0));
        assertEquals("2", response.getData().get(0).getRoleIds().get(1));
    }

    @Test
    void execute_whenRoleIdsNull_shouldLeaveCoRoleIdsNull() {
        User user = new User();
        user.setId(2L);
        when(userRepository.findPage(null, null, 1, 10))
                .thenReturn(new PageResult<>(Arrays.asList(user), 1L));
        UserCO co = new UserCO();
        when(userAssembler.toCO(user)).thenReturn(co);

        PageUserQuery query = new PageUserQuery();

        PageResponse<UserCO> response = pageUserQueryExe.execute(query);

        assertNull(response.getData().get(0).getRoleIds());
    }
}
