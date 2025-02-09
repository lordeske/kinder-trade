package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.StatusTrgovine;
import com.kinder_figurice.modeli.Trgovina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrgovinaRepo extends JpaRepository<Trgovina, Long> {
    List<Trgovina> findByPrimalac(Korisnik primalac);
    List<Trgovina> findByPosiljalac(Korisnik posiljalac);

    List<Trgovina> findByPosiljalacOrPrimalac(Korisnik posiljalac, Korisnik primalac );


    @Query("SELECT t FROM Trgovina t WHERE (t.posiljalac = :korisnik OR t.primalac = :korisnik) AND t.status = :status")
    List<Trgovina> findAllByKorisnikAndStatus(@Param("korisnik") Korisnik korisnik, @Param("status") StatusTrgovine status);

}
