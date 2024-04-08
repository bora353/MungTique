package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.adaptor.in.web.dto.CustomUserDetailsDTO;
import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;
import com.mung.mungtique.member.application.port.in.CustomUserDetailsService;
import com.mung.mungtique.member.application.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserPort userPort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userPort.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new CustomUserDetailsDTO(userEntity);
    }
}
