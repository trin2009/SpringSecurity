package com.example.demo.filter;

import com.example.demo.pojo.SysUser;
import com.example.demo.service.MyTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String tokenParameter = "token";

    @Autowired
    private MyTokenService myTokenService;

    public MyUsernamePasswordAuthenticationFilter() {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);
        String token = request.getParameter(this.tokenParameter);

        if (token != null) {
            SysUser user = myTokenService.findUserByToken(token);
            if (user != null) {
                username = user.getUsername();
                password = user.getPassword();
            }
        }

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }
}
