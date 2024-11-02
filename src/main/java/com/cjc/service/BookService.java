package com.cjc.service;

import java.util.List;

import com.cjc.model.Book;

public interface BookService {

	void addBooks(Book book);

	List<Book> viewAllEbooks();

}
