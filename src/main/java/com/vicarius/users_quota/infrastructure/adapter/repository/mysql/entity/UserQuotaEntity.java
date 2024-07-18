package com.vicarius.users_quota.infrastructure.adapter.repository.mysql.entity;

import com.vicarius.users_quota.domain.model.UserQuota;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_quota")
public class UserQuotaEntity {

    @Id
    private String userId;
    private int quota;

    public UserQuotaEntity() {
    }

    public UserQuotaEntity(String userId, int quota) {
        this.userId = userId;
        this.quota = quota;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public UserQuota toDomain() {
        return new UserQuota(userId, quota);
    }
}