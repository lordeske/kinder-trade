package com.kinder_figurice.kontroler;


import com.kinder_figurice.modeli.Poruka;
import com.kinder_figurice.servisi.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/poruke")
public class PorukaKontroler {


    @Autowired
    private ChatService chatService;


    @PostMapping("posalji")
    private ResponseEntity<Poruka> posaljiPoruku(
            @RequestParam String posiljalac,
            @RequestParam String primalac,
            @RequestParam String sadrzajPoruke


    )
    {

        Poruka poruka =  chatService.posaljiPoruku(posiljalac,primalac,sadrzajPoruke);
        return new ResponseEntity<>(poruka, HttpStatus.OK);


    }

    @GetMapping("/izmedju")
    public ResponseEntity<List<Poruka>> svePorukeIzmedjuDvaKorisnika(
            @RequestParam String korisnik1,
            @RequestParam String korisnik2
    )
    {

       List<Poruka> poruke = chatService.svePorukeIzmedjuKorisnika(korisnik1,korisnik2);


       return new ResponseEntity<>(poruke, HttpStatus.OK);

    }









}
