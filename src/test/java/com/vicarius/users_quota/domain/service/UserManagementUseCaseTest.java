package com.vicarius.users_quota.domain.service;

import com.vicarius.users_quota.domain.exception.UserNotFoundException;
import com.vicarius.users_quota.domain.model.User;
import com.vicarius.users_quota.domain.port.in.UserManagementUseCase;
import com.vicarius.users_quota.domain.port.out.QuotaRepository;
import com.vicarius.users_quota.domain.port.out.UserRepository;
import com.vicarius.users_quota.domain.port.out.UserRepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserManagementUseCaseTest {

    @Mock
    private UserRepositoryFactory userRepositoryFactory;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuotaRepository quotaRepository;

    private UserManagementUseCase userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepositoryFactory, quotaRepository, 10);
    }

    @Test
    @DisplayName("Given a valid user Id, when getUser is called, then return the user")
    public void testGetUserById() {
        User user = new User(
                "1",
                "Test",
                "01",
                LocalDateTime.now()
        );

        when(userRepositoryFactory.getUserRepository())
                .thenReturn(userRepository);
        when(userRepository.findById("1"))
                .thenReturn(Optional.of(user));

        User returnedUser = userService.getUser("1");

        assertNotNull(returnedUser);
        assertEquals(user.firstName(), returnedUser.firstName());
        assertEquals(user.lastName(), returnedUser.lastName());
    }

    @Test
    @DisplayName("Given an invalid user Id, when getUser is called, then throw UserNotFound")
    void testGetUserByIdAndThrowUserNotFound() {
        when(userRepositoryFactory.getUserRepository())
                .thenReturn(userRepository);
        when(userRepository.findById("1"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser("1"));

        verify(userRepository, Mockito.times(1)).findById("1");
    }

    @Test
    @DisplayName("Given a valid user, when createUser is called, then return the created user")
    void testCreateUser() {
        User user = new User(
                "1",
                "Test",
                "01",
                LocalDateTime.now()
        );

        when(userRepositoryFactory.getUserRepository())
                .thenReturn(userRepository);
        when(userRepository.save(user))
                .thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.firstName(), createdUser.firstName());
        assertEquals(user.lastName(), createdUser.lastName());
    }

    @Test
    @DisplayName("Given a valid user, when updateUser is called, then return the created user")
    void testUpdateUser() {
        User user = new User(
                "1",
                "Test",
                "01",
                LocalDateTime.now()
        );

        User updatedUser = new User(
                "1",
                "Updated Test",
                "01",
                LocalDateTime.now()
        );

        when(userRepositoryFactory.getUserRepository())
                .thenReturn(userRepository);
        when(userRepository.findById("1"))
                .thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser))
                .thenReturn(updatedUser);

        User result = userService.updateUser("1", updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.id(), "1");
        assertEquals(updatedUser.firstName(), result.firstName());
        assertEquals(updatedUser.lastName(), result.lastName());
    }

    @Test
    @DisplayName("Given an invalid user, when updateUser is called, then throw UserNotFound")
    void testUpdateInvalidUserAndThrowUserNotFoundException() {
        User updatedUser = new User(
                "1",
                "Updated Test",
                "01",
                LocalDateTime.now()
        );

        when(userRepositoryFactory.getUserRepository())
                .thenReturn(userRepository);
        when(userRepository.findById("1"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser("1", updatedUser));

        verify(userRepository, Mockito.times(1)).findById("1");
    }

    @Test
    @DisplayName("Given a valid user Id, when deleteUser is called, then delete the user")
    void testDeleteUser() {
        User user = new User(
                "1",
                "Test",
                "01",
                LocalDateTime.now()
        );

        when(userRepositoryFactory.getUserRepository())
                .thenReturn(userRepository);
        when(userRepository.findById("1"))
                .thenReturn(Optional.of(user));

        userService.deleteUser("1");

        verify(userRepository, Mockito.times(1)).deleteById("1");
    }
}