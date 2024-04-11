package it.cgmconsulting.boccia.service;

import it.cgmconsulting.boccia.entity.Film;
import it.cgmconsulting.boccia.entity.Genre;
import it.cgmconsulting.boccia.entity.Language;
import it.cgmconsulting.boccia.payload.request.FilmRequest;
import it.cgmconsulting.boccia.payload.response.FilmRentResponse;
import it.cgmconsulting.boccia.payload.response.FilmResponse;
import it.cgmconsulting.boccia.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final CustomerRepository customerRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;
    private final StaffRepository staffRepository;
    private final FilmStaffIdRepository filmStaffIdRepository;

    public FilmService(FilmRepository filmRepository, CustomerRepository customerRepository, LanguageRepository languageRepository, GenreRepository genreRepository, StaffRepository staffRepository, FilmStaffIdRepository filmStaffIdRepository) {
        this.filmRepository = filmRepository;
        this.customerRepository = customerRepository;
        this.languageRepository = languageRepository;
        this.genreRepository = genreRepository;
        this.staffRepository = staffRepository;
        this.filmStaffIdRepository = filmStaffIdRepository;
    }

    /*****RESTITUISCE UNA LISTA DEI FILM NOLEGGIATI DAL CLIENTE SELEZIONATO*****/
    public ResponseEntity<?> findFilmsRentByCustomers(long customerId) {

        //controlla che l'id corrisponda ad un customers esistente
        if (!filmRepository.existsById(customerId))
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);

        //popola la lista con la response
        List<FilmRentResponse> frr = customerRepository.findFilmsRentByCustomers(customerId);

        if (frr.isEmpty())//controlla se ha noleggiato
            return new ResponseEntity<>("This customer has never rented", HttpStatus.OK);
        return new ResponseEntity(frr, HttpStatus.OK);
    }


    /*************ESEGUE UN UPDATE SUL FILM RESTIUTIO DALL'ID************/
    public ResponseEntity<?> update(long filmId, FilmRequest filmRequest) {

        //recupera il film e controllo che sia presente
        Optional<Film> f = filmRepository.findById(filmId);
        if (!f.isPresent())
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);

        //setta i parametri
        f.get().setTitle(filmRequest.getTitle());
        f.get().setDescription(filmRequest.getDescription());
        f.get().setReleaseYear(filmRequest.getReleaseYear());

        //recupera la lingua tramite languageId nella FilmRequest
        Optional<Language> lan = languageRepository.findById(filmRequest.getLanguageId());
        if (!lan.isPresent())
            return new ResponseEntity<>("Language not found", HttpStatus.NOT_FOUND);
        f.get().setLanguage(lan.get());//setto la lingua trovata

        //recupera il genere tramite genreId nella FilmRequest
        Optional<Genre> gen = genreRepository.findById(filmRequest.getGenreId());
        if (!gen.isPresent())
            return new ResponseEntity<>("Genre not found", HttpStatus.NOT_FOUND);
        f.get().setGenre(gen.get());//setto il genere trovato

        return new ResponseEntity<>(f.get().getTitle() + " updated", HttpStatus.OK);
    }


    /****************RESTITUISCE UNA LISTA DI FILM SECONDO LA LINGUA SELEZIONATA**************/
    public ResponseEntity<?> findFilmByLanguage(long languageId) {

        //controlla che l'id abbia una corrispondenza
        if (!languageRepository.existsById(languageId))
            return new ResponseEntity<>("Language not found", HttpStatus.BAD_REQUEST);

        //Popola la lista e controllo che non sia vuota
        List<FilmResponse> fr = filmRepository.findByLanguageId(languageId);
        if (fr.isEmpty())
            return new ResponseEntity<>("None films in this language", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(fr, HttpStatus.OK);
    }


    /**************RESTITUISCE UNA LISTA DEI FILM DOVE HANNO COLLABORATO GLI ATTORI SELEZIONATI**************/
    public ResponseEntity<?> findFilmByActor(List<Long> staffId) {

        //controlla che gli staffId esistano
        for (long l : staffId)
            if (!staffRepository.existsById(l))
                return new ResponseEntity<>("Staff with id  " + l + " does not exist", HttpStatus.BAD_REQUEST);

        //Inizializza il ruolo che voglio filtrare
        long roleId = 1;

        //controlla che tutti i ruoli siano attori
        List<Long> listStaffId = filmStaffIdRepository.findStaffIdsWithRole(staffId, roleId);

        //imposta il numero di staffId passati
        int count = listStaffId.size();

        //Crea una lista di filmResponse secondo la lista di staffId data
        List<FilmResponse> lfr = filmRepository.findFilmsByActors(listStaffId, count);

        if (lfr.isEmpty())// Controlla se l'elenco non è vuoto
            return new ResponseEntity<>("There are no movies where the requested staff participated together", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(lfr, HttpStatus.OK);
    }

}


