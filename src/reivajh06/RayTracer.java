package reivajh06;

import reivajh06.hitables.Hitable;
import reivajh06.hitables.HitableList;
import reivajh06.hitables.Sphere;
import reivajh06.materials.Dielectric;
import reivajh06.materials.Lambertian;
import reivajh06.materials.Metal;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class RayTracer {

	public static final Random RANDOM = new Random();

	public void run(Scene scene, Renderer renderer, ImageConfig config) {
		int width = config.width();
		int height = config.height();
		int samplesPerPixel = config.samplesPerPixel();

		Camera camera = scene.camera();
		Hitable world = scene.world();

		for(int j = height - 1; j >= 0; j--) {
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

	public Vector3D color(Ray r, Hitable hitable, int depth) {
		Hitable.HitRecord record = new Hitable.HitRecord();

		if(hitable.hit(r, 0.001, Double.MAX_VALUE, record)) {
			Ray scattered = new Ray();
			Vector3D attenuation = new Vector3D();

			if(depth < 50 && record.material.scatter(r, record, attenuation, scattered)) {
				return Vector3D.multiply(attenuation, color(scattered, hitable, depth + 1));
			}
		}

		Vector3D unitDirection = Vector3D.unitVector(r.direction());

		double t = 0.5 * (unitDirection.y() + 1.0);

		return Vector3D.add(
				Vector3D.scalarProduct(new Vector3D(1.0, 1.0, 1.0), (1.0 - t)),
				Vector3D.scalarProduct(new Vector3D(0.5, 0.7, 1.0), t)
		);
	}
}
