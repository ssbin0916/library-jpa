package com.project.library.controller;

import com.project.library.domain.Book;
import com.project.library.domain.Category;
import com.project.library.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanBookDTO {

    private Long id;
    private Book book;
    private Member member;
    private String title;
    private String author;
    private Category category;

    private LocalDateTime rentDate;
    private LocalDateTime returnDate;

}
