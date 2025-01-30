package com.kinder_figurice.kontroler;

import com.kinder_figurice.modeli.Poruka;
import com.kinder_figurice.servisi.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PrivatniChatKontorler {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/privateMessage")
    public void posaljiPrivatnuPoruku(@Payload Poruka poruka) {
        System.out.println("Privatna poruka primljena: " + poruka.getPosiljalac() + " → " + poruka.getPrimalac());

        Poruka sacuvanaPoruka = chatService.posaljiPoruku(
                poruka.getPosiljalac(),
                poruka.getPrimalac(),
                poruka.getSadrzajPoruke()
        );


        messagingTemplate.convertAndSendToUser(poruka.getPrimalac(), "/privatne", sacuvanaPoruka);


        messagingTemplate.convertAndSendToUser(poruka.getPosiljalac(), "/privatne", sacuvanaPoruka);
    }
}