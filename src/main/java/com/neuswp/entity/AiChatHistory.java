package com.neuswp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiChatHistory {
    private Integer id;
    private String question;
    private String reply;
    private Integer userId;
}