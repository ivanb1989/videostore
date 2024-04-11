package it.cgmconsulting.boccia.repository;

import it.cgmconsulting.boccia.entity.FilmStaff;
import it.cgmconsulting.boccia.entity.FilmStaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmStaffIdRepository extends JpaRepository<FilmStaff, FilmStaffId> {



    @Query("SELECT DISTINCT fs.filmStaffId.staffId.staffId " +
            "FROM FilmStaff fs " +
            "WHERE fs.filmStaffId.staffId.staffId IN :staffIds " +
            "AND fs.filmStaffId.roleId.roleId = :roleId")
    List<Long> findStaffIdsWithRole(@Param("staffIds") List<Long> staffIds,
                                       @Param("roleId")long roleId  );






}
