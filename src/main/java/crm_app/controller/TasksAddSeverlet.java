package crm_app.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Job;
import crm_app.config.Member;
import crm_app.service.JobTableService;
import crm_app.service.ListUserService;
import crm_app.service.TaskInforService;

@WebServlet (name = "taskAddSeverlet",urlPatterns = "/task-add")
public class TasksAddSeverlet extends HttpServlet {
	ListUserService listUserService = new ListUserService();
	JobTableService jobTableService = new JobTableService();
	TaskInforService taskInforService = new TaskInforService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Member> listMembers = listUserService.findListUser();
		List<Job> listJobs = jobTableService.getAllJobs();
		
		req.setAttribute("jobs", listJobs);
		req.setAttribute("users", listMembers);
		req.getRequestDispatcher("/task-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nameTask = req.getParameter("nameJob");
		String startDate = req.getParameter("datestart");
		String endDate = req.getParameter("dateend");
		String userId = req.getParameter("userId");
  		String  jobId = req.getParameter("jobId"); 
  		String statusId = "1";
  		
  		if (nameTask != null && !nameTask.isEmpty()) {
  	  		taskInforService.insertTask(nameTask, Date.valueOf(startDate),Date.valueOf(endDate),
  	  			Integer.parseInt(userId),Integer.parseInt(jobId), Integer.parseInt(statusId));
  	  		resp.sendRedirect(req.getContextPath()+"/task");
		} else {
			req.getRequestDispatcher("/task-add.jsp").forward(req, resp);
		}
 		
		
		
		
	}

}
