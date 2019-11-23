package com.proshine.expo.gateway.security.dto;

import com.proshine.expo.gateway.security.entity.TbResource;
import com.proshine.expo.gateway.security.entity.TbUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 2051148821951397573L;
    private final String username;
    private final String cstmId;
    private final Set<GrantedAuthority> authorities;
    //帐号是否不过期，false则验证不通过
    private final boolean accountNonExpired;
    //帐号是否不锁定，false则验证不通过
    private final boolean accountNonLocked;
    //凭证是否不过期，false则验证不通过
    private final boolean credentialsNonExpired;
    //该帐号是否启用，false则验证不通过
    private final boolean enabled;
    private final TbUser userInfo;
    private final List<TbResource> menus;
    private String password;
    private String userId;
    private String imageId;
    // 微信openid
    private String openid;

    @Override
    public void eraseCredentials() {

    }
}