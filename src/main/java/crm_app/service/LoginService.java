package crm_app.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Users;
import crm_app.repository.LoginRepository;

public class LoginService {
	
	private LoginRepository loginRepository = new LoginRepository();
	
	public boolean findLogin(String email, String password) {
		Users users = loginRepository.findLogin(email, password);

		if (users.getEmail() != null && users.getPassword() != null) {
			
            System.out.println("true");
			return true;

		} else {
            System.out.println("false");
			return false;
		}
	}
	public boolean getLogOut( HttpServletResponse resp,HttpServletRequest req) {
	    boolean resultSignOut = false;
        String contextPath = req.getContextPath(); // Ví dụ: /crm_app07buoi30


	    try {
	        // Xóa cookie email và password
	        Cookie cookieEmail = new Cookie("email", null);
	        cookieEmail.setMaxAge(0); // Xóa ngay lập tức
	        cookieEmail.setPath(contextPath);

	        Cookie cookiePassword = new Cookie("password", null);
	        cookiePassword.setMaxAge(0); // Xóa ngay lập tức
	        cookiePassword.setPath(contextPath);
	        
	        Cookie cookieRole = new Cookie("role", null);
	        cookieRole.setMaxAge(0);
	        cookieRole.setPath(contextPath);
	        
	        
	        resp.addCookie(cookieRole);
	        resp.addCookie(cookieEmail);
	        resp.addCookie(cookiePassword);
	        resultSignOut = true; // Đăng xuất thành công
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return resultSignOut;
	}
	public boolean getExistedUserByEmail(String email) {
		return loginRepository.findExistByEmail(email);
		
	}


}
