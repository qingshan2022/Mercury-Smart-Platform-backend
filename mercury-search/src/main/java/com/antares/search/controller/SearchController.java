package com.mercury.search.controller;

import com.mercury.search.manager.SearchFacade;
import com.mercury.search.model.dto.SearchRequest;
import com.mercury.search.model.vo.SearchVo;
import com.mercury.common.model.response.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private SearchFacade searchFacade;

    @PostMapping
    public R<SearchVo> searchByType(@RequestBody SearchRequest searchRequest){
        return R.ok(searchFacade.search(searchRequest));
    }
}
