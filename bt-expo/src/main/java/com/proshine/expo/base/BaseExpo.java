package com.proshine.expo.base;

import com.proshine.expo.gateway.security.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

public interface BaseExpo {
    /**
     * 得到RequestContext对象</p>
     * 可以通过   {@code  requestContext.getMessage("key");} 获取国际化语言信息</p>
     * 多用于后端给前端返回json的时候所附带的message消息</p>
     */
    static RequestContext requestContext(HttpServletRequest request) {
        return new RequestContext(request);
    }
    CustomUserDetails getSessionUser();
    Authentication getUserAuthentication();
    Object getSessionParameter(HttpServletRequest request, String key);
    void setSessionParameter(HttpServletRequest request, String key, Object param);
    String operation(HttpServletRequest request);
    String getBaseUrl(HttpServletRequest request);
    String getAppbaseUrl(HttpServletRequest request, String uri);
    RequestContext getRequestContext(HttpServletRequest request);
}
