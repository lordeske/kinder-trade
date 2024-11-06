package com.kinder_figurice.servisi;


import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
import com.kinder_figurice.dto.FiguricaDTO.FiguricaUpdateDTO;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.repo.FiguricaRepo;
import com.kinder_figurice.repo.KorisnikRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FiguricaServis {


    @Autowired
    private FiguricaRepo figuricaRepo;

    @Autowired
    private KorisnikRepo korisnikRepo;



    public List<Figurica> findAll() {
        return figuricaRepo.findAll();
    }


    public Optional<Figurica> findById(Long id) {
        return figuricaRepo.findById(id);
    }


    public List<FiguricaDTO> nadjiPoNazivu(String naslovFigurice) {
        List<Figurica> figurice = figuricaRepo.findByNaslov(naslovFigurice);

        if (figurice != null && !figurice.isEmpty()) {
            return mapirajObjekatFiguriceUDTO(figurice);
        } else {
            throw new EntityNotFoundException("Figurice sa nazivom: " + naslovFigurice + " nisu pronadjene.");
        }
    }


    public FiguricaDTO kreirajFiguricu(FiguricaDTO figurica, Long idKorisnika) {
        Optional<Korisnik> korisnikOptional = korisnikRepo.findById(idKorisnika);
        if (korisnikOptional.isEmpty()) {
            throw new EntityNotFoundException("Korisnik sa ID " + idKorisnika + " nije pronađen");
        }

        Figurica figuricaZaKreiranje = new Figurica();
        figuricaZaKreiranje.setNaslov(figurica.getNaslov());
        figuricaZaKreiranje.setCena(figurica.getCena());
        figuricaZaKreiranje.setKategorija(figurica.getKategorija());
        figuricaZaKreiranje.setStanje(figurica.getStanje());
        figuricaZaKreiranje.setSlikaUrl(figurica.getSlikaUrl());
        figuricaZaKreiranje.setDatumKreiranja(LocalDateTime.now());
        figuricaZaKreiranje.setOpis(figurica.getOpis());
        figuricaZaKreiranje.setKorisnik(korisnikOptional.get());

        Figurica sacuvanaFigurica = figuricaRepo.save(figuricaZaKreiranje);


        FiguricaDTO kreiranaFiguricaDTO = new FiguricaDTO();
        kreiranaFiguricaDTO.setNaslov(sacuvanaFigurica.getNaslov());
        kreiranaFiguricaDTO.setCena(sacuvanaFigurica.getCena());
        kreiranaFiguricaDTO.setKategorija(sacuvanaFigurica.getKategorija());
        kreiranaFiguricaDTO.setStanje(sacuvanaFigurica.getStanje());
        kreiranaFiguricaDTO.setSlikaUrl(sacuvanaFigurica.getSlikaUrl());
        kreiranaFiguricaDTO.setDatumKreiranja(LocalDateTime.now());

        return kreiranaFiguricaDTO;
    }



    public FiguricaDTO azurirajFiguricu(FiguricaUpdateDTO figuricaInsert, Long idFigurice) {

        Optional<Figurica> dobijenaFigurica = figuricaRepo.findById(idFigurice);
        if(dobijenaFigurica.isPresent())
        {
            Figurica figurica1 = dobijenaFigurica.get();
            figurica1.setCena(figuricaInsert.getCena());
            figurica1.setKategorija(figuricaInsert.getKategorija());
            figurica1.setStanje(figuricaInsert.getStanje());
            figurica1.setNaslov(figuricaInsert.getNaslov());
            figurica1.setSlikaUrl(figuricaInsert.getSlikaUrl());
            figurica1.setOpis(figuricaInsert.getOpis());

            figuricaRepo.save(figurica1);

            FiguricaDTO figuricaDTO = new FiguricaDTO();
            figuricaDTO.setCena(figuricaInsert.getCena());
            figuricaDTO.setKategorija(figuricaInsert.getKategorija());
            figuricaDTO.setStanje(figuricaInsert.getStanje());
            figuricaDTO.setNaslov(figuricaInsert.getNaslov());
            figuricaDTO.setSlikaUrl(figuricaInsert.getSlikaUrl());
            figuricaDTO.setOpis(figuricaInsert.getOpis());
            figuricaDTO.setDatumKreiranja(figurica1.getDatumKreiranja());

            return figuricaDTO;


        }
        else {
            throw new RuntimeException("Figurica sa ID: " + idFigurice + " nije pronadjena.");
        }


    }


    public void deleteById(Long id) {
        figuricaRepo.deleteById(id);
    }


    public List<FiguricaDTO> sveFiguriceKorisnika(Long idKorisnika)
    {

        List<Figurica> nizFigurica = figuricaRepo.findByKorisnikId(idKorisnika);

        if (nizFigurica != null) {
            return  mapirajObjekatFiguriceUDTO(nizFigurica);
        } else {
            throw new EntityNotFoundException("Desila se greska sa pretragom za korisnika" + idKorisnika);
        }




    }


    /// Funckije basic

    public List<FiguricaDTO> mapirajObjekatFiguriceUDTO(List<Figurica> dataLista) {
        List<FiguricaDTO> potrebnaLista = new ArrayList<>();

        for (Figurica figurica : dataLista) {
            FiguricaDTO figuricaDTO = new FiguricaDTO();
            figuricaDTO.setNaslov(figurica.getNaslov());
            figuricaDTO.setDatumKreiranja(figurica.getDatumKreiranja());
            figuricaDTO.setOpis(figurica.getOpis());
            figuricaDTO.setCena(figurica.getCena());
            figuricaDTO.setKategorija(figurica.getKategorija());
            figuricaDTO.setSlikaUrl(figurica.getSlikaUrl());
            figuricaDTO.setStanje(figurica.getStanje());

            potrebnaLista.add(figuricaDTO);
        }

        return potrebnaLista;
    }

}
