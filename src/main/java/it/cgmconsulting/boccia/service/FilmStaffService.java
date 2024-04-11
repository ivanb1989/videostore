package it.cgmconsulting.boccia.service;

import it.cgmconsulting.boccia.repository.FilmStaffIdRepository;
import org.springframework.stereotype.Service;

@Service
public class FilmStaffService {

    private final FilmStaffIdRepository filmStaffIdRepository;

    public FilmStaffService(FilmStaffIdRepository filmStaffIdRepository) {
        this.filmStaffIdRepository = filmStaffIdRepository;
    }

}
