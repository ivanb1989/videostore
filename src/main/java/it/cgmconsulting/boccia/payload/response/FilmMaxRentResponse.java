package it.cgmconsulting.boccia.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmMaxRentResponse {

    private long filmId;
    private String title;
    private long maxRentCount;

    public FilmMaxRentResponse(long filmId, String title) {
        this.filmId = filmId;
        this.title = title;
    }
}
