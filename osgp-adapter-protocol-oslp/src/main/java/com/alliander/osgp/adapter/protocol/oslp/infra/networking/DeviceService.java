/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.protocol.oslp.infra.networking;

import java.io.IOException;

import com.alliander.osgp.adapter.protocol.oslp.device.DeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.DeviceResponseHandler;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.GetPowerUsageHistoryDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.GetStatusDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.ResumeScheduleDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.SetConfigurationDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.SetEventNotificationsDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.SetLightDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.SetScheduleDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.SetTransitionDeviceRequest;
import com.alliander.osgp.adapter.protocol.oslp.device.requests.UpdateFirmwareDeviceRequest;
import com.alliander.osgp.dto.valueobjects.PageInfoDto;
import com.alliander.osgp.dto.valueobjects.PowerUsageHistoryResponseMessageDataContainerDto;
import com.alliander.osgp.oslp.OslpEnvelope;

public interface DeviceService {

    void getConfiguration(DeviceRequest deviceRequest);

    void doGetConfiguration(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void getFirmwareVersion(DeviceRequest deviceRequest);

    void doGetFirmwareVersion(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void getStatus(GetStatusDeviceRequest deviceRequest);

    void doGetStatus(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void setReboot(DeviceRequest deviceRequest);

    void doSetReboot(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void setConfiguration(SetConfigurationDeviceRequest deviceRequest);

    void doSetConfiguration(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void setEventNotifications(SetEventNotificationsDeviceRequest deviceRequest);

    void doSetEventNotifications(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void startSelfTest(DeviceRequest deviceRequest);

    void doStartSelfTest(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void stopSelfTest(DeviceRequest deviceRequest);

    void doStopSelfTest(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void updateFirmware(UpdateFirmwareDeviceRequest deviceRequest);

    void doUpdateFirmware(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void getActualPowerUsage(DeviceRequest deviceRequest);

    void doGetActualPowerUsage(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void getPowerUsageHistory(GetPowerUsageHistoryDeviceRequest deviceRequest);

    void doGetPowerUsageHistory(OslpEnvelope oslpRequest,
            PowerUsageHistoryResponseMessageDataContainerDto powerUsageHistoryResponseMessageDataContainer,
            GetPowerUsageHistoryDeviceRequest deviceRequest, DeviceResponseHandler deviceResponseHandler,
            String ipAddress, String domain, String domainVersion, String messageType, int retryCount,
            boolean isScheduled) throws IOException;

    void resumeSchedule(ResumeScheduleDeviceRequest deviceRequest);

    void doResumeSchedule(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;

    void setLight(SetLightDeviceRequest deviceRequest);

    void doSetLight(OslpEnvelope oslpRequest, DeviceRequest deviceRequest, DeviceResponseHandler deviceResponseHandler,
            String ipAddress) throws IOException;

    void setSchedule(SetScheduleDeviceRequest deviceRequest);

    void doSetSchedule(OslpEnvelope oslpRequest, SetScheduleDeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress, String domain, String domainVersion,
            String messageType, int retryCount, boolean isScheduled, PageInfoDto pageInfo) throws IOException;

    void setTransition(SetTransitionDeviceRequest deviceRequest);

    void doSetTransition(OslpEnvelope oslpRequest, DeviceRequest deviceRequest,
            DeviceResponseHandler deviceResponseHandler, String ipAddress) throws IOException;
}
