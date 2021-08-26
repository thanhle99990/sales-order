package com.mwg.api.taskman;

public abstract class ATask<T> {

	private T result = null;
	private long executionTime = 0l;
	private long startTime = 0l;
	private boolean finish = false;
	private boolean runing = false;

	private Exception error = null;
	private TaskListener listener = null;

	public ATask<T> setListener(TaskListener listener) {
		this.listener = listener;
		return this;
	}

	protected abstract T doTask() throws Exception;

	public long getExecutionTime() {
		return executionTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public Exception getError() {
		return error;
	}

	public T getResult() {
		return result;
	}

	public boolean isFinish() {
		return finish;
	}

	public boolean reset() {
		if (!runing) {
			executionTime = 0l;
			finish = false;
			startTime = 0l;
			error = null;
			result = null;
			return true;
		}
		return false;
	}

	public synchronized boolean start() {
		if (!runing) {
			new Thread(() -> {
				try {
					onTaskStart();
					result = doTask();
				} catch (Exception e) {
					error = e;
				} finally {
					onTaskStop();
				}
			}).start();
			return true;
		}
		return false;
	}

	private void onTaskStart() {
		startTime = System.currentTimeMillis();
		executionTime = 0l;
		error = null;
		result = null;
		runing = true;
		if (listener != null) {
			listener.onStart(ATask.this);
		}
	}

	private void onTaskStop() {
		executionTime = System.currentTimeMillis() - startTime;
		finish = true;
		runing = false;
		if (listener != null) {
			listener.onFinish(ATask.this);
		}
	}
	
}
