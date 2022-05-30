package com.nowcoder.community.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sun
 * @create 2022-02-24 19:59
 */

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hell(){
        return "heklll";
    }
}
