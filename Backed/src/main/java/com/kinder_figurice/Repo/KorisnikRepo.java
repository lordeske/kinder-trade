package com.kinder_figurice.Repo;

import com.kinder_figurice.Modeli.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface KorisnikRepo extends JpaRepository<Korisnik, Long> {
    Korisnik findByEmail(String email);
}
