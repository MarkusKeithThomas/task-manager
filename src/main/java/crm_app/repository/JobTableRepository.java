package crm_app.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm_app.config.Job;
import crm_app.config.MysqlConfig;

public class JobTableRepository {
	public List<Job> findAllJob() {
		String query = "SELECT j.id,j.name,j.start_date,j.end_date FROM jobs j";
		List<Job> list = new ArrayList<>();
        System.out.println("Bắt đầu lấy danh sách tasks.");

		
		try(Connection connection = MysqlConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Job job = new Job();
				job.setIdJob(rs.getString("id"));
				job.setNameJob(rs.getString("name"));
				job.setStartDateJob(rs.getString("start_date"));
				job.setEndDateJob(rs.getString("end_date"));
				list.add(job);
			}			
			
			
		} catch (Exception e) {
			 e.printStackTrace();
	            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
		}
		return list;
	}

	public int deleteJob(int idJob) {
		int rowDelete = 0;
		String query = "DELETE FROM jobs j WHERE j.id = ?";

		
		try (Connection connection = MysqlConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, idJob);
			rowDelete = preparedStatement.executeUpdate();
		} catch (Exception e) {
			 e.printStackTrace();
	            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());	
	     }
		return rowDelete;
		
	}
	public int insertJob(String nameJob,Date dateStart,Date dateEnd) {
		int rowInsert = 0;
		String query = "INSERT INTO jobs (name, start_date, end_date) VALUES (?,?,?)";
		
		try (Connection connection = MysqlConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, nameJob);
			preparedStatement.setDate(2, dateStart);
			preparedStatement.setDate(3, dateEnd);
			rowInsert = preparedStatement.executeUpdate();
		} catch (Exception e) {
			 e.printStackTrace();
	            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());	
	     }
		return rowInsert;
		
	}
	
	
	
	
	
	

}
