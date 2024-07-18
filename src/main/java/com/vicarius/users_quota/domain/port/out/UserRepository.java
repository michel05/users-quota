package com.vicarius.users_quota.domain.port.out;

import com.vicarius.users_quota.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(String userId);

    void deleteById(String userId);
}
