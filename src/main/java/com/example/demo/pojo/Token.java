package com.example.demo.pojo;

public class Token {
    private SysUser sysUser;
    private long expireTime;

    public Token(SysUser sysUser, long expireTime) {
        this.sysUser = sysUser;
        this.expireTime = expireTime;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
