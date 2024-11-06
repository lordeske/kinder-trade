package com.kinder_figurice.repo;

import com.kinder_figurice.modeli.Chat.RazgovorSoba;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RazgovorSobaRepo extends JpaRepository<RazgovorSoba, Long> {


    Optional<RazgovorSoba> findByPrimalac_IdAndPosiljalac_Id(Long posiljalacID, Long primilacID);
}
