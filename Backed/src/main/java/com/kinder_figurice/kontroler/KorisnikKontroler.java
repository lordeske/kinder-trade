package com.kinder_figurice.kontroler;


import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.servisi.KorisnikServis;
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
        return new ResponseEntity<>(korisnici, HttpStatus.OK); // Vraća 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<Korisnik> nadjiKorisnika(@PathVariable Long id) {
        Optional<Korisnik> korisnik = korisnikServis.nadjiKorisnikaPoID(id);
        if (korisnik.isPresent()) {
            return new ResponseEntity<>(korisnik.get(), HttpStatus.OK); // Vraća 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vraća 404 NOT FOUND
        }
    }


    @PostMapping
    public ResponseEntity<Korisnik> dodajKorisnika(@RequestBody Korisnik korisnik) {
        Korisnik noviKorisnik = korisnikServis.sacuvajKorisnika(korisnik);
        return new ResponseEntity<>(noviKorisnik, HttpStatus.CREATED); // Vraća 201 CREATED
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiKorisnika(@PathVariable Long id) {
        Optional<Korisnik> korisnik = korisnikServis.nadjiKorisnikaPoID(id);
        if (korisnik.isPresent()) {
            korisnikServis.obrisiKorisnika(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vraća 204 NO CONTENT
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vraća 404 NOT FOUND
        }
    }


}
