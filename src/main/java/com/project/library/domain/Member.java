package com.project.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty(message = "아이디를 입력하세요")
    @Column(unique = true)
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;

    @NotEmpty(message = "비밀번호 확인을 입력하세요")
    private String confirmPassword;

    @NotEmpty(message = "이름을 입력하세요")
    private String name;
}
