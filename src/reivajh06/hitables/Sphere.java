package reivajh06.hitables;

import reivajh06.Ray;
import reivajh06.Vector3D;
import reivajh06.materials.Material;

public class Sphere implements Hitable {

	private Vector3D center;
	private double radius;
	private Material material;

	public Sphere() {}

	public Sphere(Vector3D center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	public Sphere(Vector3D center, double radius, Material material) {
		this.center = center;
		this.radius = radius;
		this.material = material;
	}

	@Override
	public boolean hit(Ray r, double tMin, double tMax, HitRecord record) {
		Vector3D oc = Vector3D.subtract(r.origin(), center);

		double a = Vector3D.dot(r.direction(), r.direction());
		double b = Vector3D.dot(oc, r.direction());
		double c = Vector3D.dot(oc, oc) - radius * radius;
		double discriminant = b * b - a * c;

		if(discriminant > 0) {
			double sqrt = Math.sqrt(discriminant);
			double solution1 = (- b - sqrt) / a;
			double solution2 = (- b + sqrt) / a;

			return recordIfHit(r, solution1, tMin, tMax, record) || recordIfHit(r, solution2, tMin, tMax, record);
		}

		return false;
	}

	private boolean recordIfHit(Ray r, double temp, double tMin, double tMax, HitRecord record) {
		if(temp < tMax && temp > tMin) {
			record.t = temp;
			record.p = r.pointAtParameter(record.t);
			record.normal = Vector3D.divide(Vector3D.subtract(record.p, center), radius);
			record.material = material;

			return true;
		}

		return false;
	}
}
