package com.mercury.blog.feign;

import com.mercury.common.model.dto.UsernameAndAvtarDto;
import com.mercury.common.model.response.R;
import com.mercury.common.model.vo.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@FeignClient("mercury-member")
public interface UserFeignService {
    /**
     * 根据uid获取用户信息
     * @param uid
     * @return
     */
    @GetMapping("/member/info/{uid}")
    R<UserInfoVo> info(@PathVariable("uid") Long uid);

    /**
     * 获取当前用户关注的所有用户的id
     * @return
     */
    @GetMapping("/member/followIds/of/current")
    List<Long> getFollowIdsOfCurrent();

    /**
     * 获取对应的用户名和头像
     * @param uids
     * @return
     */
    @PostMapping("/member/list/username/avatar")
    List<UsernameAndAvtarDto> getUsernameAndAvatarByUids(@RequestBody Collection<Long> uids);

    @GetMapping("/member/{uid}/username/avatar")
    UsernameAndAvtarDto getUsernameAndAvatar(@PathVariable("uid") Long uid);

    @PostMapping("/member/conversation/clear")
    void clearConversationUnread(@RequestParam("uid") Long uid);
}