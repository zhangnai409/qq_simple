package com.zrd.common;

import java.io.Serializable;

/**
 * �û�
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId; // �û�id
    private String pwd; // �û�����

    public User(){}
    public User(String userId, String pwd) {
        this.userId = userId;
        this.pwd = pwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
