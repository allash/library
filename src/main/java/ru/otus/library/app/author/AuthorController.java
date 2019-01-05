package ru.otus.library.app.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.app.author.dto.request.DtoCreateOrUpdateAuthorRequest;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/authors")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<DtoGetAuthorResponse> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping("/{id}")
    public DtoGetAuthorResponse getAuthorById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoGetAuthorResponse createAuthor(@Valid @RequestBody DtoCreateOrUpdateAuthorRequest dto) {
        return authorService.createAuthor(dto);
    }

    @PutMapping("/id")
    public DtoGetAuthorResponse updateAuthor(@PathVariable Integer id, @Valid @RequestBody DtoCreateOrUpdateAuthorRequest dto) {
        return authorService.updateAuthor(id, dto);
    }
}
