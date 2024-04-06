package com.mercury.member.controller;

import com.mercury.common.model.response.R;
import com.mercury.member.service.UserService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Data
@RequestMapping(value = "/avatar")
public class AvatarChangeController {

    @Resource
    private UserService userService;


    /**
     * 更新当前用户的头像
     * @param request
     * @return
     */
    @PostMapping("/change")
    public R update(HttpServletRequest request){
        userService.changeAvatar(request);
        return R.ok();
    }


}
