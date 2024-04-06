package com.mercury.search.service.impl;

import com.mercury.search.mapper.ArticleMapper;
import com.mercury.search.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercury.search.service.ArticleService;
import org.springframework.stereotype.Service;

/**
* @author mercury
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2023-05-21 10:42:23
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
}




