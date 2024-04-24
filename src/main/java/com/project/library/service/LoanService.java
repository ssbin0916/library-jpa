package com.project.library.service;

import com.project.library.controller.LoanBookDTO;
import com.project.library.domain.Book;
import com.project.library.domain.Loan;
import com.project.library.domain.Member;
import com.project.library.repository.BookRepository;
import com.project.library.repository.LoanRepository;
import com.project.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public void rentBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없음"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없음"));

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setRentDate(LocalDateTime.now());

        loanRepository.save(loan);

        book.setRented(true);
        bookRepository.save(book);
    }

    public void returnBook(Long loanId) {
        Optional<Loan> findRentBook = loanRepository.findById(loanId);

        if (findRentBook.isPresent()) {
            Loan loan = findRentBook.get();

            Book book = loan.getBook();
            Member member = loan.getMember();

            loan.setReturnDate(LocalDateTime.now());

            bookRepository.save(book);

            book.setRented(false);
            loanRepository.save(loan);
        } else {
            throw new IllegalArgumentException("대출 정보가 없습니다.");
        }
    }

    public List<LoanBookDTO> histories(Long id) {
        List<Loan> loans = loanRepository.findByMemberId(id);
        return loans.stream()
                .map(l -> new LoanBookDTO(
                        l.getId(),
                        l.getBook(),
                        l.getMember(),
                        l.getBook().getTitle(),
                        l.getBook().getAuthor(),
                        l.getBook().getCategory(),
                        l.getRentDate(),
                        l.getReturnDate()
                ))
                .collect(Collectors.toList());
    }


    public void delete(Long id) {
        loanRepository.deleteById(id);
    }
}
