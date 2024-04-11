package it.cgmconsulting.boccia.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;


@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RentalId {



    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="customer_id")
    private Customer customerId;

    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="inventory_id")
    private Inventory inventoryId;


    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime rentalDate;

    public RentalId(Customer customerId, Inventory inventoryId) {
        this.customerId = customerId;
        this.inventoryId = inventoryId;
    }
}
