package com.zosh.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String fullName;
    private String otp;
    private String role;
    private String mobile;
}
