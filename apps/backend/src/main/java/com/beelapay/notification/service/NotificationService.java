package com.beelapay.notification.service;

public interface NotificationService {
    void sendOtp(String phoneNumber, String otpCode);
    void sendTransferConfirmation(String phoneNumber, String message);
}
