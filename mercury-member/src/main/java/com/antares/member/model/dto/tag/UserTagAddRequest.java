package com.mercury.member.model.dto.tag;

import lombok.Data;

@Data
public class UserTagAddRequest {
    private Long parentId;
    private String name;
}
