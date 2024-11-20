package com.mung.mungtique.user.application.port.in;

import jakarta.mail.MessagingException;

public interface MailService {

    int sendEmailWithVerificationCode(String mail) throws MessagingException;
    boolean isEmailDuplicate(String mail);
    boolean checkMailVerificationCode(String mail, String providedVerificationCode);
}
