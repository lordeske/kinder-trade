package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Trgovina;
import com.kinder_figurice.modeli.TrgovinaFigurice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrgovinaFiguriceRepo extends JpaRepository<TrgovinaFigurice, Long> {
    List<TrgovinaFigurice> findByTrgovina(Trgovina trgovina);
    void deleteAllByTrgovina(Trgovina trgovina);
}