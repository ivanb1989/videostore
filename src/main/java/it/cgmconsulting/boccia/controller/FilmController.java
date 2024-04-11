package it.cgmconsulting.boccia.controller;

import it.cgmconsulting.boccia.payload.request.FilmRequest;
import it.cgmconsulting.boccia.service.CustomerService;
import it.cgmconsulting.boccia.service.FilmService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("film")
public class FilmController {

    private final FilmService filmService;
    private final CustomerService customerService;


    public FilmController(FilmService filmService, CustomerService customerService) {
        this.filmService = filmService;
        this.customerService = customerService;
    }

    @GetMapping("/find-all-films-rent-by-one-customer/{customerId}")
    public ResponseEntity<?> findFilmsByCustomers(@PathVariable long customerId) {
        return filmService.findFilmsRentByCustomers(customerId);
    }

    @Transactional
    @PatchMapping("/update-film/{filmId}")
    public ResponseEntity<?> update(@PathVariable long filmId, @Valid @RequestBody FilmRequest filmRequest) {

        return filmService.update(filmId, filmRequest);
    }

    @GetMapping("/find-films-by-language/{languageId}")
    public ResponseEntity<?> findFilmByLanguage(@PathVariable long languageId) {
        return filmService.findFilmByLanguage(languageId);
    }

    @GetMapping("/find-films-by-actors")
    public ResponseEntity<?> findFilmByActor(@RequestParam List<Long> staffId) {
        return filmService.findFilmByActor(staffId);
    }
}
