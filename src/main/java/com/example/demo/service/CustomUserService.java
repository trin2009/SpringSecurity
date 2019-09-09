package com.example.demo.service;

import com.example.demo.pojo.SysPermission;
import com.example.demo.pojo.SysRole;
import com.example.demo.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private MyTokenService myTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = SysUser.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        myTokenService.cacheUser(user);

        System.out.println("username:" + user.getUsername() + ";password:" + user.getPassword());
        System.out.print("roles: ");
        Set authoritiesSet = new HashSet();
        GrantedAuthority authority;
        for (SysRole role : user.getRoles()) {
            System.out.print(" " + role.getName());
            for (SysPermission permission : role.getPermissions()) {
                authority = new SimpleGrantedAuthority(permission.getName());
                authoritiesSet.add(authority);
            }
        }
        System.out.println();
        user.setAuthorities(authoritiesSet);

        return user;
    }
}