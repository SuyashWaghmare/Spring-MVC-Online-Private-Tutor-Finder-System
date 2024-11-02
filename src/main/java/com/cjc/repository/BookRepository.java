package com.cjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cjc.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
