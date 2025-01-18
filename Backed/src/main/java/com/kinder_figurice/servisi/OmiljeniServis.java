package com.kinder_figurice.servisi;


import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
import com.kinder_figurice.dto.FiguricaDTO.FiguricaPrikaz;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Omiljeni;
import com.kinder_figurice.repo.FiguricaRepo;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.repo.OmiljeniRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OmiljeniServis {


    @Autowired
    private OmiljeniRepo omiljeniRepo;

    @Autowired
    private KorisnikRepo korisnikRepo;

    @Autowired
    private FiguricaRepo figuricaRepo;

    public List<FiguricaPrikaz> getMojeOmiljene()
    {
        String korisisnickoImeizTokena = SecurityContextHolder.getContext().getAuthentication().getName();


        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisisnickoImeizTokena)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen: " + korisisnickoImeizTokena));

        List<Omiljeni> omiljeniList = omiljeniRepo.findByKorisnik(korisnik);

        return omiljeniList.stream()
                .map(omiljeni -> mapirajUDto(omiljeni.getFigurica()))
                .toList();

    }


    public void dodajFiguricuUOmiljeni(Long figuricaId) {


        String korisisnickoImeizTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisisnickoImeizTokena)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen: " + korisisnickoImeizTokena));


        Figurica figurica = figuricaRepo.findById(figuricaId)
                .orElseThrow(()-> new EntityNotFoundException("Figurica sa ID: " + figuricaId +" nije pronadjena"));

        if(figurica.getKorisnik().equals(korisnik))
        {

            throw new IllegalStateException("Ne mozete dodati svoju figuricu u omiljene.");


        }


        Optional<Omiljeni> postojecaOmiljena = omiljeniRepo.findByKorisnikAndFiguricaId(korisnik, figuricaId);


        if (postojecaOmiljena.isPresent()) {
            throw new IllegalStateException("Vec je srcnuta.");
        }


            Omiljeni novaOmiljena = new Omiljeni();
            novaOmiljena.setFigurica(figurica);
            novaOmiljena.setKorisnik(korisnik);
            omiljeniRepo.save(novaOmiljena);


    }

    public void obrisiOmiljenu(Long figuricaID) {


        String korisisnickoImeizTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisisnickoImeizTokena)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen: " + korisisnickoImeizTokena));

        Figurica figurica = figuricaRepo.findById(figuricaID)
                .orElseThrow(()-> new EntityNotFoundException("Figurica sa ID: " + figuricaID +" nije pronadjena"));


        Omiljeni omiljeni = omiljeniRepo.findByKorisnikAndFiguricaId(korisnik, figuricaID)
                .orElseThrow(() -> new EntityNotFoundException("Figurica nije pronađena u omiljenim."));





        omiljeniRepo.delete(omiljeni);

        System.out.println("Figurica sa ID: " + figuricaID + " uklonjena iz omiljenih korisnika: " + korisisnickoImeizTokena);

    }




    public boolean daLiJeFiguricaUOmiljenim(Long idFigurice)
    {

        String korisisnickoImeizTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisisnickoImeizTokena)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen: " + korisisnickoImeizTokena));


        Figurica figurica = figuricaRepo.findById(idFigurice)
                .orElseThrow(() -> new EntityNotFoundException("Figurica nije pronadjena: " + idFigurice));

        return omiljeniRepo.existsByKorisnikAndFigurica(korisnik,figurica);


    }





    /// Funkcije

    private FiguricaPrikaz mapirajUDto(Figurica figurica)
    {
        FiguricaPrikaz figuricaPrikaz = new FiguricaPrikaz();

        figuricaPrikaz.setIdFigurice(figurica.getId());
        figuricaPrikaz.setStanje(figurica.getStanje());
        figuricaPrikaz.setOpis(figurica.getOpis());
        figuricaPrikaz.setCena(figurica.getCena());
        figuricaPrikaz.setKategorija(figurica.getKategorija());
        figuricaPrikaz.setDatumKreiranja(figurica.getDatumKreiranja());
        figuricaPrikaz.setSlikaUrl(figurica.getSlikaUrl());
        figuricaPrikaz.setNaslov(figurica.getNaslov());

        return  figuricaPrikaz;

    }








}
