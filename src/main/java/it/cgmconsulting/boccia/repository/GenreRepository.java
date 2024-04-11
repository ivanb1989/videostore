package it.cgmconsulting.boccia.repository;

import it.cgmconsulting.boccia.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
