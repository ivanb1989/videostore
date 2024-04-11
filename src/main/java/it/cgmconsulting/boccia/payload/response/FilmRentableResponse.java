package it.cgmconsulting.boccia.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FilmRentableResponse extends FilmRentResp {

    private long numCopie;
    private long numCopieDisp;

    public FilmRentableResponse(String title, String StoreName, long numCopie, long numCopieDisp) {
        super(title, StoreName);
        this.numCopie = numCopie;
        this.numCopieDisp = numCopieDisp;
    }
}
