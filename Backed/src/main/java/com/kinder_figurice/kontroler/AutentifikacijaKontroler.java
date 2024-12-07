package com.kinder_figurice.kontroler;


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

@RestController
@RequestMapping("api/autentifikacija")
public class AutentifikacijaKontroler {



    @Autowired
    private KorisnikServis korisnikServis;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginKorisnika(
            @RequestBody LoginDTO loginDTO)
    {

        try {

            korisnikServis.loginKorisnika(loginDTO);

            return new ResponseEntity<>(new AuthResponseDTO(korisnikServis.loginKorisnika(loginDTO)), HttpStatus.CREATED);

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






}
