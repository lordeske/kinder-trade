package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Poruka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PorukaRepo extends JpaRepository<Poruka, Long> {


    List<Poruka> findBySoba_NazivSobe(String nazivSobe);



}


