package com.mercury.member.model.dto.chat;

import com.mercury.member.model.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    private Integer code;
    private Long uid;
    private Long conversationId;
    private ChatMessage chatMessage;
}
