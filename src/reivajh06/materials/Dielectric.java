package reivajh06.materials;

import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;

public class Dielectric implements Material {

	private double refractionIndex;

	public Dielectric(double refractionIndex) {
		this.refractionIndex = refractionIndex;
	}

	private boolean refract(Vector3D v, Vector3D n, double niOverNt, Vector3D refracted) {
		Vector3D uv = Vector3D.unitVector(v);

		double dt = Vector3D.dot(uv, n);
		double discriminant = 1.0 - niOverNt * niOverNt * (1.0 - dt * dt);

		if(discriminant > 0) {
			Vector3D newRefracted = Vector3D.subtract(
					Vector3D.scalarProduct(Vector3D.subtract(uv, Vector3D.scalarProduct(n, dt)), niOverNt),
					Vector3D.scalarProduct(n, Math.sqrt(discriminant))
			);

			refracted
					.x(newRefracted.x())
					.y(newRefracted.y())
					.z(newRefracted.z());

			return true;
		}

		return false;
	}

	@Override
	public boolean scatter(Ray rIn, Hitable.HitRecord record, Vector3D attenuation, Ray scattered) {
		Vector3D outwardNormal = new Vector3D();
		Vector3D reflected = reflect(rIn.direction(), record.normal);

		double niOverNt;

		attenuation
				.x(1.0)
				.y(1.0)
				.z(1.0);

		Vector3D refracted = new Vector3D();

		if(Vector3D.dot(rIn.direction(), record.normal) > 0) {
			outwardNormal
					.x(-record.normal.x())
					.y(-record.normal.y())
					.z(-record.normal.z());

			niOverNt = refractionIndex;

		} else {
			outwardNormal
					.x(record.normal.x())
					.y(record.normal.y())
					.z(record.normal.z());

			niOverNt = 1.0 / refractionIndex;

		}

		if(refract(rIn.direction(), outwardNormal, niOverNt, refracted)) {
			Ray newScattered = new Ray(record.p, refracted);

			scattered
					.origin(newScattered.origin())
					.direction(newScattered.direction());

		} else {
			Ray newScattered = new Ray(record.p, reflected);

			scattered
					.origin(newScattered.origin())
					.direction(newScattered.direction());

			return false;
		}

		return true;
	}
}
