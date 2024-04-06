package com.mercury.blog.model.dto.star;

import lombok.Data;

@Data
public class StarBlogRequest {
    private Long[] bookIds;
    private Long articleId;
}
