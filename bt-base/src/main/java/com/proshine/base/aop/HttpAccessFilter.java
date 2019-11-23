package com.proshine.base.aop;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ClassName: HttpAccessFilter
 * Description: 面向HTTP的访问过滤器
 * Author: zyl
 * Date: 2019/10/11 17:58
 */
@Log4j2
// @Service
@Component
public class HttpAccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("========== 启动访问统计过滤器 ==========");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("========== Http请求[" + ((HttpServletRequest) servletRequest).getRequestURL() + "]建立 ==========");
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("========== Http请求[" + ((HttpServletRequest) servletRequest).getRequestURL() + "]结束 总耗时: " + (System.currentTimeMillis() - start) + "ms ==========");
    }

    @Override
    public void destroy() {
        log.info("========== 销毁访问统计过滤器 ==========");
    }
}
