package com.example.E_Commerce.E_commerce.Filters;
import com.example.E_Commerce.E_commerce.Service.Auth.JwtUtilService;
import com.example.E_Commerce.E_commerce.Service.Auth.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter  {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final UserService userDetailsService;
    private final JwtUtilService jwtUtil;

    public JwtRequestFilter(UserService userDetailsService, JwtUtilService jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public static void deleteCookies(HttpServletResponse response) {
        Cookie deleteServletCookie1 = new Cookie("header.payload", null);
        deleteServletCookie1.setMaxAge(0);
        deleteServletCookie1.setSecure(true);
        deleteServletCookie1.setHttpOnly(true);
        deleteServletCookie1.setPath("/");
        response.addCookie(deleteServletCookie1);

        Cookie deleteServletCookie2 = new Cookie("signature", null);
        deleteServletCookie2.setMaxAge(0);
        deleteServletCookie2.setSecure(true);
        deleteServletCookie2.setHttpOnly(false);
        deleteServletCookie2.setPath("/");
        response.addCookie(deleteServletCookie2);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        boolean cookiesDeleted = false;

        Cookie header = WebUtils.getCookie(request, "header.payload");
        String authorizationToken = "";

        if (header != null) {
            Cookie signature = WebUtils.getCookie(request, "signature");
            if (signature != null) {
                authorizationToken = header.getValue() + "." + signature.getValue();
            } else {
                deleteCookies(response);
                cookiesDeleted = true;
                logger.warn("Cookie Signature not found");
            }
        } else {
            deleteCookies(response);
            cookiesDeleted = true;
            logger.warn("Cookies not found: method={}, URI={}, servletPath={}, contextPath={}",
                    request.getMethod(), request.getRequestURI(), request.getServletPath(), request.getContextPath());
        }

        logger.debug("Authorization token length: {}", authorizationToken.length());

        String username = null;

        if (!authorizationToken.isEmpty()) {
            try {
                username = jwtUtil.extractUsername(authorizationToken);
            } catch (ExpiredJwtException expiredJwtException) {
                cookiesDeleted = true;
                deleteCookies(response);
                logger.warn("JWT token expired");
            }
        } else {
            if (!cookiesDeleted) {
                deleteCookies(response);
                cookiesDeleted = true;
            }
            logger.warn("Authorization Token is empty");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(authorizationToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                if (!cookiesDeleted) {
                    deleteCookies(response);
                }
                logger.warn("Invalid Token");
            }
        } else {
            if (!cookiesDeleted) {
                deleteCookies(response);
            }
            logger.debug("username is null or authentication already set");
        }

        chain.doFilter(request, response);
    }
}
