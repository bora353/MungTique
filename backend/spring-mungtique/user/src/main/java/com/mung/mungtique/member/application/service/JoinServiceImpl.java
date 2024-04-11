package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.member.domain.Authority;
import com.mung.mungtique.member.domain.User;
import com.mung.mungtique.member.application.port.in.JoinService;
import com.mung.mungtique.member.application.port.out.UserRepoPort;
import com.mung.mungtique.member.application.service.mapper.UserMapper;
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
    public User joinProcess(JoinDTO joinDTO) {

        joinDTO.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        Boolean isExist = userRepoPort.existsByEmail(joinDTO.getEmail());

        if (isExist) {
            // TODO : 존재하면 어떻게 처리할지 고민
            return null;
        }

        User user = userMapper.joinDtoToUserEntity(joinDTO, Authority.ROLE_ADMIN.name());
        User userResult = userRepoPort.save(user);
        log.info("userEntity : {}", user);
        return userResult;

        // TODO : dto(req)로 받아서 매퍼로 엔티티변환해서 저장하고 다시 dto(res)로 반환하자!
    }
}
