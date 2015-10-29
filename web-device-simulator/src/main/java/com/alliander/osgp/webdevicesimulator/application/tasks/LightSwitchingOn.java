package com.alliander.osgp.webdevicesimulator.application.tasks;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alliander.osgp.oslp.Oslp;
import com.alliander.osgp.webdevicesimulator.application.services.DeviceManagementService;
import com.alliander.osgp.webdevicesimulator.domain.entities.Device;
import com.alliander.osgp.webdevicesimulator.domain.repositories.DeviceRepository;
import com.alliander.osgp.webdevicesimulator.service.RegisterDevice;
import com.alliander.osgp.webdevicesimulator.service.SwitchingServices;

@Component
public class LightSwitchingOn implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LightSwitchingOn.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RegisterDevice registerDevice;

    @Autowired
    private SwitchingServices switchingServices;

    @Autowired
    private DeviceManagementService deviceManagementService;

    @Override
    public void run() {

        if (this.deviceManagementService.getLightSwitching()) {
            LOGGER.info("Publiclighting Switching on for devices without Evening/Morning Burners");

            final List<Device> devices = this.deviceRepository.findByHasEveningMorningBurner(false);

            for (final Device device : devices) {
                LOGGER.info("Light switching for : {}: {} ", device.getId(), device.getDeviceIdentification());

                // Switching on Light
                this.switchingServices.lightSwitchOn(device.getId());

                // Send EventNotifications for LightSwitching on
                LOGGER.info("Sending LIGHT_EVENTS_LIGHT_ON event for device : {}: {} ", device.getId(),
                        device.getDeviceIdentification());
                this.registerDevice.sendEventNotificationCommand(device.getId(),
                        Oslp.Event.LIGHT_EVENTS_LIGHT_ON_VALUE,
                        "LIGHT_EVENTS_LIGHT_ON event occurred on Light Switching on ", null);
            }
        }
    }
}