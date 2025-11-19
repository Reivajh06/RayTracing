package reivajh06.multithreading;

import reivajh06.*;
import reivajh06.hitables.Hitable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreMTRayTracer implements RayTracer {

	@Override
	public void run(Scene scene, Renderer renderer, ImageConfig config) {
		int width = config.width();
		int height = config.height();
		int samplesPerPixel = config.samplesPerPixel();


		int numCores = Runtime.getRuntime().availableProcessors();

		Semaphore semaphore = new Semaphore(numCores - 1);

		Camera camera = scene.camera();
		Hitable world = scene.world();

		List<Thread> threads = new ArrayList<>();

		for(int j = 0; j < height; j++) {
			int y = j;

			Thread thread = new Thread(() -> {
				try {
					semaphore.acquire();

					renderRow(renderer, width, samplesPerPixel, y, (double) height, camera, world);

				} catch (InterruptedException e) {
					throw new RuntimeException(e);

				} finally {
					semaphore.release();
				}
			});

			thread.start();

			threads.add(thread);
		}

		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException ignored) {
			}
		}
	}

	private void renderRow(Renderer renderer, int width, int samplesPerPixel, int y, double height, Camera camera, Hitable world) {
		for (int i = 0; i < width; i++) {
			Vector3D col = new Vector3D();

			for (int s = 0; s < samplesPerPixel; s++) {
				double u = (double) (i + Math.random()) / (double) width;
				double v = (double) (y + Math.random()) / height;

				Ray r = camera.getRay(u, v);

				col.add(color(r, world, 0));
			}

			col.divide(samplesPerPixel);
			col = new Vector3D(Math.sqrt(col.r()), Math.sqrt(col.g()), Math.sqrt(col.b()));

			int ir = (int) (255.99 * col.r());
			int ig = (int) (255.99 * col.g());
			int ib = (int) (255.99 * col.b());

			int rgb = ((ir & 0xFF) << 16) |
					((ig & 0xFF) << 8) |
					((ib & 0xFF));

			renderer.render(i, y, rgb);
		}
	}
}
