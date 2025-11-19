package reivajh06.multithreading;

public class ThreadPool {

	private final RenderThread[] threads;
	private int index;

	public ThreadPool(int numThreads) {
		threads = new RenderThread[numThreads];

		for(int i = 0; i < threads.length; i++) {
			threads[i] = new RenderThread();
			threads[i].start();
		}
	}

	public void submit(Runnable task) {
		threads[index++ % threads.length].submit(task);
	}

	public void shutdown() {
		for(RenderThread t : threads) {
			try {
				t.shutdown();
				t.join();
			} catch (InterruptedException ignored) {
			}
		}
	}
}
