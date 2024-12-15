package com.itmk.config.mvc;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.itmk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 2:32
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${web.load-path}")
    private String loadPath;

    /**
     * Sa-Token全局过滤器
     * @return
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 指定拦截路由与放行路由 addExclude里面为排除掉
                .addInclude("/**")
                // 放行接口
                .addExclude("/api/sysUser/login")
                .addExclude("/api/sysUser/captcha")
                .addExclude("/api/upload/uploadImage")
                .addExclude("/images/**")

                // 认证函数：每次请求都会进行拦截
                .setAuth(e -> {
                    System.out.println("----------进入Sa-token全局认证----------");
                    // 输出API请求日志，方便调试代码
                    SaManager.getLog().debug("请求path={} 提交token={}", SaHolder.getRequest().getRequestPath(), StpUtil.getTokenValue());
                    //登录认证--拦截所有路由，并排除/api/sysUser/Login 用于开放登录
                    SaRouter.match("/**", "/api/sysUser/login", () -> StpUtil.checkLogin());

                    // 更多拦截处理方式，请参考“路由拦截式鉴权”章节
                })
                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    System.out.println("----------进入Sa-token异常处理----------");
                    System.out.println(e.getMessage());
                    if(e instanceof NotLoginException) {
                        // 返回给前端的数据
                        return SaResult.error(e.getMessage()).setCode(600);
                    }
                    return ResultUtils.error(e.getMessage());
                })
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(r -> {
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(re -> System.out.println("----------OPTIONS请求，直接放行----------"))
                            .back();
                });
    }

    /**
     * 静态资源处理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations(loadPath);
    }

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("-----注册拦截器了-----");
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    /**
     * 引入密码加密类
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 跨域配置
     * @param registry
     */
    //@Override
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
