package reivajh06;


public class Vector3D {

	public static Vector3D add(Vector3D v1, Vector3D v2) {
		return new Vector3D(
				v1.x() + v2.x(),
				v1.y() + v2.y(),
				v1.z() + v2.z()
		);
	}

	public static Vector3D subtract(Vector3D v1, Vector3D v2) {
		return new Vector3D(
				v1.x() - v2.x(),
				v1.y() - v2.y(),
				v1.z() - v2.z()
		);
	}

	public static double dot(Vector3D v1, Vector3D v2) {
		return v1.x() * v2.x() + v1.y() * v2.y() + v1.z() * v2.z();
	}

	public static Vector3D cross(Vector3D v1, Vector3D v2) {
		return new Vector3D(
				v1.y() * v2.z() - v1.z() * v2.y(),
				(-(v1.x() * v2.z() - v1.z() * v2.x())),
				v1.x() * v2.y() - v1.y() * v2.x()
		);
	}

	public static Vector3D scalarProduct(Vector3D v, double scalar) {
		return new Vector3D(
				v.x() * scalar,
				v.y() * scalar,
				v.z() * scalar
		);
	}

	public static Vector3D divide(Vector3D v1, Vector3D v2) {
		return new Vector3D(
				v1.x() / v2.x(),
				v2.y() / v2.y(),
				v1.z() / v2.z()
		);

	}

	public static Vector3D divide(Vector3D v, double scalar) {
		return new Vector3D(
				v.x() / scalar,
				v.y() / scalar,
				v.z() / scalar
		);
	}

	public static Vector3D unitVector(Vector3D v) {
		return divide(v, v.length());
	}

	private final double[] coordinates = new double[3];

	public Vector3D(double x, double y, double z) {
		coordinates[0] = x;
		coordinates[1] = y;
		coordinates[2] = z;
	}

	public Vector3D() {}

	public double x() {
		return coordinates[0];
	}

	public double y() {
		return coordinates[1];
	}

	public double z() {
		return coordinates[2];
	}

	public double r() {
		return coordinates[0];
	}

	public double g() {
		return coordinates[1];
	}

	public double b() {
		return coordinates[2];
	}

	public double length() {
		return Math.sqrt(squaredLength());
	}

	public double squaredLength() {
		return x() * x() + y() * y() + z() * z();
	}

	public void add(Vector3D v) {
		coordinates[0] += v.x();
		coordinates[1] += v.y();
		coordinates[2] += v.z();
	}

	public void subtract(Vector3D v) {
		coordinates[0] -= v.x();
		coordinates[1] -= v.y();
		coordinates[2] -= v.z();
	}

	public void divide(Vector3D v) {
		coordinates[0] /= v.x();
		coordinates[1] /= v.y();
		coordinates[2] /= v.z();
	}

	public void divide(double scalar) {
		coordinates[0] /= scalar;
		coordinates[1] /= scalar;
		coordinates[2] /= scalar;
	}

	public void scalarProduct(double scalar) {
		coordinates[0] *= scalar;
		coordinates[1] *= scalar;
		coordinates[2] *= scalar;
	}

	@Override
	public String toString() {
		return "%s, %s, %s".formatted(x(), y(), z());
	}
}
