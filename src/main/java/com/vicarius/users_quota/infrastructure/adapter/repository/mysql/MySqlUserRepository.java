package com.vicarius.users_quota.infrastructure.adapter.repository.mysql;

import com.vicarius.users_quota.domain.model.User;
import com.vicarius.users_quota.domain.port.out.UserRepository;
import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MySqlUserRepository implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(MySqlUserRepository.class);

    private final JpaUserRepository jpaUserRepository;

    public MySqlUserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        logger.info("Saving user to MySql: " + user);
        return jpaUserRepository.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public Optional<User> findById(String userId) {
        logger.info("Finding user in MySql with ID: " + userId);
        return jpaUserRepository.findById(userId).map(UserEntity::toDomain);
    }

    @Override
    public void deleteById(String userId) {
        logger.info("Deleting user from MySql with ID: " + userId);
        jpaUserRepository.deleteById(userId);
    }
}
