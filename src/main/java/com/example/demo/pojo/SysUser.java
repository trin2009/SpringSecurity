package com.example.demo.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SysUser implements UserDetails {

    private int id;
    private String username;
    private String password;
    private List<SysRole> roles;
    private Set<? extends GrantedAuthority> authorities;

    public SysUser(int id ,String username,String password){
        this.id = id;
        this.username = username;
        this.password = password;
        roles = new ArrayList<SysRole>();
        if(username.equals("admin")){
            roles.add(new SysRole(1,SysRole.ROLE_ADMIN1));
            roles.add(new SysRole(2,SysRole.ROLE_ADMIN2));
        }
        if(username.equals("member")){
            roles.add(new SysRole(3,SysRole.ROLE_MEMBER1));
            roles.add(new SysRole(4,SysRole.ROLE_MEMBER2));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
          this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SysRole> getRoles(){
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public static SysUser findByUsername(String username){
        if(username.equals("admin")){
            return new SysUser(1,username,username);
        }
        if(username.equals("member")){
            return new SysUser(2,username,username);
        }
        return null;
    }


}
