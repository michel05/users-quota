package com.vicarius.users_quota.domain.port.out;

import java.time.LocalDateTime;

public interface DateTimeProvider {

    LocalDateTime now();
}
