package com.nowcoder.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sun
 * @create 2022-02-26 11:11
 */

@Controller
@RequestMapping("/a")
public class AlphaContorller {
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //获得请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }

        System.out.println(request.getParameter("code"));

        //向浏览器返回响应
        response.setContentType("text/html; charset=utf-8");
        try(PrintWriter writer = response.getWriter();
        ) {
            writer.write("<h1> 牛客网 </h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //get请求
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit){

        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    //  / students/123
    @RequestMapping(path = "/student/{id}" ,method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";

    }

    //浏览器向服务器提交数据 post
    //post
    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String postStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return "student";
    }

    // 响应html数据
    @RequestMapping(path = "/tercher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name","张安");
        modelAndView.addObject("age","30");
        modelAndView.setViewName("/demol/view");
        return modelAndView;
    }

    // 另一种响应的格式
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "北大");
        model.addAttribute("age",80);

        return "/demol/view";
    }

    // 响应json数据（异步请求）
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp(){
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张安");
        emp.put("age",55);
        return emp;

    }






}
