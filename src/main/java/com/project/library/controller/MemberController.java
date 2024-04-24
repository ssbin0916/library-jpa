package com.project.library.controller;

import com.project.library.domain.Member;
import com.project.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String joinForm(@ModelAttribute("member") Member member) {
        return "members/joinForm";
    }

    @PostMapping("/add")
    public String join(@Valid @ModelAttribute("member") Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/joinForm";
        }

        Optional<Member> existLoginId = memberService.findByLoginId(member.getLoginId());
        if (existLoginId.isPresent()) {
            bindingResult.rejectValue("loginId", "duplicate", "이미 사용 중인 아이디입니다.");
            return "members/joinForm";
        }

        if (!member.getPassword().equals(member.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "members/joinForm";
        }

        memberService.save(member);
        return "redirect:/";
    }

    @PutMapping("/update")
    public String update(@Valid @ModelAttribute("id") Long id, @ModelAttribute("name") String name) {
        memberService.update(id, name);
        return "redirect:/";
    }

}
