package it.cgmconsulting.boccia.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Staff {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long staffId;

    @Column(nullable = false,length = 50)
    private String firstname;

    @Column(nullable = false,length = 50)
    private String lastname;


    private LocalDate dob;//date of birth
}
