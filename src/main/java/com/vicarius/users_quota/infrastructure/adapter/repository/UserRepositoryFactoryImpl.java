package com.vicarius.users_quota.infrastructure.adapter.repository;

import com.vicarius.users_quota.domain.port.out.DateTimeProvider;
import com.vicarius.users_quota.domain.port.out.UserRepository;
import com.vicarius.users_quota.domain.port.out.UserRepositoryFactory;
import com.vicarius.users_quota.infrastructure.adapter.repository.elasticsearch.ElasticSearchUserRepository;
import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.MySqlUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class UserRepositoryFactoryImpl implements UserRepositoryFactory {

    private final MySqlUserRepository mySqlUserRepository;
    private final ElasticSearchUserRepository elasticSearchUserRepository;
    private final DateTimeProvider dateTimeProvider;

    private final LocalTime switchToElastic;
    private final LocalTime switchToMySql;

    public UserRepositoryFactoryImpl(
            MySqlUserRepository mySqlUserRepository,
            ElasticSearchUserRepository elasticSearchUserRepository,
            DateTimeProvider dateTimeProvider,
            @Value("${app.db.switch-to-elasticsearch}") int switchToElastic,
            @Value("${app.db.switch-to-mysql}") int switchToMySql) {
        this.mySqlUserRepository = mySqlUserRepository;
        this.elasticSearchUserRepository = elasticSearchUserRepository;
        this.dateTimeProvider = dateTimeProvider;
        this.switchToElastic = LocalTime.of(switchToElastic, 0);
        this.switchToMySql =  LocalTime.of(switchToMySql, 0);
    }

    @Override
    public UserRepository getUserRepository() {
        LocalTime now = LocalTime.from(dateTimeProvider.now());
        if(switchToElastic.isAfter(switchToMySql)) {
            if (now.isAfter(switchToElastic) || now.isBefore(switchToMySql)) {
                return elasticSearchUserRepository;
            } else {
                return mySqlUserRepository;
            }
        } else {
            if (now.isAfter(switchToElastic) && now.isBefore(switchToMySql)) {
                return elasticSearchUserRepository;
            } else {
                return mySqlUserRepository;
            }
        }
    }
}
