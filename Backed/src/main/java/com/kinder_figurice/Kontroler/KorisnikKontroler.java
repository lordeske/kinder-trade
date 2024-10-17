package com.kinder_figurice.Kontroler;

import com.kinder_figurice.DataTransferModeli.KorisnikDTO;
import com.kinder_figurice.DataTransferModeli.KorisnikRegistracijaDTO;
import com.kinder_figurice.DataTransferModeli.LoginDTO;
import com.kinder_figurice.Modeli.Korisnik;
import com.kinder_figurice.Servisi.KorisnikService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/korisnici")
public class KorisnikKontroler {


    @Autowired
    private KorisnikService korisnikService;


    @PostMapping("/kreirajKorisnika")
    public ResponseEntity<Korisnik> kreirajKorisnika(@RequestBody KorisnikRegistracijaDTO korisnikRegistracijaDTO)
    {
        Korisnik korisnik = korisnikService.kreirajKorisnika(korisnikRegistracijaDTO);

        return ResponseEntity.status(201).body(korisnik);


    }


    @PostMapping("/login")
    public ResponseEntity<String>  loginKorisnika(@RequestBody LoginDTO loginDTO)
    {
        Boolean ishod = korisnikService.loginKorisnika(loginDTO);

        if (ishod)
        {
           return ResponseEntity.ok("Uspesno ste se prijavili");

        }
        else
        {
           return ResponseEntity.status(401).body("Netacni podaci");
        }

    }


    @GetMapping







}
