package com.mung.mungtique.user.application.port.in;

import com.mung.mungtique.user.domain.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface TokenService {
    RefreshToken saveRefreshToken(String email, String refresh);
    void deleteRefreshToken(String refreshToken);
    Map<String, String> reissueToken(HttpServletRequest request);
}
