package reivajh06.hitables;

import reivajh06.Ray;
import reivajh06.Vector3D;

public class Sphere implements Hitable {

	private Vector3D center;
	private double radius;

	public Sphere() {}

	public Sphere(Vector3D center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	@Override
	public boolean hit(Ray r, double tMin, double tMax, HitRecord record) {
		Vector3D oc = Vector3D.subtract(r.origin(), center);

		double a = Vector3D.dot(r.direction(), r.direction());
		double b = 2.0 * Vector3D.dot(oc, r.direction());
		double c = Vector3D.dot(oc, oc) - radius * radius;
		double discriminant = b * b - 4 * a * c;

		if(discriminant > 0) {
			double solution1 = (- b - Math.sqrt(b * b - a * c)) / a;
			double solution2 = (-b + Math.sqrt(b * b - a * c)) / a;

			return recordIfHit(r, solution1, tMin, tMax, record) | recordIfHit(r, solution2, tMin, tMax, record);
		}

		return false;
	}

	private boolean recordIfHit(Ray r, double temp, double tMin, double tMax, HitRecord record) {
		if(temp < tMax && temp > tMin) {
			record.t = temp;
			record.p = r.pointAtParameter(record.t);
			record.normal = Vector3D.divide(Vector3D.subtract(record.p, center), radius);

			return true;
		}

		return false;
	}
}
