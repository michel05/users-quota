package com.vicarius.users_quota.infrastructure.adapter.datetimeprovider;

import com.vicarius.users_quota.domain.port.out.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class GenericDateTimeProvider implements DateTimeProvider {

    @Override
    public LocalDateTime now() {
        return ZonedDateTime.now(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * DateTimeProvider abstraction provides the possibility of returning the current local date and time
     * based on different behaviors, such as user location per example.

     public LocalDateTime now(String userLocation) {
        return ZonedDateTime.now(getZoneIdByUserLocation(userLocation)).toLocalDateTime();
     }
     */
}
