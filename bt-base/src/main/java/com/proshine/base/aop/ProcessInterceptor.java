package com.proshine.base.aop;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: ProcessInterceptor
 * Description: 面向方法的访问拦截器
 * Author: zyl
 * Date: 2019/10/16 18:39
 */
@Log4j2
// @Service
@Component
public class ProcessInterceptor implements HandlerInterceptor {

    private long start;

    /**
     * 控制器调用前执行
     *
     * @return 决定是否调用后续方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("---------- 访问方法[" + ((HandlerMethod) handler).getBean().getClass().getName() + "." + ((HandlerMethod) handler).getMethod().getName() + "] ----------");
        request.setAttribute("prettyBoy", "张友谅");
        start = System.currentTimeMillis();
        return true;
    }

    /**
     * 控制器调用成功后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("---------- 方法[" + ((HandlerMethod) handler).getMethod().getName() + "]结束 总耗时: " + (System.currentTimeMillis() - start) + "ms ----------");
    }

    /**
     * 控制器调用过程中进入异常执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            log.error(">>>>>>>>>> 捕获异常 <<<<<<<<<<");
            ex.printStackTrace();
        }
    }
}
