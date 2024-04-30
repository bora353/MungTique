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
//@RequestMapping("/..")
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail-send")
    public int sendMail(@RequestBody MailReq mail) throws MessagingException {

        // TODO : advice controller 추가
        log.info(mail.getMail());
        return mailService.sendMail(mail.getMail());
    }

    @GetMapping("/mail-check")
    public ResponseEntity<?> checkMail(@RequestParam String userNumber, int sentNumber) {

        boolean isMatch = userNumber.equals(Integer.toString(sentNumber));
        return ResponseEntity.ok(isMatch);
    }


}
