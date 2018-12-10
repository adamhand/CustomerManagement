package Utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Utils {
    private static ComboPooledDataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource();
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

    //使用queryrunner配合C3P0则用不到下面这两个方法
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void releaseConnection(Connection conn){
        try {
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
