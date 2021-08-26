package com.mwg.api.taskman;

import java.util.ArrayList;
import java.util.List;

public class TaskMan {
	
	private List<ATask<?>> taskList = new ArrayList<>();

	public TaskMan addTask(ATask<?> task) {
		taskList.add(task);
		return this;
	}

	public List<ATask<?>> getTaskList() {
		return taskList;
	}

	public long runTasksAndWait() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		taskList.forEach((task) -> task.start());
		while (!isFinishAll()) {
			Thread.sleep(10L);
		}
		return System.currentTimeMillis() - startTime;
	}

	private boolean isFinishAll() {
		for (ATask<?> task : taskList) {
			if (!task.isFinish()) {
				return false;
			}
		}
		return true;
	}

	public long getTotalExecutionTime() {
		long total = 0;
		for (ATask<?> task : taskList) {
			total += task.getExecutionTime();
		}
		return total;
	}

}
