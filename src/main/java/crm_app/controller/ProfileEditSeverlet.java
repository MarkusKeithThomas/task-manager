package crm_app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Status;
import crm_app.config.TaskInfor;
import crm_app.service.StatusTaskService;
import crm_app.service.TaskInforService;

@WebServlet(name = "profileEditSeverlet", urlPatterns = "/profile-edit")
public class ProfileEditSeverlet extends HttpServlet {

    private final StatusTaskService statusTaskService = new StatusTaskService();
    private final TaskInforService taskInforService = new TaskInforService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idTask = req.getParameter("idTask");

        if (idTask == null || idTask.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/profile");
            return;
        }

        try {
            int idTaskInt = Integer.parseInt(idTask);
            TaskInfor taskInfor = taskInforService.getOneTaskById(idTaskInt);
            List<Status> statusList = statusTaskService.getAllStatus();

            if (taskInfor != null) {
                req.setAttribute("taskInfor", taskInfor);
                req.setAttribute("status", statusList);
                req.getRequestDispatcher("/profile-edit.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/profile");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idTask = req.getParameter("idTask");
        String status = req.getParameter("status");

        if (idTask == null || idTask.isEmpty() || status == null || status.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/profile");
            return;
        }

        try {
            int idTaskInt = Integer.parseInt(idTask);
            int statusIdInt = Integer.parseInt(status);

            int rowsUpdated = taskInforService.updateOneTaskById(idTaskInt, statusIdInt);

            if (rowsUpdated > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không có bản ghi nào được cập nhật.");
            }

            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (NumberFormatException e) {
            System.out.println("Dữ liệu không hợp lệ: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/profile");
        }
    }
}
