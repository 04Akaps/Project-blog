package com.cos.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    //http://localhost:8000/blog

    @GetMapping({"", "/"})
    public String index(){
        return "index";
    }

    @GetMapping("user/joinform")
    public String joinForm(){
        return "user/joinform";
    }

    @GetMapping("user/loginform")
    public String loginForm(){
        return "user/loginform";
    }


}
