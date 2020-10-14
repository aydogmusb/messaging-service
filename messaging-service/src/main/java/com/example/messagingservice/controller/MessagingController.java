package com.example.messagingservice.controller;

import com.example.messagingservice.dto.MessageDto;
import com.example.messagingservice.entity.User;
import com.example.messagingservice.service.MessagingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessagingController {

    private final MessagingService messagingService;

    public MessagingController(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping(path = "/send")
    public void sendMessage(@RequestBody String message, User sender, String receiverName) {
        messagingService.sendMessage(message, sender, receiverName);
    }

    @GetMapping(path = "/messages/{id}")
    public List<MessageDto> retrieveMessages(@PathVariable Long id) {
        return messagingService.retrieveMessages(id);
    }
}
