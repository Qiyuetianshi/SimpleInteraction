package cn.edu.bzu.ie.mysqldb;
import java.io.Serializable;
import java.util.Date;

public class room implements Serializable {
    private int id;
    private String patname;
    private String proname;
    private int state;
    private String ctime;
    private int isreadpro;

    public room() {

    }

    public room(String patname,String proname) {
        this.patname = patname;
        this.proname = proname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatname() {
        return patname;
    }

    public void setPatname(String patname) {
        this.patname = patname;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }


    public int getIsreadpro() {
        return isreadpro;
    }

    public void setIsreadpro(int isreadpro) {
        this.isreadpro = isreadpro;
    }
}
