package reivajh06;

import reivajh06.hitables.Hitable;

public class Main {

	public static void main(String[] args) {

	}

	public static Vector3D color(Ray r, Hitable hitable) {
		Hitable.HitRecord record;

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

		return null;
	}
}
