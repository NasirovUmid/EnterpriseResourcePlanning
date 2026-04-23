package com.pm.EnterpriseResourcePlanning.datasource.helper;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
public abstract class MessageAlertDataSource {

    private final AlertSystemDao dao;
    private final Class<?> clazz;

    public MessageAlertDataSource(AlertSystemDao alertSystemDao, Class<? extends MessageAlertDataSource> clazz) {
        Objects.requireNonNull(clazz);

        this.dao = alertSystemDao;
        this.clazz = clazz;
    }

    protected <T> T execute(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            sendAlert(e.getMessage());
            throw e;
        }
    }

    protected void execute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            sendAlert(e.getMessage());
            throw e;
        }
    }

    private void sendAlert(String message) {
        String sourceName = clazz.getSimpleName();

        try {
            String msg = String.format("Source: %s, msg: %s", sourceName, message);
            dao.alert(msg);
        } catch (Exception e) {
            log.error("Exception on sending alert ({}): {}", sourceName, e.getMessage(), e);
        }
    }

}
