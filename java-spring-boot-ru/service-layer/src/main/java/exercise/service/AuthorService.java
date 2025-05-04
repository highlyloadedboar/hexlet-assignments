package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    // BEGIN
    @Transactional(readOnly = true)
    public List<AuthorDTO> findAllAuthors() {
        return authorRepository.findAll().stream().map(authorMapper::map).toList();
    }

    @Transactional(readOnly = true)
    public AuthorDTO findAuthorById(Long id) {
        return authorMapper.map(getOrElseThrow(id));
    }

    @Transactional
    public AuthorDTO createAuthor(AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.map(authorCreateDTO);
        authorRepository.save(author);

        return authorMapper.map(author);
    }

    @Transactional
    public AuthorDTO updateAuthor(Long id, AuthorUpdateDTO authorUpdateDTO) {
        Author author = getOrElseThrow(id);
        authorMapper.update(authorUpdateDTO, author);

        authorRepository.save(author);

        return authorMapper.map(author);
    }

    @Transactional
    public AuthorDTO deleteAuthor(Long id) {
        Author author = getOrElseThrow(id);

        authorRepository.delete(author);

        return authorMapper.map(author);
    }

    private Author getOrElseThrow(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found!"));
    }
    // END
}
