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


    @PutMapping("/azuriraj/{korisnickoIme}")
    public ResponseEntity<Korisnik> azurirajKorisnika(@PathVariable String korisnickoIme, @RequestBody AzurirajKorisnikaDTO azuriraniKorisnik) {
        try {
            Korisnik izmenjenKorisnik = korisnikServis.azurirajKorisnika(korisnickoIme, azuriraniKorisnik);
            return new ResponseEntity<>(izmenjenKorisnik, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping
    public ResponseEntity<Korisnik> kreirajKorisnika(@RequestBody RegistracijaDTO korisnik) {
        try {
            Korisnik noviKorisnik = korisnikServis.kreirajKorisnika(korisnik);
            return new ResponseEntity<>(noviKorisnik, HttpStatus.CREATED);
        } catch (EmailConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }



    @DeleteMapping("/obrisi/{korisnickoIme}")
    public ResponseEntity<String> obrisiKorisnika(@PathVariable String korisnickoIme) {
        try {
            korisnikServis.obrisiKorisnika(korisnickoIme);
            return ResponseEntity.ok("Korisnik sa imenom " + korisnickoIme + " je uspesno obrisan.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Doslo je do greske.");
        }
    }



    @GetMapping("/predlozeni/{trenutniKorisnik}")
    public List<PrikazKorisnikaDrugimaDTO> prikaziPredlozeneKorisnike(
            @PathVariable String trenutniKorisnik
    ) {

        return korisnikServis.prikaziPredlozeneKorisnike(trenutniKorisnik);
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
