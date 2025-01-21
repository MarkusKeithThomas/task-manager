package crm_app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Job;
import crm_app.service.JobTableService;

@WebServlet(name = "groupWorkSeverlet",urlPatterns = "/groupwork")
public class GroupWorkSeverlet extends HttpServlet {
	JobTableService tableService = new JobTableService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Job> jobs = tableService.getAllJobs();
		req.setAttribute("jobs", jobs);
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String  idJob = req.getParameter("idJob");
		if (idJob != null && !idJob.isEmpty()) {
			tableService.deleteJob(Integer.parseInt(idJob));
		} else {
			
			
		}
		doGet(req, resp);
	}
	

}
