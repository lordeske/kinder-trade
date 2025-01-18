package com.kinder_figurice.kontroler;


import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
import com.kinder_figurice.dto.FiguricaDTO.FiguricaPrikaz;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Omiljeni;
import com.kinder_figurice.servisi.FiguricaServis;
import com.kinder_figurice.servisi.KorisnikServis;
import com.kinder_figurice.servisi.OmiljeniServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/moje-omiljene")
    public ResponseEntity<List<FiguricaPrikaz>> getMojeOmiljeneFigurice() {
        List<FiguricaPrikaz> favoriteFigurice = omiljeniServis.getMojeOmiljene();



        return ResponseEntity.ok(favoriteFigurice);
    }

    @PostMapping("/{figuricaId}")
    public ResponseEntity<String> dodajFiguricuUOmiljeno(

            @PathVariable Long figuricaId
    ) {


            omiljeniServis.dodajFiguricuUOmiljeni(figuricaId);
            return new ResponseEntity<>("Figurica je uspjesno dodata u omiljene", HttpStatus.CREATED);

    }

    @DeleteMapping("/{idFigurice}")
    public ResponseEntity<String> obrisiFiguricuIzOmiljeno(
            @PathVariable Long idFigurice
    ) {
        omiljeniServis.obrisiOmiljenu(idFigurice);
        return new ResponseEntity<>("Figurica izbrisana iz omiljenih", HttpStatus.OK);

    }



    @GetMapping("/omiljeno/{figuricaId}")
    public ResponseEntity<Boolean> daLiJeFiguricaUOmiljenima(@PathVariable Long figuricaId) {
        boolean uOmiljenima = omiljeniServis.daLiJeFiguricaUOmiljenim(figuricaId);
        return ResponseEntity.ok(uOmiljenima);
    }








}
