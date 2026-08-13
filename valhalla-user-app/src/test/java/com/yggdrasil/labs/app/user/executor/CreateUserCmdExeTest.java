package com.yggdrasil.labs.app.user.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
import com.yggdrasil.labs.app.user.dto.cmd.CreateUserCmd;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.model.UserStatus;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

/** {@link CreateUserCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class CreateUserCmdExeTest {

    @Mock private UserRepository userRepository;

    @InjectMocks private CreateUserCmdExe createUserCmdExe;

    private CreateUserCmd buildCmd() {
        CreateUserCmd cmd = new CreateUserCmd();
        cmd.setUsername("alice");
        cmd.setEmail("alice@example.com");
        cmd.setPhone("13800000000");
        cmd.setNickname("Alice");
        return cmd;
    }

    @Test
    void execute_whenUsernameExists_shouldReturnFailure() {
        when(userRepository.existsByUsername("alice")).thenReturn(true);

        Response response = createUserCmdExe.execute(buildCmd());

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_USERNAME_EXISTS.getErrCode(), response.getErrCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    void execute_whenEmailExists_shouldReturnFailure() {
        when(userRepository.existsByEmail("alice@example.com")).thenReturn(true);

        Response response = createUserCmdExe.execute(buildCmd());

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_EMAIL_EXISTS.getErrCode(), response.getErrCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    void execute_whenPhoneExists_shouldReturnFailure() {
        when(userRepository.existsByPhone("13800000000")).thenReturn(true);

        Response response = createUserCmdExe.execute(buildCmd());

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_PHONE_EXISTS.getErrCode(), response.getErrCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    void execute_whenValid_shouldSaveUserWithDefaults() {
        Response response = createUserCmdExe.execute(buildCmd());

        assertTrue(response.isSuccess());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());
        User saved = captor.getValue();
        assertEquals("alice", saved.getUsername());
        assertEquals("alice@example.com", saved.getEmail());
        assertEquals(UserStatus.ENABLED, saved.getStatus());
        assertEquals("ADMIN", saved.getSource());
        assertEquals("", saved.getRegisterType());
        assertNull(saved.getRoleIds());
    }

    @Test
    void execute_whenStatusProvided_shouldUseGivenStatus() {
        CreateUserCmd cmd = buildCmd();
        cmd.setStatus(0);

        createUserCmdExe.execute(cmd);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals(UserStatus.DISABLED, captor.getValue().getStatus());
    }

    @Test
    void execute_whenRoleIdsProvided_shouldAssignRoles() {
        CreateUserCmd cmd = buildCmd();
        List<Long> roleIds = Arrays.asList(1L, 2L);
        cmd.setRoleIds(roleIds);

        createUserCmdExe.execute(cmd);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals(roleIds, captor.getValue().getRoleIds());
    }

    @Test
    void execute_whenOptionalFieldsNullAndRoleIdsEmpty_shouldCreateWithDefaults() {
        CreateUserCmd cmd = new CreateUserCmd();
        cmd.setUsername("alice");
        cmd.setRoleIds(List.of());

        Response response = createUserCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertNull(saved.getEmail());
        assertNull(saved.getPhone());
        assertNull(saved.getRoleIds());
    }
}
