package com.mercury.search.feign;

import com.mercury.search.model.vo.ArticleVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("mercury-blog")
public interface ArticleFeignService {
    /**
     * 根据Id列表查询对应的文章，只用于远程调用
     * @param articleIds
     * @return
     */
    @PostMapping("/blog/article/list")
    List<ArticleVo> getArticlesByIds(@RequestBody List<Long> articleIds);
}
