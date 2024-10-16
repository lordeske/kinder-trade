package com.kinder_figurice.DataTransferModeli;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KorisnikRegistracijaDTO {

        /// DTO za  Registaciju

    private String ime;
    private String prezime;
    private String email;
    private String lozinka; // Lozinka će biti enkodovana unutar servisa
}