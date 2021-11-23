package cn.edu.bzu.ie.mysqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 直接连接数据库的辅助工具类
 */
public class MySqlHelp {
    public static int getUserSize(){
        final String CLS = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://121.37.183.246:3306/dblearner?useUnicode=true&characterEncoding=utf8";
        final String USER = "root";
        final String PWD = "Question12306";

        int count = 0;   // 用户数量

        try{
            Class.forName(CLS);
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "select count(1) as sl from patinfo";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                count = rs.getInt("sl");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return count;
    }
}
