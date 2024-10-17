package com.kinder_figurice.Repo;

import com.kinder_figurice.Modeli.Figurica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiguricaRepo extends JpaRepository<Figurica, Long> {
}
