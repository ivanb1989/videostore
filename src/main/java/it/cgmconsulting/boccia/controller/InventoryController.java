package it.cgmconsulting.boccia.controller;


import it.cgmconsulting.boccia.service.InventoryService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InventoryController {

    private final InventoryService inventoryService;


    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;

    }
    @Transactional
    @PutMapping("/add-film-to-store/{storeId}/{filmId}")
    public ResponseEntity<?> addFilm(@PathVariable long storeId, @PathVariable long filmId) {
        return inventoryService.addFilm(storeId, filmId);
    }

}
