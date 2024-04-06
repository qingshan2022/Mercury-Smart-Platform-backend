package com.mercury.member.model.dto.chat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.mercury.common.utils.PageRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageQueryRequest extends PageRequest {
    private Long conversationId;
}
