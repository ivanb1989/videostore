package it.cgmconsulting.boccia.service;


import it.cgmconsulting.boccia.repository.GenreRepository;
import org.springframework.stereotype.Service;



@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }



}
