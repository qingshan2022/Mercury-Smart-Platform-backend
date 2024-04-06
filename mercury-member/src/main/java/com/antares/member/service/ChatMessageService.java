package com.mercury.member.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mercury.member.model.dto.chat.MessageQueryRequest;
import com.mercury.member.model.entity.ChatMessage;
import com.mercury.member.model.vo.chat.MessageVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author mercury
* @description 针对表【message】的数据库操作Service
* @createDate 2023-05-18 21:30:23
*/
public interface ChatMessageService extends IService<ChatMessage> {

    Page<MessageVo> listMessageVoByPage(MessageQueryRequest messageQueryRequest, HttpServletRequest request);

    MessageVo messageToMessageVo(ChatMessage chatMessage);

    Long saveMessage(ChatMessage chatMessage);
}
