package it.cgmconsulting.boccia.repository;

import it.cgmconsulting.boccia.entity.Film;
import it.cgmconsulting.boccia.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query(value = "SELECT new it.cgmconsulting.boccia.payload.response.FilmResponse(" +
            "f.filmId, " +
            "f.title, " +
            "f.description, " +
            "f.releaseYear, " +
            "f.language.languageName) " +
            "FROM Film f " +
            "JOIN f.language l " +
            "WHERE l.languageId = :id")
    List<FilmResponse> findByLanguageId(@Param("id") long languageId);


    @Query("SELECT new it.cgmconsulting.boccia.payload.response.FilmResponse(" +
            "f.filmId, " +
            "f.title, " +
            "f.description, " +
            "f.releaseYear, " +
            "f.language.languageName) " +
            "FROM FilmStaff fs " +
            "JOIN fs.filmStaffId.filmId f " +
            "WHERE fs.filmStaffId.staffId.staffId IN :staffIds " +
           // "AND fs.filmStaffId.roleId.roleId = 1 " +
            "GROUP BY f.filmId, f.title, f.description, f.releaseYear, f.language.languageName " +
            "HAVING COUNT(fs.filmStaffId.staffId.staffId) = :actorCount")
    List<FilmResponse> findFilmsByActors(@Param("staffIds") List<Long> staffIds, @Param("actorCount") long actorCount);


    boolean existsByTitle(String title);

}

