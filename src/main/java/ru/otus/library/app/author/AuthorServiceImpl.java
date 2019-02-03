package ru.otus.library.app.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.app.author.dto.request.DtoCreateOrUpdateAuthorRequest;
import ru.otus.library.app.author.dto.response.DtoGetAuthorBookResponse;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.repositories.interfaces.AuthorRepositoryJpa;
import ru.otus.library.domain.repositories.interfaces.BookRepositoryJpa;
import ru.otus.library.shared.exceptions.author.AuthorNotFoundByIdException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepositoryJpa authorRepository;
    private BookRepositoryJpa bookRepository;
    private AuthorMapper mapper;

    @Autowired
    public AuthorServiceImpl(
            AuthorRepositoryJpa authorRepository,
            BookRepositoryJpa bookRepository,
            AuthorMapper mapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DtoGetAuthorResponse> getAuthors() {
        return mapper.toList(authorRepository.findAll());
    }

    @Override
    public DtoGetAuthorResponse getAuthorById(Long id) {

        Optional<DbAuthor> author = authorRepository.findById(id);
        if (!author.isPresent()) throw new AuthorNotFoundByIdException(id);

        return mapper.toDto(author.get());
    }

    @Override
    public DtoGetAuthorResponse createAuthor(DtoCreateOrUpdateAuthorRequest dto) {
        DbAuthor author = new DbAuthor(dto.getFirstName(), dto.getLastName());
        return mapper.toDto(authorRepository.save(author));
    }

    @Override
    public DtoGetAuthorResponse updateAuthor(Long id, DtoCreateOrUpdateAuthorRequest dto) {

        Optional<DbAuthor> authorOptional = authorRepository.findById(id);
        if (!authorOptional.isPresent()) throw new AuthorNotFoundByIdException(id);

        DbAuthor author = authorOptional.get();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());

        return mapper.toDto(authorRepository.save(author));
    }

    @Override
    public List<DtoGetAuthorBookResponse> getBooksByAuthorId(Long id) {
        Optional<DbAuthor> author = authorRepository.findById(id);
        if (!author.isPresent()) throw new AuthorNotFoundByIdException(id);
        return mapper.toDto(bookRepository.findByAuthorsId(id));
    }
}
