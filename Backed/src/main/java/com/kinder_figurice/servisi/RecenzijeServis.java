package com.kinder_figurice.servisi;


import com.kinder_figurice.dto.RecenzijeDTO.RecenzijaDTO;
import com.kinder_figurice.dto.RecenzijeDTO.RecenzijaKreiranjeDTO;

import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Recenzije;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.repo.RecenzijeRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import java.util.List;


@Service
public class RecenzijeServis {
    @Autowired
    private RecenzijeRepo recenzijeRepo;

    @Autowired
    private KorisnikRepo korisnikRepo;

    public RecenzijaDTO kreirajRecenziju(RecenzijaKreiranjeDTO recenzijaKreiranjeDTO) {
        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();
        Korisnik recenzent = korisnikRepo.findByKorisnickoIme(korisnickoImeIzTokena)
                .orElseThrow(() -> new EntityNotFoundException("Recenzent nije pronadjen."));
        Korisnik recenziraniKorisnik = korisnikRepo.findByKorisnickoIme(recenzijaKreiranjeDTO.getImeRecenziranog())
                .orElseThrow(() -> new EntityNotFoundException("Recenzirani korisnik nije pronadjen."));

        validateRecenzija(recenzijaKreiranjeDTO, recenzent, recenziraniKorisnik);
        return kreirajRecenzijuFunkija(recenzijaKreiranjeDTO, recenzent);
    }

    public Page<RecenzijaDTO> prikaziRecenzijeKorisnika(String korisnickoIme, Pageable pageable) {
        Page<Recenzije> recenzijePage = recenzijeRepo.findByRecenziraniKorisnik_KorisnickoIme(korisnickoIme, pageable);
        return recenzijePage.map(this::mapirajUDto);
    }

    public void obrisiRecenziju(Long recenzijaId) {
        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();
        Recenzije recenzija = recenzijeRepo.findById(recenzijaId)
                .orElseThrow(() -> new EntityNotFoundException("Recenzija nije pronadjena."));
        if (!recenzija.getRecenzent().getKorisnickoIme().equals(korisnickoImeIzTokena)) {
            throw new SecurityException("Nemate pravo da obrisete ovu recenziju.");
        }
        recenzijeRepo.deleteById(recenzijaId);
    }

    private void validateRecenzija(RecenzijaKreiranjeDTO recenzijaKreiranjeDTO, Korisnik recenzent, Korisnik recenziraniKorisnik) {
        if (recenzijaKreiranjeDTO.getOcena() < 1 || recenzijaKreiranjeDTO.getOcena() > 5) {
            throw new IllegalArgumentException("Ocena mora biti izmedju 1 i 5.");
        }
        if (recenzent.equals(recenziraniKorisnik)) {
            throw new IllegalStateException("Ne mozete recenzirati samog sebe.");
        }
        long brojRecenzija = recenzijeRepo.countByRecenzentAndRecenziraniKorisnik(recenzent, recenziraniKorisnik);
        if (brojRecenzija >= 3) {
            throw new IllegalStateException("Ne mozete dodati više od 3 recenzije za ovog korisnika.");
        }
    }

    private RecenzijaDTO mapirajUDto(Recenzije recenzije) {
        RecenzijaDTO recenzijaDTO = new RecenzijaDTO();
        recenzijaDTO.setImeRecenzenta(recenzije.getRecenzent().getKorisnickoIme());
        recenzijaDTO.setKomentar(recenzije.getKomentar());
        recenzijaDTO.setOcena(recenzije.getOcena());
        recenzijaDTO.setDatumKreiranja(recenzije.getDatumKreiranja());
        recenzijaDTO.setIdRecenzije(recenzije.getId());
        return recenzijaDTO;
    }


    public List<Recenzije> sveRecenzije()
    {
        return    recenzijeRepo.findAll();
    }

    public  RecenzijaDTO  kreirajRecenzijuFunkija(RecenzijaKreiranjeDTO recenzijaKreiranjeDTO, Korisnik recenzent)
    {

        Korisnik recenziraniKorisnik = korisnikRepo.findByKorisnickoIme(recenzijaKreiranjeDTO.getImeRecenziranog())
                .orElseThrow(() -> new EntityNotFoundException("recenzirani Korisnik nije pronadjen: " + recenzijaKreiranjeDTO.getImeRecenziranog()));





        Recenzije recenzija = new Recenzije();
        recenzija.setKomentar(recenzijaKreiranjeDTO.getKomentar());
        recenzija.setOcena(recenzijaKreiranjeDTO.getOcena());
        recenzija.setRecenzent(recenzent);
        recenzija.setRecenziraniKorisnik(recenziraniKorisnik);
        recenzijeRepo.save(recenzija);

        return mapirajUDto(recenzija);






    }

}
