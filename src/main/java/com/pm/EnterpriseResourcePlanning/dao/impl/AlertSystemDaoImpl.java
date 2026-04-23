package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.integrations.TelegramIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlertSystemDaoImpl implements AlertSystemDao {

    private final TelegramIntegration telegramIntegration;

    @Override
    public void alert(String message) {

        telegramIntegration.alert(message);

    }
}
