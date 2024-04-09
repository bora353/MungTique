package com.mung.mungtique.member.application.port.in;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface RefreshTokenService {

    Map<String, String> reissueToken(HttpServletRequest request);
}
