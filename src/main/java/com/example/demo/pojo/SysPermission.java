package com.example.demo.pojo;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

import java.util.ArrayList;
import java.util.List;

public class SysPermission {
    private  int id;
    private  String name;
    private String url;

    public SysPermission(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public static List<SysPermission> getAdminPermissions(){
        List<SysPermission> permissions = new ArrayList<>();
        permissions.add(new SysPermission(1,"adminPermission1","/view/adminUrl1"));
        permissions.add(new SysPermission(2,"adminPermission2","/api/adminUrl2"));
        return permissions;
    }

    public static List<SysPermission> getMemberPermissions(){
        List<SysPermission> permissions = new ArrayList<>();
        permissions.add(new SysPermission(3,"memberPermission1","/view/membeUrl1"));
        permissions.add(new SysPermission(4,"memberPermission2","/api/memberUrl2"));
        return permissions;
    }

    public static List<SysPermission> getAllPermissions(){
        List<SysPermission> permissions = new ArrayList<>();
        permissions.addAll(SysPermission.getAdminPermissions());
        permissions.addAll(SysPermission.getMemberPermissions());
        return permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
