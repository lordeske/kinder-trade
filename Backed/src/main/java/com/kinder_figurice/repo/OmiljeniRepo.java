package com.kinder_figurice.repo;


import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Omiljeni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OmiljeniRepo extends JpaRepository<Omiljeni, Long> {

    List<Omiljeni> findByKorisnikId(Long idKorisnika);

    List<Omiljeni> findByKorisnik(Korisnik korisnik);
    Optional<Omiljeni> findByKorisnikAndFiguricaId(Korisnik korisnik, Long figuricaId);

    boolean existsByKorisnikAndFigurica(Korisnik korisnik, Figurica figurica);


}
