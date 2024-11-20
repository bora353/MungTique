package com.mung.mungtique.user.application.service;

import com.mung.mungtique.user.adaptor.in.web.dto.MailReq;
import com.mung.mungtique.user.application.port.in.MailService;
import com.mung.mungtique.user.application.port.out.RedisPort;
import com.mung.mungtique.user.application.port.out.UserRepoPort;
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

    private final UserRepoPort userRepoPort;
    private final RedisPort redisPort;
    private final JavaMailSender javaMailSender;

    @Override
    public boolean isEmailDuplicate(String mail) {
        return userRepoPort.existsByEmail(mail);
    }

    @Override
    public int sendEmailWithVerificationCode(String mail) throws MessagingException {
        int verificationCode = createVerificationCode();
        log.info("인증번호 : {} ", verificationCode);

        redisPort.saveVerificationCodeFor3Minutes(mail, verificationCode);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

        helper.setFrom("mungtique@mung.com"); // TODO : 이메일의 발신자 주소 설정 (작동 안함)
        helper.setTo(mail);
        helper.setSubject("뭉티끄 이메일 인증 메일입니다.");

        String body = "";
        body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
        body += "<h1>" + verificationCode + "</h1>";
        body += "<h3>" + "감사합니다." + "</h3>";

        helper.setText(body,true);
        javaMailSender.send(message);

        return verificationCode;
    }

    @Override
    public boolean checkMailVerificationCode(String mail, String userNumber) {
        String verificationCode = redisPort.findVerificationCode(mail);

        if (verificationCode == null) {
            return false;
        }

        return userNumber.equals(verificationCode);
    }

    private int createVerificationCode() {
        return (int) (Math.random() * (90000)) + 100000;
    }
}
