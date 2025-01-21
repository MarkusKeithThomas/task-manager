package crm_app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.TaskInfor;
import crm_app.service.TaskInforService;

@WebServlet(name = "tasksSeverlet",urlPatterns = "/task")
public class TaskSeverlet extends HttpServlet{
	TaskInforService taskInforService = new TaskInforService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TaskInfor> list = taskInforService.getAllTasks();
		req.setAttribute("list", list);
		req.getRequestDispatcher("task.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idTask = req.getParameter("idTask");
		taskInforService.deleteTasks(Integer.parseInt(idTask));
		doGet(req, resp);
	}

}
