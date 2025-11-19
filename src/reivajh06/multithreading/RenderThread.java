package reivajh06.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class RenderThread extends Thread {

	private final BlockingQueue<Runnable> tasks = new LinkedBlockingDeque<>();
	private volatile boolean terminating = false;

	public void submit(Runnable task) {
		tasks.add(task);
	}

	@Override
	public void run() {
		while(!terminating || !tasks.isEmpty()) {

			Runnable task = nextTask();

			if(task == null) continue;

			task.run();
		}
	}

	private Runnable nextTask() {
		try {
			return tasks.poll(5, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {
		}

		return null;
	}

	public void shutdown() {
		terminating = true;
	}
}
