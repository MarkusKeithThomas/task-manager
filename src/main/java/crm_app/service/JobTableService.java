package crm_app.service;

import java.sql.Date;
import java.util.List;

import crm_app.config.Job;
import crm_app.repository.JobTableRepository;

public class JobTableService {
	JobTableRepository  jobRepository = new JobTableRepository();
	public List<Job> getAllJobs() {
		return jobRepository.findAllJob();
	}
	public int deleteJob(int idJob) {
		return jobRepository.deleteJob(idJob);
	}
	public boolean checkingFilling(String nameJob,String dateStart,String dateEnd) {
		if (nameJob!=null && !nameJob.isEmpty()
				&& dateStart != null && dateStart != null ) {
			return true;
		}
		return false;
	}
	public int insertJob(String nameJob,Date dateStart,Date dateEnd) {
		return jobRepository.insertJob(nameJob, dateStart, dateEnd);
	}

}
