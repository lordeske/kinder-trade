package com.kinder_figurice.kontroler;

import com.kinder_figurice.dto.PorukaDTO.ChatPoruka;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class JavniChat {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatPoruka broadcastMessage(@Payload ChatPoruka poruka) {
        return poruka;
    }



}
