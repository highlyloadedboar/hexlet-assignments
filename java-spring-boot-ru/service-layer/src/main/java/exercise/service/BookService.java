package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.model.Book;
import exercise.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    // BEGIN
    @Transactional(readOnly = true)
    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::map).toList();
    }

    @Transactional(readOnly = true)
    public BookDTO findBookById(Long id) {
        return bookMapper.map(getOrElseThrowException(id));
    }

    @Transactional
    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        Book book = bookMapper.map(bookCreateDTO);

        bookRepository.save(book);

        return bookMapper.map(book);
    }

    @Transactional
    public BookDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        Book book = getOrElseThrowException(id);

        bookMapper.update(bookUpdateDTO, book);

        bookRepository.save(book);

        return bookMapper.map(book);
    }

    @Transactional
    public BookDTO deleteBook(Long id) {
        Book book = getOrElseThrowException(id);

        bookRepository.deleteById(id);

        return bookMapper.map(book);
    }


    private Book getOrElseThrowException(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "not found!"));
    }
    // END
}
