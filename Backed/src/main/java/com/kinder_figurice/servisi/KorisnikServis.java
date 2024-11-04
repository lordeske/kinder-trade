package com.kinder_figurice.servisi;


import com.kinder_figurice.dto.KorisnikDTO.AzurirajKorisnikaDTO;
import com.kinder_figurice.dto.KorisnikDTO.PrikazKorisnikaDrugimaDTO;
import com.kinder_figurice.dto.KorisnikDTO.RegistracijaDTO;
import com.kinder_figurice.exceptions.EmailConflictException;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.repo.KorisnikRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KorisnikServis {


    @Autowired
    private KorisnikRepo korisnikRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        noviKorisnik.setDatumKreiranja(LocalDateTime.now());

        return korisnikRepo.save(noviKorisnik);
    }



    public void obrisiKorisnika(Long id) {
        korisnikRepo.deleteById(id);
    }


    public Korisnik azurirajKorisnika(Long id, AzurirajKorisnikaDTO azuriraniKorisnik) {
        Optional<Korisnik> postojeciKorisnik = korisnikRepo.findById(id);
        if (postojeciKorisnik.isPresent()) {
            Korisnik korisnikZaCuvanje = postojeciKorisnik.get();


            korisnikZaCuvanje.setKorisnickoIme(azuriraniKorisnik.getKorisnickoIme());


            if (azuriraniKorisnik.getLozinka() != null && !azuriraniKorisnik.getLozinka().isEmpty()) {
                korisnikZaCuvanje.setLozinka(passwordEncoder.encode(azuriraniKorisnik.getLozinka()));
            }

            korisnikZaCuvanje.setSlika(azuriraniKorisnik.getSlika());
            korisnikZaCuvanje.setDatumAzuriranja(LocalDateTime.now());

            return korisnikRepo.save(korisnikZaCuvanje);
        } else {
            throw new RuntimeException("Korisnik sa ID: " + id + " nije pronađen.");
        }
    }


    public PrikazKorisnikaDrugimaDTO nadjiKorisnikaPoImenu(String imeKorisnika) {
        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(imeKorisnika);
        Optional<Korisnik> optionalKorisnik = Optional.ofNullable(korisnik);

        if (optionalKorisnik.isPresent()) {
            Korisnik dobijeniKorisnik = optionalKorisnik.get();
            PrikazKorisnikaDrugimaDTO prikazKorisnika = new PrikazKorisnikaDrugimaDTO();
            prikazKorisnika.setSlika(dobijeniKorisnik.getSlika());
            prikazKorisnika.setDatumKreiranja(dobijeniKorisnik.getDatumKreiranja());
            prikazKorisnika.setKorisnickoIme(dobijeniKorisnik.getKorisnickoIme());

            return prikazKorisnika;
        } else {
            throw new EntityNotFoundException("Korisnik nije pronađen");
        }
    }





}
