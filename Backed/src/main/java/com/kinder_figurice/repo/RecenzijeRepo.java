package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Recenzije;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecenzijeRepo extends JpaRepository<Recenzije, Long> {


    Page<Recenzije> findByRecenziraniKorisnik_KorisnickoIme(String korisnickoIme, Pageable pageable);

    long countByRecenzentAndRecenziraniKorisnik(Korisnik recenzent, Korisnik recenziraniKorisnik);


}
