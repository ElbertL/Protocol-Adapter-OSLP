/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.webdevicesimulator.application.tasks;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alliander.osgp.webdevicesimulator.domain.entities.Device;
import com.alliander.osgp.webdevicesimulator.domain.repositories.DeviceRepository;
import com.alliander.osgp.webdevicesimulator.service.RegisterDevice;

@Component
public class AutonomousDeviceRegister implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutonomousDeviceRegister.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RegisterDevice registerDevice;

    @Override
    public void run() {
        LOGGER.info("Registering devices");

        final List<Device> devices = this.deviceRepository.findAll();

        for (final Device device : devices) {
            LOGGER.info("Autonomous device register for : {}: {} ", device.getId(), device.getDeviceIdentification());
            this.registerDevice.sendRegisterDeviceCommand(device.getId(), false);
            LOGGER.info("Autonomous device register confirmation for : {}: {} ", device.getId(),
                    device.getDeviceIdentification());
            this.registerDevice.sendConfirmDeviceRegistrationCommand(device.getId());
        }
    }
}