package com.kinder_figurice.kontroler;


import com.kinder_figurice.dto.KorisnikDTO.AzurirajKorisnikaDTO;
import com.kinder_figurice.dto.KorisnikDTO.PrikazKorisnikaDrugimaDTO;
import com.kinder_figurice.dto.KorisnikDTO.RegistracijaDTO;
import com.kinder_figurice.exceptions.EmailConflictException;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.servisi.KorisnikServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/korisnici")
public class KorisnikKontroler {



    @Autowired
    private KorisnikServis korisnikServis;


    @GetMapping
    public ResponseEntity<List<Korisnik>> sviKorisnici() {
        List<Korisnik> korisnici = korisnikServis.sviKorisnici();
        return new ResponseEntity<>(korisnici, HttpStatus.OK);
    }


    @PutMapping(value = "/azuriraj", consumes = "multipart/form-data")
    public ResponseEntity<Korisnik> azurirajKorisnika(
            @ModelAttribute AzurirajKorisnikaDTO azuriraniKorisnik,
            @RequestPart(value = "slika", required = false) MultipartFile slika) {

        Korisnik izmenjenKorisnik = korisnikServis.azurirajKorisnika(azuriraniKorisnik, slika);
        return ResponseEntity.ok(izmenjenKorisnik);
    }




    @PostMapping
    public ResponseEntity<Korisnik> kreirajKorisnika(@RequestBody RegistracijaDTO korisnik) {

            Korisnik noviKorisnik = korisnikServis.kreirajKorisnika(korisnik);
            return new ResponseEntity<>(noviKorisnik, HttpStatus.CREATED);

    }



    @DeleteMapping("/obrisi/{korisnickoIme}")
    public ResponseEntity<String> obrisiKorisnika(@PathVariable String korisnickoIme) {

            korisnikServis.obrisiKorisnika(korisnickoIme);
            return ResponseEntity.ok("Korisnik sa imenom " + korisnickoIme + " je uspesno obrisan.");

    }



    @GetMapping("/predlozeni/{trenutniKorisnik}")
    public List<PrikazKorisnikaDrugimaDTO> prikaziPredlozeneKorisnike(
            @PathVariable String trenutniKorisnik
    ) {

        return korisnikServis.prikaziPredlozeneKorisnike(trenutniKorisnik);
    }


    @GetMapping("/loginprofile/me")
    public ResponseEntity<Korisnik> prikaziMojProfil() {


        return ResponseEntity.ok(korisnikServis.prikaziMojProfil());


    }






    @GetMapping("/profil/{imeKorisnika}")
    public ResponseEntity<PrikazKorisnikaDrugimaDTO> prikaziProfilKorisnika(
            @PathVariable String imeKorisnika
    )
    {
        try {

            PrikazKorisnikaDrugimaDTO korisnik = korisnikServis.nadjiKorisnikaPoImenu(imeKorisnika);
            return new ResponseEntity<>(korisnik , HttpStatus.OK);

        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
