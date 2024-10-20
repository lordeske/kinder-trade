package com.kinder_figurice.Kontroler;


import com.kinder_figurice.DataTransferModeli.FiguricaDTO;
import com.kinder_figurice.Servisi.FiguricaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("figurice")
public class FiguricaKontroler {



    @Autowired
    private  FiguricaService figuricaService;

    @PostMapping("/kreirajFiguricu/{idKorisnika}")
    ResponseEntity<FiguricaDTO> kreirajFiguricu(
            @RequestBody FiguricaDTO figuricaDTO,
            @PathVariable Long idKorisnika)


    {

        figuricaService.kreirajFiguricu(figuricaDTO,idKorisnika);

        return ResponseEntity.status(200).body(figuricaDTO);



    }


    @GetMapping("/{idFigurice}")
    public ResponseEntity<FiguricaDTO> prikaziFiguricu(
            @PathVariable Long idFigurice
    ) {
        Optional<FiguricaDTO> figuricaDTO = figuricaService.pronadjiFiguricu(idFigurice);

        return figuricaDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }




    @GetMapping("/sveFigurice/{idKorisnika}")
    public ResponseEntity<List<FiguricaDTO>> vratiFiguriceZaKorisnika(
            @PathVariable Long idKorisnika
    ) {
        List<FiguricaDTO> figurice = figuricaService.vratiFiguriceZaKorisnika(idKorisnika);

        if (figurice.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        } else {
            return ResponseEntity.ok(figurice);
        }
    }


    @PutMapping("/azurirajFiguricu/{idFigurice}")
    public ResponseEntity<FiguricaDTO> azurirajFiguricu(
            @PathVariable Long idFigurice,
            @RequestBody  FiguricaDTO figuricaDTO
    )
    {

        try {
                return  ResponseEntity.status(200).body(figuricaService.azurirajFiguricu(idFigurice,figuricaDTO));

        }
        catch (Exception e)
        {
            return ResponseEntity.status(404).body(null);
        }


    }










}
