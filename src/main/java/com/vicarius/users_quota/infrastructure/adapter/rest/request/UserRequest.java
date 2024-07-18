package com.vicarius.users_quota.infrastructure.adapter.rest.request;

import com.vicarius.users_quota.common.DateTimeUtils;
import com.vicarius.users_quota.domain.model.User;

import java.time.LocalDateTime;

public record UserRequest(
        String firstName,
        String lastName,
        String lastLoginTimeUtc
) {
    public User toDomain(UserRequest request) {
        LocalDateTime lastLoginTimeUtc = DateTimeUtils.parse(request.lastLoginTimeUtc());
        return new User(null, request.firstName(), request.lastName(), lastLoginTimeUtc);
    }
}
