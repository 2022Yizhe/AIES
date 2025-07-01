package com.jubilantz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiMessage {
    private String role;    // 'system', 'user', 'assistant'
    private String content; // System's prompt, User's prompt, Assistant's reply
}