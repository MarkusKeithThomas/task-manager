package crm_app.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.service.JobTableService;

@WebServlet(name = "groupAdd",urlPatterns = "/groupwork-add")
public class GroupAddSeverlet extends HttpServlet {
	JobTableService jobTableService = new JobTableService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    // Chuyển hướng đến trang role-add.jsp để hiển thị form
	    req.getRequestDispatcher("/groupwork-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nameJob = req.getParameter("namejob");
		String dateStart = req.getParameter("datestart");
		String dateEnd = req.getParameter("dateend");
		// Chuyển đổi chuỗi sang java.sql.Date (định dạng chuỗi nhập là "YYYY-MM-DD")
        Date startDate = Date.valueOf(dateStart);
        Date endDate = Date.valueOf(dateEnd);
        
        if (jobTableService.checkingFilling(nameJob, dateStart, dateEnd)) {
        	jobTableService.insertJob(nameJob, startDate, endDate);
			// Chuyển hướng về trang /groupwork
	        resp.sendRedirect(req.getContextPath() + "/groupwork");
		} else {
			doGet(req, resp);
		}		
				
	}
}
