package com.mercury.member.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import com.mercury.member.job.NettyStarter;
import com.mercury.member.model.dto.chat.Command;
import com.mercury.member.model.dto.chat.Response;
import com.mercury.member.model.enums.WebSocketCodeEnum;
import com.mercury.member.service.ConversationService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ChangeHandler {
    @Resource
    private ConversationService conversationService;

    public void execute(ChannelHandlerContext ctx, Command command){
        try {
            Long uid = command.getUid();
            Long conversationId = command.getConversationId();
            NettyStarter.USERS.get(uid).setRight(conversationId);
            ctx.channel().writeAndFlush(Response.error(WebSocketCodeEnum.SUCCESS, "切换对话成功"));
            //清除unread
            conversationService.clearUnread(uid, conversationId);
        } catch (Exception e) {
            ctx.channel().writeAndFlush(Response.error(WebSocketCodeEnum.INTERNAL_SERVER_ERROR));
        }
    }
}
