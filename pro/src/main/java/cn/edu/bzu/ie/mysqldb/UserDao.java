package cn.edu.bzu.ie.mysqldb;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据库操作类
 * 实现用户的CRUD操作
 */
public class UserDao extends DbOpenHelper {

    //查询所有的romm
    public List<room> getAllroom(String proname){
        List<room> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from room where proname=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, proname);
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                room item = new room();
                item.setId(rs.getInt("id"));
                item.setPatname(rs.getString("patname"));
                item.setProname(rs.getString("proname"));
                item.setCtime(rs.getString("ctime"));
                item.setIsreadpro(rs.getInt("isreadpro"));
                if(!list.isEmpty()&&item.getId()>list.get(0).getId()){
                    list.add(0,item);
                } else {
                    list.add(item);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

    //
    public List<message> getAllmessage(int roomID){
        List<message> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from info where roomID=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, roomID);
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                message item = new message();
                item.setMes(rs.getString("message"));
                item.setSendname(rs.getString("sendname"));
                list.add(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

    // 查询所有的用户信息 R
    public List<proinfo> getAllUserList(){
        List<proinfo> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from proinfo";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                proinfo item = new proinfo();
                item.setUname(rs.getString("uname"));
                item.setInfo(rs.getString("info"));
                item.setState(rs.getString("state"));
                item.setStatement(rs.getInt("statement"));
                list.add(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

    // 查询所有的用户信息 R
    public List<proinfo> getsomeUserList(String a){
        List<proinfo> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from proinfo where uname like ? or info like ? or state like ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, "%"+a+"%");
            pStmt.setString(2, "%"+a+"%");
            pStmt.setString(3, "%"+a+"%");
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                proinfo item = new proinfo();
                item.setUname(rs.getString("uname"));
                item.setInfo(rs.getString("info"));
                item.setState(rs.getString("state"));
                item.setStatement(rs.getInt("statement"));
                list.add(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

    /**
     * 按用户名和密码查询用户信息 R
     * @param uname 用户名
     * @param upass 密码
     * @return Userinfo 实例
     */
    public Userinfo getUserByUnameAndUpass(String uname, String upass){
        Userinfo item = null;
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from proinfo where uname=? and upass=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uname);
            pStmt.setString(2, upass);
            rs = pStmt.executeQuery();
            if(rs.next()){
                item = new Userinfo();
                item.setId(rs.getInt("id"));
                item.setUname(uname);
                item.setUpass(upass);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    public Userinfo getUserByUname(String uname){
        Userinfo item = null;
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from proinfo where uname=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, uname);
            rs = pStmt.executeQuery();
            if(rs.next()){
                item = new Userinfo();
                item.setId(rs.getInt("id"));
                item.setUname(uname);
                item.setStatement(rs.getInt("statement"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }
    /**
     * 添加用户信息 C
     * @param item 要添加的用户
     * @return int 影响的行数
     */
    public boolean addUser(Userinfo item){
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from proinfo where uname=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, item.getUname());
            rs = pStmt.executeQuery();
            if(rs.next()){
                return false;
            }
            String sql2 = "insert into proinfo(uname, upass ,state ,info) values(?, ? ,? ,?)";
            pStmt = conn.prepareStatement(sql2);
            pStmt.setString(1, item.getUname());
            pStmt.setString(2, item.getUpass());
            pStmt.setString(3, item.getState());
            pStmt.setString(4, item.getInfo());
            pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return true;
    }

    public boolean sendmessage(message item){
        try{
            getConnection();   // 取得连接信息
            String sql2 = "insert into info(roomID, sendname ,message) values(?, ? ,?)";
            pStmt = conn.prepareStatement(sql2);
            pStmt.setInt(1, item.getRoomId());
            pStmt.setString(2, item.getSendname());
            pStmt.setString(3, item.getMes());
            pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            closeAll();
        }
        return true;
    }

    public boolean creatRoom(room item){
        try{
            getConnection();   // 取得连接信息
            String sql2 = "insert into room(state,patname ,proname ) values(?, ? ,?)";
            pStmt = conn.prepareStatement(sql2);
            pStmt.setInt(1, 1);
            pStmt.setString(2, item.getPatname());
            pStmt.setString(3, item.getProname());
            Log.i("1",item.getPatname()+item.getProname());
            pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            closeAll();
        }
        return true;
    }

    /**
     * 修改用户信息 U
     * @param item 要修改的用户
     * @return int 影响的行数
     */
    public int editUser(Userinfo item){
        int iRow = 0;
        try{
            getConnection();   // 取得连接信息
            String sql = "update patinfo set uname=?, upass=? where id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, item.getUname());
            pStmt.setString(2, item.getUpass());
            pStmt.setInt(3, item.getId());
            iRow = pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }
    public boolean updatestatement(int state,String name){
        try{
            getConnection();   // 取得连接信息
            String sql = "update proinfo set statement=? where uname=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, state);
            pStmt.setString(2, name);
            pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return true;
    }

    public boolean updateroomstatepat(int room){
        try{
            getConnection();   // 取得连接信息
            String sql = "update room set isreadpat=? where id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, 1);
            pStmt.setInt(2, room);
            pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return true;
    }

    public boolean updateroompro(int room){
        try{
            getConnection();   // 取得连接信息
            String sql = "update room set isreadpro=? where id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, 0);
            pStmt.setInt(2, room);
            pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return true;
    }
    /**
     * 根据id 删除用户信息
     * @param id 要删除用户的id
     * @return int 影响的行数
     */
    public int delUser(int id){
        int iRow = 0;
        try{
            getConnection();   // 取得连接信息
            String sql = "delete from patinfo where id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, id);
            iRow = pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }
}
