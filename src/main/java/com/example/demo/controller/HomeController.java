package com.example.demo.controller;


import com.example.demo.dto.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(Model model) {
        Msg msg = new Msg("测试标题", "测试内容");
        model.addAttribute("msg", msg);
        return "/view/index";
    }
}
