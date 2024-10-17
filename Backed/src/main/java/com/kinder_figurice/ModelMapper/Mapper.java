package com.kinder_figurice.ModelMapper;

import com.kinder_figurice.Modeli.Figurica;
import com.kinder_figurice.Modeli.Korisnik;
import com.kinder_figurice.DataTransferModeli.FiguricaDTO;
import com.kinder_figurice.DataTransferModeli.KorisnikDTO;
import com.kinder_figurice.DataTransferModeli.KorisnikRegistracijaDTO;
import com.kinder_figurice.DataTransferModeli.KorisnikWishlistDTO;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    // Mapiranje KorisnikDTO u Korisnik
    public static Korisnik toEntity(KorisnikRegistracijaDTO korisnikRegistracijaDTO) {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(korisnikRegistracijaDTO.getIme());
        korisnik.setPrezime(korisnikRegistracijaDTO.getPrezime());
        korisnik.setEmail(korisnikRegistracijaDTO.getEmail());
        // Lozinka će biti enkodovana u servisu
        return korisnik;
    }

    // Mapiranje Korisnik u KorisnikDTO
    public static KorisnikDTO toDTO(Korisnik korisnik) {
        KorisnikDTO korisnikDTO = new KorisnikDTO();

        korisnikDTO.setIme(korisnik.getIme());
        korisnikDTO.setPrezime(korisnik.getPrezime());
        korisnikDTO.setEmail(korisnik.getEmail());
        korisnikDTO.setSlikaKorisnika(korisnik.getSlikaKorisnika());
        return korisnikDTO;
    }

    // Mapiranje FiguricaDTO u Figurica
    public static Figurica toEntity(FiguricaDTO figuricaDTO, Korisnik korisnik) {
        Figurica figurica = new Figurica();

        figurica.setNazivFigurice(figuricaDTO.getNazivFigurice());
        figurica.setSerijaFigurice(figuricaDTO.getSerijaFigurice());
        figurica.setGodinaIzdanja(figuricaDTO.getGodinaIzdanja());
        figurica.setOpisFigurice(figuricaDTO.getOpisFigurice());
        figurica.setSlikaFigurice(figuricaDTO.getSlikaFigurice());
        figurica.setKorisnik(korisnik); // Postavljanje vlasnika
        return figurica;
    }


    // Mapiranje Figurica u FiguricaDTO

    public static FiguricaDTO toDTO(Figurica figurica) {
        FiguricaDTO figuricaDTO = new FiguricaDTO();

        figuricaDTO.setNazivFigurice(figurica.getNazivFigurice());
        figuricaDTO.setSerijaFigurice(figurica.getSerijaFigurice());
        figuricaDTO.setGodinaIzdanja(figurica.getGodinaIzdanja());
        figuricaDTO.setOpisFigurice(figurica.getOpisFigurice());
        figuricaDTO.setSlikaFigurice(figurica.getSlikaFigurice());

        // Dodavanje ID korisnika
        if (figurica.getKorisnik() != null) {
            figuricaDTO.setIdKorisnika(figurica.getKorisnik().getIdKorisnika());
        }

        return figuricaDTO;
    }


    // Mapiranje Korisnik u KorisnikWishlistDTO
    public static KorisnikWishlistDTO toWishlistDTO(Korisnik korisnik) {
        KorisnikWishlistDTO wishlistDTO = new KorisnikWishlistDTO();
        wishlistDTO.setIdKorisnika(korisnik.getIdKorisnika());
        wishlistDTO.setIme(korisnik.getIme());
        wishlistDTO.setPrezime(korisnik.getPrezime());
        wishlistDTO.setWishList(korisnik.getWishList().stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList()));
        return wishlistDTO;
    }
}
