package com.mung.mungtique.user.application.service;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;
import com.mung.mungtique.user.application.port.in.UserService;
import com.mung.mungtique.user.application.port.out.UserRepoPort;
import com.mung.mungtique.user.application.service.mapper.UserMapper;
import com.mung.mungtique.user.domain.Authority;
import com.mung.mungtique.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepoPort userRepoPort;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepoPort.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        return new User(userEntity.getEmail(), userEntity.getPassword()
        , true, true, true, true
                , new ArrayList<>()); // 권한 추가 작업 넣을 수 있음
    }

    @Override
    @Transactional
    public JoinRes createUser(JoinReq joinReq) {
        String encodedPassword = bCryptPasswordEncoder.encode(joinReq.password());
        Boolean isExist = userRepoPort.existsByEmail(joinReq.email());

        if (isExist) return null;

        UserEntity userEntity = userMapper.toUserEntity(joinReq, Authority.ROLE_ADMIN.name());
        userEntity.setPassword(encodedPassword);
        UserEntity savedUserEntity = userRepoPort.save(userEntity);
        log.info("Create User Complete : {}", savedUserEntity);

        return userMapper.toJoinRes(userEntity);
    }

    @Override
    public UserEntity getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepoPort.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return userEntity;
    }
}
