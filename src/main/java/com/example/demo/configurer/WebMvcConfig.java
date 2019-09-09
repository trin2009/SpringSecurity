package com.example.demo.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 配置页面跳转
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("/view/auth");
        registry.addViewController("/view/adminUrl1").setViewName("/view/adminUrl1");
        registry.addViewController("/view/memberUrl1").setViewName("/view/memberUrl1");
    }
}
