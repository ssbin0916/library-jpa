package com.project.library.controller;

import com.project.library.domain.Book;
import com.project.library.domain.Member;
import com.project.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public String books(Model model, @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);

        // 현재 로그인한 회원 정보를 모델에 추가합니다.
        model.addAttribute("member", loginMember);

        return "books/books";
    }

    @GetMapping("/{id}")
    public String Book(@PathVariable("id") Long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/book";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("book") Book book) {
        return "books/addForm";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/addForm";
        }

        Optional<Book> existBook = bookService.findByTitle(book.getTitle());
        if (existBook.isPresent()) {
            bindingResult.rejectValue("title", "title", "이미 존재하는 책입니다.");
            return "books/addForm";
        }

        bookService.save(book);
        return "redirect:/books/books";

    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/books/books";
    }
}
