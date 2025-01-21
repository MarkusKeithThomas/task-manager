package crm_app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.TaskInfor;
import crm_app.service.StatusTaskService;
import crm_app.service.TaskInforService;

@WebServlet(name = "groupWorkDetailsSeverlet",urlPatterns = "/groupwork-details")
public class GroupWorkDetailsSeverlet extends HttpServlet {
	TaskInforService taskInforService = new TaskInforService();
	StatusTaskService statusTaskService = new StatusTaskService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String idJob = req.getParameter("idJob");

	    if (idJob != null && !idJob.isEmpty()) {
	        try {
	            int jobId = Integer.parseInt(idJob);

	            // Lấy danh sách công việc theo idJob
	            Map<Integer, List<TaskInfor>> userTaskMap = taskInforService.getOneByOneMemberByJobId(jobId);
	            req.setAttribute("userTaskMap", userTaskMap);

	            // Lấy danh sách trạng thái
	            List<String> listStringStatus = statusTaskService.getOneJobStatusByjobId(Integer.parseInt(idJob));

	            if (!listStringStatus.isEmpty()) {
	                req.setAttribute("chuabatdau", listStringStatus.get(0));
	                req.setAttribute("dangthuchien", listStringStatus.get(1));
	                req.setAttribute("hoanthanh", listStringStatus.get(2));
	            } else {
	                req.setAttribute("chuabatdau", "0");
	                req.setAttribute("dangthuchien", "0");
	                req.setAttribute("hoanthanh", "0");
	                req.setAttribute("error", "Không tìm thấy danh sách Status.");
	            }
	        } catch (NumberFormatException e) {
	            req.setAttribute("error", "ID công việc không hợp lệ.");
	        }
	    } else {
	        req.setAttribute("error", "ID công việc không được để trống.");
	    }

	    // Chuyển tiếp tới JSP để hiển thị
	    req.getRequestDispatcher("/groupwork-details.jsp").forward(req, resp);
	}

	
}
