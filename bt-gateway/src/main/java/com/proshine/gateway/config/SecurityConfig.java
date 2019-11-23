package com.proshine.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SecurityConfig
 * Description: Spring Security主配置类
 * Author: zyl
 * Date: 2019/10/16 11:16
 */
@Configuration
public class SecurityConfig {

    // TODO: 2019/10/11 zylTodo 是这么用吗
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: 2019/10/11 zylTodo 为什么要有个默认key
    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider("b1a8de799c764a4cabc767736f085295");
    }

    // @Bean
    // public AuthenticationManager authenticationManager(){
    //     return new AuthenticationManager() {
    //         @Override
    //         public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    //             return null;
    //         }
    //     };
    // }

    @Bean
    public ProviderManager providerManager() {
        List<AuthenticationProvider> list = new ArrayList<>();
        list.add(rememberMeAuthenticationProvider());
        return new ProviderManager(list);
    }

}
