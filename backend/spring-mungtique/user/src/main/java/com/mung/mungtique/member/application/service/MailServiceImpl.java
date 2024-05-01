package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.application.port.in.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private static int number;

    @Override
    public int sendMail(String mail) throws MessagingException {

        createNumber();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

        helper.setFrom("mungtique@mung.com");//이메일의 발신자 주소 설정 (이거 안돼 ...!)
        helper.setTo(mail);
        helper.setSubject("뭉티끄 이메일 인증 메일입니다.");

        String body = "";
        body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>" + "감사합니다." + "</h3>";

        helper.setText(body,true);
        javaMailSender.send(message);

        return number;
    }

    private static void createNumber() {
        number = (int) (Math.random() * (90000)) + 100000;
        log.info("인증번호 : {} ", number);
    }
}
