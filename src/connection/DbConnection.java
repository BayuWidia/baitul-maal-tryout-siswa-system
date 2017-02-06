package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/db_sekolah_baitul_maal";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	static Connection connection;

	public static Connection getDBConnection() {

        connection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            System.out.println("SUKSES");
            return connection;
        } catch (SQLException e) {
        	System.out.println("GAGAL");
            System.out.println(e.getMessage());
         }
        return connection;
    }
}