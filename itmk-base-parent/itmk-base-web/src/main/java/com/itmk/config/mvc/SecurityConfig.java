package com.itmk.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/15 18:13
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //http
        //        .csrf().disable() // 在开发环境中禁用 CSRF，但在生产环境中应该启用
        //        .authorizeRequests()
        //        .anyRequest().permitAll(); // 允许所有请求，仅用于示例

        http.authorizeHttpRequests((authorize) -> authorize
                        // 允许所有请求
                        .requestMatchers("/**").permitAll()
                )
                // 退出时，让session失效
                //.logout(logout -> logout.invalidateHttpSession(true))
                // 配置登录页面 和 登录成功后页面
                //.formLogin(form -> form.loginPage("/login").permitAll()
                //        .loginProcessingUrl("/login").defaultSuccessUrl("/"))
                // 禁用CSRF保护
                .csrf(csrf -> csrf.disable());
        //.csrf(csrf -> csrf.disable()) // 在开发环境中禁用 CSRF，但在生产环境中应该启用
        //.authorizeRequests(authorize -> authorize
        //        .anyRequest().permitAll()); // 允许所有请求，仅用于示例

        return http.build();
    }
}
