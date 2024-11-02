package com.kinder_figurice.kontroler;


import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Omiljeni;
import com.kinder_figurice.servisi.FiguricaServis;
import com.kinder_figurice.servisi.KorisnikServis;
import com.kinder_figurice.servisi.OmiljeniServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/omiljeni")
public class OmiljeniKontroler {

    @Autowired
    private OmiljeniServis omiljeniServis;

    @Autowired
    private KorisnikServis korisnikServis;

    @Autowired
    private FiguricaServis figuricaServis;

    @GetMapping("/{id}")
    public ResponseEntity<List<Omiljeni>> getOmiljeniByKorisnik(
            @PathVariable Long id
    ) {
        Optional<Korisnik> korisnik = korisnikServis.nadjiKorisnikaPoID(id);

        if (!korisnik.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<Omiljeni> omiljeni = omiljeniServis.getOmiljeniByKorisnik(korisnik.get());
        return ResponseEntity.ok(omiljeni);
    }

    @PostMapping("/{korisnikId}/{figuricaId}")
    public ResponseEntity<Omiljeni> dodajFiguricuUOmiljeno(
            @PathVariable Long korisnikId,
            @PathVariable Long figuricaId
    ) {
        Optional<Korisnik> korisnik = korisnikServis.nadjiKorisnikaPoID(korisnikId);
        Optional<Figurica> figurica = figuricaServis.findById(figuricaId);

        if (!korisnik.isPresent() || !figurica.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Omiljeni omiljeni = omiljeniServis.dodajFiguricuUOmiljeni(korisnik.get(), figurica.get());
            return ResponseEntity.ok(omiljeni);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiFiguricuIzOmiljeno(
            @PathVariable Long id
    ) {
        omiljeniServis.obrisiOmiljenu(id);
        return ResponseEntity.noContent().build();
    }









}
