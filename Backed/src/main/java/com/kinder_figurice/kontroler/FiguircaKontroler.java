package com.kinder_figurice.kontroler;

import com.kinder_figurice.dto.FiguricaDTO.*;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.servisi.FiguricaServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/figurice")
public class FiguircaKontroler {

    @Autowired
    private FiguricaServis figuricaServis;

    @GetMapping
    public ResponseEntity<List<Figurica>> pronadjiSveFigurice() {
        List<Figurica> figurice = figuricaServis.findAll();
        return ResponseEntity.ok(figurice);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FiguricaPrikaz> getFiguricaById(@PathVariable Long id) {

            FiguricaPrikaz figuricaPrikaz = figuricaServis.findById(id);
            return ResponseEntity.ok(figuricaPrikaz);

    }


    @PostMapping(value = "/kreiraj", consumes = "multipart/form-data")
    public ResponseEntity<FiguricaDTO> kreirajFiguricu(
            @ModelAttribute FiguricaDTO figuricaDTO,
            @RequestPart("slika") MultipartFile slika) {

        FiguricaDTO kreiranaFigurica = figuricaServis.kreirajFiguricu(figuricaDTO, slika);
        return ResponseEntity.ok(kreiranaFigurica);
    }

    @PutMapping(value = "/azuriraj/{id}", consumes = "multipart/form-data")
    public ResponseEntity<FiguricaDTO> updateFigurica(@PathVariable Long id,
                                                      @ModelAttribute FiguricaUpdateDTO azuriranaFigurica,
                                                    @RequestPart(value = "slika", required = false) MultipartFile slika) {

            FiguricaDTO figuricaDTO = figuricaServis.azurirajFiguricu(azuriranaFigurica, id,slika);
            return ResponseEntity.ok(figuricaDTO);

    }


    @DeleteMapping("/obrisi/{id}")
    public ResponseEntity<Void> deleteFigurica(@PathVariable Long id) {
        figuricaServis.deleteById(id);
        return ResponseEntity.noContent().build();
    }




    @GetMapping("/random")
    public ResponseEntity<List<FiguricaPocetna>> dobaviRandomFigurice(@RequestParam int limit) {
        List<FiguricaPocetna> randomFigurice = figuricaServis.dohvatiRandomFigurice(limit);
        return ResponseEntity.ok(randomFigurice);
    }

    @GetMapping("/profil/{korisnickoIme}")
    public ResponseEntity<List<FiguricaIDPrikaz>> korisnickeFiguricePoImenu(@PathVariable String korisnickoIme) {

            List<FiguricaIDPrikaz> figurice = figuricaServis.sveFiguriceKorisnikaPoImenu(korisnickoIme);
            return new ResponseEntity<>(figurice, HttpStatus.OK);

    }

    @GetMapping("/moje-figurice")
    public ResponseEntity<List<Figurica>> korisnickeFiguricePoImenu() {

        List<Figurica> figurice = figuricaServis.mojeFigurice();
        return new ResponseEntity<>(figurice, HttpStatus.OK);

    }
}
