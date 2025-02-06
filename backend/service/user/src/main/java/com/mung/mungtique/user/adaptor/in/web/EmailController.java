package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.adaptor.in.web.dto.EmailReq;
import com.mung.mungtique.user.application.port.in.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "이메일 중복 체크해서 사용자에게 인증코드 메일을 보낸다.")
    @PostMapping("/mail-send")
    public ResponseEntity<String> sendMail(@RequestBody @Valid EmailReq emailReq) throws MessagingException {

        log.info(emailReq.email());

        if (emailService.isEmailDuplicate(emailReq.email())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate email");
        }

        int verificationCode = emailService.sendEmailWithVerificationCode(emailReq.email());
        return ResponseEntity.ok(String.valueOf(verificationCode));
    }

    @Operation(summary = "사용자가 보낸 인증번호와 실제 인증번호를 확인해서 boolean으로 반환한다.")
    @GetMapping("/mail-check")
    public ResponseEntity<Boolean> checkMail(@RequestParam String email, @RequestParam String providedVerificationCode) {
        boolean isMatch = emailService.checkMailVerificationCode(email, providedVerificationCode);

        if (!isMatch) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        }

        return ResponseEntity.ok(true);
    }
}
