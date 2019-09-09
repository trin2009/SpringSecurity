package com.example.demo.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * 权限判断
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * decide 方法是判定是否拥有权限的决策方法，
     * authentication 是 CustomUserService 中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * configAttributes 为 MyInvocationSecurityMetadataSource 的 getAttributes(Object object)这个方法返回的结果
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        ConfigAttribute c;
        for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            c = iter.next();
            String permissionName = c.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (permissionName.equals(ga.getAuthority())) {
                    return;// 放行通过
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
