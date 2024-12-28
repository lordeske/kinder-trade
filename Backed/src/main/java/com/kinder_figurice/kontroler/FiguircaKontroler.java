package com.kinder_figurice.kontroler;

import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
import com.kinder_figurice.dto.FiguricaDTO.FiguricaPocetna;
import com.kinder_figurice.dto.FiguricaDTO.FiguricaPrikaz;
import com.kinder_figurice.dto.FiguricaDTO.FiguricaUpdateDTO;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.servisi.FiguricaServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        try {
            FiguricaPrikaz figuricaPrikaz = figuricaServis.findById(id);
            return ResponseEntity.ok(figuricaPrikaz);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/kreiraj")
    public ResponseEntity<FiguricaDTO> kreirajFiguricu(@RequestBody FiguricaDTO figurica) {
        try {
            FiguricaDTO novaFigurica = figuricaServis.kreirajFiguricu(figurica);
            return new ResponseEntity<>(novaFigurica, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("azuriraj/{id}")
    public ResponseEntity<FiguricaDTO> updateFigurica(@PathVariable Long id, @RequestBody FiguricaUpdateDTO azuriranaFigurica) {
        try {
            FiguricaDTO figuricaDTO = figuricaServis.azurirajFiguricu(azuriranaFigurica, id);
            return ResponseEntity.ok(figuricaDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
    public ResponseEntity<List<FiguricaDTO>> korisnickeFiguricePoImenu(@PathVariable String korisnickoIme) {
        try {
            List<FiguricaDTO> figurice = figuricaServis.sveFiguriceKorisnikaPoImenu(korisnickoIme);
            return new ResponseEntity<>(figurice, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
