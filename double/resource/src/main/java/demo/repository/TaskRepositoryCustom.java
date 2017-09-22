package demo.repository;

import demo.domain.Task.Status;

public interface TaskRepositoryCustom {

	public int updateStatus(String taskId, Status status);
}
