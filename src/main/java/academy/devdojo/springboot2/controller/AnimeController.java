package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("animes")
@RequiredArgsConstructor // construtor para atributos "final"
public class AnimeController {

    //@Autowired -> spring boot instância para você
    private final AnimeService animeService;

    @GetMapping
    @Operation(summary = "List all animes paginated and sorted",
    description = "To use pagination and sort add the params ?page='number'&sort='field' to the url",
    tags = {"anime"})
    public ResponseEntity<Page<Anime>> listAll(Pageable pageable) {
        return ResponseEntity.ok(animeService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) { //(@PathVariable("id") int id)
        log.info("User logged in {}", userDetails);
        return ResponseEntity.ok(animeService.findById(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid Anime anime) {
        return ResponseEntity.ok(animeService.save(anime));
    }

    @DeleteMapping(path = "/admin/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Anime not found")
    })
    public ResponseEntity<Anime> delete(@PathVariable int id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Anime> update(@RequestBody Anime anime) {
        animeService.update(anime);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
