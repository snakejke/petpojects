package com.javamentor.qa.platform.webapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/tag_page").setViewName("tag_page");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/question").setViewName("question");
        registry.addViewController("/main").setViewName("main");
    }
}
