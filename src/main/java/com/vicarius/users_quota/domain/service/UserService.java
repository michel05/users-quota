package com.vicarius.users_quota.domain.service;

import com.vicarius.users_quota.domain.exception.QuotaExceededException;
import com.vicarius.users_quota.domain.exception.UserNotFoundException;
import com.vicarius.users_quota.domain.model.User;
import com.vicarius.users_quota.domain.model.UserQuota;
import com.vicarius.users_quota.domain.port.in.UserManagementUseCase;
import com.vicarius.users_quota.domain.port.in.UserQuotaUseCase;
import com.vicarius.users_quota.domain.port.out.QuotaRepository;
import com.vicarius.users_quota.domain.port.out.UserRepositoryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserManagementUseCase, UserQuotaUseCase {

    private final UserRepositoryFactory userRepositoryFactory;
    private final QuotaRepository quotaRepository;
    private final int maxUserQuota;

    public UserService(
            UserRepositoryFactory userRepositoryFactory,
            QuotaRepository quotaRepository,
            @Value("${app.max-user-quota}") int maxUserQuota
    ) {
        this.userRepositoryFactory = userRepositoryFactory;
        this.quotaRepository = quotaRepository;
        this.maxUserQuota = maxUserQuota;
    }

    @Override
    public User createUser(final User user) {
        return userRepositoryFactory.getUserRepository().save(user);
    }

    @Override
    public User getUser(final String userId) {
        return userRepositoryFactory.getUserRepository().findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
    }

    @Override
    public User updateUser(final String userId, final User updatedUser) {
        var user = getUser(userId);
        return userRepositoryFactory.getUserRepository().save(
                new User(
                        user.id(),
                        updatedUser.firstName(),
                        updatedUser.lastName(),
                        updatedUser.lastLoginTimeUtc()
                )
        );
    }

    @Override
    public void deleteUser(final String userId) {
        userRepositoryFactory.getUserRepository().deleteById(userId);
    }

    @Override
    public synchronized void consumeQuota(final String userId) {
        var user = getUser(userId);
        int currentQuota = quotaRepository.getQuota(user.id());
        if (currentQuota >= maxUserQuota) {
            throw new QuotaExceededException("User has exceeded the maximum allowed quota.");
        }
        quotaRepository.consumeQuota(user.id());
    }

    @Override
    public List<UserQuota> getUsersQuota() {
        return quotaRepository.getUsersQuota().entrySet().stream()
                .map(entry -> new UserQuota(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
