package com.kinder_figurice.servisi;

import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Poruka;
import com.kinder_figurice.modeli.Soba;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.repo.PorukaRepo;
import com.kinder_figurice.repo.SobaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ChatService {



    @Autowired
    private PorukaRepo porukaRepo;

    @Autowired
    private SobaRepo sobaRepo;


    @Autowired
    private KorisnikRepo korisnikRepo;


    public Soba kreirajIliNadjiSobu(String korisnik1, String korisnik2)
    {

        String nazivSobe = korisnik1.compareTo(korisnik2) < 0 ? korisnik1 + "_" + korisnik2 :
                korisnik2 + "_" + korisnik1;


        return sobaRepo.findByNazivSobe(nazivSobe)
                .orElseGet(() ->
                {
                    Soba novaSoba = new Soba();
                    novaSoba.setNazivSobe(nazivSobe);
                    return sobaRepo.save(novaSoba);

                });





    }

    public List<Poruka> svePorukeIzmedjuKorisnika(String korisnik2) {

        String korisnik1  = SecurityContextHolder.getContext().getAuthentication().getName();



        Optional<Korisnik> postojeciKorisnik = korisnikRepo.findByKorisnickoIme(korisnik1);

        if(postojeciKorisnik.isEmpty())
        {
            throw new RuntimeException("Korisnik nije pronadjen sa imenom "+ korisnik1);
        }

        String nazivSobe = korisnik1.compareTo(korisnik2) < 0 ? korisnik1 + "_" + korisnik2 : korisnik2 + "_" + korisnik1;

        return porukaRepo.findBySoba_NazivSobe(nazivSobe);
    }


    public Poruka posaljiPoruku(String posiljalac, String primalac, String sadrzaj) {
        Soba soba = kreirajIliNadjiSobu(posiljalac, primalac);

        Poruka poruka = new Poruka();
        poruka.setPosiljalac(posiljalac);
        poruka.setPrimalac(primalac);
        poruka.setSadrzajPoruke(sadrzaj);
        poruka.setSoba(soba);

        return porukaRepo.save(poruka);
    }



    public List<String> dobijSveRazgovoreZaKorisnika() {

        String korisnickoIme = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Korisnik> postojeciKorisnik = korisnikRepo.findByKorisnickoIme(korisnickoIme);

        if(postojeciKorisnik.isEmpty())
        {
            throw new RuntimeException("Korisnik nije pronadjen sa imenom "+ korisnickoIme);
        }

        List<Soba> sobe = sobaRepo.findAll();
        List<String> razgovori = new ArrayList<>();

        for (Soba soba : sobe) {

            if (soba.getNazivSobe().contains(korisnickoIme)) {
                String[] korisnici = soba.getNazivSobe().split("_");
                String drugiKorisnik = korisnici[0].equals(korisnickoIme) ? korisnici[1] : korisnici[0];
                razgovori.add(drugiKorisnik);
            }
        }
        return razgovori;
    }
}
