package com.mercury.search.feign;

import com.mercury.common.model.vo.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("mercury-member")
public interface UserFeignService {
    /**
     * 根据uid列表获取用户信息，
     * 根据uid列表获取用户信息，
     * @param uids
     * @return
     */
    @PostMapping("/member/info/list")
    public List<UserInfoVo> getUserListByUids(@RequestBody List<Long> uids);
}
