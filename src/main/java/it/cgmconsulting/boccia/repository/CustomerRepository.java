package it.cgmconsulting.boccia.repository;

import it.cgmconsulting.boccia.entity.Customer;
import it.cgmconsulting.boccia.payload.response.FilmRentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query("SELECT new it.cgmconsulting.boccia.payload.response.FilmRentResponse(" +
            "r.rentalId.inventoryId.film.title, " +
            "r.rentalId.inventoryId.store.storeName, " +
            "r.rentalId.inventoryId.film.filmId) " +

            "FROM Rental r " +
            "WHERE r.rentalId.customerId.customerId = :customerId")
    List<FilmRentResponse> findFilmsRentByCustomers(@Param("customerId") long customerId);

}
