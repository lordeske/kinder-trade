package com.kinder_figurice.servisi.Chat;

import com.kinder_figurice.modeli.Chat.RazgovorSoba;
import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.repo.RazgovorSobaRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RazgovorSobaService {


    @Autowired
    private RazgovorSobaRepo razgovorSobaRepo;

    @Autowired
    private KorisnikRepo korisnikRepo;



    public Optional<String> getIdRazgovora(
            Long primalacId,
            Long posiljalacId,
            Boolean kreirajAkoNePostoji
    ) {
        return razgovorSobaRepo.findByPrimalac_IdAndPosiljalac_Id(posiljalacId, primalacId)
                .map(RazgovorSoba::getIdRazgovorSoba)
                .or(() -> {
                    if (kreirajAkoNePostoji) {
                        var idRazgovora = kreirajRazgovor(primalacId, posiljalacId);
                        return Optional.of(idRazgovora);
                    }
                    return Optional.empty();
                });
    }




    public String kreirajRazgovor(
            Long primalacId,
            Long posiljalacId
    )
    {

        var buduciRazgovorId = String.format("%s_%s", primalacId, posiljalacId);


        Optional<Korisnik> primalac =korisnikRepo.findById(primalacId);
        Optional<Korisnik> posiljac =korisnikRepo.findById(posiljalacId);




        RazgovorSoba razgovorSobaChat =  RazgovorSoba.builder()
                .idRazgovorSoba(buduciRazgovorId)
                .primalac(primalac.get())
                .posiljalac(posiljac.get())
                .build();


        RazgovorSoba razgovorSobaChat2 =  RazgovorSoba.builder()
                .idRazgovorSoba(buduciRazgovorId)
                .primalac(posiljac.get())
                .posiljalac(primalac.get())
                .build();


        razgovorSobaRepo.save(razgovorSobaChat);
        razgovorSobaRepo.save(razgovorSobaChat2);


        return buduciRazgovorId;




    }




}
