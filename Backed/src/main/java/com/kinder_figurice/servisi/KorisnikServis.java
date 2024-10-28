package com.kinder_figurice.servisi;


import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.repo.KorisnikRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikServis {


    @Autowired
    private KorisnikRepo korisnikRepo;

    public List<Korisnik> sviKorisnici()
    {
        return korisnikRepo.findAll();
    }


    public Optional<Korisnik> nadjiKorisnikaPoID(Long id)
    {
        return korisnikRepo.findById(id);
    }


    public Korisnik sacuvajKorisnika(Korisnik korisnik) {
        return korisnikRepo.save(korisnik);
    }


    public void obrisiKorisnika(Long id) {
        korisnikRepo.deleteById(id);
    }




}
