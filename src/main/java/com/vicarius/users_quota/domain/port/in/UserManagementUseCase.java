package com.vicarius.users_quota.domain.port.in;

import com.vicarius.users_quota.domain.model.User;

public interface UserManagementUseCase {

    User createUser(final User user);

    User getUser(final String userId);

    User updateUser(final String userId, final User updateUser);

    void deleteUser(String userId);
}
