package com.mung.mungtique.user.adaptor.in.web.dto;


public record TokenRes (String error, String accessToken, String refreshToken) {
}
