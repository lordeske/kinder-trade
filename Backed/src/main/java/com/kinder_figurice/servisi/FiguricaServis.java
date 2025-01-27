package com.kinder_figurice.servisi;


import com.kinder_figurice.dto.FiguricaDTO.*;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.repo.FiguricaRepo;
import com.kinder_figurice.repo.KorisnikRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;


@Service
public class FiguricaServis {


    @Autowired
    private FiguricaRepo figuricaRepo;

    @Autowired
    private KorisnikRepo korisnikRepo;



    public List<Figurica> findAll() {
        return figuricaRepo.findAll();
    }


    public FiguricaPrikaz findById(Long id) {
        Figurica figurica = figuricaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Figurica sa ID " + id + " nije pronađena."));

        FiguricaPrikaz figuricaPrikaz = new FiguricaPrikaz();
        figuricaPrikaz.setIdFigurice(figurica.getId());
        figuricaPrikaz.setNaslov(figurica.getNaslov());
        figuricaPrikaz.setOpis(figurica.getOpis());
        figuricaPrikaz.setKategorija(figurica.getKategorija());
        figuricaPrikaz.setCena(figurica.getCena());
        figuricaPrikaz.setSlikaUrl(figurica.getSlikaUrl());
        figuricaPrikaz.setVlasnikFigurice(figurica.getKorisnik().getKorisnickoIme());
        figuricaPrikaz.setStanje(figurica.getStanje());

        return figuricaPrikaz;
    }






    public List<FiguricaPocetna> dohvatiRandomFigurice(int limit) {





        if (limit <= 0) {
            throw new IllegalArgumentException("Limit mora biti veci od 0");
        }

        List<Figurica> randomFigurice = figuricaRepo.dobaviRandomFigurice(limit);


        return mapirajObjekatFiguricePocetna(randomFigurice);
    }



    public FiguricaDTO kreirajFiguricu(FiguricaDTO figuricaNova) {

        String korisickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisickoImeIzTokena)
                .orElseThrow(()-> new EntityNotFoundException("Korisnik nije pronadjen"));


        Figurica figurica = new Figurica();
        figurica.setNaslov(figuricaNova.getNaslov());
        figurica.setCena(figuricaNova.getCena());
        figurica.setKategorija(figuricaNova.getKategorija());
        figurica.setStanje(figuricaNova.getStanje());
        figurica.setSlikaUrl(figuricaNova.getSlikaUrl());
        figurica.setOpis(figuricaNova.getOpis());
        figurica.setKorisnik(korisnik);

        Figurica sacuvanaFigurica = figuricaRepo.save(figurica);

        return mapriajJednuUDto(sacuvanaFigurica);


    }



    public FiguricaDTO azurirajFiguricu(FiguricaUpdateDTO figuricaInsert, Long idFigurice) {

        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Figurica figurica = figuricaRepo.findById(idFigurice)
                .orElseThrow(() -> new EntityNotFoundException("Figurica sa ID: " + idFigurice + " nije pronađena."));


        if (!figurica.getKorisnik().getKorisnickoIme().equals(korisnickoImeIzTokena)) {
            throw new SecurityException("Nemate pravo da menjate ovu figuricu.");
        }

        figurica.setCena(figuricaInsert.getCena());
        figurica.setKategorija(figuricaInsert.getKategorija());
        figurica.setStanje(figuricaInsert.getStanje());
        figurica.setNaslov(figuricaInsert.getNaslov());
        figurica.setSlikaUrl(figuricaInsert.getSlikaUrl());
        figurica.setOpis(figuricaInsert.getOpis());


        figuricaRepo.save(figurica);


        return mapriajJednuUDto(figurica);
    }


    public void deleteById(Long id) {

        String korisicnkoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();


        Figurica figurica = figuricaRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Nije pronadjena figurica sa ID: "+ id));

        if(!figurica.getKorisnik().getKorisnickoIme().equals(korisicnkoImeIzTokena))
        {

            throw new SecurityException("Nemate pravo da menjate ovu figuricu");

        }
         figuricaRepo.deleteById(id);
    }




    public List<FiguricaIDPrikaz> sveFiguriceKorisnikaPoImenu(String imeKorisnika)
    {

        List<Figurica> nizFigurica = figuricaRepo.findByKorisnik_KorisnickoIme(imeKorisnika);

        if (nizFigurica != null) {
            return  mapirajObjekatFiguriceUPrikazID(nizFigurica);
        } else {
            throw new EntityNotFoundException("Desila se greska sa pretragom za korisnika" + imeKorisnika);
        }




    }


    public List<Figurica> mojeFigurice()
    {
        String korisicnkoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Figurica> nizFigurica = figuricaRepo.findByKorisnik_KorisnickoIme(korisicnkoImeIzTokena);

        try{

            return  nizFigurica;
        } catch (Exception e){
            throw new EntityNotFoundException("Desila se greska sa pretragom za korisnika" + korisicnkoImeIzTokena);
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


    public List<FiguricaIDPrikaz> mapirajObjekatFiguriceUPrikazID(List<Figurica> dataLista) {
        List<FiguricaIDPrikaz> potrebnaLista = new ArrayList<>();

        for (Figurica figurica : dataLista) {
            FiguricaIDPrikaz figuricaIDPrikaz = new FiguricaIDPrikaz();
            figuricaIDPrikaz.setNaslov(figurica.getNaslov());
            figuricaIDPrikaz.setIdFigurice(figurica.getId());

            figuricaIDPrikaz.setCena(figurica.getCena());

            figuricaIDPrikaz.setSlikaUrl(figurica.getSlikaUrl());


            potrebnaLista.add(figuricaIDPrikaz);
        }

        return potrebnaLista;
    }

    public FiguricaDTO mapriajJednuUDto(Figurica prilozenaFigurica) {
        List<FiguricaDTO> potrebnaLista = new ArrayList<>();


            FiguricaDTO figuricaDTO = new FiguricaDTO();
            figuricaDTO.setNaslov(prilozenaFigurica.getNaslov());
            figuricaDTO.setDatumKreiranja(prilozenaFigurica.getDatumKreiranja());
            figuricaDTO.setOpis(prilozenaFigurica.getOpis());
            figuricaDTO.setCena(prilozenaFigurica.getCena());
            figuricaDTO.setKategorija(prilozenaFigurica.getKategorija());
            figuricaDTO.setSlikaUrl(prilozenaFigurica.getSlikaUrl());
            figuricaDTO.setStanje(prilozenaFigurica.getStanje());




        return figuricaDTO;
    }


    public List<FiguricaPocetna> mapirajObjekatFiguricePocetna(List<Figurica> dataLista) {
        List<FiguricaPocetna> potrebnaLista = new ArrayList<>();

        for (Figurica figurica : dataLista) {
            FiguricaPocetna figuricaPocetna = new FiguricaPocetna();
            figuricaPocetna.setIdFigurice(figurica.getId());
            figuricaPocetna.setNaslov(figurica.getNaslov());
            figuricaPocetna.setDatumKreiranja(figurica.getDatumKreiranja());
            figuricaPocetna.setOpis(figurica.getOpis());
            figuricaPocetna.setCena(figurica.getCena());
            figuricaPocetna.setKategorija(figurica.getKategorija());
            figuricaPocetna.setSlikaUrl(figurica.getSlikaUrl());
            figuricaPocetna.setStanje(figurica.getStanje());
            figuricaPocetna.setVlasnikFigurice(figurica.getKorisnik().getKorisnickoIme());

            potrebnaLista.add(figuricaPocetna);
        }

        return potrebnaLista;
    }

}
