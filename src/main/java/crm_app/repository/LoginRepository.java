package crm_app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import crm_app.config.MysqlConfig;
import crm_app.config.Users;

public class LoginRepository {
	public Users findLogin(String email,String password) {
        String query = "SELECT * FROM users u WHERE u.email = ? AND u.password = ?";
		Users users = new Users();
        
        // Mở kết nối đến CSDL
        try (Connection connection = MysqlConfig.getConnection();
        		 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Gán các giá trị email và password vào vị trí giữ chỗ
        	preparedStatement.setString(1, email);
        	preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            // Xử lý kết quả
            if (rs.next()) {
                System.out.println("User authenticated successfully.");
                // Lấy thông tin người dùng
                String email1 = rs.getString("email");
                String password1 = rs.getString("password");
                users.setEmail(email1);
                users.setPassword(password1);
                System.out.println("Email: " + email1 + ", Password: " + password1);
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
        return users;
	}
	public boolean findExistByEmail(String email) {
    	boolean isExisted = false;
    	String query = "SELECT u.email FROM users u WHERE u.email = ?";
        

        try (Connection connection = MysqlConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

               // Đặt giá trị email từ cookie vào câu truy vấn
               preparedStatement.setString(1, email);

               ResultSet rs = preparedStatement.executeQuery();

               // Nếu tìm thấy người dùng, thiết lập các thuộc tính của `member`
               if (rs.next()) {
                  String emailUserString = rs.getString("email");
                  if (emailUserString != null || !emailUserString.isEmpty()) {
					return !isExisted;
				}
               }
           } catch (Exception e) {
               e.printStackTrace();
               System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
           }
    	
    	
    	
    	return isExisted;
    }

}
