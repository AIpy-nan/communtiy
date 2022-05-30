package com.nowcoder.community;

import com.nowcoder.community.Util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sun
 * @create 2022-04-11 19:16
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommuntiyApplication.class)
public class sensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "这里@#以￥赌￥博、可以嫖#￥娼、可以￥开#票、哈哈哈哈哈";
        String filter = sensitiveFilter.filter(text);
        System.out.println(filter);
    }
}
