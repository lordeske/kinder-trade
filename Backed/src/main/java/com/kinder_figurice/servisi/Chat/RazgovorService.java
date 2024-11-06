package com.kinder_figurice.servisi.Chat;


import com.kinder_figurice.modeli.Chat.Razgovor;
import com.kinder_figurice.repo.RazgovorRepo;
import com.kinder_figurice.repo.RazgovorSobaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RazgovorService {




    @Autowired

    private RazgovorRepo razgovorRepo;

    @Autowired
    private RazgovorSobaService razgovorSobaService;

    @Autowired
    private RazgovorSobaRepo razgovorSobaRepo;




    public Razgovor sacuvajRazgovor(Razgovor razgovor)
    {


        var razgovorId = razgovorSobaService.getIdRazgovora(
                razgovor.getPrimalac().getId(),
                razgovor.getPosiljalac().getId(),
                true
        );




        razgovor.setIdRazgovora(razgovorId.get());
        razgovorRepo.save(razgovor);

        return razgovor;

    }


    public List<Razgovor> pronadjiPoruke(
            Long posiljalacID,
            Long primilacID
    )
    {
        var idRazgovora = razgovorSobaService.getIdRazgovora(primilacID, posiljalacID, false);

        return idRazgovora.map(razgovorRepo :: findByIdRazgovora).orElse(new ArrayList<>());


    }








}
