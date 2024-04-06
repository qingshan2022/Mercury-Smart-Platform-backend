package com.mercury.blog.model.vo.tag;

import lombok.Data;

import java.util.List;

@Data
public class ArticleTagCategoryVo {
    private Long id;

    private String name;

    private List<ArticleTagVo> tags;
}
