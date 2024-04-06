package com.mercury.member.model.vo.user;

import lombok.Data;
import com.mercury.common.model.vo.UserInfoVo;

@Data
public class RecommendUserVo {
    private UserInfoVo userInfo;
    private Double score;
}
