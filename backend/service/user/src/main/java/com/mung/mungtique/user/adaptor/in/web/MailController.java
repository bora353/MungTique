package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.adaptor.in.web.dto.MailReq;
import com.mung.mungtique.user.application.port.in.MailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MailController {

    private final MailService mailService;

    @Operation(summary = "이메일 중복 체크해서 사용자에게 인증코드 메일을 보낸다.")
    @PostMapping("/mail-send")
    public ResponseEntity<String> sendMail(@RequestBody MailReq mail) throws MessagingException {

        log.info(mail.getMail());

        if (mailService.isEmailDuplicate(mail.getMail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate email");
        }

        int verificationCode = mailService.sendEmailWithVerificationCode(mail.getMail());
        return ResponseEntity.ok(String.valueOf(verificationCode));
    }

    @Operation(summary = "사용자가 보낸 인증번호와 실제 인증번호를 확인해서 boolean으로 반환한다.")
    @GetMapping("/mail-check")
    public ResponseEntity<Boolean> checkMail(@RequestParam String mail, @RequestParam String providedVerificationCode) {
        boolean isMatch = mailService.checkMailVerificationCode(mail, providedVerificationCode);

        if (!isMatch) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        }

        return ResponseEntity.ok(true);
    }
}
