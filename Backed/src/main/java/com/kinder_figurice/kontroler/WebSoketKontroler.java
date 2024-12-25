package com.kinder_figurice.kontroler;


import com.kinder_figurice.dto.PorukaDTO.PorukaDTO;
import com.kinder_figurice.modeli.Poruka;
import com.kinder_figurice.servisi.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSoketKontroler {

    @Autowired
    private ChatService chatService;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public Poruka posaljiPoruku(@Payload Poruka poruka)
    {

        return chatService.posaljiPoruku(poruka.getPosiljalac(), poruka.getPrimalac(),poruka.getSadrzajPoruke());

    }

    @MessageMapping("/privateMessage")
    public Poruka posaljiPrivatnuPoruku(@Payload PorukaDTO poruka){

        Poruka savedPoruka = chatService.posaljiPoruku(poruka.getPosiljalac(), poruka.getPrimalac(), poruka.getSadrzajPoruke());

        //Salje poruku primaocu na kanal /user/{username}/queue/messages
        messagingTemplate.convertAndSendToUser(poruka.getPrimalac(),"/queue/messages", savedPoruka);

        //Salje poruku primaocu na kanal /user/{username}/queue/messages
        messagingTemplate.convertAndSendToUser(poruka.getPosiljalac(),"/queue/messages", savedPoruka);
        messagingTemplate.convertAndSendToUser(poruka.getPrimalac(), "/queue/notifications", "Notifikacija");


        return savedPoruka;


    }





}
