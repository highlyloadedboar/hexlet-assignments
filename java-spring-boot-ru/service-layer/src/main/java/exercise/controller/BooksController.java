package exercise.controller;

import java.util.List;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.model.Book;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping
    public List<BookDTO> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public BookDTO findBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDTO createBook(@RequestBody BookCreateDTO bookCreateDTO) {
        return bookService.createBook(bookCreateDTO);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookUpdateDTO bookUpdateDTO) {
        return bookService.updateBook(id, bookUpdateDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public BookDTO deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
    // END
}
