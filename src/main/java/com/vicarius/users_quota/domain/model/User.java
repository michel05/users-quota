package com.vicarius.users_quota.domain.model;

import java.time.LocalDateTime;

public record User(
        String id,
        String firstName,
        String lastName,
        LocalDateTime lastLoginTimeUtc
) {
}
