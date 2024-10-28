package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Figurica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FiguricaRepo extends JpaRepository<Figurica, Long> {
}
