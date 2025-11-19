import reivajh06.Camera;
import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;
import reivajh06.hitables.HitableList;
import reivajh06.hitables.Sphere;
import reivajh06.materials.Lambertian;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class SamplePositionableCamera {

	private static final String RESOURCESDIRECTORYPATH = "RayTracing/resources/";
	public static final Random RANDOM = new Random();

	public static void main(String[] args) {
		Random random = new Random();
		StringBuilder data = new StringBuilder();

		int nx = 200;
		int ny = 100;
		int ns = 100;

		data.append("P3\n%s %s\n255\n\n".formatted(nx, ny));

		double R = Math.cos(Math.PI / 4);

		HitableList world = new HitableList();
		world.add(new Sphere(new Vector3D(-R, 0, -1), R, new Lambertian(new Vector3D(0, 0, 1))));
		world.add(new Sphere(new Vector3D(R, 0, -1), R, new Lambertian(new Vector3D(1, 0, 0))));

		Camera camera = new Camera(
				new Vector3D(-2, 2, 1),
				new Vector3D(0, 0, -1),
				new Vector3D(0, 1, 0),
				40,
				(double) nx / (double) ny

		);

		for(int j = ny - 1; j >= 0; j--) {
			for(int i = 0; i < nx; i++) {

				Vector3D col = new Vector3D();

				for(int s = 0; s < ns; s++) {
					double u = (double) (i + random.nextDouble(0, 1)) / (double) nx;
					double v = (double) (j + random.nextDouble(0, 1)) / (double) ny;

					Ray r = camera.getRay(u, v);

					Vector3D p = r.pointAtParameter(2.0);

					col.add(color(r, world, 0));
				}

				col.divide(ns);
				col = new Vector3D(Math.sqrt(col.r()), Math.sqrt(col.g()), Math.sqrt(col.b()));

				int ir = (int) (255.99 * col.r());
				int ig = (int) (255.99 * col.g());
				int ib = (int) (255.99 * col.b());

				data.append("%s %s %s\n".formatted(ir, ig, ib));
			}
		}

		try {
			File file = new File(RESOURCESDIRECTORYPATH, "sample_positionable_camera.ppm");
			file.getParentFile().mkdirs();
			Files.writeString(file.toPath(), data.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Vector3D randomInUnitSphere() {
		Vector3D p;

		do {
			p = Vector3D.subtract(
					Vector3D.scalarProduct(
							new Vector3D(RANDOM.nextDouble(0, 1), RANDOM.nextDouble(0, 1), RANDOM.nextDouble(0, 1)),
							2.0),
					new Vector3D(1, 1, 1)

			);

		} while(p.squaredLength() >= 1.0);

		return p;
	}

	public static Vector3D color(Ray r, Hitable hitable, int depth) {
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
