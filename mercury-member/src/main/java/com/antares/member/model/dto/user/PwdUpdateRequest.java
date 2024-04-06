package com.mercury.member.model.dto.user;

import lombok.Data;

@Data
public class PwdUpdateRequest {
    private String originalPwd;
    private String newPwd;
}
