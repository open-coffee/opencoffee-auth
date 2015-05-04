package de.synyx.selfservice.ui.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by klem on 04.05.15.
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        Cookie authCookie = new Cookie("JSESSIONID", null);
        authCookie.setPath("/uaa");
        authCookie.setMaxAge(0);
        httpServletResponse.addCookie(authCookie);
    }
}
