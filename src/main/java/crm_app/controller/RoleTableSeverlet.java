package crm_app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Roles;
import crm_app.service.RoleTableService;

@WebServlet(name = "roleTableSeverlet", urlPatterns = "/role-table")
public class RoleTableSeverlet extends HttpServlet{
	RoleTableService roleTableService = new RoleTableService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 // Gọi service để lấy danh sách người dùng
        List<Roles> roles = roleTableService.getRoles();
        // Đặt danh sách người dùng vào request
        req.setAttribute("roles", roles);
        // Chuyển tiếp đến JSP để hiển thị
     // Chuyển tiếp đến JSP để hiển thị dữ liệu
        req.getRequestDispatcher("/role-table.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idRoleString = req.getParameter("idRoles");
		roleTableService.deleteRoles(Integer.parseInt(idRoleString));
        doGet(req, resp);
	}
	

}
