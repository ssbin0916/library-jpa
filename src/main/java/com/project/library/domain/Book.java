package com.project.library.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    @NotEmpty(message = "제목 입력은 필수입니다.")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "저자 입력은 필수입니다.")
    @Column(name = "author")
    private String author;

    @NotNull(message = "장르 입력은 필수입니다.")
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    private boolean isRented;
}
