package cn.edu.bzu.ie.mysqldb;

import java.io.Serializable;

/**
 * 用户信息实体类
 */
public class Userinfo implements Serializable {
    private int id;    // 用户的id
    private String uname;   // 用户名
    private String upass;   // 用户密码
    private int statement;
    private String info;
    private String state;

    public Userinfo() {
    }

    public Userinfo(int id, String uname, String upass, String createDt) {
        this.id = id;
        this.uname = uname;
        this.upass = upass;
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


    public int getStatement() {
        return statement;
    }

    public void setStatement(int statement) {
        this.statement = statement;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
