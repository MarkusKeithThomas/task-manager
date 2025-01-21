package crm_app.controller;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.service.LoginService;
import crm_app.service.RoleTableService;



@WebServlet(name = "loginController",urlPatterns = "/login")
public class LoginController extends HttpServlet{
	LoginService loginService = new LoginService();
	RoleTableService roleTableService = new RoleTableService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		String valueEmail = "";
		String valuePassword = "";
		boolean rememberme = false;
		String  signOut = req.getParameter("signOut");
		
		if ("signOut".equals(signOut)) {
	        // Thực hiện đăng xuất và chuyển hướng
	        boolean isLogout = loginService.getLogOut(resp,req);
	        System.out.print(isLogout);
	        resp.sendRedirect(req.getContextPath() + "/login"); // Chuyển hướng sau khi đăng xuất
	        return; // Chặn xử lý tiếp
	    }
		if (cookies != null) {		
			for(Cookie item : cookies) {
				String name = item.getName();
				String value = item.getValue();
				if(name.equals("email")) {
					valueEmail = value;
					rememberme = true;
				}
				if (name.equals("password")) {
					String decodedPassword = new String(Base64.getDecoder().decode(value));
					valuePassword = decodedPassword;
				}
				req.setAttribute("email", valueEmail);
				req.setAttribute("password", valuePassword);
			}
			 }
		
		
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String  email = req.getParameter("email");
		String  password = req.getParameter("password");

		String  passwordMaHoa = Base64.getEncoder().encodeToString(req.getParameter("password").getBytes()); // Mã hóa
		// Lấy giá trị checkbox "rememberme"
	    String rememberMe = req.getParameter("rememberme");
		
		boolean isCheck = loginService.findLogin(email, password);
		if(isCheck) {
			String  role = roleTableService.getRoleByEmail(email);
			if (role != null) {
				Cookie cookieRole = new Cookie("role", role);
				resp.addCookie(cookieRole);
				cookieRole.setMaxAge(60 * 60 * 24 * 7);
			}
			
			
			if ("true".equals(rememberMe)) {
				Cookie cookieEmail = new Cookie("email", email);
				Cookie cookiepassword = new Cookie("password",passwordMaHoa);
				Base64.getEncoder().encodeToString(password.getBytes()); // Mã hóa
				resp.addCookie(cookieEmail);
				resp.addCookie(cookiepassword);
                cookieEmail.setMaxAge(60 * 60 * 24 * 7); // Lưu cookie trong 7 ngày
                cookiepassword.setMaxAge(60 * 60 * 24 * 7); // Lưu cookie trong 7 ngày

			} else {
                Cookie cookiePass = new Cookie("password", null);
                cookiePass.setMaxAge(0); // Đặt thời gian sống bằng 0 để xóa cookie
                resp.addCookie(cookiePass);
			}
			// Điều hướng người dùng dựa trên vai trò
	        if ("ROLE_ADMIN".equals(role)) {
	            resp.sendRedirect(req.getContextPath() + "/index"); // Trang dành cho Admin
	        } else if ("ROLE_MANAGER".equals(role)) {
	            resp.sendRedirect(req.getContextPath() + "/groupwork"); // Trang dành cho Leader
	        } else if ("ROLE_USER".equals(role)) {
	            resp.sendRedirect(req.getContextPath() + "/profile"); // Trang dành cho Member
	        } else {
	            // Vai trò không hợp lệ
	            req.setAttribute("loginError", "Vai trò không hợp lệ.");
	            req.getRequestDispatcher("login.jsp").forward(req, resp);
	        }
		} else {
            req.setAttribute("loginError", "Tài khoản hoặc mật khẩu không đúng.");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}

		
		

	}

	
}
