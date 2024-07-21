package com.mung.mungtique.user.application.port.in;

import jakarta.mail.MessagingException;

public interface MailService {

    int sendMail(String mail) throws MessagingException;
}
