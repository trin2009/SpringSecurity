package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberUrlController {

    @RequestMapping("/api/memberUrl2")
    public String url2(){
        return "这个是会员接口2";
    }

}
