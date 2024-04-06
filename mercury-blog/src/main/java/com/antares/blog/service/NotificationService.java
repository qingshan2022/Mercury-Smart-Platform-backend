package com.mercury.blog.service;

import com.mercury.blog.model.vo.notification.CommentNotificationVo;
import com.mercury.blog.model.vo.notification.LikeNotificationVo;
import com.mercury.blog.model.vo.notification.NotificationCountVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercury.blog.model.dto.notification.NotificationQueryRequest;


import javax.servlet.http.HttpServletRequest;

/**
 * @author mercury
 * @description 针对表【notification】的数据库操作Service
 * @createDate 2023-05-18 16:36:19
 */
public interface NotificationService {

    NotificationCountVo count(HttpServletRequest request);

    Page<LikeNotificationVo> listLikeNotificationByPage(NotificationQueryRequest notificationQueryRequest, HttpServletRequest request);

    void clearNotification(String type, HttpServletRequest request);

    Page<CommentNotificationVo> listCommentNotificationByPage(NotificationQueryRequest notificationQueryRequest, HttpServletRequest request);
}
