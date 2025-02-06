package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Trgovina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrgovinaRepo extends JpaRepository<Trgovina, Long> {
    List<Trgovina> findByPrimalac(Korisnik primalac);
    List<Trgovina> findByPosiljalac(Korisnik posiljalac);

    List<Trgovina> findByPosiljalacOrPrimalac(Korisnik korisnik);
}
