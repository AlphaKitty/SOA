package com.proshine.gateway.security.base.service;

import com.proshine.expo.gateway.security.dto.CustomUserDetails;
import com.proshine.expo.gateway.security.entity.TbResource;
import com.proshine.expo.gateway.security.entity.TbRole;
import com.proshine.expo.gateway.security.entity.TbUser;
import com.proshine.gateway.security.service.impl.TbResourceServiceImpl;
import com.proshine.gateway.security.service.impl.TbRoleResourceServiceImpl;
import com.proshine.gateway.security.service.impl.TbUserRoleServiceImpl;
import com.proshine.gateway.security.service.impl.TbUserServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TbUserServiceImpl userService;
    @Autowired
    private TbUserRoleServiceImpl tbUserRoleService;
    @Autowired
    private TbRoleResourceServiceImpl tbRoleResourceService;
    @Autowired
    private TbResourceServiceImpl resourceService;

    @Override
    public CustomUserDetails loadUserByUsername(String usname) throws UsernameNotFoundException {
        String username = usname.substring(0, usname.lastIndexOf("{"));
        String other = usname.substring(usname.lastIndexOf("{") + 1, usname.lastIndexOf("}"));
        String[] others = other.split(",");
        String cstmId = others[0];
        String openid = others[1];
        //从数据库查询用户信息
        TbUser user = userService.findUserInfoByUsnameAndCstmId(username, cstmId);

        if (user == null) {
            log.info("User not found!");
            throw new UsernameNotFoundException("Username not found");
        }

        int userIsAvailable = user.getIsAvailable();

        if (userIsAvailable != 1) {
            log.info("User is UnAvailable!");
            throw new LockedException("User is UnAvailable");
        }

        int isAvailable = user.getCustomer().getIsAvailable();

        if (isAvailable != 1) {
            log.info("Customer is UnAvailable!");
            throw new LockedException("Customer is UnAvailable");
        }

        Set<GrantedAuthority> authorities = getGrantedAuthorities(user);
        // TODO: 2019/10/29 zylTodo 非Master客户域, 移除资源/控件/客户域管理模块 tb_resource.is_super = 0
        // 根据user_id在user_role表中查到所有role_id
        List<String> roleIds = tbUserRoleService.getRoleIdsByUserId(user.getId());
        // 根据role_id在role_resource表中查到所有resource_id
        List<String> resourceIds = tbRoleResourceService.getResourceIdsByRoleIds(roleIds);
        // 根据resource_id在resource表中查到所有resource
        List<TbResource> resources = resourceService.getResourceByIds(resourceIds);

        String password = user.getPsword();
        if (!"0".equals(openid)) {
            password = new BCryptPasswordEncoder().encode(openid);
        }

        // lombok @AllArgsConstructor注解按照字段排列顺序进行的构造
        // 格式化实体造成的任何属性顺序变化都会导致构造方法入参匹配失败
        return new CustomUserDetails(
                user.getUsname(),
                user.getCustomer().getId(),
                authorities,
                true,
                true,
                true,
                true,
                user,
                resources,
                password,
                user.getId(),
                user.getImageId(),
                user.getOpenid()
        );
    }

    private Set<GrantedAuthority> getGrantedAuthorities(TbUser user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (TbRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getId()));
        }
        return authorities;
    }
}
