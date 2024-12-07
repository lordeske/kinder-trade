package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Soba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SobaRepo extends JpaRepository<Soba, Long> {

   Optional<Soba> findByNazivSobe(String nazivSobe);


}
