package com.yggdrasil.labs.app.user.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.app.user.dto.cmd.DeleteUserCmd;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

/** {@link DeleteUserCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class DeleteUserCmdExeTest {

    @Mock private UserRepository userRepository;

    @InjectMocks private DeleteUserCmdExe deleteUserCmdExe;

    private DeleteUserCmd buildCmd(Long id) {
        DeleteUserCmd cmd = new DeleteUserCmd();
        cmd.setId(id);
        return cmd;
    }

    @Test
    void execute_whenUserNotFound_shouldReturnFailure() {
        when(userRepository.findById(1L)).thenReturn(null);

        Response response = deleteUserCmdExe.execute(buildCmd(1L));

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(userRepository, never()).delete(anyLong());
    }

    @Test
    void execute_whenUserExists_shouldDelete() {
        when(userRepository.findById(1L)).thenReturn(new User());

        Response response = deleteUserCmdExe.execute(buildCmd(1L));

        assertTrue(response.isSuccess());
        verify(userRepository).delete(1L);
    }
}
