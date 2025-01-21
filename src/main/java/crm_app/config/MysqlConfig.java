package crm_app.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
	public static Connection getConnection() {
		Connection connection1 = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3307/buoi30", "root", "admin123");
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();  // In thông tin lỗi chi tiết
			 System.out.println("Lỗi kết nối CSDL: " + e.getMessage());	
		
	}
		return connection1;
	}
}
