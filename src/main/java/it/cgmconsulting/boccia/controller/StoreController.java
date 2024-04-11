package it.cgmconsulting.boccia.controller;

import it.cgmconsulting.boccia.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }


    @GetMapping("/count-customers-by-store/{storeName}")
    public ResponseEntity<?>countCustomersByStoreName(@PathVariable String storeName){

        return storeService.countCustomersByStoreName(storeName);
    }



}
