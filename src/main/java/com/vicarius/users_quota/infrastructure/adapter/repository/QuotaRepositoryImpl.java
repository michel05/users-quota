package com.vicarius.users_quota.infrastructure.adapter.repository;

import com.vicarius.users_quota.domain.model.UserQuota;
import com.vicarius.users_quota.domain.port.out.QuotaRepository;
import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.JpaUserQuotaRepository;
import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.entity.UserQuotaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuotaRepositoryImpl implements QuotaRepository {

    private final JpaUserQuotaRepository jpaUserQuotaRepository;

    public QuotaRepositoryImpl(JpaUserQuotaRepository jpaUserQuotaRepository) {
        this.jpaUserQuotaRepository = jpaUserQuotaRepository;
    }

    @Override
    @Transactional
    public void consumeQuota(String userId) {
        UserQuotaEntity userQuotaEntity = jpaUserQuotaRepository.findById(userId)
                .orElse(new UserQuotaEntity(userId, 0));
        userQuotaEntity.setQuota(userQuotaEntity.getQuota() + 1);
        jpaUserQuotaRepository.save(userQuotaEntity);
    }

    @Override
    public int getQuota(String userId) {
        return jpaUserQuotaRepository.findById(userId)
                .map(UserQuotaEntity::getQuota)
                .orElse(0);
    }

    @Override
    public List<UserQuota> getUsersQuota() {
        return jpaUserQuotaRepository.findAll()
                .stream().map(UserQuotaEntity::toDomain)
                .collect(Collectors.toList());
    }
}