package reivajh06.materials;

import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;

public class Metal implements Material {

	private Vector3D albedo;

	public Metal(Vector3D albedo) {
		this.albedo = albedo;
	}

	private Vector3D reflect(Vector3D v, Vector3D n) {
		return Vector3D.subtract(
				v,
				Vector3D.scalarProduct(n, Vector3D.dot(v, n) * 2)
		);
	}

	@Override
	public boolean scatter(Ray rIn, Hitable.HitRecord record, Vector3D attenuation, Ray scattered) {
		Vector3D reflected = reflect(rIn.direction(), record.normal);
		scattered
				.origin(record.p)
				.direction(reflected);

		attenuation
				.r(albedo.r())
				.g(albedo.g())
				.b(albedo.b());

		return Vector3D.dot(scattered.direction(), record.normal) > 0;
	}
}
