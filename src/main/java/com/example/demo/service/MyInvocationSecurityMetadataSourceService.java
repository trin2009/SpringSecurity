package com.example.demo.service;

import com.example.demo.pojo.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 设置拦截url权限和判断权限
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {
    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<SysPermission> permissions = SysPermission.getAllPermissions();
        for (SysPermission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            //此处只添加了权限的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getUrl(), array);
        }

    }

    /**
     * 判定用户请求的url与哪些权限配置的url规则匹配，如果在权限表中，则返回给 decide 方法。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        // 没有任何配置的权限中包含此url

        AntPathRequestMatcher matcher_login = new AntPathRequestMatcher("/login");
        AntPathRequestMatcher matcher_error = new AntPathRequestMatcher("/error");
        AntPathRequestMatcher matcher_logout = new AntPathRequestMatcher("/logout");

        if (matcher_login.matches(request) || matcher_error.matches(request) || matcher_logout.matches(request)) {
            return null; // 放行通过不拦截
        }

        Collection<ConfigAttribute> array = new ArrayList<>();
        ConfigAttribute cfg = new SecurityConfig("denied");
        array.add(cfg);
        return array;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}