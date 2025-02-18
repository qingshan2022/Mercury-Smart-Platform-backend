package com.mercury.member.controller;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.mercury.common.utils.HttpUtils;
import com.mercury.member.model.vo.user.SocialUser;
import com.mercury.member.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@Data
@RequestMapping(value = "/member/oauth2.0")
@ConfigurationProperties(prefix = "mercury.third-party.oauth.gitee")
public class OAuth2Controller {
    @Resource
    private UserService userService;

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String redirectUri;

    @Value("${mercury.domain}")
    private String domain;

    /**
     * 第三方登录成功后的回调
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/gitee/success")
    public String gitee(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>();

        map.put("client_id",clientId);
        map.put("client_secret",clientSecret);
        map.put("grant_type",grantType);
        map.put("redirect_uri",redirectUri);
        map.put("code",code);

        //1、根据用户授权返回的code换取access_token
        log.info("请求gitee：{}", map);
        HttpResponse res = HttpUtils.doPost("https://gitee.com", "/oauth/token", "post", new HashMap<>(), map, new HashMap<>());
        log.info("gitee响应：{}", res);
        //2、处理
        if (res.getStatusLine().getStatusCode() == 200) {
            //获取到了access_token,转为通用社交登录对象
            String json = EntityUtils.toString(res.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);

            //第一次使用社交帐号登录自动注册（在远程调用方法中已经将token保存至redis中）
            userService.oauthLogin(socialUser, response);
            return "redirect:http://blog." + domain + "/note";
        }
        return "redirect:http://blog." + domain + "/user/register";
    }
}
