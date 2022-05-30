package com.nowcoder.community;

import com.nowcoder.community.Util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author sun
 * @create 2022-02-28 16:15
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommuntiyApplication.class)
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("sunkenan12@163.com","TEST","KENAN");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();

        //这里的username 就是一个动态的值，templateEngine 就是为了生成动态网页
        context.setVariable("username", "sunday");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("sunkenan12@163.com","HTML",content);

    }


}
