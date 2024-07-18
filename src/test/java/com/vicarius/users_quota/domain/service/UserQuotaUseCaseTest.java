package com.vicarius.users_quota.domain.service;

import com.vicarius.users_quota.domain.exception.QuotaExceededException;
import com.vicarius.users_quota.domain.model.User;
import com.vicarius.users_quota.domain.model.UserQuota;
import com.vicarius.users_quota.domain.port.in.UserQuotaUseCase;
import com.vicarius.users_quota.domain.port.out.QuotaRepository;
import com.vicarius.users_quota.domain.port.out.UserRepository;
import com.vicarius.users_quota.domain.port.out.UserRepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserQuotaUseCaseTest {

    @Mock
    private UserRepositoryFactory userRepositoryFactory;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuotaRepository quotaRepository;

    private UserQuotaUseCase userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepositoryFactory, quotaRepository, 10);
    }

    @Test
    @DisplayName("Given a valid user Id, when consumeQuota is called, then consume the quota")
    void givenValidUserId_shouldConsumeQuota() {
        User user = new User(
                "1",
                "Test",
                "01",
                LocalDateTime.now()
        );

        when(userRepositoryFactory.getUserRepository()).thenReturn(userRepository);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(quotaRepository.getQuota("1")).thenReturn(9);
        doNothing().when(quotaRepository).consumeQuota("1");

        userService.consumeQuota("1");

        verify(quotaRepository, times(1)).getQuota("1");
        verify(quotaRepository, times(1)).consumeQuota("1");
    }

    @Test
    @DisplayName("Given a user Id with no quota, when consumeQuota is called, then throw QuotaExceededException")
    void givenUserWithNoQuota_tryConsumeQuota_shouldThrowQuotaExceededException() {
        User user = new User(
                "1",
                "Test",
                "01",
                LocalDateTime.now()
        );

        when(userRepositoryFactory.getUserRepository()).thenReturn(userRepository);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(quotaRepository.getQuota("1")).thenReturn(11);

        assertThrows(QuotaExceededException.class, () -> userService.consumeQuota("1"));

        verify(quotaRepository, times(1)).getQuota("1");
        verify(quotaRepository, times(0)).consumeQuota("1");
    }

    @Test
    @DisplayName("When getUsersQuota is called, then return the user's quota")
    void getUsersQuota_shouldReturnUsersQuota() {
        var userQuotasMap = List.of(new UserQuota("1", 9));

        when(userRepositoryFactory.getUserRepository()).thenReturn(userRepository);
        when(quotaRepository.getUsersQuota()).thenReturn(userQuotasMap);

        var usersQuota = userService.getUsersQuota();

        assertNotNull(usersQuota);
        assertEquals(usersQuota.size(), 1);
        assertEquals(usersQuota.get(0).userId(), "1");
        assertEquals(usersQuota.get(0).quota(), 9);
        verify(quotaRepository, times(0)).consumeQuota(any());
        verify(quotaRepository, times(1)).getUsersQuota();
    }
}