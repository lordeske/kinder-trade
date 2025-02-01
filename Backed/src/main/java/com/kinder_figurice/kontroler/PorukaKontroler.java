package com.kinder_figurice.kontroler;


import com.kinder_figurice.modeli.Poruka;
import com.kinder_figurice.servisi.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/poruke")
public class PorukaKontroler {


    @Autowired
    private ChatService chatService;



    @GetMapping("/izmedju")
    public ResponseEntity<List<Poruka>> svePorukeIzmedjuDvaKorisnika(

            @RequestParam String korisnik2
    )
    {

        List<Poruka> poruke = chatService.svePorukeIzmedjuKorisnika(korisnik2);


        return new ResponseEntity<>(poruke, HttpStatus.OK);

    }

}
