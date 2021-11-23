package cn.edu.bzu.ie.mysqldb;

import java.io.Serializable;

/**
 * 用户信息实体类
 */
public class Userinfo implements Serializable {
    private int id;    // 用户的id
    private String uname;   // 用户名
    private String upass;   // 用户密码
    private String createDt;   // 创建时间

    public Userinfo() {
    }

    public Userinfo(int id, String uname, String upass, String createDt) {
        this.id = id;
        this.uname = uname;
        this.upass = upass;
        this.createDt = createDt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}
