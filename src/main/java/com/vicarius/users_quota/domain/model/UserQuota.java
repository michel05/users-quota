package com.vicarius.users_quota.domain.model;

public record UserQuota(
        String userId,
        int quota
) {

}
