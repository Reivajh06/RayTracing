package reivajh06.multithreading;

import reivajh06.*;
import reivajh06.hitables.Hitable;

public class ThreadPoolMTRayTracer implements RayTracer {

	@Override
	public void run(Scene scene, Renderer renderer, ImageConfig config) {
		int width = config.width();
		int height = config.height();
		int samplesPerPixel = config.samplesPerPixel();

		Camera camera = scene.camera();
		Hitable world = scene.world();

		ThreadPool threadPool = new ThreadPool(15);

		for(int j = 0; j < height; j++) {
			int y = j;

			threadPool.submit(() -> renderRow(renderer, width, samplesPerPixel, y, height, camera, world));
		}

		threadPool.shutdown();
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
