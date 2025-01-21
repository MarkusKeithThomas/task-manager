package crm_app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import crm_app.config.Status;
import crm_app.repository.StatusTaskRepository;

public class StatusTaskService {
	StatusTaskRepository statusTaskRepository = new StatusTaskRepository();
	
	public List<Status> getAllStatus() {
		return statusTaskRepository.findAllStatus();		
	}

	public List<String> getOneManStatus(HttpServletRequest request) {
	    String emailFromCookie = null;
	    // Khởi tạo danh sách với 3 phần tử mặc định là "0"
	    List<String> list = new ArrayList<>(Arrays.asList("0", "0", "0"));

	    // Lấy email từ cookie
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("email".equals(cookie.getName())) {
	                emailFromCookie = cookie.getValue();
	                break;
	            }
	        }
	    }

	    // Lấy danh sách trạng thái và tổng số công việc
	    List<Status> listStatus = statusTaskRepository.findCountStatus(emailFromCookie);
	    int countTotal = statusTaskRepository.findCountAllTaskOne(emailFromCookie);

	    // Kiểm tra nếu tổng số công việc = 0 để tránh lỗi chia cho 0
	    if (countTotal == 0) {
	        return list; // Trả về danh sách mặc định ["0", "0", "0"]
	    }

	    for (Status status : listStatus) {
	        double temp;
	        switch (status.getIdStatus()) {
	            case "1": // Chưa bắt đầu
	                temp = (int)((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(0, String.valueOf(temp));
	                break;
	            case "3": // Đang thực hiện
	                temp = (int) ((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(1, String.valueOf(temp));
	                break;
	            case "5": // Hoàn thành
	                temp = (int) ((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(2, String.valueOf(temp));
	                break;
	            default:
	                // Nếu trạng thái không nằm trong các giá trị cần xử lý, bỏ qua
	                break;
	        }
	    }

	    return list;
	}
	public List<String> getOneManStatusByEmail(String email) {
	    // Khởi tạo danh sách với 3 phần tử mặc định là "0"
	    List<String> list = new ArrayList<>(Arrays.asList("0", "0", "0"));

	    
	    // Lấy danh sách trạng thái và tổng số công việc
	    List<Status> listStatus = statusTaskRepository.findCountStatus(email);
	    int countTotal = statusTaskRepository.findCountAllTaskOne(email);

	    // Kiểm tra nếu tổng số công việc = 0 để tránh lỗi chia cho 0
	    if (countTotal == 0) {
	        return list; // Trả về danh sách mặc định ["0", "0", "0"]
	    }

	    for (Status status : listStatus) {
	        double temp;
	        switch (status.getIdStatus()) {
	            case "1": // Chưa bắt đầu
	                temp = (int)((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(0, String.valueOf(temp));
	                break;
	            case "3": // Đang thực hiện
	                temp = (int) ((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(1, String.valueOf(temp));
	                break;
	            case "5": // Hoàn thành
	                temp = (int) ((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(2, String.valueOf(temp));
	                break;
	            default:
	                // Nếu trạng thái không nằm trong các giá trị cần xử lý, bỏ qua
	                break;
	        }
	    }

	    return list;
	}
	public List<String> getAllManStatusper() {
	    
	    List<String> list = new ArrayList<>(Arrays.asList("0", "0", "0"));

	    // Lấy danh sách trạng thái và tổng số công việc
	    List<Status> listStatus = statusTaskRepository.findCountStatuAllMember();
	    int countTotal = statusTaskRepository.findCountTotalAllTask();

	    // Kiểm tra nếu tổng số công việc = 0 để tránh lỗi chia cho 0
	    if (countTotal == 0) {
	        return list; // Trả về danh sách mặc định ["0", "0", "0"]
	    }

	    for (Status status : listStatus) {
	        double temp;
	        switch (status.getIdStatus()) {
	            case "1": // Chưa bắt đầu
	                temp = (int)((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(0, String.valueOf(temp));
	            case "3": // Đang thực hiện
	                temp = (int) ((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(1, String.valueOf(temp));

	            case "5": // Hoàn thành
	                temp = (int) ((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
	                list.set(2, String.valueOf(temp));

	            default:
	                // Nếu trạng thái không nằm trong các giá trị cần xử lý, bỏ qua
	                break;
	        }
	    }

	    return list;
	}
public List<String> getAllManStatusCount() {
	    
	    List<String> list = new ArrayList<>(Arrays.asList("0", "0", "0"));

	    // Lấy danh sách trạng thái và tổng số công việc
	    List<Status> listStatus = statusTaskRepository.findCountStatuAllMember();
	    int countTotal = statusTaskRepository.findCountTotalAllTask();

	    // Kiểm tra nếu tổng số công việc = 0 để tránh lỗi chia cho 0
	    if (countTotal == 0) {
	        return list; // Trả về danh sách mặc định ["0", "0", "0"]
	    }

	    for (Status status : listStatus) {
	        double temp;
	        switch (status.getIdStatus()) {
	            case "1": // Chưa bắt đầu
	                temp = (int)(Double.parseDouble(status.getCountStatus()));
	                list.set(0, String.valueOf(temp));
	            case "3": // Đang thực hiện
	                temp = (int) (Double.parseDouble(status.getCountStatus()));
	                list.set(1, String.valueOf(temp));

	            case "5": // Hoàn thành
	                temp = (int) (Double.parseDouble(status.getCountStatus()));
	                list.set(2, String.valueOf(temp));

	            default:
	                // Nếu trạng thái không nằm trong các giá trị cần xử lý, bỏ qua
	                break;
	        }
	    }

	    return list;
	}
public List<String> getOneJobStatusByjobId(int jobId) {
    // Khởi tạo danh sách với 3 phần tử mặc định là "0"
    List<String> list = new ArrayList<>(Arrays.asList("0", "0", "0"));

    
    // Lấy danh sách trạng thái và tổng số công việc
    List<Status> listStatus = statusTaskRepository.findCountStatuOneJobByJobId(jobId);
    int countTotal = statusTaskRepository.findCountAllTaskByJobId(jobId);

    // Kiểm tra nếu tổng số công việc = 0 để tránh lỗi chia cho 0
    if (countTotal == 0) {
        return list; // Trả về danh sách mặc định ["0", "0", "0"]
    }

    for (Status status : listStatus) {
        double temp;
        switch (status.getIdStatus()) {
            case "1": // Chưa bắt đầu
                temp = (int)((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
                list.set(0, String.valueOf(temp));
                break;
            case "3": // Đang thực hiện
                temp = (int) ((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
                list.set(1, String.valueOf(temp));
                break;
            case "5": // Hoàn thành
                temp = (int) ((Double.parseDouble(status.getCountStatus()) / countTotal) * 100);
                list.set(2, String.valueOf(temp));
                break;
            default:
                // Nếu trạng thái không nằm trong các giá trị cần xử lý, bỏ qua
                break;
        }
    }

    return list;
}
	

}
