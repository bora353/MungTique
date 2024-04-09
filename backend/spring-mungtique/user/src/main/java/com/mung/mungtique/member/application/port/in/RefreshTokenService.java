package com.mung.mungtique.member.application.port.in;

import jakarta.servlet.http.HttpServletRequest;

public interface RefreshTokenService {

    String reissueToken(HttpServletRequest request);
}
