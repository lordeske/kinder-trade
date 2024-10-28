package com.kinder_figurice.repo;


import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Omiljeni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OmiljeniRepo extends JpaRepository<Omiljeni, Long> {

    List<Omiljeni> findByKorisnik(Korisnik korisnik);


}
