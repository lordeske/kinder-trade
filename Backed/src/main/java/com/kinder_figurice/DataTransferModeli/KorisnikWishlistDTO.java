package com.kinder_figurice.DataTransferModeli;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KorisnikWishlistDTO {

    /// Za prikaz one koje je srcnuo da ce da kupi u buducnosti

    private Long idKorisnika;
    private String ime;
    private String prezime;
    private List<FiguricaDTO> wishList; // DTO za figurice


}
