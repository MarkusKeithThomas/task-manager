package crm_app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.service.StatusTaskService;

@WebServlet(name = "indexSeverlet",urlPatterns = "/index")
public class IndexSeverlet extends HttpServlet{
	StatusTaskService statusTaskService = new StatusTaskService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> listStringStatusper = statusTaskService.getAllManStatusper();
		List<String> listStringStatusCount = statusTaskService.getAllManStatusCount();
		if (listStringStatusper.size()>0) {
			
			req.setAttribute("chuabatdauper", listStringStatusper.get(0));
			req.setAttribute("dangthuchienper", listStringStatusper.get(1));
			req.setAttribute("hoanthanhper", listStringStatusper.get(2));
			
			req.setAttribute("chuabatdau", listStringStatusCount.get(0));
			req.setAttribute("dangthuchien", listStringStatusCount.get(1));
			req.setAttribute("hoanthanh", listStringStatusCount.get(2));
			


			

		} else {
		    req.setAttribute("chuabatdau", "0"); // Gán mặc định nếu không có dữ liệu
		    req.setAttribute("dangthuchien", "0");
		    req.setAttribute("hoanthanh", "0");
			req.setAttribute("error", "Không tìm thấy danh sach Status.");

		}
		
		// Chuyển tiếp đến trang JSP
		req.getRequestDispatcher("/index.jsp").forward(req, resp);

	}

}
