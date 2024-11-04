package com.kinder_figurice.kontroler;


import com.kinder_figurice.dto.RecenzijeDTO.RecenzijaDTO;
import com.kinder_figurice.dto.RecenzijeDTO.RecenzijaRequest;
import com.kinder_figurice.modeli.Recenzije;
import com.kinder_figurice.servisi.RecenzijeServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public ResponseEntity<Recenzije> kreirajRecenziju(@RequestBody RecenzijaRequest recenzijaRequest)
    {

        try
        {
            Recenzije recenzije =  recenzijeServis.kreirajRecenziju(recenzijaRequest);
            return new  ResponseEntity<>(recenzije, HttpStatus.CREATED);
        }
        catch (EntityNotFoundException e)
        {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/{idRecenziranog}")
    public ResponseEntity<List<RecenzijaDTO>> sveRecenzijeKorisnika(
            @PathVariable Long idRecenziranog
    )
    {

        try {

            List<RecenzijaDTO> listaRecenzija = recenzijeServis.sveRecenzijeKorisnika(idRecenziranog);
            return new ResponseEntity<>(listaRecenzija, HttpStatus.OK);

        }
        catch (EntityNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }








    }




    @GetMapping("/sve")
    public ResponseEntity<List<Recenzije>> sveRecenzije() {
        List<Recenzije> listaRecenzija = recenzijeServis.sveRecenzije();
        return new ResponseEntity<>(listaRecenzija, HttpStatus.OK);
    }


    @DeleteMapping("/obrisi/{id}")
    public ResponseEntity<Void> obrisiRecenziju(@PathVariable Long id) {
        try {
            recenzijeServis.obrisiRecenziju(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }








}
