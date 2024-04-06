package com.mercury.blog.model.dto.tag;

import lombok.Data;

@Data
public class ArticleTagAddRequest {
    private Long parentId;
    private String name;
}
