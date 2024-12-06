package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KorisnikRepo extends JpaRepository<Korisnik, Long> {

        Boolean existsByEmail(String email);
        Optional<Korisnik> findByKorisnickoIme(String korisnickoIme);
        Optional<Korisnik> findByEmail(String email);
        Boolean existsByKorisnickoIme(String korisnickoIme);
}
