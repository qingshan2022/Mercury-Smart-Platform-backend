package com.mercury.search.service;

import com.mercury.search.model.dto.article.ArticleQueryRequest;
import com.mercury.search.model.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ArticleSearchService {
    /**
     * 从 ES 查询
     * @param articleQueryRequest
     * @return
     */
    Page<ArticleVo> searchFromEs(ArticleQueryRequest articleQueryRequest);
}
