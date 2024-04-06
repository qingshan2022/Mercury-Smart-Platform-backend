package com.mercury.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mercury.common.model.vo.UserInfoVo;
import com.mercury.member.model.entity.Follow;
import com.mercury.member.model.vo.user.FollowVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author mercury
* @description 针对表【follow】的数据库操作Service
* @createDate 2023-05-15 15:15:38
*/
public interface FollowService extends IService<Follow> {

    void follow(Long uid, HttpServletRequest request);

    List<Long> getFollowIdsOfCurrent(HttpServletRequest request);

    List<FollowVo> getFollowsOfCurrent(HttpServletRequest request);

    List<UserInfoVo> getFollowsByUid(Long uid, HttpServletRequest request);

    List<UserInfoVo> getFansByUid(Long uid, HttpServletRequest request);
}
