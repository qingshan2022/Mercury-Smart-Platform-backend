package com.mercury.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mercury.blog.model.dto.star.StarBookQueryRequest;
import com.mercury.blog.model.entity.StarBook;
import com.mercury.blog.model.vo.article.ArticleVo;
import com.mercury.blog.model.vo.star.StarBookBoolVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author mercury
* @description 针对表【star_book】的数据库操作Service
* @createDate 2023-04-20 21:31:59
*/
public interface StarBookService extends IService<StarBook> {

    List<StarBookBoolVo> getStarBooks(Long articleId, HttpServletRequest request);

    Long createStarBook(String name, HttpServletRequest request);

    Page<ArticleVo> getArticlesInStarBook(StarBookQueryRequest starBookQueryRequest);

    List<StarBookBoolVo> getStarBooksByUid(Long uid);
}
