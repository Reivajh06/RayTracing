package reivajh06;

import static reivajh06.RayTracer.RANDOM;

public class Camera {

	private static Vector3D randomInUnitDisk() {
		Vector3D p;

		do {
			p = Vector3D.scalarProduct(new Vector3D(RANDOM.nextDouble(0, 1), RANDOM.nextDouble(0, 1), 0), 2);
			p.subtract(new Vector3D(1, 1, 0));

		} while(Vector3D.dot(p, p) >= 1.0);

		return p;
	}

	private final Vector3D origin;
	private final Vector3D lowerLeftCorner;
	private final Vector3D horizontal;
	private final Vector3D vertical;
	private final Vector3D u;
	private final Vector3D v;
	private final Vector3D w;
	private final double lensRadius;

	private Camera(Vector3D lookfrom, Vector3D lookat, Vector3D vup, double vfov, double aspect, double aperture, double focusDistance) {
		lensRadius = aperture / 2;

		double theta = vfov * Math.PI / 180;
		double halfHeight = Math.tan(theta / 2);
		double halfWidth = aspect * halfHeight;

		origin = lookfrom;

		w = Vector3D.unitVector(Vector3D.subtract(lookfrom, lookat));
		u = Vector3D.unitVector(Vector3D.cross(vup, w));
		v = Vector3D.cross(w, u);

		lowerLeftCorner = Vector3D.subtract(origin, Vector3D.scalarProduct(u, halfWidth * focusDistance));
		lowerLeftCorner.subtract(Vector3D.scalarProduct(v, halfHeight * focusDistance));
		lowerLeftCorner.subtract(Vector3D.scalarProduct(w, focusDistance));

		horizontal = Vector3D.scalarProduct(u, 2 * halfWidth * focusDistance);
		vertical = Vector3D.scalarProduct(v, 2 * halfHeight * focusDistance);
	}

	public Ray getRay(double s, double t) {
		Vector3D rd = Vector3D.scalarProduct(randomInUnitDisk(), lensRadius);
		Vector3D offset = Vector3D.add(Vector3D.scalarProduct(u, rd.x()), Vector3D.scalarProduct(v, rd.y()));

		return new Ray(
				Vector3D.add(origin, offset),
				Vector3D.add(
						Vector3D.add(lowerLeftCorner, Vector3D.scalarProduct(horizontal, s)),
						Vector3D.subtract(Vector3D.scalarProduct(vertical, t), Vector3D.add(origin, offset))
				));
	}

	public static class Builder {

		private Vector3D lookfrom = new Vector3D(13, 2, 10);
		private Vector3D lookat = new Vector3D();
		private Vector3D vup = new Vector3D(0, -1, 0);
		private double vfov = 20;
		private double aspect = 16.0 / 9.0;
		private double aperture = 0.1;
		private double focusDistance = Vector3D.subtract(lookfrom, lookat).length();

		public Camera build() {
			return new Camera(lookfrom, lookat, vup, vfov, aspect, aperture, focusDistance);
		}

		public Builder vup(Vector3D vup) {
			this.vup = vup;
			return this;
		}

		public Builder vfov(double vfov) {
			this.vfov = vfov;
			return this;
		}

		public Builder lookat(Vector3D lookat) {
			this.lookat = lookat;
			return this;
		}

		public Builder lookfrom(Vector3D lookfrom) {
			this.lookfrom = lookfrom;
			return this;
		}

		public Builder aperture(double aperture) {
			this.aperture = aperture;
			return this;
		}

		public Builder aspect(double w, double h) {
			return aspect(w / h);
		}

		public Builder aspect(double aspect) {
			this.aspect = aspect;
			return this;
		}

		public Builder focusDistance(double focusDistance) {
			this.focusDistance = focusDistance;
			return this;
		}
	}
}
