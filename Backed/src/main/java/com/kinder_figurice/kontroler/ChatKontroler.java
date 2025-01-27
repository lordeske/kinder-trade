package com.kinder_figurice.kontroler;

import com.kinder_figurice.servisi.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatKontroler {

    @Autowired
    private ChatService chatService;

    @GetMapping("/razgovori/{korisnickoIme}")
    public ResponseEntity<List<String>> sviRazgovoriKorisnika(@PathVariable String korisnickoIme) {
        try {
            List<String> razgovori = chatService.dobijSveRazgovoreZaKorisnika(korisnickoIme);
            return ResponseEntity.ok(razgovori);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
