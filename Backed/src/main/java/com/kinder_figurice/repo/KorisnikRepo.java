package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KorisnikRepo extends JpaRepository<Korisnik, Long> {

        Boolean existsByEmail(String email);
        Optional<Korisnik> findByKorisnickoIme(String korisnickoIme);
        Optional<Korisnik> findByEmail(String email);
        Boolean existsByKorisnickoIme(String korisnickoIme);

        @Query(value = "SELECT * FROM korisnici WHERE korisnicko_ime != :trenutnoIme ORDER BY RAND() LIMIT 3", nativeQuery = true)
        List<Korisnik> prikaziPredlozeneProfile(@Param("trenutnoIme") String trenutnoIme);

}
