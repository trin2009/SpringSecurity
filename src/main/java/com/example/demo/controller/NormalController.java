package com.example.demo.controller;

import com.example.demo.dto.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NormalController {

    @RequestMapping("/api/hi")
    public String index(Model model) {
        Msg msg = new Msg("非rest接口", "非rest接口");
        model.addAttribute("msg", msg);
        return "/view/error";
    }
}
