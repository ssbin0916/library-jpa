package com.project.library.repository;

import com.project.library.domain.Book;
import com.project.library.domain.Loan;
import com.project.library.domain.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByBookAndMember(@Param("book") Book book, @Param("member") Member member);

    List<Loan> findByMemberId (Long id);

}
