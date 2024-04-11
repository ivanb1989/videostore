package it.cgmconsulting.boccia.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class FilmRentResponse extends FilmRentResp{

    private long filmId;

    public FilmRentResponse(String title, String StoreName, long filmId) {
        super(title, StoreName);
        this.filmId = filmId;
    }

    public FilmRentResponse(long filmId) {
        this.filmId = filmId;
    }
}

