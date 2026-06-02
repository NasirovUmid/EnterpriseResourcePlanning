package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.BlackListEntity;

import java.util.UUID;

public interface BlackListDao {

    BlackListEntity save(BlackListEntity blackListEntity);

    BlackListEntity findById(UUID id);

    void deleteById(UUID id);

}
