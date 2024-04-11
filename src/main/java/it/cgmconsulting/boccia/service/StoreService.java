package it.cgmconsulting.boccia.service;


import it.cgmconsulting.boccia.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }


    /**************RESTITUISCE IL NUMERO DI CLIENTI PER UNO SPECIFICO STORE**********************/
    public ResponseEntity<?> countCustomersByStoreName(String storeName) {

        //controlla che il nome sia presente
        if (!storeRepository.existsByStoreName(storeName))
            return new ResponseEntity<>(storeName + " not found", HttpStatus.NOT_FOUND);

        //recupera il count dei customers già distinti dalla query
        Optional<Long> count = storeRepository.countCustomersByStoreName(storeName);
        if (!count.isPresent())
            return new ResponseEntity<>("No costumers for " + storeName + " store", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(storeName + " has " + count.get() + " customers", HttpStatus.OK);
    }

}
