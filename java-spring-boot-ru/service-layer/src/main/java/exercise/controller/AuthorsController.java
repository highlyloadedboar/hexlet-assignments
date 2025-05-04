package exercise.controller;

import exercise.dto.AuthorDTO;
import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping
    public List<AuthorDTO> findAllAuthors() {
        return authorService.findAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDTO findAuthorById(@PathVariable Long id) {
        return authorService.findAuthorById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthorDTO createAuthor(@RequestBody AuthorCreateDTO authorCreateDTO) {
        return authorService.createAuthor(authorCreateDTO);
    }

    @PutMapping("/{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody AuthorUpdateDTO authorUpdateDTO) {
        return authorService.updateAuthor(id, authorUpdateDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public AuthorDTO deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id);
    }
    // END
}
