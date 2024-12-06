package com.kinder_figurice.kontroler;


import com.kinder_figurice.modeli.Chat.PorukaNotif;
import com.kinder_figurice.modeli.Chat.Razgovor;
import com.kinder_figurice.servisi.Chat.RazgovorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RazgovorKontroler {



    private RazgovorService razgovorService;

    private SimpMessagingTemplate template;



    @MessageMapping("razgovor")
    private void procesirajPoruku(
            @Payload Razgovor razgovor
            )
    {

        /// Sacuvaj u bazu

        Razgovor sacuvanaPoruka = razgovorService.sacuvajRazgovor(razgovor);



        // Posalji
        template.convertAndSendToUser(
                razgovor.getPrimalac().getId().toString(),
                "/queue/poruke",
                PorukaNotif.builder()
                        .id(sacuvanaPoruka.getId())
                        .posiljalacID(sacuvanaPoruka.getPosiljalac().getId())
                        .primalacID(sacuvanaPoruka.getPrimalac().getId())
                        .poruka(sacuvanaPoruka.getTekstPoruke())
                        .build()

        );





    }



    @GetMapping(path = "/poruke/{posiljalacID}/{primalacID}")
    public ResponseEntity<List<Razgovor>> prikaziPoruke(
            @PathVariable("posiljalacID") Long posiljalacID,
            @PathVariable ("primalacID")  Long primalacID
    )
    {

        return ResponseEntity.ok(razgovorService.pronadjiPoruke(posiljalacID, primalacID));



    }














}
