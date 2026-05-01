package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ActionDao;
import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.ActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActionDaoImpl implements ActionDao {

    private final ActionRepository repository;

    @Override
    public ActionEntity saveAction(String name) {
        return repository.save(name).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<ActionEntity> getActionPage() {
        return repository.findAll();
    }

    @Override
    public void updateAction(String name, UUID id) {
        repository.updateAction(name, id);
    }

    @Override
    public ActionEntity getActionById(UUID id) {
        return repository.getActionById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.ACTION_NOT_FOUND, id));
    }
}
