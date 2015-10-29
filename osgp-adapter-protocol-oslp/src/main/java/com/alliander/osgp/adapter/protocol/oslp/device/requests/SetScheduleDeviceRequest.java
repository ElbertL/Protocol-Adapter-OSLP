/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.protocol.oslp.device.requests;

import java.util.List;

import com.alliander.osgp.adapter.protocol.oslp.device.DeviceRequest;
import com.alliander.osgp.dto.valueobjects.RelayType;
import com.alliander.osgp.dto.valueobjects.Schedule;

public class SetScheduleDeviceRequest extends DeviceRequest {

    private List<Schedule> schedules;
    private RelayType relayType;

    public SetScheduleDeviceRequest(final String organisationIdentification, final String deviceIdentification,
            final String correlationUid, final List<Schedule> schedules, final RelayType relayType) {
        super(organisationIdentification, deviceIdentification, correlationUid);
        this.schedules = schedules;
        this.relayType = relayType;
    }

    public SetScheduleDeviceRequest(final String organisationIdentification, final String deviceIdentification,
            final String correlationUid, final List<Schedule> schedules, final RelayType relayType,
            final String domain, final String domainVersion, final String messageType, final String ipAddress,
            final int retryCount, final boolean isScheduled) {
        super(organisationIdentification, deviceIdentification, correlationUid, domain, domainVersion, messageType,
                ipAddress, retryCount, isScheduled);
        this.schedules = schedules;
        this.relayType = relayType;
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }

    public RelayType getRelayType() {
        return this.relayType;
    }
}
