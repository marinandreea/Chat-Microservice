package com.example.DS_Assignment3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypingNotification {
    private String senderName;
    private String receiverName;
    private String message;
    private Status status;
}
