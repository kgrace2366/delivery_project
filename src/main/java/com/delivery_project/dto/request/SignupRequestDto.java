package com.delivery_project.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDto {
    private String username;
    private String password;
    private String address;
    private boolean manager = false;
    private String managerToken = "";
}