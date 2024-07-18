package com.vicarius.users_quota.infrastructure.adapter.rest.response;

import com.vicarius.users_quota.common.DateTimeUtils;
import com.vicarius.users_quota.domain.model.User;

public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String lastLoginTimeUtc
) {
    public static UserResponse fromDomain(User user) {
        String lastLoginTimeUtc = DateTimeUtils.format(user.lastLoginTimeUtc());
        return new UserResponse(user.id(), user.firstName(), user.lastName(), lastLoginTimeUtc);
    }
}
