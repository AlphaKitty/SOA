package com.proshine.camera;

import com.proshine.base.aop.HttpAccessFilter;
import com.proshine.base.aop.ProcessInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MainConfig
 * Description: 通用主配置
 * Author: zyl
 * Date: 2019/9/19 16:58
 */
@Configuration
public class MainConfig implements WebMvcConfigurer {

    @Bean
    public HttpAccessFilter httpAccessFilter() {
        return new HttpAccessFilter();
    }

    @Bean
    public ProcessInterceptor processInterceptor() {
        return new ProcessInterceptor();
    }

    @Bean
    public FilterRegistrationBean<Filter> filterLoader() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(httpAccessFilter());
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(processInterceptor());
    }

}
