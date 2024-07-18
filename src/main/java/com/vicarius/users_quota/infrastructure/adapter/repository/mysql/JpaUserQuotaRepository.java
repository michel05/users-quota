package com.vicarius.users_quota.infrastructure.adapter.repository.mysql;

import com.vicarius.users_quota.infrastructure.adapter.repository.mysql.entity.UserQuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserQuotaRepository extends JpaRepository<UserQuotaEntity, String> {
}