package crm_app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Member;
import crm_app.service.ListUserService;

@WebServlet(name = "userTableServlet", urlPatterns = "/user-table")
public class UserTableSeverlet extends HttpServlet {
	
    private ListUserService listUserService = new ListUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String userId = req.getParameter("userId");
    	if(userId != null) {
    		 try {
                 // Thực hiện tính năng xóa
                 listUserService.findDeleteUser(Integer.parseInt(userId));
                 System.out.println("Đã xóa người dùng với ID: " + userId);
              
             } catch (NumberFormatException e) {
                 System.out.println("ID người dùng không hợp lệ: " + userId);
             }
    		// Chuyển hướng lại sau khi xóa để tránh việc lặp lại xóa khi reload
             resp.sendRedirect(req.getContextPath() + "/user-table");
             return;
    	}
        // Gọi service để lấy danh sách người dùng
        List<Member> members = listUserService.findListUser();
        // Đặt danh sách người dùng vào request
        req.setAttribute("members", members);
     // Chuyển tiếp đến JSP để hiển thị dữ liệu
        req.getRequestDispatcher("user-table.jsp").forward(req, resp);
        }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }
    

}