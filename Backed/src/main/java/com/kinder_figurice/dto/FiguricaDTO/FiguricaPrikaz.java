package com.kinder_figurice.dto.FiguricaDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FiguricaPrikaz {

    private Long idFigurice;

    private String naslov;

    private String opis;

    private BigDecimal cena;

    private String stanje;

    private String slikaUrl;

    private String kategorija;

    private LocalDateTime datumKreiranja;

    private String vlasnikFigurice;

}
