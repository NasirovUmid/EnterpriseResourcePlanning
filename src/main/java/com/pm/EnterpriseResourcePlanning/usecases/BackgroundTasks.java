package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.impl.RefreshTokenDataSourceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class BackgroundTasks {

    private final RefreshTokenDataSourceImpl refreshTokenDataSource;

    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Tashkent")
    private void cleanExpiredTokens() {

        log.info("Clean up is starting...");

        int count = refreshTokenDataSource.deleteExpired();

        log.info("Clean up is done , Tokens were deleted {}", count);
    }

}
