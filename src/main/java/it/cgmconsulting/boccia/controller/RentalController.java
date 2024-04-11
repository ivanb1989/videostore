package it.cgmconsulting.boccia.controller;

import it.cgmconsulting.boccia.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/count-rentals-in-date-range-by-store/{storeId}")
    ResponseEntity<?> countRentals(@PathVariable long storeId,
                                   @RequestParam  LocalDate start,
                                   @RequestParam  LocalDate end) {

        return rentalService.countRentals(storeId, start, end);
    }

    @PutMapping("/add-update-rental")
    public ResponseEntity<?> addUpdateRental(@RequestParam long customerId,
                                             @RequestParam long filmId,
                                             @RequestParam String storeName) {

        return rentalService.addUpdateRental(customerId, filmId, storeName);
    }

    @GetMapping("/find-film-with-max-number-of-rent")
    public ResponseEntity<?> getMaxRentedFilm() {
        return rentalService.getMaxRentedFilm();
    }

    @GetMapping("/find-rentable-films")
    public ResponseEntity<?> findRentableFilms(@RequestParam String title) {

        return rentalService.findRentableFilms(title);
    }


}
