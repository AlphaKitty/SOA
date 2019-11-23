package com.proshine.gateway.security.base.controller;

import com.alibaba.dubbo.config.annotation.Service;
import com.proshine.expo.base.BaseExpo;
import com.proshine.expo.gateway.security.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨雪
 */
@Service
@Component
public class BaseHandler implements BaseExpo {
    protected ModelAndView view;

    public BaseHandler() {
        view = new ModelAndView();
    }

    /**
     * 获取保存在Session中的用户对象 </p>
     *
     * @return session中的用户
     */
    public CustomUserDetails getSessionUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    /**
     * 获取保存在Session中的用户权限 </p>
     *
     * @return session中的用户权限
     */
    public Authentication getUserAuthentication() {
        return SecurityContextHolder.getContext()
                .getAuthentication();
    }

    /**
     * 从session取值</p>
     *
     * @param request 请求对象
     * @param key     从session中取值的时候所用的key
     * @return {@link Object} 取出的值
     */
    public Object getSessionParameter(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    /**
     * 往session存值</p>
     *
     * @param request 请求对象
     * @param key     往session中存入的值所对应的key
     * @param param   存入的值
     */
    public void setSessionParameter(HttpServletRequest request, String key, Object param) {
        request.getSession().setAttribute(key, param);
    }

    public String operation(HttpServletRequest request) {
        String url = "";
        url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getServletPath();
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }
        System.out.println(url);
        return url;
    }

    public String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() // 当前链接使用的协议
                + "://" + request.getServerName()// 服务器地址
                + ":" + request.getServerPort() // 端口号
                + request.getContextPath();
    }

    /**
     * 获取基于应用程序的url绝对路径 </p>
     *
     * @param request HttpServletRequest
     * @return url
     */
    public final String getAppbaseUrl(HttpServletRequest request, String uri) {
        Assert.hasLength(uri, "url不能为空");
        Assert.isTrue(uri.startsWith("/"), "必须以/打头");
        return request.getContextPath() + uri;
    }

    /**
     * 得到RequestContext对象</p>
     * 可以通过   {@code  requestContext.getMessage("key");} 获取国际化语言信息</p>
     * 多用于后端给前端返回json的时候所附带的message消息</p>
     *
     * @param request HttpServletRequest
     */
    public RequestContext getRequestContext(HttpServletRequest request) {
        return new RequestContext(request);
    }
}
