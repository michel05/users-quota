package com.vicarius.users_quota.domain.port.out;

import com.vicarius.users_quota.domain.model.UserQuota;

import java.util.List;

public interface QuotaRepository {
    void consumeQuota(String userId);

    int getQuota(String userId);

    List<UserQuota> getUsersQuota();
}
