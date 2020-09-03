package com.project.xghk416.framework.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class GlobalCorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // 重写父类提供的跨域请求处理的接口
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 添加映射路径
                registry.addMapping("/**")
                        // 放行哪些原始域
                        .allowedOrigins("http://192.168.3.43:9528", "http://localhost:9528","http://39.106.228.42:9528")
                        // 放行哪些请求方式
                        .allowedMethods("*")
                        // 是否允许携带 cookie
                        .allowCredentials(true)
                        // 放行哪些头部信息
                        .allowedHeaders("*");
            }
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("swagger-ui.html")
                        .addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
            }

        };
    }

}
