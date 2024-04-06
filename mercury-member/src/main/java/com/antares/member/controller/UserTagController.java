package com.mercury.member.controller;

import com.mercury.common.model.response.R;
import com.mercury.common.model.vo.UserTagVo;
import com.mercury.member.model.dto.tag.UserTagAddRequest;
import com.mercury.member.model.vo.tag.UserTagCategoryVo;
import com.mercury.member.service.UserTagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/member/tags")
public class UserTagController {
    @Resource
    private UserTagService userTagService;

    /**
     * 获取所有标签
     * @return
     */
    @GetMapping
    public R<List<UserTagCategoryVo>> getAllTags(){
        List<UserTagCategoryVo> allTags = userTagService.getAllTags();
        return R.ok(allTags);
    }

    /**
     * 添加一个标签
     * @param userTagAddRequest
     * @param request
     * @return
     */
    @PutMapping
    public R<UserTagVo> addATag(@RequestBody UserTagAddRequest userTagAddRequest, HttpServletRequest request){
        UserTagVo userTagVo = userTagService.addATag(userTagAddRequest, request);
        return R.ok(userTagVo);
    }
}
