package com.kinder_figurice.Servisi;


import com.kinder_figurice.DataTransferModeli.KorisnikDTO;
import com.kinder_figurice.DataTransferModeli.KorisnikRegistracijaDTO;
import com.kinder_figurice.DataTransferModeli.LoginDTO;
import com.kinder_figurice.ModelMapper.Mapper;
import com.kinder_figurice.Modeli.Korisnik;
import com.kinder_figurice.Repo.KorisnikRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class KorisnikService {


    private final KorisnikRepo korisnikRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public KorisnikService(KorisnikRepo korisnikRepo, PasswordEncoder passwordEncoder) {
        this.korisnikRepo = korisnikRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public Korisnik kreirajKorisnika(KorisnikRegistracijaDTO korisnikRegistracijaDTO)
    {
        /// Autentifikacije ce kasnije biti ubacena, naknadno

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(korisnikRegistracijaDTO.getIme());
        korisnik.setPrezime(korisnikRegistracijaDTO.getPrezime());
        korisnik.setEmail(korisnikRegistracijaDTO.getEmail());
        korisnik.setLozinka(passwordEncoder.encode(korisnikRegistracijaDTO.getLozinka()));
        return korisnikRepo.save(korisnik);
    }


    public Boolean loginKorisnika(LoginDTO loginDTO)
    {
        Korisnik korisnik = korisnikRepo.findByEmail(loginDTO.getEmail());

        if (korisnik == null)
        {
            return  false;
        }

        return passwordEncoder.matches(loginDTO.getLozinka(), korisnik.getLozinka());


    }


    public KorisnikDTO prikaziProfilKorisnika(Long idKorisnika) {
        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        return korisnik.map(Mapper::toDTO).orElse(null);


    }


    public KorisnikDTO azurirajProfil(Long idKorisnika, KorisnikDTO korisnikDTO)
    {
        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if(korisnik.isPresent())
        {
            Korisnik korisnik1 = korisnik.get();

            korisnik1.setIme(korisnikDTO.getIme());
            korisnik1.setPrezime(korisnikDTO.getPrezime());
            korisnik1.setEmail(korisnikDTO.getEmail());

            Korisnik sacuvaniKorisnik = korisnikRepo.save(korisnik1);
            return Mapper.toDTO(sacuvaniKorisnik);
        }
        else
        {
            return null;
        }

    }


    public  KorisnikDTO azurirajLozinku(Long idKorisnika, String novaLozinka)
    {

        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if(korisnik.isPresent())
        {
            Korisnik izmenjeniKorisnik = korisnik.get();

            izmenjeniKorisnik.setLozinka(passwordEncoder.encode(novaLozinka));
            return  Mapper.toDTO(korisnikRepo.save(izmenjeniKorisnik));
        }
        else
        {
            return null;
            /// posle implementacija ovoga!
        }


    }


    public void promijeniSliku(Long idKorisnika, String novaSlika)
    {
        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if(korisnik.isPresent()) {

            Korisnik dobijeniKorisnik = korisnik.get();
            dobijeniKorisnik.setSlikaKorisnika(novaSlika);
            korisnikRepo.save(dobijeniKorisnik);

        }

    }


    public void  promijeniStatusKorisnika(Long idKorisnika)
    {
        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if(korisnik.isPresent())
        {
            Korisnik dobijeniKorisnik = korisnik.get();
            dobijeniKorisnik.setAktivanKorisnik(!dobijeniKorisnik.getAktivanKorisnik());
            korisnikRepo.save(dobijeniKorisnik);

        }
    }






}
