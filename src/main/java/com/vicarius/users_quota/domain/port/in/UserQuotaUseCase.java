package com.vicarius.users_quota.domain.port.in;

import com.vicarius.users_quota.domain.model.UserQuota;

import java.util.List;

public interface UserQuotaUseCase {

    void consumeQuota(String userId);

    List<UserQuota> getUsersQuota();
}
