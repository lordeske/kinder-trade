package com.kinder_figurice.kontroler;


import com.kinder_figurice.Konfiguracija.JWTGenerator;
import com.kinder_figurice.dto.AuthResponseDTO.AuthResponseDTO;
import com.kinder_figurice.dto.KorisnikDTO.LoginDTO;
import com.kinder_figurice.dto.KorisnikDTO.RegistracijaDTO;
import com.kinder_figurice.exceptions.UserNameExistException;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.servisi.KorisnikServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/autentifikacija")
public class AutentifikacijaKontroler {



    @Autowired
    private KorisnikServis korisnikServis;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTGenerator jwtGenerator;



    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginKorisnika(
            @RequestBody LoginDTO loginDTO)
    {

        try {

            AuthResponseDTO authResponseDTO =  korisnikServis.loginKorisnika(loginDTO);

            return new ResponseEntity<>(authResponseDTO ,HttpStatus.OK);

        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }





    }




    @PostMapping("/registracija")
    public ResponseEntity<String> registracijaKorisnika(
            @RequestBody RegistracijaDTO registracijaDTO)
    {

        try {
            korisnikServis.registrujKorisnika(registracijaDTO);
            return new ResponseEntity<>("Korisnik kreiran", HttpStatus.CREATED);

        }
        catch (UserNameExistException e)
        {
            return new ResponseEntity<>("Korisnik vec postoji", HttpStatus.CONFLICT);
        }





    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> osvjeziToken(
            @RequestBody Map<String, String> zahtjev
            )
    {

        String refreshToken = zahtjev.get("refreshToken");

        if(jwtGenerator.validisiRefreshToken(refreshToken))
        {

            String korisnickoIme = jwtGenerator.dobijKorisnickoImeIzJWT(refreshToken);


            String noviAccessToken = jwtGenerator.generisiToken(korisnickoIme);

            AuthResponseDTO response = new AuthResponseDTO(noviAccessToken, refreshToken);
            return ResponseEntity.ok(response);



        }
        else {
             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }


    }



    /// Na frontu ovo treba da se izvrsi tako sto cu obrisati iz LocalStoragea
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Uspesno ste se odjavili.");
    }

















}
