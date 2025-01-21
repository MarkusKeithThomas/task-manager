package crm_app.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import crm_app.config.TaskInfor;
import crm_app.repository.TaskInforRepository;

public class TaskInforService {
	TaskInforRepository taskInforRepository = new TaskInforRepository();
	public List<TaskInfor> getAllTasks() {
		return taskInforRepository.findAllTaskInfo();
	}
	public int deleteTasks(int idTask) {
		return taskInforRepository.deleteTask(idTask);
	}
	public int insertTask(String nameTask,Date dateStart,Date dateEnd,int userId,int jobId, int statusId) {
		return taskInforRepository.insertTasks(nameTask, dateStart, dateEnd, userId, jobId, statusId);
	}
	public List<TaskInfor> getOneManTasks(HttpServletRequest request) {
		String emailFromCookie = null;

		Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    emailFromCookie = cookie.getValue();
                    break;
                }
            }
        }
        
		return taskInforRepository.findOneTaskInfo(emailFromCookie);
	}
	public List<TaskInfor> getOneManTasksByEmail(String email) {
        
		return taskInforRepository.findOneTaskInfo(email);
	}
	public TaskInfor getOneTaskById(int idTaks){
		return taskInforRepository.findOneTaskInfoById(idTaks);
	}
	public int updateOneTaskById(int idTask,int idStatus) {
		return taskInforRepository.findTaskToUpdate(idTask, idStatus);
	}
	public Map<Integer, List<TaskInfor>> getOneByOneMemberByJobId(int jobId){
		return taskInforRepository.findOneByOneMemberByJobId(jobId);
	}
	
	
	
}
