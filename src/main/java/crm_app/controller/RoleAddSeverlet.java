package crm_app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.service.RoleTableService;

@WebServlet(name = "roleAddSeverlet",urlPatterns = "/role-add")
public class RoleAddSeverlet extends HttpServlet{
	
	RoleTableService roleTableService = new RoleTableService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    // Chuyển hướng đến trang role-add.jsp để hiển thị form
	    req.getRequestDispatcher("/role-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nameRole = req.getParameter("nameRole");
		String descriptionRole = req.getParameter("descriptionRole");
		if (roleTableService.checkingFilling(nameRole, descriptionRole)) {
			roleTableService.insertRoles(nameRole, descriptionRole);
            resp.sendRedirect(req.getContextPath() + "/role-table");
		} else {
			System.out.println("Cần điền đầy đủ thông tin.");
            req.getRequestDispatcher("/role-add.jsp").forward(req, resp);
		}
		
		
	}
	
	

}
