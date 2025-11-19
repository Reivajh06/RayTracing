package reivajh06;

import reivajh06.hitables.Hitable;

public interface RayTracer {

	void run(Scene scene, Renderer renderer, ImageConfig config);

	default Vector3D color(Ray r, Hitable hitable, int depth) {
		Hitable.HitRecord record = new Hitable.HitRecord();

		if(hitable.hit(r, 0.001, Double.MAX_VALUE, record)) {
			Ray scattered = new Ray();
			Vector3D attenuation = new Vector3D();

			if(depth < 50 && record.material.scatter(r, record, attenuation, scattered)) {
				return Vector3D.multiply(attenuation, color(scattered, hitable, depth + 1));
			}
		}

		Vector3D unitDirection = Vector3D.unitVector(r.direction());

		double t = 0.5 * (unitDirection.y() + 1.0);

		return Vector3D.add(
				Vector3D.scalarProduct(new Vector3D(1.0, 1.0, 1.0), (1.0 - t)),
				Vector3D.scalarProduct(new Vector3D(0.5, 0.7, 1.0), t)
		);
	}
}
