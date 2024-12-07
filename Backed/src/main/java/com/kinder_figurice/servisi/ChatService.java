package com.kinder_figurice.servisi;


import com.kinder_figurice.modeli.Poruka;
import com.kinder_figurice.modeli.Soba;
import com.kinder_figurice.repo.PorukaRepo;
import com.kinder_figurice.repo.SobaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ChatService {


    @Autowired
    private PorukaRepo porukaRepo;

    @Autowired
    private SobaRepo sobaRepo;




    public Soba kreirajIliNadjiSobu(String korisnik1, String korisnik2) {
        String nazivSobe = korisnik1.compareTo(korisnik2) < 0 ? korisnik1 + "_" + korisnik2 : korisnik2 + "_" + korisnik1;

        return sobaRepo.findByNazivSobe(nazivSobe).orElseGet(() -> {
            Soba novaSoba = new Soba();
            novaSoba.setNazivSobe(nazivSobe);
            return sobaRepo.save(novaSoba);
        });
    }


    public List<Poruka> svePorukeIzmedjuKorisnika(String korisnik1, String korisnik2)
    {

        String nazivSobe = korisnik1.compareTo(korisnik2) < 0 ? korisnik1 + "_" + korisnik2 : korisnik2 + "_" + korisnik1;

        return porukaRepo.findBySoba_NazivSobe(nazivSobe);

    }


    public  Poruka posaljiPoruku(String posiljalac, String primalac, String sadrzaj)
    {

        Soba soba = kreirajIliNadjiSobu(primalac, posiljalac);


        Poruka poruka = new Poruka();
        poruka.setPosiljalac(posiljalac);
        poruka.setPrimalac(primalac);
        poruka.setSadrzajPoruke(sadrzaj);
        poruka.setSoba(soba);

        return porukaRepo.save(poruka);


    }






}
