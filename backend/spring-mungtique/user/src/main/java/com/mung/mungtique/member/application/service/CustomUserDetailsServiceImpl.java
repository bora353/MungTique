package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.adaptor.in.web.dto.CustomUserDetailsDTO;
import com.mung.mungtique.member.domain.User;
import com.mung.mungtique.member.application.port.in.CustomUserDetailsService;
import com.mung.mungtique.member.application.port.out.UserRepoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepoPort userRepoPort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepoPort.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new CustomUserDetailsDTO(user);
    }
}
