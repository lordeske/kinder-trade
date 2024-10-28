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



    @GetMapping
    public ResponseEntity<List<Omiljeni>> getOmiljeniByKorisnik(
            @PathVariable Long id
    )
    {
        Optional<Korisnik> korisnik = korisnikServis.nadjiKorisnikaPoID(id);

        if(!korisnik.isPresent())
        {
            return ResponseEntity.notFound().build();
        }

        List<Omiljeni> omiljeni = omiljeniServis.getOmiljeniByKorisnik(korisnik.get());
        return ResponseEntity.ok(omiljeni);

    }

    @PostMapping("{korisnikId}/{figuricaId}")
    public ResponseEntity<Omiljeni> dodajFiguricuUOmiljeno(
            @PathVariable Long idKorisnika,
            @PathVariable Long idFigurice
    )
    {
        Optional<Korisnik> korisnik = korisnikServis.nadjiKorisnikaPoID(idKorisnika);
        Optional<Figurica> figurica = figuricaServis.findById(idFigurice);

        if(!korisnik.isPresent() && !figurica.isPresent())
        {
            return ResponseEntity.notFound().build();
        }

        Omiljeni omiljeni = omiljeniServis.dodajFiguricuUOmiljeni(korisnik.get(), figurica.get());

        return ResponseEntity.ok(omiljeni);

    }



    @DeleteMapping
    public ResponseEntity<Void> obrisiFiguricuIzOmiljeno(
            @PathVariable Long id
    )
    {

        omiljeniServis.obrisiOmiljenu(id);
        return  ResponseEntity.noContent().build();
    }









}
