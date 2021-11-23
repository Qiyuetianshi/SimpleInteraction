package cn.edu.bzu.ie.mysqldb;
import java.io.Serializable;

public class proinfo implements Serializable {
    private int id;    // 用户的id
    private String uname;   // 用户名
    private String state;
    private String info;
    private int statement;

    public proinfo() {
    }

    public proinfo(int id, String uname,String state,String info) {
        this.id = id;
        this.uname = uname;
        this.state = state;
        this.info = info;
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

    public int getStatement() {
        return statement;
    }

    public void setStatement(int statement) {
        this.statement = statement;
    }
}
