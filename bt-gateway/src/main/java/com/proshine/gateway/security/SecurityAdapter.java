package com.proshine.gateway.security;

import com.proshine.gateway.security.base.filter.CustomUsernamePasswordAuthenticationFilter;
import com.proshine.gateway.security.base.handler.CustomAuthenticationFailureHandler;
import com.proshine.gateway.security.base.handler.CustomAuthenticationSuccessHandler;
import com.proshine.gateway.security.base.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ClassName: WebSecurityConfig
 * Description: Spring Security权限控制
 * Author: zyl
 * Date: 2019/10/10 12:02
 */
// TODO: 2019/10/11 zylTodo 暂时禁用了security配置 如果要暂时禁用gateway的访问控制 需要启用此配置并设置所有访问不需授权
@Configuration
@EnableWebSecurity
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    // TODO: 2019/10/11 zylTodo 暂时禁用
    @Autowired
    private CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RememberMeAuthenticationProvider rememberMeAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http

                // 禁用跨域
                .csrf()
                .disable()

                .headers()
                .frameOptions()
                .sameOrigin()
                .and()

                // 表单登陆
                .formLogin()
                // .loginPage("/login")
                .loginProcessingUrl("/login")
                // .usernameParameter("usname")
                // .passwordParameter("psword")
                .successHandler(customAuthenticationSuccessHandler)
                .defaultSuccessUrl("/success")
                .failureHandler(customAuthenticationFailureHandler)
                .failureUrl("/failure")
                .permitAll()
                .and()

                // 授权请求的
                .authorizeRequests()
                // 任何请求
                .anyRequest()
                // 都需要授权
                .authenticated()
                // .permitAll()
                .and()

                // 异常页面
                .exceptionHandling()
                .accessDeniedPage("/noAuth")
                .and()

                // session管理
                .sessionManagement()
                .invalidSessionUrl("/timeout")
                .and()

                // 登出
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()

                .rememberMe()
                .key("b1a8de799c764a4cabc767736f085295")
                .useSecureCookie(true)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 表达式处理器 默认设置就是ROLE_开头
        // .expressionHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // TODO: 2019/10/11 zylTodo authentication-provider没闹明白
                .eraseCredentials(false)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .authenticationProvider(rememberMeAuthenticationProvider)
        ;
    }

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //     auth
    //             // TODO: 2019/10/11 zylTodo <security:global-method-security secured-annotations="enabled"/>没配置
    //             // .authenticationProvider()
    //             .inMemoryAuthentication()
    //             .withUser("user").password("password").roles("USER");
    // }

}