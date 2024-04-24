package com.project.library.service;

import com.project.library.domain.Book;
import com.project.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book save(Book book) {
        validateBook(book);
        return bookRepository.save(book);
    }

    private void validateBook(Book book) {
        List<Book> findBooks = bookRepository.findListByTitle(book.getTitle());
        if (!findBooks.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 책입니다.");
        }
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional
    public int update(Long id, String title) {
        return bookRepository.update(id, title);
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public boolean existById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.isPresent();
    }
}
