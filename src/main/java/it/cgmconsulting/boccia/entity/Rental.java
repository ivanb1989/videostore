package it.cgmconsulting.boccia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Rental {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private RentalId rentalId;



    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime rentalReturn;

}
