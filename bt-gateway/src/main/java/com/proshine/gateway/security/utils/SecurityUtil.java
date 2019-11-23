package com.proshine.gateway.security.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.proshine.expo.gateway.SecurityExpo;
import com.proshine.expo.gateway.security.dto.CustomUserDetails;
import com.proshine.expo.gateway.security.entity.TbCustomer;
import com.proshine.expo.gateway.security.entity.TbRole;
import com.proshine.gateway.security.service.impl.TbRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SecurityUtil implements SecurityExpo {

    @Autowired
    private TbRoleServiceImpl roleService;

    private Map<String, String> superMap;

    /**
     * 获取session中当前登录的用户的信息
     */
    public CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public String getCstmId() {
        CustomUserDetails userDetails = getUserDetails();
        return userDetails.getCstmId();
    }

    public String getUserId() {
        CustomUserDetails userDetails = getUserDetails();
        return userDetails.getUserId();
    }

    public String getUserName() {
        CustomUserDetails userDetails = getUserDetails();
        return userDetails.getUsername();
    }

    public boolean getSuperUser() {
        if (superMap == null) {
            superMap = new HashMap<>();
            QueryWrapper<TbRole> wrapper = new QueryWrapper<>();
            wrapper.eq("role_name","超级管理员");
            List<TbRole> superList = roleService.list(wrapper);
            for (TbRole role : superList) {
                TbCustomer customer = role.getCustomer();
                if (customer == null) {
                    continue;
                }
                String customerId = customer.getId();
                superMap.put(customerId, role.getId());
            }
        }
        CustomUserDetails userDetails = getUserDetails();
        String cstmId = userDetails.getCstmId();
        String roleId = superMap.get(cstmId);
        Set<GrantedAuthority> authorities = userDetails.getAuthorities();
        for (org.springframework.security.core.GrantedAuthority GrantedAuthority : authorities) {
            if (GrantedAuthority.getAuthority().equals(roleId)) {
                return true;
            }
        }
        return false;
    }
}
