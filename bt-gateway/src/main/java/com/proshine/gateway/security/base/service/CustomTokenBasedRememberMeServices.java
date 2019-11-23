package com.proshine.gateway.security.base.service;

import com.proshine.expo.gateway.security.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

/**
 * CustomTokenBasedRememberMeServices
 */
// TODO: 2019/10/11 zylTodo 暂时取消bean注册
// @Component
public class CustomTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
    public CustomTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    private static boolean equals(String expected, String actual) {
        byte[] expectedBytes = bytesUtf8(expected);
        byte[] actualBytes = bytesUtf8(actual);
        if (expectedBytes.length != actualBytes.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expectedBytes.length; i++) {
            result |= expectedBytes[i] ^ actualBytes[i];
        }
        return result == 0;
    }

    private static byte[] bytesUtf8(String s) {
        if (s == null) {
            return null;
        }
        return Utf8.encode(s);
    }

    @Override
    protected String retrieveUserName(Authentication authentication) {
        CustomUserDetails userDetails;
        if (authentication instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) authentication;
        } else {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        }

        return userDetails.getUsername() + "{" + userDetails.getCstmId() + "," + userDetails.getOpenid() + "}";
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {

        if (cookieTokens.length != 3) {
            throw new InvalidCookieException("Cookie token did not contain 3"
                    + " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
        }

        long tokenExpiryTime;

        try {
            tokenExpiryTime = new Long(cookieTokens[1]);
        } catch (NumberFormatException nfe) {
            throw new InvalidCookieException(
                    "Cookie token[1] did not contain a valid number (contained '"
                            + cookieTokens[1] + "')");
        }

        if (isTokenExpired(tokenExpiryTime)) {
            throw new InvalidCookieException("Cookie token[1] has expired (expired on '"
                    + new Date(tokenExpiryTime) + "'; current time is '" + new Date()
                    + "')");
        }

        // Check the user exists.
        // Defer lookup until after expiry time checked, to possibly avoid expensive
        // database call.

        CustomUserDetails userDetails = (CustomUserDetails) getUserDetailsService().loadUserByUsername(
                cookieTokens[0]);

        // Check signature of token matches remaining details.
        // Must do this after user lookup, as we need the DAO-derived password.
        // If efficiency was a major issue, just add in a UserCache implementation,
        // but recall that this method is usually only called once per HttpSession - if
        // the token is valid,
        // it will cause SecurityContextHolder population, whilst if invalid, will cause
        // the cookie to be cancelled.
        String expectedTokenSignature = makeTokenSignature(tokenExpiryTime,
                userDetails.getUsername() + "{" + userDetails.getCstmId() + "," + userDetails.getOpenid() + "}", userDetails.getPassword());

        if (!equals(expectedTokenSignature, cookieTokens[2])) {
            throw new InvalidCookieException("Cookie token[2] contained signature '"
                    + cookieTokens[2] + "' but expected '" + expectedTokenSignature + "'");
        }

        return userDetails;
    }
}
