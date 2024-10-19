package com.kinder_figurice.Kontroler;

import com.kinder_figurice.DataTransferModeli.KorisnikDTO;
import com.kinder_figurice.DataTransferModeli.KorisnikRegistracijaDTO;
import com.kinder_figurice.DataTransferModeli.LoginDTO;
import com.kinder_figurice.Modeli.Korisnik;
import com.kinder_figurice.Servisi.KorisnikService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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


    @GetMapping("/korisnik/{idKorisnika}")
    public ResponseEntity<KorisnikDTO> prikaziKorisnika(@PathVariable Long idKorisnika) {
        return ResponseEntity.ok().body(korisnikService.prikaziProfilKorisnika(idKorisnika));
    }


    @PutMapping("/azurirajKorisnika/{idKorisnika}")
    public ResponseEntity<KorisnikDTO> azurirajProfilKorisnika(
            @PathVariable Long idKorisnika,
            @RequestBody KorisnikDTO korisnikDTO)
    {
        KorisnikDTO azuriraniKorisnik = korisnikService.azurirajProfil(idKorisnika,korisnikDTO);

        if(azuriraniKorisnik!=null)
        {
            return ResponseEntity.ok(azuriraniKorisnik);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/azurirajLozinku/{idKorisnika}")
    public ResponseEntity<KorisnikDTO> azurirajLozinku(
            @PathVariable Long idKorisnika,
            @RequestBody String novaLozinka
    )
    {
        KorisnikDTO korisnikDTO = korisnikService.azurirajLozinku(idKorisnika, novaLozinka);

        if(korisnikDTO!=null)
        {
            return ResponseEntity.ok(korisnikDTO);
        }
        else
        {
            return  ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/azurirajSliku/{idKorisnika}")
    public ResponseEntity<String> azurirajSliku(
            @PathVariable Long idKorisnika,
            @RequestBody String novaSlika
    )
    {
        try {
            korisnikService.promijeniSliku(idKorisnika, novaSlika);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(500).body("Greska prilikom azuriranja slike");
        }



    }


    @PutMapping("/azurirajStatus")
    public ResponseEntity<String> azurirajStatusKorisnika(
            @RequestBody Map<String, Long> payload

            )
    {
        Long idKorisnika = payload.get("idKorisnika");
        try {
            korisnikService.promijeniStatusKorisnika(idKorisnika);
            return ResponseEntity.ok().body("Uspesno promenjen status");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(404).body("Desila se greska");
        }


    }







}
