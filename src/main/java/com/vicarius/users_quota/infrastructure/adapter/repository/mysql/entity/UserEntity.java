package com.vicarius.users_quota.infrastructure.adapter.repository.mysql.entity;

import com.vicarius.users_quota.domain.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public final class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "last_login_time_utc")
    private LocalDateTime lastLoginTimeUtc;

    public UserEntity() {
    }

    public UserEntity(
            String firstName,
            String lastName,
            LocalDateTime lastLoginTimeUtc
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastLoginTimeUtc = lastLoginTimeUtc;
    }

    public User toDomain() {
        return new User(id, firstName, lastName, lastLoginTimeUtc);
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(user.firstName(), user.lastName(), user.lastLoginTimeUtc());
    }
}
