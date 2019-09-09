package com.example.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUrlController {

    @RequestMapping("/api/adminUrl2")
    public String url2(){
        return "这个是管理员接口2";
    }

    @RequestMapping("/api/adminUrl3")
    public String url3(){
        return "这个是管理员接口3";
    }

}
