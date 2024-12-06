package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Chat.Razgovor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RazgovorRepo extends JpaRepository<com.kinder_figurice.modeli.Chat.Razgovor, Long> {


    List<Razgovor> findByIdRazgovora(String idRazgovora);




}
