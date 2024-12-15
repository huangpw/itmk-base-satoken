package com.itmk.config.mvc;

import jakarta.servlet.FilterRegistration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * 接口跨域类
 * @Author: AlbertHPW
 * @Date: 2024/12/15 17:30
 */
@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1.允许任何来源
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        // 2.允许任何请求头
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        // 3.允许任何方法
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        // 4.允许任何凭证
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(source);

        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(corsFilter);
        filterRegistrationBean.setOrder(-101); // 小于 sa-token SaServletFilter 的Order（-100)）即可

        return filterRegistrationBean;
    }

    //public void addCorsMappings(CorsRegistry registry) {
    //    // 添加全局CORS配置
    //    registry.addMapping("/**")
    //             // 允许所有域进行跨域请求
    //            .allowedOriginPatterns("*")
    //             // 允许所有HTTP方法进行跨域请求
    //            .allowedMethods("*")
    //             // 允许所有头部进行跨域请求
    //            .allowedHeaders("*")
    //             // 预检请求的缓存时间为3600秒
    //            .maxAge(3600)
    //             // 允许跨域请求带上用户凭证
    //            .allowCredentials(true);
    //}
}
