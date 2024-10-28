package com.kinder_figurice.servisi;


import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Omiljeni;
import com.kinder_figurice.repo.OmiljeniRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OmiljeniServis {


    @Autowired
    private OmiljeniRepo omiljeniRepo;

    public List<Omiljeni> getOmiljeniByKorisnik(Korisnik korisnik)
    {

        return omiljeniRepo.findByKorisnik(korisnik);

    }


    public Omiljeni dodajFiguricuUOmiljeni(Korisnik korisnik, Figurica figurica)
    {

        Omiljeni omiljeni = new Omiljeni();
        omiljeni.setKorisnik(korisnik);
        omiljeni.setFigurica(figurica);
        return omiljeniRepo.save(omiljeni) ;

    }


    public void obrisiOmiljenu(Long id)
    {

        omiljeniRepo.deleteById(id);

    }









}
