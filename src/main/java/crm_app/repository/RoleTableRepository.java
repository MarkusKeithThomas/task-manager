package crm_app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm_app.config.MysqlConfig;
import crm_app.config.Roles;

public class RoleTableRepository {
	public List<Roles> findAllRoles() {
        String query = "SELECT r.id, r.name, r.description FROM roles r";

        List<Roles> list = new ArrayList<>();
        System.out.println("Bắt đầu lấy danh sách roles.");

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Roles roles = new Roles();
                roles.setIdRoles(rs.getString("id"));
                roles.setNameRoles(rs.getString("name"));
                roles.setDescriptionRoles(rs.getString("description"));
                list.add(roles);
            }
            System.out.println("Lấy danh sách roles thanh .");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
        return list;
    }
	public int insertRole(String nameRole,String descriptionRole) {
		int rowInsert = 0;
		String query = "INSERT INTO roles (name, description) VALUES (?, ?)";  
		try (
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setString(1, nameRole);
			preparedStatement.setString(2, descriptionRole);
			rowInsert = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());		
            }
		return rowInsert;
	}
	public int deleteRole(int roleId) {
		int rowDelete = 0;
		String query = "DELETE FROM roles r WHERE r.id = ?";
		try(Connection connection = MysqlConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setInt(1, roleId);
			rowDelete = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());			}		
		return rowDelete;
	}
	public String findRoleByEmail(String email) {
		String roleString = null;
		String query = "SELECT u.id AS user_id,u.email, r.name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ?";
        System.out.println("Bắt đầu lấy Quyen nguoi dung.");
        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
            	roleString = rs.getString("name");
            }
            System.out.println("Lấy roles thanh cong.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
		return roleString;
	}

}
