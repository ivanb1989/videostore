package it.cgmconsulting.boccia.service;

import it.cgmconsulting.boccia.entity.Film;
import it.cgmconsulting.boccia.entity.Inventory;
import it.cgmconsulting.boccia.entity.Store;
import it.cgmconsulting.boccia.repository.FilmRepository;
import it.cgmconsulting.boccia.repository.InventoryRepository;
import it.cgmconsulting.boccia.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final FilmRepository filmRepository;
    private final StoreRepository storeRepository;

    public InventoryService(InventoryRepository inventoryRepository, FilmRepository filmRepository, StoreRepository storeRepository) {
        this.inventoryRepository = inventoryRepository;
        this.filmRepository = filmRepository;
        this.storeRepository = storeRepository;
    }


    /**************AGGIUNGE UN RECORD IN INVETORY CON RELATIVO FILM E STORE********************/
    public ResponseEntity<?> addFilm(long storeId, long filmId) {
        //recupero il film e controllo che sia presente
        Optional<Film> f = filmRepository.findById(filmId);
        if (!f.isPresent())
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);

        //recupero lo store e controllo che sia presente
        Optional<Store> s = storeRepository.findById(storeId);
        if (!s.isPresent())
            return new ResponseEntity<>("Store not found", HttpStatus.NOT_FOUND);

        //creo un nuovo inventory e lo aggiungo in db
        inventoryRepository.save(new Inventory(s.get(), f.get()));

        return new ResponseEntity<>("Film added to store", HttpStatus.OK);
    }

}
