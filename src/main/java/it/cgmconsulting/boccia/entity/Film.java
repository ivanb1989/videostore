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
public class Film {


    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long filmId;

    @Column(nullable = false,length = 100)
    private String title;

    @Column(nullable = false, length = 65535)
    private String description;

    @Column(nullable = false)
    private short releaseYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="language_id")
    private Language language;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="genre_id")
    private Genre genre;


}


