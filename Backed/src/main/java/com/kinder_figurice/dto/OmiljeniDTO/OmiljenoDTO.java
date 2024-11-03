package com.kinder_figurice.dto.OmiljeniDTO;


import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
import lombok.Data;

import java.util.List;

@Data
public class OmiljenoDTO {


    private Long idKorisnika;

    private List<FiguricaDTO>  figurice;



}
