package it.cgmconsulting.boccia.repository;


import it.cgmconsulting.boccia.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT COUNT(DISTINCT r.rentalId.customerId) FROM Rental r " +
            "JOIN r.rentalId.inventoryId inv " +
            "JOIN inv.store s " +
            "WHERE s.storeName = :storeName")
    Optional<Long> countCustomersByStoreName(@Param("storeName") String storeName);

    Optional<Store> findByStoreName(String storeName);


    @Query("SELECT s.storeName FROM Store s")
    List<String> findAllStoreName();


    boolean existsByStoreName(String storeName);
}