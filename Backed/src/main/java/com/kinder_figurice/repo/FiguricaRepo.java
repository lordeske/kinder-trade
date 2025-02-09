package com.kinder_figurice.repo;

import com.kinder_figurice.dto.FiguricaDTO.FiguricaDTO;
import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FiguricaRepo extends JpaRepository<Figurica, Long> {


   List<Figurica> findByNaslov(String naslov);
   List<Figurica> findByKorisnikId(Long idKorisnika);
   List<Figurica> findByKorisnik_KorisnickoIme(String korisnickoIme);


   @Query(value = "SELECT * FROM figurice ORDER BY RAND() LIMIT :limit", nativeQuery = true)
   List<Figurica> dobaviRandomFigurice(@Param("limit") int limit);


   boolean existsByIdAndKorisnik(Long id, Korisnik vlasnik);



}
