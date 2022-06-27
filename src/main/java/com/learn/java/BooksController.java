package com.learn.java;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/rest/book")
public class BooksController {

    @Autowired
    BookRepository bookRepository;

    @Operation(summary = "Get all the books") //this is for documentation.
    @GetMapping(value = "/allBooks",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Book>> getAll()
    {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/getOne/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Book> getOne(@PathVariable Integer id)
    {
        Book b = bookRepository.findById(id).get();

        try {
            System.out.println(b);
        }
        catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(b,HttpStatus.OK);
    }

    @PostMapping(value = "/addBook",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> addBook(@RequestBody Book book)
    {
        bookRepository.save(book);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(value = "/changeBook",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> changeBook(@RequestBody Book book)
    {

        if(book.getId() != null)
        {
            bookRepository.save(book);
        }
        else {
            addBook(book);
        }
        return new ResponseEntity<Book>(book,HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteBook/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id)
    {
        bookRepository.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
