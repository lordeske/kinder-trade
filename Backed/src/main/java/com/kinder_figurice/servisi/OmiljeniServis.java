package com.kinder_figurice.servisi;


import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Omiljeni;
import com.kinder_figurice.repo.FiguricaRepo;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.repo.OmiljeniRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OmiljeniServis {


    @Autowired
    private OmiljeniRepo omiljeniRepo;

    @Autowired
    private KorisnikRepo korisnikRepo;

    @Autowired
    private FiguricaRepo figuricaRepo;

    public List<FiguricaDTO> getOmiljeniByKorisnikId(Long idKorisnika)
    {
        List<Omiljeni> omiljeniList = omiljeniRepo.findByKorisnikId(idKorisnika);

        List<FiguricaDTO> figuricaDTOList = new ArrayList<>();

        for(Omiljeni omiljeni : omiljeniList )
        {
            Figurica figurica = omiljeni.getFigurica();
            FiguricaDTO figuricaDTO = mapirajUDto(figurica);
            figuricaDTOList.add(figuricaDTO);

        }

        return figuricaDTOList;

    }


    public Omiljeni dodajFiguricuUOmiljeni(Long korisnikId, Long figuricaId) {

        Optional<Omiljeni> postojecaOmiljena = omiljeniRepo.findByKorisnikIdAndFiguricaId(korisnikId, figuricaId);
        if (postojecaOmiljena.isPresent()) {
            throw new IllegalStateException("Vec je srcnuta.");
        }


        Korisnik korisnik = korisnikRepo.findById(korisnikId)
                .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronadjen: " + korisnikId));

        Figurica figurica = figuricaRepo.findById(figuricaId)
                .orElseThrow(() -> new IllegalArgumentException("Figurica nije pronadjena: " + figuricaId));


        Omiljeni omiljeni = new Omiljeni();
        omiljeni.setKorisnik(korisnik);
        omiljeni.setFigurica(figurica);


        return omiljeniRepo.save(omiljeni);
    }

    public boolean obrisiOmiljenu(Long id) {
        if (omiljeniRepo.existsById(id)) {
            omiljeniRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }





    /// Funkcije

    private FiguricaDTO mapirajUDto(Figurica figurica)
    {
        FiguricaDTO figuricaDTO = new FiguricaDTO();

        figuricaDTO.setStanje(figurica.getStanje());
        figuricaDTO.setOpis(figurica.getOpis());
        figuricaDTO.setCena(figurica.getCena());
        figuricaDTO.setKategorija(figurica.getKategorija());
        figuricaDTO.setDatumKreiranja(figurica.getDatumKreiranja());
        figuricaDTO.setSlikaUrl(figurica.getSlikaUrl());
        figuricaDTO.setNaslov(figurica.getNaslov());

        return  figuricaDTO;

    }








}
