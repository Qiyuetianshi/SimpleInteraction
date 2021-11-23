package cn.edu.bzu.ie.mysqldb;
import java.io.Serializable;

public class message implements Serializable {
    private int id;
    private int roomId;
    private String sendname;
    private String mes;

    public message() {

    }

//    public message(String patname,String proname) {
//        this.patname = patname;
//        this.proname = proname;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getSendname() {
        return sendname;
    }

    public void setSendname(String sendname) {
        this.sendname = sendname;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
