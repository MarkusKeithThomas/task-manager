package crm_app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm_app.config.MysqlConfig;
import crm_app.config.Status;

public class StatusTaskRepository {
	public List<Status> findAllStatus() {
        String query = " SELECT s.id,s.name FROM status s";

        List<Status> list = new ArrayList<>();
        System.out.println("Bắt đầu lấy danh sách task.");

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
            	Status status = new Status();
            	status.setIdStatus(rs.getString("id"));
            	status.setNameStatuString(rs.getString("name"));
                list.add(status);
            }
            System.out.println("Lấy danh sách status thanh .");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
        }
        return list;
    }
	
	public List<Status> findCountStatus(String email){
		String  query = "SELECT \n"
				+ "    s.id AS status_id,\n"
				+ "    s.name AS status_name,\n"
				+ "    COUNT(t.id) AS total_tasks\n"
				+ "FROM \n"
				+ "    users u\n"
				+ "JOIN \n"
				+ "    tasks t ON u.id = t.user_id\n"
				+ "JOIN \n"
				+ "    status s ON s.id = t.status_id\n"
				+ "WHERE \n"
				+ "    u.email = ?\n"
				+ "GROUP BY \n"
				+ "    s.id, s.name";
		 List<Status> list = new ArrayList<>();
	        System.out.println("Bắt đầu lấy danh sách count status.");
	        try (Connection connection = MysqlConfig.getConnection();
	                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        		preparedStatement.setString(1, email);
	               ResultSet rs = preparedStatement.executeQuery();

	               while (rs.next()) {
	               	Status status = new Status();
	               	status.setIdStatus(rs.getString("status_id"));
	               	status.setNameStatuString(rs.getString("status_name"));
	               	status.setCountStatus(rs.getString("total_tasks"));
	                list.add(status);
	               }
	               System.out.println("Lấy danh sách count status thanh cong .");
	           } catch (Exception e) {
	               e.printStackTrace();
	               System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
	           }
	        return list;
		
	}
	public int findCountAllTaskOne(String email) {
	    String query = "SELECT COUNT(t.id) AS total_tasks " +
	                   "FROM users u " +
	                   "JOIN tasks t ON u.id = t.user_id " +
	                   "WHERE u.email = ?";

	    System.out.println("Bắt đầu lấy tổng số công việc.");
	    int countAll = 0;

	    try (Connection connection = MysqlConfig.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        // Đặt giá trị cho tham số email
	        preparedStatement.setString(1, email);

	        // Thực thi truy vấn và xử lý kết quả
	        ResultSet rs = preparedStatement.executeQuery();
	        if (rs.next()) { // Phải gọi rs.next() để di chuyển con trỏ đến hàng đầu tiên
	            countAll = rs.getInt("total_tasks");
	        }
	        System.out.println("Lấy tổng số công việc thành công.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
	    }


	    return countAll;
	}
	public List<Status> findCountStatuAllMember(){
		String  query = "SELECT \n"
				+ "    s.id AS status_id,\n"
				+ "    s.name AS status_name,\n"
				+ "    COUNT(t.id) AS total_tasks\n"
				+ "FROM \n"
				+ "    users u\n"
				+ "JOIN \n"
				+ "    tasks t ON u.id = t.user_id\n"
				+ "JOIN \n"
				+ "    status s ON s.id = t.status_id\n"
				+ "GROUP BY \n"
				+ "    s.id, s.name";
		 List<Status> list = new ArrayList<>();
	        System.out.println("Bắt đầu lấy danh sách count status.");
	        try (Connection connection = MysqlConfig.getConnection();
	                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	               ResultSet rs = preparedStatement.executeQuery();

	               while (rs.next()) {
	               	Status status = new Status();
	               	status.setIdStatus(rs.getString("status_id"));
	               	status.setNameStatuString(rs.getString("status_name"));
	               	status.setCountStatus(rs.getString("total_tasks"));
	                list.add(status);
	               }
	               System.out.println("Lấy danh sách count status thanh cong .");
	           } catch (Exception e) {
	               e.printStackTrace();
	               System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
	           }
	        return list;
		
	}
	public int findCountTotalAllTask() {
	    String query = "SELECT COUNT(t.id) AS total_tasks " +
	                   "FROM users u " +
	                   "JOIN tasks t ON u.id = t.user_id ";
	    
	    System.out.println("Bắt đầu lấy tổng số công việc.");
	    int countAll = 0;

	    try (Connection connection = MysqlConfig.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        // Thực thi truy vấn và xử lý kết quả
	        ResultSet rs = preparedStatement.executeQuery();
	        if (rs.next()) { // Phải gọi rs.next() để di chuyển con trỏ đến hàng đầu tiên
	            countAll = rs.getInt("total_tasks");
	        }
	        System.out.println("Lấy tổng số công việc thành công.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
	    }


	    return countAll;
	}
	
	public int findCountAllTaskByJobId(int jobId) {
	    String query = "SELECT COUNT(t.job_id) AS total_tasks FROM tasks t WHERE t.job_id =?";

	    System.out.println("Bắt đầu lấy tổng số cv trong dự án.");
	    int countAll = 0;

	    try (Connection connection = MysqlConfig.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        preparedStatement.setInt(1, jobId);

	        // Thực thi truy vấn và xử lý kết quả
	        ResultSet rs = preparedStatement.executeQuery();
	        if (rs.next()) { // Phải gọi rs.next() để di chuyển con trỏ đến hàng đầu tiên
	            countAll = rs.getInt("total_tasks");
	        }
	        System.out.println("Lấy tổng số công việc thành công.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
	    }


	    return countAll;
	}
	public List<Status> findCountStatuOneJobByJobId(int jobId){
		String  query = "SELECT \n"
				+ "    s.id AS status_id, \n"
				+ "    s.name AS status_name, \n"
				+ "    COUNT(t.id) AS total_tasks\n"
				+ "FROM \n"
				+ "    users u\n"
				+ "JOIN \n"
				+ "    tasks t ON u.id = t.user_id\n"
				+ "JOIN \n"
				+ "    status s ON s.id = t.status_id\n"
				+ "JOIN \n"
				+ "    jobs j ON t.job_id = j.id\n"
				+ "WHERE \n"
				+ "    j.id = ?\n"
				+ "GROUP BY \n"
				+ "    s.id, s.name";
		 List<Status> list = new ArrayList<>();
	        System.out.println("Bắt đầu lấy danh sách count status.");
	        try (Connection connection = MysqlConfig.getConnection();
	                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        		preparedStatement.setInt(1, jobId);   
	        		ResultSet rs = preparedStatement.executeQuery();


	               while (rs.next()) {
	               	Status status = new Status();
	               	status.setIdStatus(rs.getString("status_id"));
	               	status.setNameStatuString(rs.getString("status_name"));
	               	status.setCountStatus(rs.getString("total_tasks"));
	                list.add(status);
	               }
	               System.out.println("Lấy danh sách count status thanh cong .");
	           } catch (Exception e) {
	               e.printStackTrace();
	               System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
	           }
	        return list;
		
	}
	
	
	
	

	


}
