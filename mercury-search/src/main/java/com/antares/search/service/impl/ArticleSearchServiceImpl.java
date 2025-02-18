package com.mercury.search.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.mercury.search.feign.ArticleFeignService;
import com.mercury.search.model.dto.article.ArticleEsDTO;
import com.mercury.search.model.dto.article.ArticleQueryRequest;
import com.mercury.search.model.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import com.mercury.common.constant.SystemConstants;
import com.mercury.search.service.ArticleSearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {
    @Resource
    private ArticleFeignService articleFeignService;
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Page<ArticleVo> searchFromEs(ArticleQueryRequest articleQueryRequest) {
        String keyword = articleQueryRequest.getKeyword();
        List<String> tags = articleQueryRequest.getTags();
        int pageNum = articleQueryRequest.getPageNum();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("status", SystemConstants.ARTICLE_STATUS_PUBLISHED));

        // 必须包含所有标签
        if (CollectionUtils.isNotEmpty(tags)) {
            for (String tag : tags) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("tags", tag));
            }
        }

        if (StringUtils.isNotBlank(keyword)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", keyword));
            boolQueryBuilder.should(QueryBuilders.matchQuery("summary", keyword));
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", keyword));
            boolQueryBuilder.should(QueryBuilders.matchQuery("createdBy", keyword));
            boolQueryBuilder.minimumShouldMatch(1);
        }

        // 分页（es的起始页为0）
        PageRequest pageRequest = PageRequest.of(pageNum - 1, 10);
        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withPageable(pageRequest).build();
        SearchHits<ArticleEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, ArticleEsDTO.class);
        Page<ArticleVo> page = new Page<>();
        page.setTotal(searchHits.getTotalHits());
        // 查出结果后，从 db 获取最新动态数据（比如点赞数）
        if (searchHits.hasSearchHits()) {
            List<SearchHit<ArticleEsDTO>> searchHitList = searchHits.getSearchHits();
            List<Long> articleIds = searchHitList.stream().map(searchHit -> searchHit.getContent().getId())
                    .collect(Collectors.toList());
            // 从数据库中取出更完整的数据
            List<ArticleVo> articleVoList = articleFeignService.getArticlesByIds(articleIds);
            if(articleVoList != null) {
                page.setRecords(articleVoList);
            }
        } else {
            page.setRecords(new ArrayList<>());
        }
        return page;
    }
}
