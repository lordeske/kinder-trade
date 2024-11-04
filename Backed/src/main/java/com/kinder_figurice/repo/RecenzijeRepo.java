package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Recenzije;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecenzijeRepo extends JpaRepository<Recenzije, Long> {


    List<Recenzije> findByRecenziraniKorisnikId(Long id);

}
