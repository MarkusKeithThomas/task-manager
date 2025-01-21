package crm_app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Roles;
import crm_app.service.ListUserService;
import crm_app.service.LoginService;
import crm_app.service.RoleTableService;

@WebServlet(name = "userAddSeverlet",urlPatterns = "/user-add")
public class UserAddSeverlet extends HttpServlet{
	RoleTableService roleTableService = new RoleTableService();
	ListUserService listUserService = new ListUserService();
	LoginService loginService = new LoginService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Roles> listRoles = roleTableService.getRoles();
		req.setAttribute("listroles", listRoles);
		// Chuyển tiếp đến JSP để hiển thị
        req.getRequestDispatcher("/user-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy giá trị từ các tham số trong form
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String roleString = req.getParameter("role");
        String imageAvatar = req.getParameter("iamge");
        int roleInt = Integer.parseInt(roleString);
		if(listUserService.checkingFilling(email, password, fullname, phone)) {
			if (loginService.getExistedUserByEmail(email)) {
				// Chuyển hướng tới trang danh sách người dùng
		        resp.sendRedirect(req.getContextPath() + "/user-table");
			} else {
				listUserService.insertMember(email, password, fullname, roleInt, phone,imageAvatar);
		        resp.sendRedirect(req.getContextPath() + "/user-table");
			}
			
		} else {
			System.out.println("Cần điền đầy đủ thông tin.");
			doGet(req, resp);
			req.getRequestDispatcher("/user-add.jsp").forward(req, resp);
		}
		
	}


}
