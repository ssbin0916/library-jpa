package com.project.library.controller;

import com.project.library.domain.Member;
import com.project.library.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/loans")
@Slf4j
public class LoanController {

    private final LoanService loanService;

    @PostMapping("rent/{bookId}")
    public String rentBook(@PathVariable("bookId") Long bookId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        if (loginMember == null) {
            return "redirect:/login";
        }
        loanService.rentBook(bookId, loginMember.getId());
        return "redirect:/books/books";
    }

    @PostMapping("return/{loanId}")
    public String returnBook(@PathVariable("loanId") Long loanId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        if (loginMember == null) {
            return "redirect:/login";
        }
        loanService.returnBook(loanId);
        return "redirect:/books/books";
    }

    @GetMapping("/histories")
    public String loanedHistories(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/login";
        }

        List<LoanBookDTO> loanedBooks = loanService.histories(loginMember.getId());

        model.addAttribute("loanedBooks", loanedBooks);
        return "loans/loanedHistories";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        loanService.delete(id);
        return "redirect:/loans/histories";
    }
}
