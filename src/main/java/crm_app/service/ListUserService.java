package crm_app.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import crm_app.config.Member;
import crm_app.repository.ListUserRepository;

public class ListUserService {
	
private ListUserRepository listUserRepository = new ListUserRepository();

	public List<Member> findListUser() {
		return listUserRepository.findAllMember();
	}
	public boolean findDeleteUser(int userId) {
		 int count = listUserRepository.deleteById(userId);
		 return count>0;
	}
	
	public int insertMember(String email,String password,String fullName,int roldID, String phone, String image ) {
		return listUserRepository.insert(email, password, fullName, roldID,phone, image);
	}
	
	public boolean checkingFilling(String email, String password, String fullName, String phone) {
	    if (email != null && !email.trim().isEmpty() &&
	        password != null && !password.trim().isEmpty() &&
	        fullName != null && !fullName.trim().isEmpty() &&
	        phone != null && !phone.trim().isEmpty()) {
	        return true;
	    }
	    return false;
	}
	public Member findOneMember(HttpServletRequest request) {
		Member member = null;
        String emailFromCookie = null;
        
		
		Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    emailFromCookie = cookie.getValue();
                    break;
                }
            }
        }

        // Kiểm tra nếu không tìm thấy email trong cookie
        if (emailFromCookie == null) {
            System.out.println("Không tìm thấy email trong cookie.");
            return null; // Trả về null hoặc xử lý trường hợp không tìm thấy email trong cookie
        } else {
        	member = listUserRepository.findOneMember(emailFromCookie);
    		return member;
		}
	}
	public Member findOneMemberByEmail(String email) {
		Member member = null;

        // Kiểm tra nếu không tìm thấy email trong cookie
        if (email == null) {
            System.out.println("Không tìm thấy email trong cookie.");
            return null; // Trả về null hoặc xử lý trường hợp không tìm thấy email trong cookie
        } else {
        	member = listUserRepository.findOneMember(email);
    		return member;
		}
	}
}
