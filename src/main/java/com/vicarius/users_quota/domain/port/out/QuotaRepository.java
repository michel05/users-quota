package com.vicarius.users_quota.domain.port.out;

import java.util.Map;

public interface QuotaRepository {
    void consumeQuota(String userId);

    int getQuota(String userId);

    Map<String, Integer> getUsersQuota();
}
