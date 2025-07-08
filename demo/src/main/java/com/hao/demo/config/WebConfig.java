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
// Ánh xạ URL đến tên file HTML trong thư mục templates/ mà không cần tạo Controller riêng.

// http://localhost:8080/phacdodieutri, Spring Boot sẽ render file templates/phacdodieutri.html.

// Giảm bớt số lượng controller khi chỉ cần hiển thị trang tĩnh.(trang chưa đăng nhập)


@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("trangchu");
        registry.addViewController("/trangchu").setViewName("trangchu");
        registry.addViewController("/phacdodieutri").setViewName("phacdodieutri");
        registry.addViewController("/doingubacsi").setViewName("doingubacsi");
        registry.addViewController("/cosoyte").setViewName("cosoyte");

    
    }

}

