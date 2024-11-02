package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KorisnikRepo extends JpaRepository<Korisnik, Long> {

        Boolean  existsByEmail(String email);
        Korisnik findByKorisnickoIme(String ime);
}
