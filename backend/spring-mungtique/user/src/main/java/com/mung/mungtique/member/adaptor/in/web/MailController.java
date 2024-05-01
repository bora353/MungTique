package com.mung.mungtique.member.adaptor.in.web;

import com.mung.mungtique.member.adaptor.in.web.dto.MailReq;
import com.mung.mungtique.member.application.port.in.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail-send")
    public String sendMail(@RequestBody MailReq mail) throws MessagingException {

        // TODO : advice controller 추가
        // TODO : 인증번호 redis에 3분만 저장
        log.info(mail.getMail());
        int verifyNumber = mailService.sendMail(mail.getMail());
        return String.valueOf(verifyNumber);
    }

    @GetMapping("/mail-check")
    public ResponseEntity<?> checkMail(@RequestParam String userNumber, String sentNumber) {

        boolean isMatch = userNumber.equals(sentNumber);
        return ResponseEntity.ok(isMatch);
    }
}
