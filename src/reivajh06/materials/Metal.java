package reivajh06.materials;

import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;

import static reivajh06.Main.randomInUnitSphere;

public class Metal implements Material {

	private Vector3D albedo;
	private double fuzz;

	public Metal(Vector3D albedo, double fuzz) {
		this.albedo = albedo;

		this.fuzz = fuzz < 1 ? fuzz : 1;
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
				.direction(Vector3D.add(reflected, Vector3D.scalarProduct(randomInUnitSphere(), fuzz)));

		attenuation
				.r(albedo.r())
				.g(albedo.g())
				.b(albedo.b());

		return Vector3D.dot(scattered.direction(), record.normal) > 0;
	}
}
