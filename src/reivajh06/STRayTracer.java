package reivajh06;

import reivajh06.hitables.Hitable;

import java.util.Random;

public class STRayTracer implements RayTracer {

	public static final Random RANDOM = new Random();

	@Override
	public void run(Scene scene, Renderer renderer, ImageConfig config) {
		int width = config.width();
		int height = config.height();
		int samplesPerPixel = config.samplesPerPixel();

		Camera camera = scene.camera();
		Hitable world = scene.world();

		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {

				Vector3D col = new Vector3D();

				for(int s = 0; s < samplesPerPixel; s++) {
					double u = (double) (i + RANDOM.nextDouble(0, 1)) / (double) width;
					double v = (double) (j + RANDOM.nextDouble(0, 1)) / (double) height;

					Ray r = camera.getRay(u, v);

					col.add(color(r, world, 0));
				}

				col.divide(samplesPerPixel);
				col = new Vector3D(Math.sqrt(col.r()), Math.sqrt(col.g()), Math.sqrt(col.b()));

				int ir = (int) (255.99 * col.r());
				int ig = (int) (255.99 * col.g());
				int ib = (int) (255.99 * col.b());

				int rgb = ((ir & 0xFF) << 16) |
						((ig & 0xFF) << 8)  |
						((ib & 0xFF));

				renderer.render(i, j, rgb);
			}
		}
	}
}
