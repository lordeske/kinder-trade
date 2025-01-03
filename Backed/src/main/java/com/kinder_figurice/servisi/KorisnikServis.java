package com.kinder_figurice.servisi;


import com.kinder_figurice.Konfiguracija.JWTGenerator;
import com.kinder_figurice.dto.KorisnikDTO.AzurirajKorisnikaDTO;
import com.kinder_figurice.dto.KorisnikDTO.LoginDTO;
import com.kinder_figurice.dto.KorisnikDTO.PrikazKorisnikaDrugimaDTO;
import com.kinder_figurice.dto.KorisnikDTO.RegistracijaDTO;
import com.kinder_figurice.exceptions.EmailConflictException;
import com.kinder_figurice.exceptions.UserNameExistException;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Role;
import com.kinder_figurice.repo.KorisnikRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KorisnikServis {


    @Autowired
    private KorisnikRepo korisnikRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JWTGenerator jwtGenerator;


    public List<Korisnik> sviKorisnici()
    {
        return korisnikRepo.findAll();
    }


    public Optional<Korisnik> nadjiKorisnikaPoID(Long id)
    {
        return korisnikRepo.findById(id);
    }


    public Korisnik kreirajKorisnika(RegistracijaDTO korisnikDTO) {
        if (korisnikRepo.existsByEmail(korisnikDTO.getEmail())) {
            throw new EmailConflictException("Email već postoji.");
        }

        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setEmail(korisnikDTO.getEmail());
        noviKorisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
        noviKorisnik.setLozinka(passwordEncoder.encode(korisnikDTO.getLozinka()));
        noviKorisnik.setUloga(Role.USER);


        return korisnikRepo.save(noviKorisnik);
    }



    public void obrisiKorisnika(String korisnickoIme) {
        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisnickoIme)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen sa imenom: " + korisnickoIme));

        System.out.println("Korisnik pronadjen: " + korisnik.getKorisnickoIme());

        if (!korisnik.getKorisnickoIme().equals(korisnickoImeIzTokena)) {
            throw new SecurityException("Nemate pravo da brišete drugog korisnika.");
        }

        korisnikRepo.deleteById(korisnik.getId());
        System.out.println("Korisnik obrisan: " + korisnickoIme);
    }




    public Korisnik azurirajKorisnika(String korisnickoIme, AzurirajKorisnikaDTO azuriraniKorisnik) {

        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();


        Optional<Korisnik> postojeciKorisnik = korisnikRepo.findByKorisnickoIme(korisnickoIme);
        if(postojeciKorisnik.isEmpty())
        {
            throw new RuntimeException("Korisnik nije pronadjen sa imenom "+ korisnickoIme);
        }

        Korisnik korisnikZaCuvanje = postojeciKorisnik.get();

        if(!korisnikZaCuvanje.getKorisnickoIme().equals(korisnickoImeIzTokena))
        {
            throw new SecurityException("Nemate pravo da menjate podatke drugog korisnika.");
        }


        korisnikZaCuvanje.setEmail(azuriraniKorisnik.getEmail());

        if(azuriraniKorisnik.getLozinka() != null && !azuriraniKorisnik.getLozinka().isEmpty())
        {
            korisnikZaCuvanje.setLozinka(passwordEncoder.encode(korisnikZaCuvanje.getLozinka()));
        }

        korisnikZaCuvanje.setSlika(azuriraniKorisnik.getSlika());


        return korisnikRepo.save(korisnikZaCuvanje);




    }


    public String loginKorisnika(LoginDTO loginDTO) {

        if (loginDTO.getKorisnickoIme() == null || loginDTO.getKorisnickoIme().isEmpty()) {
            throw new IllegalArgumentException("Korisničko ime ne sme biti prazno!");
        }

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getKorisnickoIme(),
                            loginDTO.getLozinka()
                    )
            );


            SecurityContextHolder.getContext().setAuthentication(authentication);


            return jwtGenerator.generisiToken(authentication);
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Pogrešno korisničko ime ili lozinka");
        }
    }



    public  void registrujKorisnika(RegistracijaDTO registracijaDTO)
    {

        if(!korisnikRepo.existsByKorisnickoIme(registracijaDTO.getKorisnickoIme()))
        {


            Korisnik noviKorisnik = new Korisnik();
            noviKorisnik.setEmail(registracijaDTO.getEmail());
            noviKorisnik.setKorisnickoIme(registracijaDTO.getKorisnickoIme());
            noviKorisnik.setLozinka(passwordEncoder.encode(registracijaDTO.getLozinka()));
            noviKorisnik.setUloga(Role.USER);


            korisnikRepo.save(noviKorisnik);





        }
        else
        {
            throw new UserNameExistException("Korisnik sa imenom " + registracijaDTO.getKorisnickoIme() + "vec postoji");
        }



    }


    public PrikazKorisnikaDrugimaDTO nadjiKorisnikaPoImenu(String imeKorisnika) {

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(imeKorisnika)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen sa imenom: " + imeKorisnika));


        PrikazKorisnikaDrugimaDTO prikazKorisnika = new PrikazKorisnikaDrugimaDTO();
        prikazKorisnika.setSlika(korisnik.getSlika());
        prikazKorisnika.setKorisnickoIme(korisnik.getKorisnickoIme());
        prikazKorisnika.setDatumKreiranja(korisnik.getDatumKreiranja());

        return prikazKorisnika;
    }


    public List<PrikazKorisnikaDrugimaDTO> prikaziPredlozeneKorisnike(String trenutniKorisnik) {


        List<Korisnik> listaKorisnika = korisnikRepo.prikaziPredlozeneProfile(trenutniKorisnik);


        List<PrikazKorisnikaDrugimaDTO> predlozeniKorisnici = new ArrayList<>();


        for (Korisnik korisnik : listaKorisnika) {

            PrikazKorisnikaDrugimaDTO dto = new PrikazKorisnikaDrugimaDTO();
            dto.setKorisnickoIme(korisnik.getKorisnickoIme());
            dto.setDatumKreiranja(korisnik.getDatumKreiranja());
            dto.setDatumKreiranja(korisnik.getDatumKreiranja());


            predlozeniKorisnici.add(dto);
        }


        return predlozeniKorisnici;
    }






}
