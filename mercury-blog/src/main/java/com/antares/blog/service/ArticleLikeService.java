package com.mercury.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mercury.blog.model.entity.ArticleLike;

import javax.servlet.http.HttpServletRequest;

/**
* @author mercury
* @description 针对表【article_like】的数据库操作Service
* @createDate 2023-05-09 20:05:25
*/
public interface ArticleLikeService extends IService<ArticleLike> {

    void likeBlog(Long id, HttpServletRequest request);

    void likeBlog(Long uid, Long articleId, Long authorId);
}
