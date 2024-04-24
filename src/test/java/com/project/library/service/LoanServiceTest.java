package com.project.library.service;

import com.project.library.controller.LoanBookDTO;
import com.project.library.domain.Book;
import com.project.library.domain.Category;
import com.project.library.domain.Loan;
import com.project.library.domain.Member;
import com.project.library.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoanServiceTest {

    @Autowired
    LoanService loanService;
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    MemberService memberService;
    @Autowired
    BookService bookService;

    @Test
    void rentBook() {
        Book book = new Book();
        book.setTitle("제목");
        book.setAuthor("저자");
        book.setCategory(Category.ART);
        book.setRented(false);
        bookService.save(book);

        Member member = new Member();
        member.setLoginId("loginId");
        member.setPassword("password");
        member.setConfirmPassword("password");
        member.setName("name");
        member = memberService.save(member);

        loanService.rentBook(book.getId(), member.getId());

        Loan loan = loanRepository.findByBookAndMember(book, member).orElse(null);
        assertNotNull(loan);
        assertEquals(book.getId(), loan.getBook().getId());
        assertEquals(member.getId(), loan.getMember().getId());
    }

    @Test
    void returnBook() {
        Book book = new Book();
        book.setTitle("제목");
        book.setAuthor("저자");
        book.setCategory(Category.ART);
        bookService.save(book);

        Member member = new Member();
        member.setLoginId("loginId");
        member.setPassword("password");
        member.setConfirmPassword("password");
        member.setName("name");
        member = memberService.save(member);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setRentDate(LocalDateTime.now());
        loanRepository.save(loan);

        loanService.returnBook(loan.getId());

        Optional<Loan> returnLoan = loanRepository.findById(loan.getId());
        assertFalse(returnLoan.get().getBook().isRented());
    }

    @Test
    void histories() {
        Book book1 = new Book();
        book1.setTitle("제목1");
        book1.setAuthor("저자1");
        book1.setCategory(Category.ART);
        bookService.save(book1);

        Member member1= new Member();
        member1.setLoginId("loginId1");
        member1.setPassword("password1");
        member1.setConfirmPassword("password1");
        member1.setName("name1");
        member1 = memberService.save(member1);

        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setMember(member1);
        loanService.rentBook(book1.getId(), member1.getId());

        Book book2 = new Book();
        book2.setTitle("제목2");
        book2.setAuthor("저자2");
        book2.setCategory(Category.ART);
        bookService.save(book2);

//        Member member2= new Member();
//        member2.setLoginId("loginId2");
//        member2.setPassword("password2");
//        member2.setConfirmPassword("password2");
//        member2.setName("name2");
//        member2 = memberService.save(member2);

        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setMember(member1);
        loanService.rentBook(book2.getId(), member1.getId());

        List<LoanBookDTO> histories = loanService.histories(member1.getId());

        assertEquals(2, histories.size());
    }
}