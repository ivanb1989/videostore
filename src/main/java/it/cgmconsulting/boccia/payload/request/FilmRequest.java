package it.cgmconsulting.boccia.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;


//@Check(constraints = "releaseYear > 1800 AND < 2100")
@Getter
public class FilmRequest {

    @NotBlank
    @Size(max=100)
    private String title;

    @NotBlank
    @Size(min=20,max=65535)
    private String description;

    @NotNull
    @Min(1800)
    @Max(2100)
    private short releaseYear;

    @NotNull
    private long languageId;

    @NotNull
    private long genreId;

}
