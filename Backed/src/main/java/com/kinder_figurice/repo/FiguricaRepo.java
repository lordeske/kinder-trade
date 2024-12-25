package com.kinder_figurice.repo;

import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FiguricaRepo extends JpaRepository<Figurica, Long> {


   List<Figurica> findByNaslov(String naslov);
   List<Figurica> findByKorisnikId(Long idKorisnika);
   List<Figurica> findByKorisnik_KorisnickoIme(String korisnickoIme);


}
