package com.mercury.member.model.vo.tag;

import com.mercury.member.model.entity.UserTag;
import lombok.Data;

import java.util.List;

@Data
public class UserTagCategoryVo {
    private Long id;

    private String name;

    private List<UserTag> tags;
}
