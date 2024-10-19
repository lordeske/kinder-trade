package com.kinder_figurice.Repo;

import com.kinder_figurice.Modeli.Figurica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiguricaRepo extends JpaRepository<Figurica, Long> {

   List<Figurica> findByIdKorisnika(Long idKorisnika);
}
