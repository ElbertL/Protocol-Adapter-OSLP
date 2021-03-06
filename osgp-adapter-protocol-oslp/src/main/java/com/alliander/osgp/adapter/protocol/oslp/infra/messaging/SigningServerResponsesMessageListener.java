/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.protocol.oslp.infra.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alliander.osgp.adapter.protocol.oslp.application.services.oslp.OslpSigningService;
import com.alliander.osgp.oslp.SignedOslpEnvelopeDto;
import com.alliander.osgp.shared.infra.jms.Constants;
import com.alliander.osgp.shared.infra.jms.ResponseMessage;
import com.alliander.osgp.shared.infra.jms.ResponseMessageResultType;

@Component(value = "signingServerResponsesMessageListener")
public class SigningServerResponsesMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SigningServerResponsesMessageListener.class);

    @Autowired
    private OslpSigningService oslpSigningService;

    @Override
    public void onMessage(final Message message) {
        try {
            LOGGER.info("Received message of type: {}", message.getJMSType());

            final ObjectMessage objectMessage = (ObjectMessage) message;
            final String messageType = objectMessage.getJMSType();
            final String correlationId = objectMessage.getJMSCorrelationID();
            final String deviceIdentification = objectMessage.getStringProperty(Constants.DEVICE_IDENTIFICATION);
            final ResponseMessage responseMessage = (ResponseMessage) objectMessage.getObject();
            final ResponseMessageResultType result = responseMessage == null ? null : responseMessage.getResult();

            // Check the result.
            if (result.equals(ResponseMessageResultType.NOT_OK)) {
                LOGGER.error("OslpEnvelope was not signed by signing-server. Unable to send request to device: {}",
                        deviceIdentification);
                this.oslpSigningService.handleError(deviceIdentification, responseMessage);
                return;
            }

            LOGGER.info("messageType: {}, deviceIdentification: {}, result: {}, correlationId: {}", messageType,
                    deviceIdentification, result, correlationId);

            // Get the DTO object containing signed OslpEnvelope.
            final SignedOslpEnvelopeDto signedOslpEnvelopeDto = (SignedOslpEnvelopeDto) responseMessage.getDataObject();

            this.oslpSigningService.handleSignedOslpEnvelope(signedOslpEnvelopeDto, deviceIdentification);
        } catch (final JMSException ex) {
            LOGGER.error("Exception: {} ", ex.getMessage(), ex);
        }
    }
}
