package com.vicarius.users_quota.infrastructure.adapter.repository.elasticsearch;

import com.vicarius.users_quota.domain.model.User;
import com.vicarius.users_quota.domain.port.out.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ElasticSearchUserRepository implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUserRepository.class);

    @Override
    public User save(User user) {
        logger.info("Saving user to ElasticSearch: " + user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        logger.info("Finding user in ElasticSearch with ID: " + userId);
        User dummyUser = new User(userId, "Dummy", "User", LocalDateTime.now());
        return Optional.of(dummyUser);
    }

    @Override
    public void deleteById(String userId) {
        logger.info("Deleting user from ElasticSearch with ID: " + userId);
    }
}
