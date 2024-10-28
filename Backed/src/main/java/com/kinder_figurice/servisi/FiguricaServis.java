package com.kinder_figurice.servisi;


import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.repo.FiguricaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiguricaServis {


    @Autowired
    private FiguricaRepo figuricaRepo;



    public List<Figurica> findAll() {
        return figuricaRepo.findAll();
    }


    public Optional<Figurica> findById(Long id) {
        return figuricaRepo.findById(id);
    }


    public Figurica save(Figurica figurica) {
        return figuricaRepo.save(figurica);
    }


    public Figurica update(Figurica figurica) {
        return figuricaRepo.save(figurica);
    }


    public void deleteById(Long id) {
        figuricaRepo.deleteById(id);
    }


}
