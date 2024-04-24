package com.project.library.repository;

import com.project.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    List<Book> findListByTitle(String title);

    @Modifying(clearAutomatically = true)
    @Query("update Book b set b.title = :title where b.id = :id")
    int update(@Param("id") Long id, @Param("title") String title);

}
