package com.proshine.gateway.security.base.filter;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义用户帐号密码认证过滤器
 *
 * @author 杨雪
 */
// TODO: 2019/10/11 zylTodo authenticationManager配置失败 暂时取消bean注册
@Setter
@Component
public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private String customerParameter = "cstmId";
    private String usernameParameter = "usname";
    private String passwordParameter = "psword";
    private String openidParameter = "openid";
    private boolean postOnly = true;

    public CustomUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/admin/user/login", "POST"));
    }

    @Override
    @Autowired
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(successHandler);
    }

    @Override
    @Autowired
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
    }

    // TODO: 2019/10/11 zylTodo 不对
    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String post = "POST";

        if (postOnly && !post.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String cstmId = obtainCustomer(request);
        String openid = obtainOpenId(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        if (cstmId == null) {
            cstmId = "";
        }

        if (openid == null) {
            openid = "0";
        }

        // username{domain,1}
        username = username.trim() + "{" + cstmId + "," + openid + "}";

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainCustomer(HttpServletRequest request) {
        return request.getParameter(customerParameter);
    }

    private String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(openidParameter);
    }

    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    private String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
