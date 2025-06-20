package com.hao.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("trangchu");
        registry.addViewController("/trangchu").setViewName("trangchu");
        registry.addViewController("/phacdodieutri").setViewName("phacdodieutri");
        registry.addViewController("/doingubacsi").setViewName("doingubacsi");
        registry.addViewController("/cosoyte").setViewName("cosoyte");
        // registry.addViewController("/customer").setViewName("customer/customer");
        // registry.addViewController("/customer/thongbao").setViewName("customer/thongbao");
        // registry.addViewController("/customer/dangkidichvu").setViewName("customer/dangkidichvu");
        // registry.addViewController("/customer/lichtrinhdieutri").setViewName("customer/lichtrinhdieutri");
        // registry.addViewController("/customer/ketquadieutri").setViewName("customer/ketquadieutri");
        // registry.addViewController("/customer/lichsudondat").setViewName("customer/lichsudondat");
        // registry.addViewController("/customer/danhgia").setViewName("customer/danhgia");
        // registry.addViewController("/customer/hosocanhan").setViewName("customer/hosocanhan");
    
    }

}

