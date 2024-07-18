package com.vicarius.users_quota.infrastructure.adapter.repository;

import com.vicarius.users_quota.domain.port.out.DateTimeProvider;
import com.vicarius.users_quota.infrastructure.adapter.repository.elasticsearch.ElasticSearchUserRepository;
import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.MySqlUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserRepositoryFactoryImplTest {
    @Mock
    private MySqlUserRepository mySqlUserRepository;

    @Mock
    private ElasticSearchUserRepository elasticSearchUserRepository;

    @Mock
    private DateTimeProvider dateTimeProvider;

    private UserRepositoryFactoryImpl userRepositoryFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given the time is between 9 and 17, when getUserRepository is called, then return MySqlUserRepository")
    void getUserRepository_returnsMySqlUserRepository_whenTimeIsBetween9And17() {
        userRepositoryFactory = new UserRepositoryFactoryImpl(mySqlUserRepository, elasticSearchUserRepository, dateTimeProvider, 17, 9);
        LocalDateTime dateTime = LocalDateTime.of(2024, 1, 1, 10, 0);
        when(dateTimeProvider.now()).thenReturn(dateTime);

        assertTrue(userRepositoryFactory.getUserRepository() instanceof MySqlUserRepository);
    }

    @Test
    @DisplayName("Given the time is between 17 and 9, when getUserRepository is called, then return ElasticSearchUserRepository")
    void getUserRepository_returnsElasticSearchUserRepository_whenTimeIsNotBetween9And17() {
        userRepositoryFactory = new UserRepositoryFactoryImpl(mySqlUserRepository, elasticSearchUserRepository, dateTimeProvider, 17, 9);
        LocalDateTime dateTime =LocalDateTime.of(2022, 1, 1, 18, 0);
        when(dateTimeProvider.now()).thenReturn(dateTime);

        assertTrue(userRepositoryFactory.getUserRepository() instanceof ElasticSearchUserRepository);
    }

    @Test
    @DisplayName("Given the time is between 5 and 1, when getUserRepository is called, then return MySqlUserRepository")
    void getUserRepository_returnsMySqlUserRepository_whenTimeIsBetween5And1() {
        userRepositoryFactory = new UserRepositoryFactoryImpl(mySqlUserRepository, elasticSearchUserRepository, dateTimeProvider, 1, 9);
        LocalDateTime dateTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        when(dateTimeProvider.now()).thenReturn(dateTime);

        assertTrue(userRepositoryFactory.getUserRepository() instanceof MySqlUserRepository);
    }

    @Test
    @DisplayName("Given the time is between 1 and 5, when getUserRepository is called, then return ElasticSearchUserRepository")
    void getUserRepository_returnsElasticSearchUserRepository_whenTimeIsNotBetween5And1() {
        userRepositoryFactory = new UserRepositoryFactoryImpl(mySqlUserRepository, elasticSearchUserRepository, dateTimeProvider, 1, 9);
        LocalDateTime dateTime =LocalDateTime.of(2022, 1, 1, 2, 0);
        when(dateTimeProvider.now()).thenReturn(dateTime);

        assertTrue(userRepositoryFactory.getUserRepository() instanceof ElasticSearchUserRepository);
    }
}