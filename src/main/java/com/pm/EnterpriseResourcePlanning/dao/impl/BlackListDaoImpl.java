package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.BlackListDao;
import com.pm.EnterpriseResourcePlanning.entity.BlackListEntity;
import com.pm.EnterpriseResourcePlanning.repository.BlackListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class BlackListDaoImpl implements BlackListDao {

    private final BlackListRepository repository;

    @Override
    public BlackListEntity save(BlackListEntity blackListEntity) {
        return repository.save(blackListEntity);
    }

    @Override
    public BlackListEntity findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
