package com.kinder_figurice.servisi;


import com.kinder_figurice.dto.RecenzijeDTO.RecenzijaDTO;
import com.kinder_figurice.dto.RecenzijeDTO.RecenzijaRequest;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Recenzije;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.repo.RecenzijeRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecenzijeServis {


    @Autowired
    private RecenzijeRepo recenzijeRepo;


    @Autowired
    private KorisnikRepo korisnikRepo;



    public Recenzije kreirajRecenziju(RecenzijaRequest recenzijaRequest)
    {

        Korisnik recenzent = korisnikRepo.findById(recenzijaRequest.getRecenzentID())
                .orElseThrow(()-> new EntityNotFoundException("Nije pronadjen recenzent"));

        Korisnik recenzirani = korisnikRepo.findById(recenzijaRequest.getRecenziraniID())
                .orElseThrow(()-> new EntityNotFoundException("Nije pronadjen recenzirani"));

        Recenzije recenzija = new Recenzije();
        recenzija.setRecenzent(recenzent);
        recenzija.setRecenziraniKorisnik(recenzirani);
        recenzija.setDatumKreiranja(LocalDateTime.now());
        recenzija.setOcena(recenzijaRequest.getOcena());
        recenzija.setKomentar(recenzijaRequest.getKomentar());

        return recenzijeRepo.save(recenzija);




    }



    public List<RecenzijaDTO> sveRecenzijeKorisnika(Long idKorisnika) {
        return recenzijeRepo.findByRecenziraniKorisnikId(idKorisnika)
                .stream()
                .map(recenzije -> new RecenzijaDTO(
                        recenzije.getRecenzent().getKorisnickoIme(),
                        recenzije.getOcena(),
                        recenzije.getKomentar(),
                        recenzije.getDatumKreiranja()
                ))
                .collect(Collectors.toList());
    }




    /// Cisto za upraljvanje kasnije dodati za admina

    public List<Recenzije> sveRecenzije() {
        return recenzijeRepo.findAll();
    }

    public void obrisiRecenziju(Long id) {
        recenzijeRepo.deleteById(id);
    }


}
