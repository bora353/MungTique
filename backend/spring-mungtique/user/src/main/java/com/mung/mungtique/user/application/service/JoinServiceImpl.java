package com.mung.mungtique.user.application.service;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;
import com.mung.mungtique.user.application.port.in.JoinService;
import com.mung.mungtique.user.application.port.out.UserRepoPort;
import com.mung.mungtique.user.application.service.mapper.UserMapper;
import com.mung.mungtique.user.domain.Authority;
import com.mung.mungtique.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinServiceImpl implements JoinService {

    private final UserRepoPort userRepoPort;
    private final UserMapper userMapper;
    // TODO : 추후 단방향 암호화로 변경? (SHA256)
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public JoinRes registerUser(JoinReq joinReq) {
        String encodedPassword = bCryptPasswordEncoder.encode(joinReq.password());
        Boolean isExist = userRepoPort.existsByEmail(joinReq.email());

        if (isExist) return null;

        User user = userMapper.toUserEntity(joinReq, Authority.ROLE_ADMIN.name());
        user.setPassword(encodedPassword);
        User savedUser = userRepoPort.save(user);
        log.info("회원가입 완료! : {}", savedUser);

        return userMapper.toJoinRes(user);
    }
}
