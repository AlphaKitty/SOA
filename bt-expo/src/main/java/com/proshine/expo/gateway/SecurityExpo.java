package com.proshine.expo.gateway;

import com.proshine.expo.gateway.security.dto.CustomUserDetails;

public interface SecurityExpo {
    CustomUserDetails getUserDetails();
    String getCstmId();
    String getUserId();
    String getUserName();
    boolean getSuperUser();
}
