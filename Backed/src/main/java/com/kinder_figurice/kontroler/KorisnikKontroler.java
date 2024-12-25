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


    @GetMapping("/{id}")
    public ResponseEntity<Korisnik> nadjiKorisnika(@PathVariable Long id) {
        Optional<Korisnik> korisnik = korisnikServis.nadjiKorisnikaPoID(id);
        if (korisnik.isPresent()) {
            return new ResponseEntity<>(korisnik.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Korisnik> azurirajKorisnika(@PathVariable Long id, @RequestBody AzurirajKorisnikaDTO azuriraniKorisnik) {
        try {
            Korisnik izmenjenKorisnik = korisnikServis.azurirajKorisnika(id, azuriraniKorisnik);
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



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiKorisnika(@PathVariable Long id) {
        Optional<Korisnik> korisnik = korisnikServis.nadjiKorisnikaPoID(id);
        if (korisnik.isPresent()) {
            korisnikServis.obrisiKorisnika(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
