package com.proshine.base;

import com.proshine.base.aop.ProcessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: MainConfig
 * Description: 通用主配置
 * Author: zyl
 * Date: 2019/9/19 16:58
 */
@Configuration
public class MainConfig implements WebMvcConfigurer {

    @Autowired
    private ProcessInterceptor processInterceptor;

    /**
     * 把拦截器设置为组件不能直接让其起作用 还需要将拦截器加到registry里
     *
     * @param registry 拦截器容器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(processInterceptor);
    }

}
