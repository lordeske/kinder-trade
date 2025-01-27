package com.kinder_figurice.kontroler;


import com.kinder_figurice.dto.RecenzijeDTO.RecenzijaDTO;
import com.kinder_figurice.dto.RecenzijeDTO.RecenzijaKreiranjeDTO;

import com.kinder_figurice.modeli.Recenzije;
import com.kinder_figurice.servisi.RecenzijeServis;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recenzije")
public class RecenzijeKontroler {


    @Autowired
    private RecenzijeServis recenzijeServis;





    @PostMapping
    public ResponseEntity<RecenzijaDTO> kreirajRecenziju(@RequestBody RecenzijaKreiranjeDTO recenzijaKreiranjeDTO)
    {

        RecenzijaDTO kreiranaRecenzija = recenzijeServis.kreirajRecenziju(recenzijaKreiranjeDTO);
        return new ResponseEntity<>(kreiranaRecenzija, HttpStatus.CREATED);



    }



    /// GET /api/recenzije/jovan123?page=1&size=5
    @GetMapping("/{korisnickoIme}")
    public ResponseEntity<Page<RecenzijaDTO>> prikaziRecenzijeKorisnika(
            @PathVariable String korisnickoIme,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);


        Page<RecenzijaDTO> recenzijePage = recenzijeServis.prikaziRecenzijeKorisnika(korisnickoIme, pageable);


        return ResponseEntity.ok(recenzijePage);
    }












    @GetMapping("/sve")
    public ResponseEntity<List<Recenzije>> sveRecenzije() {

        return new ResponseEntity<>(recenzijeServis.sveRecenzije(), HttpStatus.OK);
    }


    @DeleteMapping("/obrisi/{id}")
    public ResponseEntity<String> obrisiRecenziju(@PathVariable Long id) {
        try {
            recenzijeServis.obrisiRecenziju(id);
            return new ResponseEntity<>("Recenzija obrisana", HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }








}
