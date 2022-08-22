package com.musala.dronedispatcher.service.cron;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.musala.dronedispatcher.service.DroneService;

@Component
public class BatteryLevelCheck {
    private static final Logger log = LoggerFactory.getLogger(BatteryLevelCheck.class);

    private final DroneService droneService;

    public BatteryLevelCheck(DroneService droneService) {
        this.droneService = droneService;
    }

    // Log info related to battery level each 30 seconds
    @Scheduled(fixedDelay = 30000)
    public void reportBatteryLevel() {
        log.info("Test logger: {}", droneService.findAllWithoutPagination());
    }
}
