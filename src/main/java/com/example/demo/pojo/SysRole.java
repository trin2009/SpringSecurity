package com.example.demo.pojo;

import java.util.ArrayList;
import java.util.List;

public class SysRole {
    private  int id;
    private  String name;

    public static String ROLE_ADMIN1="ROLE_ADMIN1";
    public static String ROLE_ADMIN2="ROLE_ADMIN1";
    public static String ROLE_MEMBER1="ROLE_MEMBER1";
    public static String ROLE_MEMBER2="ROLE_MEMBER2";

    public SysRole(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<SysPermission> getPermissions(){
        if(name.equals(SysRole.ROLE_ADMIN1)||name.equals(SysRole.ROLE_ADMIN2)){
            return SysPermission.getAdminPermissions();
        }
        if(name.equals(SysRole.ROLE_MEMBER1)||name.equals(SysRole.ROLE_MEMBER2)){
            return SysPermission.getMemberPermissions();
        }
        return null;
    }
}
