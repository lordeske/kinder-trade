package com.kinder_figurice.DataTransferModeli;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiguricaDTO {




        private String nazivFigurice;
        private String serijaFigurice;
        private String godinaIzdanja;
        private String opisFigurice;
        private String slikaFigurice;
        private Long idKorisnika;

}
