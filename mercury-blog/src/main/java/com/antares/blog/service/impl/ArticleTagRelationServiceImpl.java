package com.mercury.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercury.blog.model.entity.ArticleTag;
import com.mercury.blog.model.entity.ArticleTagRelation;
import com.mercury.blog.mapper.ArticleTagMapper;
import com.mercury.blog.mapper.ArticleTagRelationMapper;
import com.mercury.blog.service.ArticleTagRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author mercury
* @description 针对表【article_tag_relation(文章和文章标签关联表)】的数据库操作Service实现
* @createDate 2023-03-24 20:40:14
*/
@Service
public class ArticleTagRelationServiceImpl extends ServiceImpl<ArticleTagRelationMapper, ArticleTagRelation>
    implements ArticleTagRelationService {
    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<ArticleTag> getTagsByArticleId(Long articleId) {
        List<Long> tagIds = lambdaQuery().select(ArticleTagRelation::getTagId)
                .eq(ArticleTagRelation::getArticleId, articleId).list()
                .stream().map(articleTagRelation -> articleTagRelation.getTagId())
                .collect(Collectors.toList());
        if(tagIds.isEmpty()){
            return new ArrayList<>();
        }
        return articleTagMapper.selectBatchIds(tagIds);
    }
}




