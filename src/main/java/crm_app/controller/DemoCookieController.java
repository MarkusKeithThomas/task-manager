package crm_app.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "demoCookieController", urlPatterns = "/demo-cookie")
public class DemoCookieController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Cookie cookie = new Cookie("demo", URLEncoder.encode("Hello Cookie","UTF-8"));
//		resp.addCookie(cookie);
		Cookie[] cookies = req.getCookies();
		String valueCookie = "";
		for(Cookie item : cookies) {
			String name = item.getName();
			String value = item.getValue();
			if(name.equals("demo")) {
				valueCookie = value;
			}
		}
		System.out.println(valueCookie);
	}

}
