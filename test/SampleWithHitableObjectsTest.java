import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;
import reivajh06.hitables.HitableList;
import reivajh06.hitables.Sphere;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class SampleWithHitableObjectsTest {

	private static final String RESOURCESDIRECTORYPATH = "resources//";

	public static void main(String[] args) {
		StringBuilder data = new StringBuilder();

		int nx = 200;
		int ny = 100;

		data.append("P3\n%s %s\n255\n".formatted(nx, ny));

		Vector3D lowerLeftCorner = new Vector3D(-2.0, -1.0, -1.0);
		Vector3D horizontal = new Vector3D(4.0, 0.0, 0.0);
		Vector3D vertical = new Vector3D(0.0, 2.0, 0.0);
		Vector3D origin = new Vector3D();

		HitableList hitableList = new HitableList();
		hitableList.add(new Sphere(new Vector3D(0, 0, -1), 0.5));
		hitableList.add(new Sphere(new Vector3D(0, -100.5, -1), 100));

		Hitable world = hitableList;

		for(int j = ny - 1; j >= 0; j--) {
			for(int i = 0; i < nx; i++) {
				double u = (double) i / (double) nx;
				double v = (double) j / (double) ny;

				Ray r = new Ray(origin, Vector3D.add(
						Vector3D.add(lowerLeftCorner, Vector3D.scalarProduct(horizontal, u)),
						Vector3D.scalarProduct(vertical, v)
				));

				Vector3D p = r.pointAtParameter(2.0);
				Vector3D color = color(r, world);

				int ir = (int) (255.99 * color.r());
				int ig = (int) (255.99 * color.g());
				int ib = (int) (255.99 * color.b());

				data.append("%s %s %s\n".formatted(ir, ig, ib));
			}
		}

		try {
			Files.writeString(Path.of(RESOURCESDIRECTORYPATH + "sampleWithHitableList.ppm"), data.toString(), CREATE, TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Vector3D color(Ray r, Hitable hitable) {
		Hitable.HitRecord record = new Hitable.HitRecord();

		if(hitable.hit(r, 0.0, Double.MAX_VALUE, record)) {
			return Vector3D.scalarProduct(
					new Vector3D(
							record.normal.x() + 1,
							record.normal.y() + 1,
							record.normal.z() + 1
					),
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

