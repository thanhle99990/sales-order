package com.mwg.api.taskman;

public interface TaskListener {

	public void onStart(ATask<?> src);

	public void onFinish(ATask<?> src);
	
}
