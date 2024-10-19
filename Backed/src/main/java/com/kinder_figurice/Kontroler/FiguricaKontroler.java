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
    private ResponseEntity<FiguricaDTO> prikaziFiguricu(
            @PathVariable Map<String, Long> paylaod
            )
    {
         Long idFigurice = paylaod.get("idFigurice");

        Optional<FiguricaDTO> figuricaDTO = figuricaService.pronadjiFiguricu(idFigurice);

        return figuricaDTO
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(404).body(null));

    }



    @GetMapping("/sveFigurice/{idKorisnika}")
    public ResponseEntity<List<FiguricaDTO>> vratiFiguriceZaKorisnika(
            @PathVariable Long idKorisnika
            )

    {

        try
        {

            return ResponseEntity.status(200).body( figuricaService.vratiFiguriceZaKorisnika(idKorisnika));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(404).body(null);
        }





    }








}
