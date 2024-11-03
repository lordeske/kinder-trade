package com.kinder_figurice.kontroler;


import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
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

    @GetMapping("/{korisnikId}")
    public ResponseEntity<List<FiguricaDTO>> listFavoriteFigurice(@PathVariable Long korisnikId) {
        List<FiguricaDTO> favoriteFigurice = omiljeniServis.getOmiljeniByKorisnikId(korisnikId);

        if (favoriteFigurice.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(favoriteFigurice);
    }

    @PostMapping("/{korisnikId}/{figuricaId}")
    public ResponseEntity<Omiljeni> dodajFiguricuUOmiljeno(
            @PathVariable Long korisnikId,
            @PathVariable Long figuricaId
    ) {

        try {
            Omiljeni omiljeni = omiljeniServis.dodajFiguricuUOmiljeni(korisnikId, figuricaId);
            return new ResponseEntity<>(omiljeni, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiFiguricuIzOmiljeno(
            @PathVariable Long id
    ) {
        Boolean jelObrisana = omiljeniServis.obrisiOmiljenu(id);

        if(jelObrisana)
        {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }









}
