package ru.otus.library.app.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.app.genre.dto.request.DtoCreateOrUpdateGenreRequest;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/genres")
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<DtoGetGenreResponse> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping("/{id}")
    public DtoGetGenreResponse getGenreById(@PathVariable Integer id) {
        return genreService.getGenreById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoGetGenreResponse createGenre(@Valid @RequestBody DtoCreateOrUpdateGenreRequest dto) {
        return genreService.createGenre(dto);
    }

    @PutMapping("/id")
    public DtoGetGenreResponse updateGenre(@PathVariable Integer id, @Valid @RequestBody DtoCreateOrUpdateGenreRequest dto) {
        return genreService.updateGenre(id, dto);
    }
}
