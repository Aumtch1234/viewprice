package com.ViewPrice.response;

import lombok.Data;

@Data
public class AuthReponse {
    private String jwt;
    private boolean status;
    private String message;
    private boolean isTwoFactorAuthEnabled;
    private String session;
}
