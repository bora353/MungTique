package com.mung.mungtique.user.application.port.in;

import jakarta.mail.MessagingException;

public interface EmailService {

    int sendEmailWithVerificationCode(String email) throws MessagingException;
    boolean isEmailDuplicate(String email);
    boolean checkMailVerificationCode(String email, String providedVerificationCode);
}
