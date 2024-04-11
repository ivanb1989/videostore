package it.cgmconsulting.boccia.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Customer {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    @Column(nullable = false,length = 50)
    private String firstname;

    @Column(nullable = false,length = 50)
    private String lastname;

    @Column(nullable = false,length = 100,unique = true)
    private String email;
}
