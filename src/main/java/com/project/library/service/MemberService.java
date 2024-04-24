package com.project.library.service;

import com.project.library.domain.Member;
import com.project.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(Member member) {
        validateMember(member);
        return memberRepository.save(member);
    }

    private void validateMember(Member member) {
        List<Member> findMembers = memberRepository.findListByLoginId(member.getLoginId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    @Transactional
    public int update(Long id,  String name) {
        return memberRepository.update(id, name);
    }
}
