package com.kinder_figurice.dto.KorisnikDTO;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrikazKorisnikaDrugimaDTO {



    private String korisnickoIme;

    private String slika;

    private LocalDateTime datumKreiranja;



}
