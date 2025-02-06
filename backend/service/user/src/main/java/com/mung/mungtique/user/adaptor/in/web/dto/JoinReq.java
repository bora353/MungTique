package com.mung.mungtique.user.adaptor.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record JoinReq(
        @NotEmpty(message = "Username is required.")
        String username,

        @NotEmpty(message = "Password is required.")
        @Size(min = 6, message = "Password should have at least 6 characters.")
        String password,

        @NotEmpty(message = "Password check is required.")
        String passwordCheck,

        @NotEmpty(message = "Email is required.")
        @Email(message = "Invalid email address.")
        String email,

        @NotEmpty(message = "Phone number is required.")
        @Pattern(regexp = "^\\d{10,11}$", message = "Invalid phone number.")
        String phone
) {}
