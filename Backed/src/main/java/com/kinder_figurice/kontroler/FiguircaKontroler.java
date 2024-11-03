package com.kinder_figurice.kontroler;


import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
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
    private ResponseEntity<List<Figurica>> pronadjiSveFigurice()
    {
        List<Figurica> figurice = figuricaServis.findAll();

        return ResponseEntity.ok(figurice);

    }



    @GetMapping("/{id}")
    public ResponseEntity<Figurica> getFiguricaById(@PathVariable Long id) {
        Optional<Figurica> figurica = figuricaServis.findById(id);
        if (figurica.isPresent()) {
            return ResponseEntity.ok(figurica.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{idKorisnika}")
    public ResponseEntity<FiguricaDTO> kreirajFiguricu (@RequestBody FiguricaDTO figurica, @PathVariable Long idKorisnika) {

        try {
            FiguricaDTO figurica1 = figuricaServis.kreirajFiguricu(figurica,idKorisnika);
            return new ResponseEntity<>(figurica1, HttpStatus.CREATED);

        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<FiguricaDTO> updateFigurica(@PathVariable Long id, @RequestBody FiguricaUpdateDTO azuriranaFigurica) {

        try {

            FiguricaDTO figuricaDTO = figuricaServis.azurirajFiguricu(azuriranaFigurica, id);
            return  new ResponseEntity<>(figuricaDTO, HttpStatus.OK);

        }
        catch (RuntimeException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFigurica(@PathVariable Long id) {
        figuricaServis.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{nazivFigurica}")
    public ResponseEntity<List<FiguricaDTO>> nadjiPoNazivu(
            @PathVariable String nazivFigurica
    )
    {
        try {

            List<FiguricaDTO>  figurice =  figuricaServis.nadjiPoNazivu(nazivFigurica);
            return new ResponseEntity<>(figurice, HttpStatus.OK);

        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }



    }


    @GetMapping("/{idKorisnika}")
    public ResponseEntity<List<FiguricaDTO>> korisnickeFigurice(
            @PathVariable Long idKorisnika
    )
    {
        try {

            List<FiguricaDTO>  figurice =  figuricaServis.sveFiguriceKorisnika(idKorisnika);
            return new ResponseEntity<>(figurice, HttpStatus.OK);

        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }



    }









}
