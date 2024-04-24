package com.project.library.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty(message = "아이디를 입력하세요.")
    public String loginId;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    public String password;
}
