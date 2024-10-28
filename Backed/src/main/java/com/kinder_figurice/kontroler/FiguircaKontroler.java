package com.kinder_figurice.kontroler;


import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.servisi.FiguricaServis;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping
    public ResponseEntity<Figurica> createFigurica(@RequestBody Figurica figurica) {
        Figurica novaFigurica = figuricaServis.save(figurica);
        return ResponseEntity.ok(novaFigurica);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Figurica> updateFigurica(@PathVariable Long id, @RequestBody Figurica figuricaDetalji) {
        Optional<Figurica> postojecaFigurica = figuricaServis.findById(id);

        if (postojecaFigurica.isPresent()) {
            Figurica figurica = postojecaFigurica.get();
            figurica.setNaslov(figuricaDetalji.getNaslov());
            figurica.setOpis(figuricaDetalji.getOpis());
            figurica.setCena(figuricaDetalji.getCena());
            figurica.setStanje(figuricaDetalji.getStanje());
            figurica.setSlikaUrl(figuricaDetalji.getSlikaUrl());
            figurica.setKategorija(figuricaDetalji.getKategorija());

            Figurica azuriranaFigurica = figuricaServis.save(figurica);
            return ResponseEntity.ok(azuriranaFigurica);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFigurica(@PathVariable Long id) {
        figuricaServis.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
