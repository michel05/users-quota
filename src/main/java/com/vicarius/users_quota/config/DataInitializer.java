package com.vicarius.users_quota.config;

import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.JpaUserRepository;
import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            jpaUserRepository.save(new UserEntity("John", "Doe", LocalDateTime.now()));
            jpaUserRepository.save(new UserEntity("Jane", "Doe", LocalDateTime.now()));
        };
    }
}