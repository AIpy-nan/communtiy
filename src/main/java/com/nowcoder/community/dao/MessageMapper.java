package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author sun
 * @create 2022-04-17 11:02
 */

@Mapper
public interface MessageMapper {

    //查询当前用户的会话列表，针对每一个会话返回一条最新的私信
    List<Message> selectConversations(int userId, int offset, int limit);

    //查询当前用户的会话数量
    int selectConversationsCount(int userId);

    //查询某个会话包含的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    //查询某个会话所包含的私信数量
    int selectLetterCount(String conversationId);

    //查询未读私信的数量 动态的拼会话id
    int selectLetterUnreadCount(int userId,String conversationId);



}
