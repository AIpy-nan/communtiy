package com.nowcoder.community.controller;

import com.nowcoder.community.Util.HostHolder;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.MessageService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sun
 * @create 2022-04-17 15:07
 */

@Controller
public class MessageController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    //私信列表
    @RequestMapping(path = "/letter/list", method = RequestMethod.GET)
    public String getLtterList(Model model, Page page){
        User user = hostHolder.getUsers();
        //设置分页信息
        page.setLimit(5);
        page.setPath("/letter/list");
        //跟当前用户的所有消息
        page.setRows(messageService.findConversationsCount(user.getId()));

        //会话管理
        List<Message> conversationList = messageService.findConversations(
                user.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();
        for(Message message : conversationList){
            Map<String, Object> map = new HashMap<>();
            map.put("conversation", message);
            map.put("letterCount", messageService.findLetterCount(message.getConversationId()));
            map.put("unreadCount", messageService.findLetterUnreadCount(user.getId(),message.getConversationId()));
            int targerId = user.getId() == message.getFromId() ? message.getToId() : message.getFromId();

            map.put("target", userService.findUserById(targerId));

            conversations.add(map);
        }

        model.addAttribute("conversations", conversations);

        //查询未读数据数量
        int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(), null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);

        return "/site/letter";

    }
}
