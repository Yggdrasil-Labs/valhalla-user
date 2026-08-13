package com.yggdrasil.labs.app.user.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.app.user.dto.cmd.AssignUserRoleCmd;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

/** {@link AssignUserRoleCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class AssignUserRoleCmdExeTest {

    @Mock private UserRepository userRepository;

    @InjectMocks private AssignUserRoleCmdExe assignUserRoleCmdExe;

    private AssignUserRoleCmd buildCmd(Long userId, List<Long> roleIds) {
        AssignUserRoleCmd cmd = new AssignUserRoleCmd();
        cmd.setUserId(userId);
        cmd.setRoleIds(roleIds);
        return cmd;
    }

    @Test
    void execute_whenUserNotFound_shouldReturnFailure() {
        when(userRepository.findById(1L)).thenReturn(null);

        Response response = assignUserRoleCmdExe.execute(buildCmd(1L, Arrays.asList(1L, 2L)));

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(userRepository, never()).update(any());
    }

    @Test
    void execute_whenUserExists_shouldAssignRolesAndUpdate() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(user);
        List<Long> roleIds = Arrays.asList(1L, 2L);

        Response response = assignUserRoleCmdExe.execute(buildCmd(1L, roleIds));

        assertTrue(response.isSuccess());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).update(captor.capture());
        assertEquals(roleIds, captor.getValue().getRoleIds());
    }
}
