package com.example.DS_Assignment3.controller;

import com.example.DS_Assignment3.model.Message;
import com.example.DS_Assignment3.model.Status;
import com.example.DS_Assignment3.model.TypingNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableMethodSecurity
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @CrossOrigin(origins = "http://localhost:3000")
    @MessageMapping("/message")
    @SendTo("/adminChat/admin")
    public Message receivePublicMessage(@Payload Message message){
        return message;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @MessageMapping("/privateMessage")
    public Message receivePrivateMessage(@Payload Message message){

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private",message);
        return message;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @MessageMapping("/typing")
    public TypingNotification handleTypingNotification(@Payload TypingNotification typingNotification) {
        // Broadcast typing notification to the relevant users
        simpMessagingTemplate.convertAndSendToUser(typingNotification.getReceiverName(), "/private", typingNotification);
        return typingNotification;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @MessageMapping("/readReceipt")
    public void handleReadReceipt(@Payload Message message) {
        // Update the isRead field in your database or data structure
        message.setRead(true);
        message.setStatus(Status.READ);

        // Broadcast the updated message with read receipt to the sender and receiver
        simpMessagingTemplate.convertAndSendToUser(message.getSenderName(), "/private", message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
    }


}