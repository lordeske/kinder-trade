package com.kinder_figurice.kontroler;


import com.kinder_figurice.dto.TradeDto.TrgovinaDto;
import com.kinder_figurice.dto.TradeDto.TrgovinaDtoFigurice;
import com.kinder_figurice.dto.TradeDto.TrgovinaNoStatusDto;
import com.kinder_figurice.dto.TradeDto.TrgovinaPrikaz;
import com.kinder_figurice.modeli.Trgovina;
import com.kinder_figurice.servisi.TrgovinaServis;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trgovina")

public class TrgovinaKontroler {




    @Autowired
    private TrgovinaServis trgovinaServis;



    @PostMapping
    public ResponseEntity<Trgovina> kreirajTrgovinu(
            @RequestBody TrgovinaNoStatusDto trgovinaDto
            )
    {

        Trgovina trgovina = trgovinaServis.kreirajTrgovinu(trgovinaDto);
        return ResponseEntity.ok(trgovina);


    }


    @PutMapping("/prihvati/{trgovinaID}")
    public ResponseEntity<Trgovina> prihvatiTrgovinu(
            @PathVariable Long trgovinaID
    )
    {

        Trgovina trgovina = trgovinaServis.prihvatiTrgovinu(trgovinaID);
        return ResponseEntity.ok(trgovina);


    }


    @PutMapping("/odbij/{trgovinaID}")
    public ResponseEntity<Trgovina> odbijTrgovinu(
            @PathVariable Long trgovinaID
    )
    {

        Trgovina trgovina = trgovinaServis.odbijTrgovinu(trgovinaID);
        return ResponseEntity.ok(trgovina);


    }

    @PutMapping("/povuci/{trgovinaID}")
    public ResponseEntity<Trgovina> povuciTrgovinu(
            @PathVariable Long trgovinaID
    )
    {

        Trgovina trgovina = trgovinaServis.povuciTrgovinu(trgovinaID);
        return ResponseEntity.ok(trgovina);


    }


    @Transactional
    @PutMapping("/counter/{id}")
    public ResponseEntity<Trgovina> counterPonuda(
            @PathVariable Long id,
            @RequestBody TrgovinaDtoFigurice trgovinaDto) {
        Trgovina trgovina = trgovinaServis.posaljiCounterPonudu(id, trgovinaDto);
        return ResponseEntity.ok(trgovina);
    }




    @GetMapping("/moje")
    public ResponseEntity<List<TrgovinaPrikaz>> sveMojeTrgovine() {
        return ResponseEntity.ok(trgovinaServis.sveMojeTrgovine());
    }


    @GetMapping("/pending")
    public ResponseEntity<List<TrgovinaPrikaz>> sveMojePendingTrgovine() {
        return ResponseEntity.ok(trgovinaServis.sveMojePendingTrgovine());
    }


    @GetMapping("/counter")
    public ResponseEntity<List<TrgovinaPrikaz>> sveMojeCounterTrgovine() {
        return ResponseEntity.ok(trgovinaServis.sveMojeCounterTrgovine());
    }




}
