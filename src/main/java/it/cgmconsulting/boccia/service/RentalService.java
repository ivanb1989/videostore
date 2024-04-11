package it.cgmconsulting.boccia.service;

import it.cgmconsulting.boccia.entity.*;
import it.cgmconsulting.boccia.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.boccia.payload.response.FilmRentableResponse;
import it.cgmconsulting.boccia.repository.CustomerRepository;
import it.cgmconsulting.boccia.repository.FilmRepository;
import it.cgmconsulting.boccia.repository.RentalIdRepository;
import it.cgmconsulting.boccia.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
@Validated
public class RentalService {

    private final RentalIdRepository rentalIdRepository;
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;
    private final FilmRepository filmRepository;

    public RentalService(RentalIdRepository rentalIdRepository, StoreRepository storeRepository, CustomerRepository customerRepository, FilmRepository filmRepository) {
        this.rentalIdRepository = rentalIdRepository;
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
        this.filmRepository = filmRepository;
    }

    /**********RESTITUISCE IL NUMERO DI NOLEGGI IN UN RANGE DI DATE*********************/
    public ResponseEntity<?> countRentals(long storeId, LocalDate start, LocalDate end) {

        if (!storeRepository.existsById(storeId))
            return new ResponseEntity<>("Store not fond", HttpStatus.BAD_REQUEST);

        //converte il tipo LocalData in LocalDataTime per l'omogeneità con il db
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atStartOfDay();

        //controlla che le date inserite siano nell'ordine corretto e non corrispondano
        if (startDateTime.isAfter(endDateTime))
            return new ResponseEntity<>("The start date must be before the end date", HttpStatus.BAD_REQUEST);
        if (startDateTime.isEqual(endDateTime))
            return new ResponseEntity<>("The start date cannot match the end date", HttpStatus.BAD_REQUEST);

        Optional<List> l = rentalIdRepository.countRentals(storeId, startDateTime, endDateTime);

        return new ResponseEntity<>("Rentals between these dates are " + l.get(), HttpStatus.OK);
    }


    /*****************NOLEGGIA UN FILM O LO RESTITUISCE*******************/
    public ResponseEntity<?> addUpdateRental(long customerId, long filmId, String storeName) {

        //controlla che clienti film e store esistano
        if (!customerRepository.existsById(customerId))
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        if (!filmRepository.existsById(filmId))
            return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);

        if (!storeRepository.existsByStoreName(storeName)) {
            List<String> allStore = storeRepository.findAllStoreName();//nel caso non esista crea una lista degli store esistenti
            return new ResponseEntity<>("Store not found, this is the list of our stores " + allStore, HttpStatus.BAD_REQUEST);
        }
        //Restituisce una lista degli id dei film da restituire in quello store
        List l = rentalIdRepository.getRentedFilmIdByCustomer(customerId, (storeName));
        if (l.isEmpty() || !l.contains(filmId)) { //se li lista è vuota o lista non contiene il film, devo noleggiare
            if (rentalIdRepository.getRentableInventoryIdByFilmId(filmId, storeName).isEmpty()) {//se la lista è vuota il film non è disponibile
                return new ResponseEntity<>("Movie not available ", HttpStatus.BAD_REQUEST);

            } else {
                //Dalla lista degli inventory prende il primo disponibile
                long invId = rentalIdRepository.getRentableInventoryIdByFilmId(filmId, storeName).get(0);
                rentalIdRepository.insertRental(LocalDateTime.now(), customerId, invId);//noleggio
                return new ResponseEntity<>("Rented movie ", HttpStatus.OK);
            }
        } else {
            rentalIdRepository.updateRentalReturn(customerId, filmId, LocalDateTime.now());//restituzione
            return new ResponseEntity<>("Movie returned ", HttpStatus.OK);
        }
    }

    /**********RESTITUISCE LA O LE RESPONSE CON IL MASSIMMO NUMERO DI NOLEGGI***********/
    public ResponseEntity<?> getMaxRentedFilm() {

        List<FilmMaxRentResponse> fmr = rentalIdRepository.getMostRentedFilms();
        if (fmr.isEmpty())//controlla se ci sono noleggi
            return new ResponseEntity<>("No rentals yet", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(fmr, HttpStatus.OK);
    }

    /************RESTITUISCE RESPONSE DI FILM RAGGRUPPATE PER STORE***************/
    public ResponseEntity<?> findRentableFilms(String title) {
        if (!filmRepository.existsByTitle(title))//controlla se il titolo esiste
            return new ResponseEntity<>(title + " does not exist", HttpStatus.BAD_REQUEST);
        //Popola la lista con le relative Response
        List<FilmRentableResponse> frr = rentalIdRepository.findRentableFilms(title);
        return new ResponseEntity<>(frr, HttpStatus.OK);
    }

}






