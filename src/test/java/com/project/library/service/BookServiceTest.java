package com.project.library.service;

import com.project.library.domain.Book;
import com.project.library.domain.Category;
import com.project.library.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    void save() {
        Book book = new Book(1L, "제목", "저자", Category.ART, true);
        bookService.save(book);
    }

    @Test
    void findById() {
        Book book = new Book(1L, "제목", "저자", Category.ART, true);
        bookService.save(book);

        Optional<Book> id = bookService.findById(1L);

        Assertions.assertEquals(1, id.get().getId());
    }

    @Test
    void update() {
        Book book = new Book(1L, "제목", "저자", Category.ART, true);
        bookService.save(book);

        Long id = book.getId();
        String title = "수정";

        int update = bookService.update(id, title);

        assertEquals(1, update);
        Optional<Book> findBook = bookService.findById(id);
        assertTrue(findBook.isPresent());
        assertEquals(title, findBook.get().getTitle());
    }

    @Test
    void delete() {
        Book book = new Book(1L, "제목", "저자", Category.ART, true);
        bookService.save(book);

        bookService.delete(book.getId());

        assertFalse(bookService.existById(book.getId()));

    }
}