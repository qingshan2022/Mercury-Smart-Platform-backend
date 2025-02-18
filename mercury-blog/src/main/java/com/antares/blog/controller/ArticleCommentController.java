package com.mercury.blog.controller;

import com.mercury.blog.model.dto.comment.PostCommentRequest;
import com.mercury.blog.model.vo.comment.ChildrenCommentVo;
import com.mercury.blog.model.vo.comment.RootCommentVo;
import com.mercury.blog.service.ArticleCommentService;
import com.mercury.common.model.response.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blog/comment")
@Validated
public class ArticleCommentController {
    @Resource
    private ArticleCommentService articleCommentService;

    /**
     * 获取某个文章的根评论
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<List<RootCommentVo>> getRootCommentsOfArticle(@PathVariable("id") Long id){
        List<RootCommentVo> vos = articleCommentService.getRootCommentsOfArticle(id);
        return R.ok(vos);
    }

    /**
     * 获取某个根评论下的子评论，后期可以优化成分页，就像B站那样
     * @param id
     * @return
     */
    @GetMapping("/children/{id}")
    public R<List<ChildrenCommentVo>> getChildrenOfRoot(@PathVariable("id") Long id){
        List<ChildrenCommentVo> vos = articleCommentService.getChildrenOfRoot(id);
        return R.ok(vos);
    }

    //Todo: 功能待实现
    /**
     * 点赞评论
     * @param id: 评论id
     * @return
     */
    @PostMapping("/like/{id}")
    public R likeComment(@PathVariable("id") Long id){
        articleCommentService.likeComment(id);
        return R.ok();
    }

    /**
     * 发表评论
     * @param postCommentRequest
     * @param request
     * @return
     */
    @PostMapping
    public R publishComment(@Valid @RequestBody PostCommentRequest postCommentRequest, HttpServletRequest request){
        articleCommentService.publishComment(postCommentRequest, request);
        return R.ok();
    }
}
