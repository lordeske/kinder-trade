package com.kinder_figurice.DataTransferModeli;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KorisnikDTO {


    /// Ovo ce da nam bude DTO za slanje podataka medju korisnicima, clean

    private Long idKorisnika;
    private String ime;
    private String prezime;
    private String email;
    private String slikaKorisnika;
}
