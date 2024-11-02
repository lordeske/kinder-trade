package com.kinder_figurice.dto.FiguricaDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FiguricaUpdateDTO {


    private String naslov;

    private String opis;

    private BigDecimal cena;

    private String stanje;

    private String slikaUrl;

    private String kategorija;


}
