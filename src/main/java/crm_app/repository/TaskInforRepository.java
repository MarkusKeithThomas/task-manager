package crm_app.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crm_app.config.MysqlConfig;
import crm_app.config.TaskInfor;

public class TaskInforRepository {
	
	// Phương thức để lấy thông tin tác vụ
    public List<TaskInfor> findAllTaskInfo() {
        List<TaskInfor> taskInfos = new ArrayList<>();
        String query = "SELECT " +
                "t.id AS task_id, " +
                "j.name AS job_name, " +
                "t.name AS task_name, " +
                "u.fullname AS user_name, " +
                "t.start_date AS task_start_date, " +
                "t.end_date AS task_end_date, " +
                "s.name AS status_name " +
                "FROM users u " +
                "JOIN tasks t ON u.id = t.user_id " +
                "JOIN jobs j ON t.job_id = j.id " +
                "JOIN status s ON s.id = t.status_id";

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                TaskInfor taskInfo = new TaskInfor();
                taskInfo.setTaskId(rs.getInt("task_id"));
                taskInfo.setJobName(rs.getString("job_name"));
                taskInfo.setTaskName(rs.getString("task_name"));
                taskInfo.setUserName(rs.getString("user_name"));
                taskInfo.setTaskStartDate(rs.getDate("task_start_date"));
                taskInfo.setTaskEndDate(rs.getDate("task_end_date"));
                taskInfo.setStatusName(rs.getString("status_name"));
                taskInfos.add(taskInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskInfos;
    }
 // Phương thức để lấy thông tin tác vụ cua mot 
    public List<TaskInfor> findOneTaskInfo(String email) {
        List<TaskInfor> taskInfos = new ArrayList<>();
        String query = "SELECT " +
                "t.id AS task_id, " +
                "j.name AS job_name, " +
                "t.name AS task_name, " +
                "u.fullname AS user_name, " +
                "t.start_date AS task_start_date, " +
                "t.end_date AS task_end_date, " +
                "t.status_id AS status_id, "+
                "s.name AS status_name " +
                "FROM users u " +
                "JOIN tasks t ON u.id = t.user_id " +
                "JOIN jobs j ON t.job_id = j.id " +
                "JOIN status s ON s.id = t.status_id " +  // Chú ý thêm khoảng trắng ở cuối dòng này
                "WHERE u.email = ?";

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	
        	// Đặt giá trị email từ tham so vào câu truy vấn
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                TaskInfor taskInfo = new TaskInfor();
                taskInfo.setTaskId(rs.getInt("task_id"));
                taskInfo.setJobName(rs.getString("job_name"));
                taskInfo.setTaskName(rs.getString("task_name"));
                taskInfo.setUserName(rs.getString("user_name"));
                taskInfo.setTaskStartDate(rs.getDate("task_start_date"));
                taskInfo.setTaskEndDate(rs.getDate("task_end_date"));
                taskInfo.setStatusName(rs.getString("status_name"));
                taskInfo.setStatusId(rs.getInt("status_id"));
                taskInfos.add(taskInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskInfos;
    }
    public int deleteTask(int taskId) {
        int rowsDeleted = 0;
        String query = "DELETE FROM tasks WHERE id = ?";

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, taskId);
            rowsDeleted = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi xóa tác vụ: " + e.getMessage());
        }
        return rowsDeleted;
    }
    public int insertTasks(String nameTask,Date dateStart,Date dateEnd,int userId,int jobId, int statusId) {
    	int insertRow = 0;
    	 String sql = "INSERT INTO tasks (name,start_date, end_date,user_id, job_id, status_id) VALUES (?, ?, ?, ?, ?,?)";

         try (Connection conn = MysqlConfig.getConnection();
              PreparedStatement ps = conn.prepareStatement(sql)) {

             ps.setString(1, nameTask);
             ps.setDate(2, dateStart);
             ps.setDate(3, dateEnd);
             ps.setInt(4, userId); // Lấy ID user từ tên hoặc xử lý theo yêu cầu
             ps.setInt(5, jobId);  // Lấy ID job từ tên hoặc xử lý theo yêu cầu
             ps.setInt(6,statusId);
             ps.executeUpdate();
             
         } catch (SQLException e) {
             e.printStackTrace();
         }
    	
    	
    	return insertRow;
    }
 // Phương thức để lấy thông tin tác vụ cua mot 
    public TaskInfor findOneTaskInfoById(int idTask) {
        TaskInfor taskInfosById = null; // Ban đầu để null
        String query = "SELECT " +
                "t.id AS task_id, " +
                "j.name AS job_name, " +
                "t.name AS task_name, " +
                "u.fullname AS user_name, " +
                "t.start_date AS task_start_date, " +
                "t.end_date AS task_end_date, " +
                "s.name AS status_name " +
                "FROM users u " +
                "JOIN tasks t ON u.id = t.user_id " +
                "JOIN jobs j ON t.job_id = j.id " +
                "JOIN status s ON s.id = t.status_id " + 
                "WHERE t.id = ?";

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	
        	// Đặt giá trị email từ tham so vào câu truy vấn
            preparedStatement.setInt(1, idTask);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                taskInfosById = new TaskInfor(); // Chỉ khởi tạo nếu có dữ liệu
                taskInfosById.setTaskId(rs.getInt("task_id"));
                taskInfosById.setJobName(rs.getString("job_name"));
                taskInfosById.setTaskName(rs.getString("task_name"));
                taskInfosById.setUserName(rs.getString("user_name"));
                taskInfosById.setTaskStartDate(rs.getDate("task_start_date"));
                taskInfosById.setTaskEndDate(rs.getDate("task_end_date"));
                taskInfosById.setStatusName(rs.getString("status_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskInfosById;
    }
    public int findTaskToUpdate(int idTask,int idStatus) {
    	int rowUpdate = 0;
    	String  query = "UPDATE tasks \n"
    			+ "SET status_id = ?\n"
    			+ "WHERE id = ?";
    	try (Connection connection = MysqlConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
           	
           	// Đặt giá trị email từ tham so vào câu truy vấn
               preparedStatement.setInt(1, idStatus);
               preparedStatement.setInt(2, idTask);
               rowUpdate = preparedStatement.executeUpdate();
           } catch (Exception e) {
               e.printStackTrace();
           }
    	return rowUpdate;
    }
    public Map<Integer, List<TaskInfor>> findOneByOneMemberByJobId(int jobId) {
        Map<Integer, List<TaskInfor>> userTaskMap = new HashMap<>();

        String query = "SELECT \n"
        		+ "    t.id AS task_id, \n"
        		+ "    j.id AS job_id,\n"
        		+ "    j.name AS job_name,\n"
        		+ "    t.name AS task_name, \n"
        		+ "    t.start_date AS task_start_date, \n"
        		+ "    t.end_date AS task_end_date,\n"
        		+ "    t.status_id AS status_id,\n"
        		+ "    u.id AS user_id,\n"
        		+ "    u.fullname AS name_user,\n"
        		+ "    u.avatar AS avatar_image,\n"
        		+ "    s.name AS status_name \n"
        		+ "FROM \n"
        		+ "    users u \n"
        		+ "JOIN \n"
        		+ "    tasks t ON u.id = t.user_id \n"
        		+ "JOIN \n"
        		+ "    jobs j ON t.job_id = j.id \n"
        		+ "JOIN \n"
        		+ "    status s ON s.id = t.status_id\n"
        		+ "WHERE j.id = ?";

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, jobId); 
        	ResultSet rs = preparedStatement.executeQuery(); 


            while (rs.next()) {
            	int userId = rs.getInt("user_id");
            	
                TaskInfor taskInfo = new TaskInfor();
                taskInfo.setTaskId(rs.getInt("task_id"));
                taskInfo.setJobId(rs.getInt("job_id"));
                taskInfo.setJobName(rs.getString("job_name"));
                taskInfo.setTaskName(rs.getString("task_name"));
                taskInfo.setUserName(rs.getString("name_user"));
                taskInfo.setTaskStartDate(rs.getDate("task_start_date"));
                taskInfo.setTaskEndDate(rs.getDate("task_end_date"));
                taskInfo.setStatusName(rs.getString("status_name"));
                taskInfo.setAvatarImage(rs.getString("avatar_image"));
                taskInfo.setStatusId(rs.getInt("status_id"));
             // Thêm TaskInfor vào danh sách của userId
                userTaskMap.putIfAbsent(userId, new ArrayList<>());
                userTaskMap.get(userId).add(taskInfo);            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userTaskMap;
    }
    
    
    


}
