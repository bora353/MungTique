package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;
import com.mung.mungtique.member.application.port.in.JoinService;
import com.mung.mungtique.member.application.port.out.UserPort;
import com.mung.mungtique.member.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinServiceImpl implements JoinService {

    private final UserPort userPort;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserEntity joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        joinDTO.setPassword(bCryptPasswordEncoder.encode(password));
        String phone = joinDTO.getPhone();
        String email = joinDTO.getEmail();

        Boolean isExist = userPort.existsByEmail(email);

        if (isExist) {
            // TODO : 존재하면 어떻게 처리할지 고민
            return null;
        }

        UserEntity userEntity = userMapper.joinDtoToUserEntity(joinDTO, "ROLE_ADMIN");
        UserEntity userResult = userPort.save(userEntity);
        log.info("userEntity : {}", userEntity);
        return userResult;
    }
}
