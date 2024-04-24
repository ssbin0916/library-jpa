package com.project.library.domain.member;

import com.project.library.domain.Member;
import com.project.library.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberService memberService;

    @Test
    void save() {
        Member member = new Member(1L, "loginId", "password", "password", "name");
        Member save = memberService.save(member);

        assertEquals(1, save.getId());

    }

    @Test
    void findByLoginId() {
        Member member = new Member(1L, "loginId", "password", "password", "name");
        memberService.save(member);

        Optional<Member> loginId = memberService.findByLoginId("loginId");
        System.out.println("loginId = " + loginId);
    }

    @Test
    void update() {
        Member member = new Member(1L, "loginId", "password", "password", "Old Name");
        memberService.save(member);

        Long id = member.getId();
        String name = "New Name";

        int update = memberService.update(id, name);

        assertEquals(1, update);
        Optional<Member> findMember = memberService.findById(id);
        assertTrue(findMember.isPresent());
        assertEquals(name, findMember.get().getName());

    }
}