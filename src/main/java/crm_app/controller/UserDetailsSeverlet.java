package crm_app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Member;
import crm_app.config.TaskInfor;
import crm_app.service.ListUserService;
import crm_app.service.StatusTaskService;
import crm_app.service.TaskInforService;

@WebServlet(name = "userDeatailsSeverlet",urlPatterns = "/user-details")
public class UserDetailsSeverlet extends HttpServlet {
	
	ListUserService listUserService = new ListUserService();
	TaskInforService taskInforService = new TaskInforService();
	StatusTaskService statusTaskService = new StatusTaskService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("userEmail");
		Member member = listUserService.findOneMemberByEmail(email);
		List<TaskInfor> listTaskInfors = taskInforService.getOneManTasksByEmail(email);
		List<String> listStringStatus = statusTaskService.getOneManStatusByEmail(email);
		
		if (member != null) {
		    // Đặt giá trị fullname và email vào request để hiển thị trên JSP
			req.setAttribute("fullname", member.getFullName());
			req.setAttribute("email", member.getUserName());
	        req.setAttribute("imageURL", member.getAvatarURL()); // Thêm URL hình ảnh

		} else {
		    // Xử lý trường hợp không tìm thấy người dùng (nếu cần)
			req.setAttribute("error", "Không tìm thấy thông tin người dùng.");
		}
		
		if (listTaskInfors.size()>0) {
			req.setAttribute("tasks", listTaskInfors);
			
		} else {
			req.setAttribute("error", "Không tìm thấy danh sach cong viec.");
		}
		if (listStringStatus.size()>0) {
			
			req.setAttribute("chuabatdau", listStringStatus.get(0));
			req.setAttribute("dangthuchien", listStringStatus.get(1));
			req.setAttribute("hoanthanh", listStringStatus.get(2));
			

		} else {
		    req.setAttribute("chuabatdau", "0"); // Gán mặc định nếu không có dữ liệu
		    req.setAttribute("dangthuchien", "0");
		    req.setAttribute("hoanthanh", "0");
			req.setAttribute("error", "Không tìm thấy danh sach Status.");

		}
		
		// Chuyển tiếp đến trang JSP
		req.getRequestDispatcher("/user-details.jsp").forward(req, resp);
	}
	

}
