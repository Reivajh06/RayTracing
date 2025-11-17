import reivajh06.Camera;
import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;
import reivajh06.hitables.HitableList;
import reivajh06.hitables.Sphere;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class SampleDiffuseMaterialsTest {

	private static final String RESOURCESDIRECTORYPATH = "resources//";
	private static final Random RANDOM = new Random();

	public static void main(String[] args) {
		Random random = new Random();
		StringBuilder data = new StringBuilder();

		int nx = 200;
		int ny = 100;
		int ns = 100;

		data.append("P3\n%s %s\n255\n\n".formatted(nx, ny));

		HitableList world = new HitableList();
		world.add(new Sphere(new Vector3D(0, 0, -1), 0.5));
		world.add(new Sphere(new Vector3D(0, -100.5, -1), 100));

		Camera camera = new Camera();

		for(int j = ny - 1; j >= 0; j--) {
			for(int i = 0; i < nx; i++) {

				Vector3D col = new Vector3D();

				for(int s = 0; s < ns; s++) {
					double u = (double) (i + random.nextDouble(0, 1)) / (double) nx;
					double v = (double) (j + random.nextDouble(0, 1)) / (double) ny;

					Ray r = camera.getRay(u, v);

					Vector3D p = r.pointAtParameter(2.0);

					col.add(color(r, world));
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
			Files.writeString(Path.of(RESOURCESDIRECTORYPATH + "sample_diffuse_materials.ppm"), data.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Vector3D randomInUnitSphere() {
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

	public static Vector3D color(Ray r, Hitable hitable) {
		Hitable.HitRecord record = new Hitable.HitRecord();

		if(hitable.hit(r, 0.001, Double.MAX_VALUE, record)) {
			Vector3D target = Vector3D.add(Vector3D.add(record.p, record.normal), randomInUnitSphere());

			return Vector3D.scalarProduct(
					color(new Ray(record.p, Vector3D.subtract(target, record.p)), hitable),
					0.5
			);
		}

		Vector3D unitDirection = Vector3D.unitVector(r.direction());

		double t = 0.5 * (unitDirection.y() + 1.0);

		return Vector3D.add(
				Vector3D.scalarProduct(new Vector3D(1.0, 1.0, 1.0), (1.0 - t)),
				Vector3D.scalarProduct(new Vector3D(0.5, 0.7, 1.0), t)
		);
	}
}
