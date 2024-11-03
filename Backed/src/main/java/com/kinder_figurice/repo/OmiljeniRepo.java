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
    Optional<Omiljeni> findByKorisnikIdAndFiguricaId(Long korisnikId, Long figuricaId);



}
