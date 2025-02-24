package com.mung.mungtique.user.application.service;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;
import com.mung.mungtique.user.adaptor.in.web.dto.UserRes;
import com.mung.mungtique.user.application.port.in.UserService;
import com.mung.mungtique.user.application.port.out.UserRepoPort;
import com.mung.mungtique.user.application.service.mapper.UserMapper;
import com.mung.mungtique.user.domain.Authority;
import com.mung.mungtique.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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
        return userRepoPort.findByEmail(email)
                .map(user -> new User(user.getEmail(), user.getPassword(),
                        true, true, true, true, List.of(new SimpleGrantedAuthority(user.getRole().name()))))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Email: " + email));
    }

    @Override
    @Transactional
    public JoinRes createUser(JoinReq joinReq) {
        String encodedPassword = bCryptPasswordEncoder.encode(joinReq.password());
        Boolean isExist = userRepoPort.existsByEmail(joinReq.email());

        if (isExist) throw new IllegalArgumentException("User with this email already exists.");

        UserEntity userEntity = userMapper.toUserEntity(joinReq, Authority.ROLE_USER);
        userEntity.setPassword(encodedPassword);
        UserEntity savedUserEntity = userRepoPort.save(userEntity);
        log.info("Create User Complete : {}", savedUserEntity);

        return userMapper.toJoinRes(userEntity);
    }

    @Override
    public UserEntity getUserDetailsByEmail(String email) {
        return userRepoPort.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User not found with Email: " + email));
    }

    @Override
    public UserRes getUserInfo(String userId) {
        UserEntity user =  userRepoPort.findById(Long.parseLong(userId)).orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
        return userMapper.toUserRes(user);
    }

    @Override
    @Transactional
    public void updateLstLoginAt(UserEntity user) {
        user.setLastLoginAt();
        userRepoPort.save(user);
    }
}
