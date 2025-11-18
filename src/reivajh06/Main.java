package reivajh06;

import reivajh06.hitables.Hitable;
import reivajh06.hitables.HitableList;
import reivajh06.hitables.Sphere;
import reivajh06.materials.Dielectric;
import reivajh06.materials.Lambertian;
import reivajh06.materials.Metal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Main {

	private static final String RESOURCESDIRECTORYPATH = "resources//";
	public static final Random RANDOM = new Random();

	public static void main(String[] args) {
		Random random = new Random();
		StringBuilder data = new StringBuilder();

		int nx = 1280;
		int ny = 720;
		int ns = 200;

		data.append("P3\n%s %s\n255\n\n".formatted(nx, ny));

		Hitable world = randomScene();

		Vector3D lookfrom = new Vector3D(13, 2, 3);
		Vector3D lookat = new Vector3D(0, 0, 0);
		Vector3D vup = new Vector3D(0, 1, 0);

		double distToFocus = 10.0;
		double aperture    = 0.1;

		Camera camera = new Camera(
				lookfrom,
				lookat,
				vup,
				20,
				(double) nx / (double) ny,
				aperture,
				distToFocus
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
			Files.writeString(Path.of(RESOURCESDIRECTORYPATH + "cover.ppm"), data.toString());
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

	public static Hitable randomScene() {
		int n = 500;

		HitableList list = new HitableList();

		list.add(new Sphere(new Vector3D(0, -1000, 0), 1000, new Lambertian(new Vector3D(0.5, 0.5, 0.5))));

		for(int a = -11; a < 11; a++) {
			for(int b = -11; b < 11; b++) {
				double chooseMat = RANDOM.nextDouble(0, 1);

				Vector3D center = new Vector3D(a + 0.9 * RANDOM.nextDouble(0, 1), 0.2, b + 0.9 * RANDOM.nextDouble(0, 1));

				if(Vector3D.subtract(center, new Vector3D(4, 0.2, 0)).length() > 0.9) {
					if(chooseMat < 0.8) { //diffuse
						Lambertian lambertian = new Lambertian(new Vector3D(
								RANDOM.nextDouble(0, 1) * RANDOM.nextDouble(0, 1),
								RANDOM.nextDouble(0, 1) * RANDOM.nextDouble(0, 1),
								RANDOM.nextDouble(0, 1) * RANDOM.nextDouble(0, 1)));

						list.add(new Sphere(center, 0.2, lambertian));

					} else if(chooseMat < 0.95) { //metal
						Metal metal = new Metal(new Vector3D(
								0.5 * (1 + RANDOM.nextDouble(0, 1)),
								0.5 * (1 + RANDOM.nextDouble(0, 1)),
								0.5 * (1 + RANDOM.nextDouble(0, 1))),
								0.5 * RANDOM.nextDouble(0, 1));

						list.add(new Sphere(center, 0.2, metal));

					} else { // glass
						list.add(new Sphere(center, 0.2, new Dielectric(1.5)));
					}
				}
			}
		}

		list.add(new Sphere(new Vector3D(0, 1, 0), 1.0, new Dielectric(1.5)));
		list.add(new Sphere(new Vector3D(-4, 1, 0), 1.0, new Lambertian(new Vector3D(0.4, 0.2, 0.1))));
		list.add(new Sphere(new Vector3D(4, 1, 0), 1.0, new Metal(new Vector3D(0.7, 0.6, 0.5), 0.0)));

		return list;
	}
}
