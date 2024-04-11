package it.cgmconsulting.boccia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Inventory {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inventoryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="store_id",nullable = false)
    private Store store;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="film_Id",nullable = false)
    private Film film;

    public Inventory(Store store, Film film) {
        this.store = store;
        this.film = film;
    }
}
