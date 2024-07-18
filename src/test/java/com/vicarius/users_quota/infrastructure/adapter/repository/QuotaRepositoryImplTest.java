package com.vicarius.users_quota.infrastructure.adapter.repository;

import com.vicarius.users_quota.domain.port.out.QuotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuotaRepositoryImplTest {

    private QuotaRepository quotaRepository;

    @BeforeEach
    void setUp() {
        quotaRepository = new QuotaRepositoryImpl();
    }

    @Test
    @DisplayName("When consumeQuota is called 5 times, then return 5 as result")
    void whenConsumeQuotaIsCalledFiveTimes_shouldReturnFiveAsResult() {
        for (int i = 0; i < 5; i++) {
            quotaRepository.consumeQuota("1");
        }

        var result = quotaRepository.getQuota("1");

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Given 3 different Users consume quota 5 times each, when getUsersQuota is called, then return their quota")
    void whenDifferentUsersConsumeQuotaFiveTimesEach_shouldReturnTheirQuota() {
        var users = new String[]{"1", "2", "3"};

        for (var user : users) {
            for (int i = 0; i < 5; i++) {
                quotaRepository.consumeQuota(user);
            }
        }

        var result = quotaRepository.getUsersQuota();

        assertEquals(3, result.size());
        assertEquals(5, result.get("1"));
        assertEquals(5, result.values().stream().findAny().get());
    }
}