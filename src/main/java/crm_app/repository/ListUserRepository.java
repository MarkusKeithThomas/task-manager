package crm_app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm_app.config.Member;
import crm_app.config.MysqlConfig;

public class ListUserRepository {
    public List<Member> findAllMember() {
        String query = "SELECT u.id, u.fullname, u.email,u.phone , r.name AS role " +
                       "FROM users u JOIN roles r ON u.role_id = r.id";
        List<Member> list = new ArrayList<>();
        System.out.println("Bắt đầu lấy danh sách người dùng.");

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Member member = new Member();
                member.setUserId(rs.getString("id"));
                member.setFullName(rs.getString("fullname"));
                member.setUserName(rs.getString("email"));
                member.setPhone(rs.getString("phone"));
                member.setRole(rs.getString("role"));
                list.add(member);
            }
            System.out.println("Lấy danh sách thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
        return list;
    }
    public int deleteById(int userId){
    	String query = "DELETE FROM users WHERE id = ?";
    	int rowDeleted = 0;
    	try (Connection connection = MysqlConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    			preparedStatement.setInt(1, userId);
    			rowDeleted = preparedStatement.executeUpdate();
               System.out.println("Xoá danh sách thành công.");
           } catch (Exception e) {
               e.printStackTrace();
               System.out.println("Lỗi kết nối CSDL deleteById: " + e.getMessage());
           }
           return rowDeleted;
    }
    public int insert(String email,String password,String fullName,int roldID,String phone,String image) {
    	int rowInsert = 0;
    	String query = "INSERT INTO users (email, password, fullname, role_id,phone,avatar) VALUES (?,?,?,?,?,?)";
    	try (Connection connection = MysqlConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    			preparedStatement.setString(1, email);
    			preparedStatement.setString(2, password);
    			preparedStatement.setString(3, fullName);
    			preparedStatement.setInt(4, roldID);
    			preparedStatement.setString(5, phone);
    			preparedStatement.setString(6, image);
    			rowInsert = preparedStatement.executeUpdate();
               System.out.println("Insert Data thanh cong.");
           } catch (Exception e) {
               e.printStackTrace();
               System.out.println("Lỗi kết nối CSDL Insert: " + e.getMessage());
           }
           return rowInsert;
		
	}
    public Member findOneMember(String email) {
        String query = "SELECT u.fullname,u.email,u.avatar FROM users u WHERE u.email = ?";
        Member member = null;

        System.out.println("Bắt đầu lấy danh sách người dùng.");        
        // Lấy email từ cookie
        

        try (Connection connection = MysqlConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

               // Đặt giá trị email từ cookie vào câu truy vấn
               preparedStatement.setString(1, email);

               ResultSet rs = preparedStatement.executeQuery();

               // Nếu tìm thấy người dùng, thiết lập các thuộc tính của `member`
               if (rs.next()) {
                   member = new Member();
                   member.setFullName(rs.getString("fullname"));
                   member.setUserName(rs.getString("email"));
                   member.setAvatarURL(rs.getString("avatar"));
               }
               System.out.println("Lấy thông tin người dùng thành công.");
           } catch (Exception e) {
               e.printStackTrace();
               System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
           }

           return member;
    }
    
   
}

