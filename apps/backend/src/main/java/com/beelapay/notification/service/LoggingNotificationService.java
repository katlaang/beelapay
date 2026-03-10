package com.beelapay.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingNotificationService implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(LoggingNotificationService.class);

    @Override
    public void sendOtp(String phoneNumber, String otpCode) {
        log.info("OTP for {} is {}", phoneNumber, otpCode);
    }

    @Override
    public void sendTransferConfirmation(String phoneNumber, String message) {
        log.info("SMS to {} -> {}", phoneNumber, message);
    }
}
