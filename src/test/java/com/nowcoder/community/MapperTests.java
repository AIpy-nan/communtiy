package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author sun
 * @create 2022-02-23 14:45
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommuntiyApplication.class)

public class MapperTests {
    //将userMapper自动注入
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MessageMapper messageMapper;


    @Test
    public void testSelectById(){
        User user = userMapper.selectById(11);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder102@sina.com");
        System.out.println(user);

        User user1 = new User();
        user1.setUsername("kenan");
        user1.setPassword("123456");
        user1.setHeaderUrl("http:www.nowder.com");
        user1.setEmail("15255545@as");
        user1.setSalt("asd");
        user1.setCreateTime(new Date());
        int l = userMapper.insertUser(user1);
        System.out.println(l);
        System.out.println(user1.getId());

    }

    @Test
    public void updateUser(){
        int rows = userMapper.updateStatus(150,1);
        System.out.println(rows);

        rows = userMapper.updateHeader(150,"http://images.nowcoder.com/head/111.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(150,"456123");
        System.out.println(rows);
    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0,10);
        for (DiscussPost post : list){
            System.out.println(post);
        }

        int row = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(row);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abs");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abs");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abs", 1);

        loginTicket = loginTicketMapper.selectByTicket("abs");
        System.out.println(loginTicket);

    }

    @Test
    public void testSelectLetters(){
        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
        for (Message m : messages){
            System.out.println("111用户的私信的最新数据"+m);
        }

        int i = messageMapper.selectConversationsCount(111);
        System.out.println("会话数量" +i);

        List<Message> messages1 = messageMapper.selectLetters("111_112", 0, 10);
        for (Message ms : messages1){
            System.out.println("111_112之间的所有会话"+ms);
        }

        int i1 = messageMapper.selectLetterCount("111_112");
        System.out.println("111_112之间私信的数量"+i1);

        int i2 = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println("未读数量" + i2);
    }
}
