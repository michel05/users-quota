package com.vicarius.users_quota.infrastructure.adapter.repository;

import com.vicarius.users_quota.domain.port.out.QuotaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class QuotaRepositoryImpl implements QuotaRepository {

    private final Map<String, Integer> quotaMap = new ConcurrentHashMap<>();

    @Override
    public void consumeQuota(String userId) {
        quotaMap.merge(userId, 1, Integer::sum);
    }

    @Override
    public int getQuota(String userId) {
        return quotaMap.getOrDefault(userId, 0);
    }

    @Override
    public Map<String, Integer> getUsersQuota() {
        return new ConcurrentHashMap<>(quotaMap);
    }
}