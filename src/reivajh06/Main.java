package reivajh06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {
		StringBuilder data = new StringBuilder();

		int nx = 200;
		int ny = 100;

		data.append("P3\n%s %s\n255\n".formatted(nx, ny));

		Vector3D lowerLeftCorner = new Vector3D(-2.0, -1.0, -1.0);
		Vector3D horizontal = new Vector3D(4.0, 0.0, 0.0);
		Vector3D vertical = new Vector3D(0.0, 2.0, 0.0);
		Vector3D origin = new Vector3D();

		for(int j = ny - 1; j >= 0; j--) {
			for(int i = 0; i < nx; i++) {
				double u = (double) i / (double) nx;
				double v = (double) j / (double) ny;

				Vector3D vector = Vector3D.add(lowerLeftCorner, Vector3D.scalarProduct(horizontal, u));

				Ray r = new Ray(origin, Vector3D.add(vector, Vector3D.scalarProduct(vertical, v)));

				Vector3D color = color(r);

				int ir = (int) (255.99 * color.r());
				int ig = (int) (255.99 * color.g());
				int ib = (int) (255.99 * color.b());

				data.append("%s %s %s\n".formatted(ir, ig, ib));
			}
		}

		try {
			Files.writeString(Path.of("sample_image2.ppm"), data.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void sampleImage() {
		StringBuilder data = new StringBuilder();
		int nx = 200;
		int ny = 100;

		data.append("P3\n%s %s\n255\n".formatted(nx, ny));

		for(int j = ny - 1; j >= 0; j--) {
			for(int i = 0; i < nx; i++) {
				Vector3D col = new Vector3D(
						(double) i / (double) nx,
						(double) j / (double) ny,
						0.2);

				int ir =  (int) (255.99 * col.r());
				int ig = (int) (255.99* col.g());
				int ib = (int) (255.99 * col.b());

				data.append("%s %s %s\n".formatted(ir, ig, ib));
			}
		}

		try {
			Files.writeString(Path.of("sample_image.ppm"), data.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Vector3D color(Ray r) {
		if(hitSphere(new Vector3D(0, 0, -1), 0.5, r)) {
			return new Vector3D(1, 0, 0);
		}

		Vector3D unitDirection = Vector3D.unitVector(r.direction());
		double t = 0.5 * (unitDirection.y() + 1.0);

		return Vector3D.add(
				Vector3D.scalarProduct(new Vector3D(1.0, 1.0, 1.0), (1.0 - t)),
				Vector3D.scalarProduct(new Vector3D(0.5, 0.7, 1.0), t)
		);
	}


	private static boolean hitSphere(Vector3D center, double radius, Ray r) {
		Vector3D oc = Vector3D.subtract(r.origin(), center);

		double a = Vector3D.dot(r.direction(), r.direction());
		double b = 2.0 * Vector3D.dot(oc, r.direction());
		double c = Vector3D.dot(oc, oc) - radius * radius;
		double discriminant = b * b - 4 * a * c;

		return (discriminant > 0);
	}
}
