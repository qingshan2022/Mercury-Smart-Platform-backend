package com.mercury.search.service.impl;

import com.mercury.search.mapper.ArticleTagMapper;
import com.mercury.search.model.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercury.search.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
* @author mercury
* @description 针对表【article_tag(文章标签表)】的数据库操作Service实现
* @createDate 2023-05-21 10:46:46
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




