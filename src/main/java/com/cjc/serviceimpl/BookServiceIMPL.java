package com.cjc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjc.model.Book;
import com.cjc.repository.BookRepository;
import com.cjc.service.BookService;

@Service
public class BookServiceIMPL implements BookService {

	@Autowired
	private BookRepository bookrepo;

	@Override
	public void addBooks(Book book) {
		bookrepo.save(book);

	}

	@Override
	public List<Book> viewAllEbooks() {

		return bookrepo.findAll();
	}

}
