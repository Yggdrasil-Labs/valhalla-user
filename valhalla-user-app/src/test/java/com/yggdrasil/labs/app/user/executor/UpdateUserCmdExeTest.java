package com.yggdrasil.labs.app.user.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.app.common.dto.enums.ErrorCode;
import com.yggdrasil.labs.app.user.dto.cmd.UpdateUserCmd;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.model.UserStatus;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

/** {@link UpdateUserCmdExe} 单元测试 */
@ExtendWith(MockitoExtension.class)
class UpdateUserCmdExeTest {

    @Mock private UserRepository userRepository;

    @InjectMocks private UpdateUserCmdExe updateUserCmdExe;

    private User baselineUser() {
        User user = new User();
        user.setId(42L);
        user.setUsername("alice");
        user.setEmail("old@example.com");
        user.setPhone("13800000000");
        user.setNickname("Alice");
        user.setAvatar("http://avatar/old.png");
        user.setStatus(UserStatus.ENABLED);
        user.setMetadata("{}");
        user.setVersion(1);
        return user;
    }

    private UpdateUserCmd buildCmd(Long id) {
        UpdateUserCmd cmd = new UpdateUserCmd();
        cmd.setId(id);
        return cmd;
    }

    @Test
    void execute_whenUserNotFound_shouldReturnFailure() {
        when(userRepository.findById(42L)).thenReturn(null);

        Response response = updateUserCmdExe.execute(buildCmd(42L));

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_NOT_FOUND.getErrCode(), response.getErrCode());
        verify(userRepository, never()).update(any());
    }

    @Test
    void execute_whenEmailChangedAndUsedByOther_shouldReturnFailure() {
        User user = baselineUser();
        when(userRepository.findById(42L)).thenReturn(user);
        UpdateUserCmd cmd = buildCmd(42L);
        cmd.setEmail("new@example.com");
        when(userRepository.existsByEmail("new@example.com")).thenReturn(true);

        Response response = updateUserCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_EMAIL_USED_BY_OTHER.getErrCode(), response.getErrCode());
        verify(userRepository, never()).update(any());
    }

    @Test
    void execute_whenPhoneChangedAndUsedByOther_shouldReturnFailure() {
        User user = baselineUser();
        when(userRepository.findById(42L)).thenReturn(user);
        UpdateUserCmd cmd = buildCmd(42L);
        cmd.setPhone("13900000000");
        when(userRepository.existsByPhone("13900000000")).thenReturn(true);

        Response response = updateUserCmdExe.execute(cmd);

        assertFalse(response.isSuccess());
        assertEquals(ErrorCode.B_USER_PHONE_USED_BY_OTHER.getErrCode(), response.getErrCode());
        verify(userRepository, never()).update(any());
    }

    @Test
    void execute_whenAllFieldsProvided_shouldUpdateAndPersist() {
        User user = baselineUser();
        when(userRepository.findById(42L)).thenReturn(user);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.existsByPhone("13900000000")).thenReturn(false);

        UpdateUserCmd cmd = buildCmd(42L);
        cmd.setEmail("new@example.com");
        cmd.setPhone("13900000000");
        cmd.setNickname("Alice2");
        cmd.setAvatar("http://avatar/new.png");
        cmd.setStatus(0);
        cmd.setMetadata("{\"k\":\"v\"}");
        cmd.setVersion(2);

        Response response = updateUserCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).update(captor.capture());
        User updated = captor.getValue();
        assertEquals("new@example.com", updated.getEmail());
        assertEquals("13900000000", updated.getPhone());
        assertEquals("Alice2", updated.getNickname());
        assertEquals("http://avatar/new.png", updated.getAvatar());
        assertEquals(UserStatus.DISABLED, updated.getStatus());
        assertEquals("{\"k\":\"v\"}", updated.getMetadata());
        assertEquals(2, updated.getVersion());
        // 基线字段不应被覆盖
        assertEquals("alice", updated.getUsername());
    }

    @Test
    void execute_whenEmailAndPhoneUnchanged_shouldSkipExistenceChecks() {
        User user = baselineUser();
        when(userRepository.findById(42L)).thenReturn(user);

        UpdateUserCmd cmd = buildCmd(42L);
        cmd.setEmail("old@example.com"); // 与基线相等
        cmd.setPhone("13800000000"); // 与基线相等
        cmd.setNickname("Alice3");

        Response response = updateUserCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).existsByPhone(anyString());
        verify(userRepository).update(any());
    }

    @Test
    void execute_whenOptionalFieldsNull_shouldKeepBaselineAndSkipChecks() {
        User user = baselineUser();
        when(userRepository.findById(42L)).thenReturn(user);

        // 不提供任何可选字段
        UpdateUserCmd cmd = buildCmd(42L);

        Response response = updateUserCmdExe.execute(cmd);

        assertTrue(response.isSuccess());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).existsByPhone(anyString());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).update(captor.capture());
        User updated = captor.getValue();
        // 所有可选字段保持基线值
        assertEquals("old@example.com", updated.getEmail());
        assertEquals("13800000000", updated.getPhone());
        assertEquals("Alice", updated.getNickname());
        assertEquals("http://avatar/old.png", updated.getAvatar());
        assertEquals(UserStatus.ENABLED, updated.getStatus());
        assertEquals("{}", updated.getMetadata());
        assertEquals(1, updated.getVersion());
    }
}
