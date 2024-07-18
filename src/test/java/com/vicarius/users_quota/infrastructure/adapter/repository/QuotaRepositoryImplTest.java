package com.vicarius.users_quota.infrastructure.adapter.repository;

import com.vicarius.users_quota.domain.port.out.QuotaRepository;
import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.JpaUserQuotaRepository;
import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.entity.UserQuotaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuotaRepositoryImplTest {

    @Mock
    private JpaUserQuotaRepository jpaUserQuotaRepository;
    private QuotaRepository quotaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quotaRepository = new QuotaRepositoryImpl(jpaUserQuotaRepository);
    }

    @Test
    @DisplayName("When consumeQuota is called 5 times, then return 5 as result")
    void whenConsumeQuotaIsCalledFiveTimes_shouldReturnFiveAsResult() {

        when(jpaUserQuotaRepository.findById("1")).thenReturn(
                Optional.of(new UserQuotaEntity("1", 0)),
                Optional.of(new UserQuotaEntity("1", 1)),
                Optional.of(new UserQuotaEntity("1", 2)),
                Optional.of(new UserQuotaEntity("1", 3)),
                Optional.of(new UserQuotaEntity("1", 4)));

        when(jpaUserQuotaRepository.save(any())).thenReturn(new UserQuotaEntity("1", 0));

        for (int i = 0; i < 5; i++) {
            quotaRepository.consumeQuota("1");
        }

        verify(jpaUserQuotaRepository, times(5)).findById("1");
        verify(jpaUserQuotaRepository, times(5)).save(any());
    }

    @Test
    @DisplayName("Given 3 different Users with 5 quotas each, when getUsersQuota is called, then return their quota")
    void whenGetUsersQuota_shouldReturnTheirQuota() {

        when(jpaUserQuotaRepository.findAll()).thenReturn(List.of(
                new UserQuotaEntity("1", 5),
                new UserQuotaEntity("2", 5),
                new UserQuotaEntity("3", 5)
        ));

        var result = quotaRepository.getUsersQuota();

        assertEquals(3, result.size());
        assertEquals(5, result.stream().findFirst().get().quota());
        assertEquals(5, result.stream().findAny().get().quota());
    }
}