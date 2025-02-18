package com.mercury.blog.job;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.mercury.blog.mapper.ArticleTagCategoryMapper;
import com.mercury.blog.mapper.ArticleTagMapper;
import com.mercury.blog.model.entity.Article;
import com.mercury.blog.model.entity.ArticleTag;
import com.mercury.blog.model.entity.ArticleTagCategory;
import com.mercury.blog.model.vo.tag.ArticleTagVo;
import com.mercury.blog.service.ArticleService;
import com.mercury.blog.utils.RedisUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import com.mercury.common.constant.SystemConstants;
import com.mercury.common.utils.BeanCopyUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.mercury.common.constant.RedisConstants.*;

/**
 * @author mercury
 * @date 2023/8/24 15:34
 * @description 启动将标签加载到redis
 */
@Component

@Slf4j
public class StartRunner implements CommandLineRunner {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private ArticleTagCategoryMapper articleTagCategoryMapper;
    @Resource
    private ArticleService articleService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public void run(String... args) {
        /**
         * 文章标签相关逻辑
         */
        //首先查询所有的类别
        List<ArticleTagCategory> articleTagCategories = articleTagCategoryMapper.selectList(null);
        if(CollectionUtils.isNotEmpty(articleTagCategories)){
            stringRedisTemplate.delete(ARTICLE_TAGS_CATEGORY);
            redisUtils.rightPushAllAsString(ARTICLE_TAGS_CATEGORY, articleTagCategories);
        }

        //根据类别查询标签
        articleTagCategories.stream().forEach(articleTagCategory -> {
            LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTag::getParentId, articleTagCategory.getId());
            List<ArticleTagVo> articleTags = articleTagMapper.selectList(wrapper).stream()
                    .map(articleTag -> BeanCopyUtils.copyBean(articleTag, ArticleTagVo.class))
                    .collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(articleTags)){
                stringRedisTemplate.delete(ARTICLE_TAGS_PREFIX + articleTagCategory.getId());
                redisUtils.rightPushAllAsString(ARTICLE_TAGS_PREFIX + articleTagCategory.getId(), articleTags);
            }
        });

        /**
         * 已发布文章的zSet
         */
        List<Article> articles = articleService.lambdaQuery().select(Article::getId, Article::getUpdateTime)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLISHED).list();
        for (Article article : articles) {
            stringRedisTemplate.opsForZSet().add(ARTICLE_PUBLISHED,
                    article.getId().toString(),
                    article.getUpdateTime().getTime());
        }
    }
}