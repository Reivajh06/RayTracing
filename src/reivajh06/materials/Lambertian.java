package reivajh06.materials;

import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.hitables.Hitable;

import static reivajh06.Main.randomInUnitSphere;

public class Lambertian implements Material {

	private final Vector3D albedo;

	public Lambertian(Vector3D albedo) {
		this.albedo = albedo;
	}

	@Override
	public boolean scatter(Ray rIn, Hitable.HitRecord record, Vector3D attenuation, Ray scattered) {
		Vector3D target = Vector3D.add(record.p, Vector3D.add(record.normal, randomInUnitSphere()));
		scattered.origin(record.p).direction(Vector3D.subtract(target, record.p));

		attenuation
				.r(albedo.r())
				.g(albedo.g())
				.b(albedo.b());

		return true;
	}
}
