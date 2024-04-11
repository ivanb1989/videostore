package it.cgmconsulting.boccia.repository;


import it.cgmconsulting.boccia.entity.Rental;
import it.cgmconsulting.boccia.entity.RentalId;
import it.cgmconsulting.boccia.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.boccia.payload.response.FilmRentableResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentalIdRepository extends JpaRepository<Rental, RentalId> {


    @Query("SELECT COUNT(r) FROM Rental r " +
            "JOIN r.rentalId.inventoryId inv " +
            "WHERE inv.store.storeId = :storeId " +
            "AND r.rentalId.rentalDate BETWEEN :start AND :end")
    Optional<List> countRentals(@Param("storeId") long storeId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO rental (rental_date, customer_id, inventory_id) VALUES (:rentalDate, :customerId, :inventoryId)", nativeQuery = true)
    void insertRental(@Param("rentalDate") LocalDateTime rentalDate, @Param("customerId") long customerId, @Param("inventoryId") long inventoryId);


    @Transactional
    @Modifying
    @Query("UPDATE Rental r " +
            "SET r.rentalReturn = :rentalReturn " +
            "WHERE r.rentalId.inventoryId IN " +
            "(SELECT inv FROM Inventory inv " +
            "JOIN inv.film f " +
            "WHERE f.filmId = :filmId) " +
            "AND r.rentalId.customerId.customerId = :customerId " +
            "AND r.rentalReturn IS NULL")
    void updateRentalReturn(@Param("customerId") long customerId,
                            @Param("filmId") long filmId,
                            @Param("rentalReturn") LocalDateTime rentalReturn);

    @Query("SELECT r.rentalId.inventoryId.film.filmId FROM Rental r " +
            "WHERE r.rentalId.customerId.customerId = :customerId " +
            "AND r.rentalReturn IS NULL " +
            "AND r.rentalId.inventoryId.store.storeName = :storeName"
    )
    List<Long> getRentedFilmIdByCustomer(@Param("customerId") long customerId,@Param("storeName")String storeName);


    @Query("SELECT new it.cgmconsulting.boccia.payload.response.FilmMaxRentResponse(" +
            "r.rentalId.inventoryId.film.filmId, " +
            "r.rentalId.inventoryId.film.title, " +
            "COUNT(r.rentalId.inventoryId.film.filmId)) " +
            "FROM Rental r " +
            "GROUP BY r.rentalId.inventoryId.film.filmId, r.rentalId.inventoryId.film.title " +
            "HAVING COUNT(r.rentalId.inventoryId.film.filmId) = (" +
            "   SELECT MAX(count) " +
            "   FROM (" +
            "       SELECT COUNT(r2.rentalId.inventoryId.film.filmId) AS count " +
            "       FROM Rental r2 " +
            "       GROUP BY r2.rentalId.inventoryId.film.filmId" +
            "   ) AS counts" +
            ")")
    List<FilmMaxRentResponse> getMostRentedFilms();

    @Query("SELECT i.inventoryId " +
            "FROM Inventory i " +
            "WHERE i.film.filmId = :filmId " +
            "AND NOT EXISTS (" +
            "   SELECT r.rentalId.inventoryId " +
            "   FROM Rental r " +
            "   WHERE r.rentalId.inventoryId = i " +
            "   AND r.rentalReturn IS NULL" +
            ") " +
            "AND i.store.storeName = :storeName")
    List<Long> getRentableInventoryIdByFilmId(@Param("filmId") long filmId,
                                              @Param("storeName") String storeName);


    @Query("SELECT DISTINCT new it.cgmconsulting.boccia.payload.response.FilmRentableResponse(" +
            "i.film.title, " +
            "i.store.storeName, " +
            "(SELECT COUNT(i2) FROM Inventory i2 " +
            "WHERE i2.film = i.film " +
            "AND i2.store = i.store), " +
            "(SELECT COUNT(i3) FROM Inventory i3 " +
            "WHERE i3.film.title = :title " +
            "AND i3.store = i.store " +
            "AND NOT EXISTS (" +
            "   SELECT r FROM Rental r " +
            "   WHERE r.rentalId.inventoryId = i3 " +
            "   AND r.rentalReturn IS NULL))) " +
            "FROM Inventory i " +
            "WHERE i.film.title = :title "
    )
    List<FilmRentableResponse> findRentableFilms(@Param("title") String title);

}